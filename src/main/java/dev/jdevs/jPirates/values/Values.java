package dev.jdevs.jPirates.values;

import com.google.common.collect.ImmutableList;
import dev.jdevs.jPirates.JPirates;
import dev.jdevs.jPirates.utils.actions.Action;
import dev.jdevs.jPirates.utils.actions.ActionType;
import dev.jdevs.jPirates.utils.colors.Colorizer;
import dev.jdevs.jPirates.utils.colors.impl.LegacyColorizer;
import dev.jdevs.jPirates.utils.colors.impl.MiniMessageColorizer;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Values {
    private final JPirates plugin;
    private final Pattern action_pattern = Pattern.compile("\\[(\\w+)] ?(.*)");
    private boolean whitelistEnabled, miniMessage;
    private List<Action> noPermission;
    private List<String> nicknames;
    private String whitelistMessage;
    private Colorizer colorizer;

    public Values(JPirates plugin) {
        this.plugin = plugin;
        try {
            Class.forName("net.kyori.adventure.text.minimessage.MiniMessage");
            miniMessage = true;
        } catch (Exception e) {
            miniMessage = false;
        }
    }

    public void setup() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection settings = config.getConfigurationSection("settings");
        setupSettings(settings);
        ConfigurationSection messages = config.getConfigurationSection("messages");
        setupMessages(messages);
        nicknames = config.getStringList("nicknames");
    }

    @SuppressWarnings("all")
    private void setupSettings(ConfigurationSection settings) {
        ConfigurationSection whitelist = null;
        if (settings != null) {
            whitelist = settings.getConfigurationSection("whitelist");
            String translate = settings.getString("translate").toLowerCase();
            if (miniMessage && translate.equals("minimessage")) {
                colorizer = new MiniMessageColorizer();
            } else {
                colorizer = new LegacyColorizer();
            }
        }
        if (whitelist != null) {
            whitelistEnabled = whitelist.getBoolean("enabled");
        }
    }

    public void setNicknames(List<String> nicknames) {
        FileConfiguration config = plugin.getConfig();
        config.set("nicknames", nicknames);
        save(config);
        setup();
    }

    public void setStatus(boolean status) {
        FileConfiguration config = plugin.getConfig();
        config.set("settings.whitelist.enabled", status);
        save(config);
        setup();
    }

    private void setupMessages(ConfigurationSection messages) {
        if (messages != null) {
            whitelistMessage = messages.getString("whitelist-kick");
            noPermission = getActionList(messages.getStringList("permission-no"));
        }
    }

    private Action fromString(String str) {
        Matcher matcher = action_pattern.matcher(str);
        if (!matcher.matches()) {
            return new Action(ActionType.MESSAGE, str);
        }
        ActionType type;
        try {
            type = ActionType.valueOf(matcher.group(1).toUpperCase());
        } catch (IllegalArgumentException e) {
            type = ActionType.MESSAGE;
            return new Action(type, str);
        }
        return new Action(type, matcher.group(2).trim());
    }

    public List<Action> getActionList(List<String> actionStrings) {
        ImmutableList.Builder<Action> actionListBuilder = ImmutableList.builder();
        for (String actionString : actionStrings) {
            actionListBuilder.add(fromString(actionString));
        }
        return actionListBuilder.build();
    }

    private void save(FileConfiguration config) {
        try {
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (Exception e) {
            plugin.getLogger().warning("Error: " + e);
        }
    }
}
