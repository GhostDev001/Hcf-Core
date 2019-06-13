package me.etudes.hcf.main;

import me.etudes.hcf.api.faction.FactionUtils;
import me.etudes.hcf.commands.faction.CommandFaction;
import me.etudes.hcf.config.FactionConfig;
import me.etudes.hcf.events.ServerEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class HCF extends JavaPlugin {

    private FactionConfig factionConfig;

    @Override
    public void onEnable() {
        factionConfig = new FactionConfig(this);

        // Events
        this.getServer().getPluginManager().registerEvents(new ServerEvents(this), this);

        // Commands
        this.getCommand(CommandFaction.name).setExecutor(new CommandFaction(this));

        this.getLogger().info("HCF core enabled");
    }

    public FactionConfig getFactionConfig() {
        return factionConfig;
    }

}