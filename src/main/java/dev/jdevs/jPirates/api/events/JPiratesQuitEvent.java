package dev.jdevs.jPirates.api.events;

import com.github.games647.fastlogin.core.PremiumStatus;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class JPiratesQuitEvent extends Event {
    private final static HandlerList handlers = new HandlerList();
    private final PremiumStatus premiumStatus;
    private final boolean inWhitelist;
    private final String username;
    private final Player player;

    public JPiratesQuitEvent(Player player, String username, PremiumStatus premiumStatus, boolean inWhitelist) {
        this.player = player;
        this.username = username;
        this.premiumStatus = premiumStatus;
        this.inWhitelist = inWhitelist;
    }

    @SuppressWarnings("all")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}