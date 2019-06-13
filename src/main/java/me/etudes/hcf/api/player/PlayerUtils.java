package me.etudes.hcf.api.player;

import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.api.faction.FactionUtils;
import me.etudes.hcf.main.HCF;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
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

}
