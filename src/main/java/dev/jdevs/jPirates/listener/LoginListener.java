package dev.jdevs.jPirates.listener;

import com.github.games647.fastlogin.bukkit.event.BukkitFastLoginEvent;
import com.github.games647.fastlogin.core.PremiumStatus;
import dev.jdevs.jPirates.JPirates;
import dev.jdevs.jPirates.utils.Utils;
import dev.jdevs.jPirates.values.Values;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class LoginListener implements Listener {
    private final BukkitScheduler scheduler;
    private final JPirates plugin;
    private final Values values;
    private final Utils utils;

    public LoginListener(JPirates plugin) {
        this.plugin = plugin;
        utils = plugin.getUtils();
        values = plugin.getValues();
        scheduler = plugin.getServer().getScheduler();
    }

    @EventHandler
    public void onBukkitFastLoginPreLogin(BukkitFastLoginEvent e) {
        String username = e.getUsername();
        PremiumStatus status = e.getStatus();
        if (!values.isWhitelistEnabled() || status == PremiumStatus.PREMIUM || values.getNicknames().contains(username)) {
            utils.getStatus().put(username, status);
            utils.callJPiratesJoinEvent(username, status, true);
            return;
        }
        utils.callJPiratesJoinEvent(username, status, false);
        scheduler.runTask(plugin, () -> utils.kick(username));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (utils.getStatus().containsKey(e.getPlayer().getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (utils.getStatus().containsKey(e.getPlayer().getName())) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String username = player.getName();
        PremiumStatus status = utils.getStatus().remove(username);
        utils.callJPiratesQuitEvent(player, username, status, status != null);
    }
}
