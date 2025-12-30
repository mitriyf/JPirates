package dev.jdevs.jPirates.events;

import com.github.games647.fastlogin.bukkit.event.BukkitFastLoginEvent;
import com.github.games647.fastlogin.core.PremiumStatus;
import dev.jdevs.jPirates.JPirates;
import dev.jdevs.jPirates.utils.Utils;
import dev.jdevs.jPirates.values.Values;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginEvents implements Listener {
    private final JPirates plugin;
    private final Values values;
    private final Utils utils;

    public LoginEvents(JPirates plugin) {
        this.plugin = plugin;
        values = plugin.getValues();
        utils = plugin.getUtils();
    }

    @EventHandler
    public void onBukkitFastLoginPreLogin(BukkitFastLoginEvent e) {
        String name = e.getUsername();
        PremiumStatus status = e.getStatus();
        if (!values.isWhitelistEnabled() || e.getStatus() == PremiumStatus.PREMIUM || values.getNicknames().contains(name)) {
            utils.getStatus().put(name, status);
            return;
        }
        plugin.getServer().getScheduler().runTask(plugin, () -> utils.kick(name));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        utils.getStatus().remove(e.getPlayer().getName());
    }
}
