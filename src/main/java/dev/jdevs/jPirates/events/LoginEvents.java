package dev.jdevs.jPirates.events;

import com.github.games647.fastlogin.bukkit.event.BukkitFastLoginEvent;
import com.github.games647.fastlogin.core.PremiumStatus;
import dev.jdevs.jPirates.JPirates;
import dev.jdevs.jPirates.api.events.JPiratesJoinEvent;
import dev.jdevs.jPirates.api.events.JPiratesQuitEvent;
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

public class LoginEvents implements Listener {
    private final BukkitScheduler scheduler;
    private final JPirates plugin;
    private final Values values;
    private final Utils utils;

    public LoginEvents(JPirates plugin) {
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
            callJPiratesJoinEvent(username, status, true);
            return;
        }
        callJPiratesJoinEvent(username, status, false);
        plugin.getServer().getScheduler().runTask(plugin, () -> utils.kick(username));
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
        callJPiratesQuitEvent(player, username, status, status != null);
    }

    private void callJPiratesJoinEvent(String username, PremiumStatus premiumStatus, boolean inWhitelist) {
        scheduler.runTask(plugin, () -> plugin.getServer().getPluginManager().callEvent(new JPiratesJoinEvent(username, premiumStatus, inWhitelist)));
    }

    private void callJPiratesQuitEvent(Player player, String username, PremiumStatus premiumStatus, boolean inWhitelist) {
        scheduler.runTask(plugin, () -> plugin.getServer().getPluginManager().callEvent(new JPiratesQuitEvent(player, username, premiumStatus, inWhitelist)));
    }
}
