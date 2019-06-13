package me.etudes.hcf.main;

import me.etudes.hcf.api.faction.FactionUtils;
import me.etudes.hcf.api.player.PlayerConfig;
import me.etudes.hcf.commands.faction.CommandFaction;
import me.etudes.hcf.config.FactionConfig;
import me.etudes.hcf.events.PlayerEvents;
import me.etudes.hcf.events.ServerEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class HCF extends JavaPlugin {

    private FactionConfig factionConfig;
    private PlayerConfig playerConfig;

    @Override
    public void onEnable() {
        factionConfig = new FactionConfig(this);
        playerConfig = new PlayerConfig(this);

        // Events
        this.getServer().getPluginManager().registerEvents(new ServerEvents(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);

        // Commands
        this.getCommand(CommandFaction.name).setExecutor(new CommandFaction(this));

        this.getLogger().info("HCF core enabled");
    }

    public FactionConfig getFactionConfig() {
        return factionConfig;
    }

    public PlayerConfig getPlayerConfig() {
        return playerConfig;
    }

}
