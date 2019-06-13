package me.etudes.hcf.api.player;

import me.etudes.hcf.api.deathban.DeathbanUtils;
import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.config.Config;
import me.etudes.hcf.main.HCF;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerConfig extends Config {

    private static final String FILE_NAME = "players.yml";

    public PlayerConfig(HCF plugin) {
        super(plugin, FILE_NAME);
        setup();
    }

    public void addPlayer(UUID uuid) {
        ConfigurationSection section = config.createSection(uuid.toString());
        section.set("name", plugin.getServer().getPlayer(uuid).getName());
        section.set("faction", null);
        section.set("deathban", null);
        saveConfig();
    }

    public void addPlayer(Player player) {
        addPlayer(player.getUniqueId());
    }

    public void setFaction(Player player, Faction faction) {
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        section.set("faction", faction.getName());
        saveConfig();
    }

    public String getFactionName(Player player) {
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        return section.getString("faction");
    }

    public boolean hasFaction(Player player) {
        ConfigurationSection section = config.getConfigurationSection(player.getUniqueId().toString());
        return section.getString("faction") != null;
    }

    public String getName(String uuid) {
        ConfigurationSection section = config.getConfigurationSection(uuid);
        return section.getString("name");
    }

    public boolean isDeathBanned(Player player) {
        return isDeathBanned(player.getUniqueId().toString());
    }

    public boolean isDeathBanned(String uuid) {
        if(getDeathBanTimeLeft(uuid) <= 0) {
            removeDeathBan(uuid);
            return false;
        }
        ConfigurationSection section = config.getConfigurationSection(uuid);
        return section.get("deathban") != null;
    }

    public long getDeathBanTimeLeft(Player player) {
        return getDeathBanTimeLeft(player.getUniqueId().toString());
    }

    public long getDeathBanTimeLeft(String uuid) {
        ConfigurationSection section = config.getConfigurationSection(uuid);
        long endTime = section.getLong("deathban");
        return endTime - System.currentTimeMillis();

    }

    public void addDeathBan(Player player) {
        String uuid = player.getUniqueId().toString();
        ConfigurationSection section = config.getConfigurationSection(uuid);
        section.set("deathban", System.currentTimeMillis() + DeathbanUtils.DEATHBAN_MS);
        saveConfig();
    }

    public void removeDeathBan(Player player) {
        removeDeathBan(player.getUniqueId().toString());
    }

    public void removeDeathBan(String uuid) {
        ConfigurationSection section = config.getConfigurationSection(uuid);
        section.set("deathban", null);
        saveConfig();
    }

}
