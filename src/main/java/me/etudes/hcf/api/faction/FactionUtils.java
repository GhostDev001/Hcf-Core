package me.etudes.hcf.api.faction;

import me.etudes.hcf.api.player.PlayerConfig;
import me.etudes.hcf.config.FactionConfig;
import me.etudes.hcf.main.HCF;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FactionUtils {

    private FactionUtils() {
    }

    public static Faction getFaction(String name) {
        HCF plugin = HCF.getPlugin(HCF.class);
        FactionConfig factionConfig = plugin.getFactionConfig();
        List<String> all = factionConfig.getAll(name);
        int size = all.size();
        int balance = factionConfig.getBalance(name);
        double dtr = calculateDtr(size);
        UUID leaderId = factionConfig.getLeaderId(name);
        return new Faction(name, size, balance, dtr, leaderId, plugin, false);
    }

    public static Faction getFaction(Player player) {
        PlayerConfig playerConfig = HCF.getPlugin(HCF.class).getPlayerConfig();
        String name = playerConfig.getFactionName(player);
        return getFaction(name);
    }

    public static String formatPlayerList(List<String> players) {
        PlayerConfig playerConfig = HCF.getPlugin(HCF.class).getPlayerConfig();
        StringBuilder output = new StringBuilder();
        HCF plugin = HCF.getPlugin(HCF.class);
        List<String> onlinePlayers = new ArrayList<String>();
        List<String> offlinePlayers = new ArrayList<String>();

        for(String playerId : players) {
            try {
                Player player = plugin.getServer().getPlayer(UUID.fromString(playerId));
                onlinePlayers.add(ChatColor.GREEN + player.getName());
            } catch(NullPointerException e) {
                offlinePlayers.add(ChatColor.GRAY + playerConfig.getName(playerId));
            }
        }

        Collections.sort(onlinePlayers);
        Collections.sort(offlinePlayers);

        for(String str : onlinePlayers) {
            output.append(", ");
            output.append(ChatColor.WHITE.toString());
            output.append(str);
        }
        for(String str : offlinePlayers) {
            output.append(", ");
            output.append(ChatColor.WHITE.toString());
            output.append(str);
        }

        if(output.length() != 0) {
            output.deleteCharAt(0); // extra comma and space at beginning
            output.deleteCharAt(0);
        }
        return output.toString();
    }

    public static double calculateDtr(int size) {
        double output;
        switch (size) {
            case 1:
                output =  1.01;
                break;
            case 2:
                output =  2.2;
                break;
            case 3:
                output =  3.5;
                break;
            case 4:
                output =  4.5;
                break;
            case 5:
                output =  5.5;
                break;
            default:
                output =  6.5;
                break;
        }
        return output;
    }

}
