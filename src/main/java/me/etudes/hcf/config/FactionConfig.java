package me.etudes.hcf.config;

import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.api.faction.dtr.DtrState;
import me.etudes.hcf.api.player.PlayerConfig;
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
        plugin.getPlayerConfig().setFaction(plugin.getServer().getPlayer(leaderId), faction);

        // init other sections
        factionSection.set("coleaders", new ArrayList<String>());
        factionSection.set("captains", new ArrayList<String>());
        factionSection.set("members", new ArrayList<String>());
        factionSection.set("balance", faction.getBalance());
        factionSection.set("dtr", faction.getDtr());
        factionSection.set("dtr-state", faction.getDtrState().toInt());
        factionSection.set("regen-time", faction.getRegenTime());

        leader.sendMessage(ChatColor.GREEN + "Faction " + name + " has been created");

        saveConfig();
    }

    public void removeFaction(Faction faction) {
        PlayerConfig playerConfig = plugin.getPlayerConfig();
        for(String uuid : faction.getAll()) {
            ConfigurationSection section = playerConfig.getConfig().getConfigurationSection(uuid);
           section.set("faction", null);
        }
        playerConfig.saveConfig();

        String name = faction.getName();
        ConfigurationSection section = config.getConfigurationSection("factions");
        section.set(name, null);
        saveConfig();
    }

    public void addPlayer(Player player, Faction faction) {
        UUID uuid = player.getUniqueId();
        String name = faction.getName();
        config.set(name, uuid.toString());
        saveConfig();
    }

    public UUID getLeaderId(String name) {
        return UUID.fromString(config.getString("factions." + name + ".leader"));
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

    public double getDtr(String name) {
        return config.getDouble("factions." + name + ".dtr");
    }

    public DtrState getDtrState(String name) {
        int state = config.getInt("factions." + name + ".dtr-state");
        return DtrState.fromInt(state);
    }

    public long getRegenTime(String name) {
        return config.getLong("factions." + name + ".regen-time");
    }

    public void setDtr(String name, double dtr) {
        dtr = (double)Math.round(dtr*100)/100;
        config.set("factions." + name + ".dtr", dtr);
        saveConfig();
    }

    public void setDtrState(String name, DtrState state) {
        config.set("factions." + name + ".dtr-state", state.toInt());
        saveConfig();
    }

    public void setRegenTime(String name, long time) {
        config.set("factions." + name + ".regen-time", time);
        saveConfig();
    }

    public List<String> getAll(String name) {
        List<String> coleaders = getColeaders(name);
        List<String> captains = getCaptains(name);
        List<String> members = getMembers(name);
        coleaders.addAll(captains);
        coleaders.addAll(members);
        coleaders.add(getLeaderId(name).toString());
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
