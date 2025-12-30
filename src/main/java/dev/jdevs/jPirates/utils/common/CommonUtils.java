package dev.jdevs.jPirates.utils.common;

import dev.jdevs.jPirates.JPirates;
import dev.jdevs.jPirates.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class CommonUtils {
    private final BukkitScheduler scheduler;
    private final JPirates plugin;
    private final Utils utils;

    public CommonUtils(Utils utils, JPirates plugin) {
        this.utils = utils;
        this.plugin = plugin;
        scheduler = plugin.getServer().getScheduler();
    }

    public void broadcast(String message) {
        String text = utils.formatString(message);
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.sendMessage(text);
        }
    }

    public void dispatchConsole(String cmd) {
        scheduler.runTaskLater(plugin, () -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd), 0);
    }
}
