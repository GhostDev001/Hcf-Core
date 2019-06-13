package me.etudes.hcf.api.player;

import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.api.faction.FactionUtils;
import me.etudes.hcf.main.HCF;
import net.minecraft.util.org.apache.commons.io.IOUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;
import java.util.UUID;

public class PlayerUtils {

    private PlayerUtils() {}

    public static boolean isLeader(Player player) {
        Faction faction = FactionUtils.getFaction(player);
        UUID playerId = player.getUniqueId();
        UUID leaderId = faction.getLeaderId();
        return playerId.equals(leaderId);
    }

    public static String getName(UUID uuid) {
        String strId = uuid.toString();
        PlayerConfig playerConfig = HCF.getPlugin(HCF.class).getPlayerConfig();
        return playerConfig.getName(strId);
    }

    public static String getUuid(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/"+name;
        try {
            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.equals("")) return "invalid name";
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("id").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }

}
