package dev.jdevs.jPirates.command;

import dev.jdevs.jPirates.JPirates;
import dev.jdevs.jPirates.utils.Utils;
import dev.jdevs.jPirates.values.Values;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class JPiratesCommand implements CommandExecutor {
    private final Values values;
    private final Utils utils;

    public JPiratesCommand(JPirates plugin) {
        utils = plugin.getUtils();
        values = plugin.getValues();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("jpirates.use")) {
            utils.sendMessage(sender, values.getNoPermission());
            return false;
        } else if (args.length == 0) {
            sendHelp(sender);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "reload": {
                values.setup();
                sender.sendMessage("§aSuccessfully!");
                break;
            }
            case "add": {
                if (args.length == 2) {
                    addNickname(sender, args[1]);
                    break;
                }
                sendHelp(sender);
                break;
            }
            case "remove": {
                if (args.length == 2) {
                    removeNickname(sender, args[1]);
                    break;
                }
                sendHelp(sender);
                break;
            }
            case "list": {
                sender.sendMessage("§fList:\n§a" + values.getNicknames());
                break;
            }
            case "enable": {
                values.setStatus(true);
                utils.checkWhiteList();
                sender.sendMessage("§aSuccessfully! Is §aenabled.");
                break;
            }
            case "disable": {
                values.setStatus(false);
                sender.sendMessage("§aSuccessfully! Is §cdisabled.");
                break;
            }
            default: {
                sendHelp(sender);
                break;
            }
        }
        return false;
    }

    private void addNickname(CommandSender sender, String nickname) {
        List<String> nicknames = values.getNicknames();
        if (nicknames.contains(nickname)) {
            sender.sendMessage("§cError!§f This player has already been added: §a" + nickname);
            return;
        }
        nicknames.add(nickname);
        values.setNicknames(nicknames);
        sender.sendMessage("§aSuccessfully!§f A nickname has been §aadded: " + nickname);
    }

    private void removeNickname(CommandSender sender, String nickname) {
        List<String> nicknames = values.getNicknames();
        if (!nicknames.contains(nickname)) {
            sender.sendMessage("§cError!§f This nickname is not found: §c" + nickname);
            return;
        }
        nicknames.remove(nickname);
        values.setNicknames(nicknames);
        if (values.isWhitelistEnabled()) {
            utils.kick(nickname);
        }
        sender.sendMessage("§aSuccessfully!§f A nickname has been §cremoved: §a" + nickname);
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§aPirates Help:\n");
        sender.sendMessage("§a/jpirates reload §f- Reload the plugin configuration.");
        sender.sendMessage("§a/jpirates add nickname §f- Add a nickname to the pirates' whitelist.");
        sender.sendMessage("§a/jpirates remove nickname §f- Remove the nickname from the pirates' whitelist.");
        sender.sendMessage("§a/jpirates list §f- Get a white list of pirates.");
        sender.sendMessage("§a/jpirates enable §f- Enable the list of pirates (default)");
        sender.sendMessage("§a/jpirates disable §f- Turn off the list of pirates.");
    }
}
