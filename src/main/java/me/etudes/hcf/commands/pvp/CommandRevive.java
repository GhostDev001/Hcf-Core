package me.etudes.hcf.commands.pvp;

import me.etudes.hcf.api.player.PlayerConfig;
import me.etudes.hcf.api.player.PlayerUtils;
import me.etudes.hcf.main.HCF;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class CommandRevive implements CommandExecutor {

    public static final String name = "revive";

    private HCF plugin;
    private PlayerConfig playerConfig;

    public CommandRevive(HCF plugin) {
        this.plugin = plugin;
        playerConfig = plugin.getPlayerConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //TODO: permission hcf.revive
        switch(args.length) {
            case 0:
                sender.sendMessage(ChatColor.RED + "Please specify a player to revive");
                break;
            case 1:
                String name = args[0];
                String uuid = PlayerUtils.getUuid(name).replaceFirst(
                        "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
                );
                System.out.println(uuid);
                if(playerConfig.isDeathBanned(uuid)) {
                    playerConfig.removeDeathBan(uuid);
                    sender.sendMessage(ChatColor.GREEN + name + " has been revived");
                } else {
                    sender.sendMessage(ChatColor.RED + name + " is not deathbanned");
                }
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Too many arguments!\nUsage: /revive <player>");
                break;
        }
        return true;
    }
}
