package me.etudes.hcf.commands.faction;

import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.api.faction.FactionUtils;
import me.etudes.hcf.config.FactionConfig;
import me.etudes.hcf.main.HCF;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandFaction implements CommandExecutor {

    public static final String name = "faction";

    private HCF plugin;

    public CommandFaction(HCF plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command");
            return true;
        }

        Player player = (Player) sender;

        switch(args.length) {
            case 0:
                // help msg
                break;
            case 1:
                if(args[0].equalsIgnoreCase("create")) {
                    player.sendMessage(ChatColor.RED + "Too few arguments!\nUsage: /f create <faction name>");
                } else if(args[0].equalsIgnoreCase("who") ||
                          args[0].equalsIgnoreCase("show") ||
                          args[0].equalsIgnoreCase("i") ||
                          args[0].equalsIgnoreCase("info")) {
                    FactionConfig factionConfig = plugin.getFactionConfig();
                    if(factionConfig.hasFaction(player)) {
                        displayTeamInfo(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "You are not in a faction");
                    }
                }
                break;
            case 2:
                if(args[0].equalsIgnoreCase("create")) {
                    new Faction(args[1], player, plugin);
                }
                break;
            default:
                if(args[0].equalsIgnoreCase("create")) {
                    player.sendMessage("Too many arguments!\nUsage: /f create <faction name>");
                }
                break;
        }

        return true;
    }

    private void displayTeamInfo(Player player) {
        Faction faction = FactionUtils.getFaction(player);
        String bar = ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + StringUtils.repeat(" ", 80);
        String onlineFraction = ChatColor.WHITE + "[" + faction.getOnline() + "/" + faction.getSize() + "]";
        StringBuilder leaderBuilder = new StringBuilder();
        Player leader = plugin.getServer().getPlayer(faction.getLeaderId());
        leaderBuilder.append(leader.isOnline() ? ChatColor.GREEN.toString() : ChatColor.GRAY.toString());
        leaderBuilder.append(leader.getName());
        String coleaderList = FactionUtils.formatPlayerList(faction.getColeaders());
        String captainList = FactionUtils.formatPlayerList(faction.getCaptains());
        String memberList = FactionUtils.formatPlayerList(faction.getMembers());
        player.sendMessage(bar);
        player.sendMessage(ChatColor.YELLOW + "Faction: " + ChatColor.WHITE + faction.getName());
        player.sendMessage(ChatColor.YELLOW + "Online players: " + onlineFraction);
        player.sendMessage(ChatColor.YELLOW + "Leader: " + leaderBuilder.toString());
        player.sendMessage(ChatColor.YELLOW + "Co-leaders: " + coleaderList);
        player.sendMessage(ChatColor.YELLOW + "Captains: " + captainList);
        player.sendMessage(ChatColor.YELLOW + "Members: " + memberList);
        player.sendMessage(ChatColor.YELLOW + "DTR: " + ChatColor.GREEN + faction.getDtr() + "◄");
        player.sendMessage(ChatColor.YELLOW + "Balance: " + ChatColor.WHITE + "$" + faction.getBalance());
        player.sendMessage(bar);
    }

}
