package me.etudes.hcf.api.player;

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

}
