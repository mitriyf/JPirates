package dev.jdevs.jPirates;

import dev.jdevs.jPirates.command.JPiratesCommand;
import dev.jdevs.jPirates.listener.LoginListener;
import dev.jdevs.jPirates.utils.Utils;
import dev.jdevs.jPirates.values.Values;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@SuppressWarnings("all")
public final class JPirates extends JavaPlugin {
    @Setter
    private LoginListener loginListener;
    private Values values;
    private Utils utils;

    @Override
    public void onEnable() {
        getLogger().info("Support: https://vk.com/jdevs");
        values = new Values(this);
        utils = new Utils(this);
        values.setup();
        registerListener();
        getCommand("jpirates").setExecutor(new JPiratesCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("To get support for the plugin, write to the discussion from where you got it or write here: https://vk.com/jdevs");
    }

    public void registerListener() {
        PluginManager manager = getServer().getPluginManager();
        if (manager.isPluginEnabled("FastLogin")) {
            loginListener = new LoginListener(this);
            manager.registerEvents(loginListener, this);
        } else {
            getLogger().warning("The plugin did not detect the FastLogin plugin that is enabled. If you can enable FastLogin again, use /jpirates reload");
        }
    }
}
