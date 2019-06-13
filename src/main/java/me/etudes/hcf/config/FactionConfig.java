package me.etudes.hcf.config;

import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.main.HCF;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionConfig extends Config {

    private static final String FILE_NAME = "factions.yml";

    public FactionConfig(HCF plugin) {
        super(plugin, FILE_NAME);
        setup();
    }

    public void addFaction(Faction faction) {
        String name = faction.getName();
        Player leader = plugin.getServer().getPlayer(faction.getLeaderId());
        if(config.contains(name)) {
            leader.sendMessage(ChatColor.RED + "Faction " + name + " already exists");
            return;
        }

        UUID leaderId = faction.getLeaderId();
        ConfigurationSection factionSection = config.getConfigurationSection("factions").createSection(name);

        // set leader
        factionSection.set("leader", leaderId.toString());
        config.getConfigurationSection("faction-members").set(leaderId.toString(), name);

        // init other sections
        factionSection.set("coleaders", new ArrayList<String>());
        factionSection.set("captains", new ArrayList<String>());
        factionSection.set("members", new ArrayList<String>());
        factionSection.set("balance", 0);

        leader.sendMessage(ChatColor.GREEN + "Faction " + name + " has been created");

        saveConfig();
    }

    public void addPlayer(Player player, Faction faction) {
        UUID uuid = player.getUniqueId();
        String name = faction.getName();
        config.set(name, uuid.toString());
        saveConfig();
    }

    public boolean hasFaction(Player player) {
        String playerId = player.getUniqueId().toString();
        ConfigurationSection memberConfig = config.getConfigurationSection("faction-members");
        return memberConfig.contains(playerId);
    }

    public String getFactionName(Player player) {
        String playerId = player.getUniqueId().toString();
        ConfigurationSection memberConfig = config.getConfigurationSection("faction-members");
        return memberConfig.getString(playerId);
    }

    public Player getLeader(String name) {
        UUID uuid = UUID.fromString(config.getString("factions." + name + ".leader"));
        return plugin.getServer().getPlayer(uuid);
    }

    public List<String> getColeaders(String name) {
        return config.getStringList("factions." + name + ".coleaders");
    }

    public List<String> getCaptains(String name) {
        return config.getStringList("factions." + name + ".captains");
    }

    public List<String> getMembers(String name) {
        return config.getStringList("factions." + name + ".members");
    }

    public int getBalance(String name) {
        return config.getInt("factions." + name + ".balance");
    }

    public List<String> getAll(String name) {
        List<String> coleaders = getColeaders(name);
        List<String> captains = getCaptains(name);
        List<String> members = getMembers(name);
        coleaders.addAll(captains);
        coleaders.addAll(members);
        coleaders.add(getLeader(name).getUniqueId().toString());
        return coleaders;
    }

    public List<String> getColeaders(Faction faction) {
        return getColeaders(faction.getName());
    }

    public List<String> getCaptains(Faction faction) {
        return getCaptains(faction.getName());
    }

    public List<String> getMembers(Faction faction) {
        return getMembers(faction.getName());
    }

    public List<String> getAll(Faction faction) {
        return getAll(faction.getName());
    }

    public int getBalance(Faction faction) {
        return getBalance(faction.getName());
    }

}
