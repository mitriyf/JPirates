package dev.jdevs.jPirates.utils;

import com.github.games647.fastlogin.core.PremiumStatus;
import dev.jdevs.jPirates.JPirates;
import dev.jdevs.jPirates.utils.actions.Action;
import dev.jdevs.jPirates.utils.actions.ActionType;
import dev.jdevs.jPirates.utils.common.CommonUtils;
import dev.jdevs.jPirates.values.Values;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Getter
public class Utils {
    private final Values values;
    private final Logger logger;
    private final JPirates plugin;
    private final CountDownLatch latch;
    private final CommonUtils commonUtils;
    private final BukkitScheduler scheduler;
    private final Map<String, PremiumStatus> status = new HashMap<>();

    public Utils(JPirates plugin) {
        this.plugin = plugin;
        values = plugin.getValues();
        logger = plugin.getLogger();
        latch = new CountDownLatch(1);
        scheduler = plugin.getServer().getScheduler();
        commonUtils = new CommonUtils(this, plugin);
    }

    public void sendMessage(CommandSender sender, List<Action> actions) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            for (Action action : actions) {
                sendSender(sender, action);
            }
        });
    }

    private void sendSender(CommandSender sender, Action action) {
        ActionType type = action.getType();
        String context = action.getContext();
        switch (type) {
            case CONSOLE:
                commonUtils.dispatchConsole(context);
                break;
            case BROADCAST:
                commonUtils.broadcast(context);
                break;
            case LOG:
                log(context);
                break;
            case DELAY:
                try {
                    if (latch.await(formatInt(context) * 50L, TimeUnit.MILLISECONDS)) {
                        break;
                    }
                } catch (Exception ignored) {
                }
                break;
            default:
                sendMessage(sender, context);
                break;
        }
    }

    @SuppressWarnings("all")
    public void kick(String name) {
        Player player = plugin.getServer().getPlayer(name);
        if (player != null && player.isOnline() && checkNotPremium(name)) {
            player.kickPlayer(formatString(values.getWhitelistMessage()));
        }
    }

    public void checkWhiteList() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            String name = player.getName();
            if (checkNotPremium(name) && !values.getNicknames().contains(name)) {
                kick(name);
            }
        }
    }

    public boolean checkNotPremium(String name) {
        return status.get(name) != PremiumStatus.PREMIUM;
    }

    private void sendMessage(CommandSender sender, String text) {
        sender.sendMessage(formatString(text));
    }

    public String formatString(String s) {
        return values.getColorizer().colorize(s);
    }

    public int formatInt(String text) {
        return Integer.parseInt(text);
    }

    private void log(String log) {
        logger.info(log);
    }
}
