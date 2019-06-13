package me.etudes.hcf.api.faction;

import me.etudes.hcf.api.faction.dtr.DtrState;
import me.etudes.hcf.main.HCF;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Faction {
    //TODO: store dtr in yml because facs aren't always max stupid

    private String name;

    private double dtr;

    private DtrState dtrState;

    private int size;
    private int balance;

    private HCF plugin;

    private UUID leaderId;

    public Faction(String name, Player leader, HCF plugin) {
        this(name, 1, 0, FactionUtils.calculateMaxDtr(1), DtrState.FULL, leader.getUniqueId(), plugin, true);
    }

    public Faction(String name, int size, int balance, double dtr, DtrState dtrState, UUID leaderId, HCF plugin, boolean addToFile) {
        this.name = name;
        this.size = size;
        this.balance = balance;
        this.dtr = dtr;
        this.dtrState = dtrState;
        this.plugin = plugin;
        this.leaderId = leaderId;

        if(addToFile) plugin.getFactionConfig().addFaction(this);
    }

    public void addPlayer(Player player) {
        plugin.getFactionConfig().addPlayer(player, this);
    }

    public int getOnline() {
        List<String> all = plugin.getFactionConfig().getAll(name);
        int online = 0;
        for(String uuidStr : all) {
            UUID uuid = UUID.fromString(uuidStr);
            try {
                Player player = plugin.getServer().getPlayer(uuid);
                if(player.isOnline()) online++;
            } catch(Exception ignored) {}
        }
        return online;
    }

    public List<String> getColeaders() {
        return plugin.getFactionConfig().getColeaders(this);
    }

    public List<String> getCaptains() {
        return plugin.getFactionConfig().getCaptains(this);
    }

    public List<String> getMembers() {
        return plugin.getFactionConfig().getMembers(this);
    }

    public List<String> getAll() {
        return plugin.getFactionConfig().getAll(this);
    }

    public String getName() {
        return name;
    }

    public double getDtr() {
        return dtr;
    }

    public DtrState getDtrState() {
        return dtrState;
    }

    public int getSize() {
        return size;
    }

    public int getBalance() {
        return balance;
    }

    public UUID getLeaderId() {
        return leaderId;
    }

    public void setDtr(double dtr) {
        this.dtr = dtr;
        plugin.getFactionConfig().setDtr(name, dtr);
    }

    public void setDtrState(DtrState dtrState) {
        this.dtrState = dtrState;
        plugin.getFactionConfig().setDtrState(name, dtrState);
    }

}
