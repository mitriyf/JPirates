package dev.jdevs.jPirates;

import dev.jdevs.jPirates.command.JPiratesCommand;
import dev.jdevs.jPirates.events.LoginEvents;
import dev.jdevs.jPirates.utils.Utils;
import dev.jdevs.jPirates.values.Values;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@SuppressWarnings("all")
public final class JPirates extends JavaPlugin {
    private Values values;
    private Utils utils;
    private int version;

    @Override
    public void onEnable() {
        getLogger().info("Support: https://vk.com/jdevs");
        values = new Values(this);
        utils = new Utils(this);
        values.setup();
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new LoginEvents(this), this);
        getCommand("jpirates").setExecutor(new JPiratesCommand(this));
    }

    @Override
    public void onDisable() {
    }
}
