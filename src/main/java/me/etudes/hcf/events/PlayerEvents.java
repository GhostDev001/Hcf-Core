package me.etudes.hcf.events;

import me.etudes.hcf.api.deathban.DeathbanUtils;
import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.api.faction.FactionUtils;
import me.etudes.hcf.api.faction.dtr.DtrState;
import me.etudes.hcf.api.player.PlayerConfig;
import me.etudes.hcf.main.HCF;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerEvents implements Listener {

    private HCF plugin;

    public PlayerEvents(HCF plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        if(FactionUtils.hasFaction(player)) {
            // update dtr
            Faction faction = FactionUtils.getFaction(player);
            double dtr = faction.getDtr();
            dtr--;
            faction.setDtr(dtr);
            faction.setDtrState(DtrState.FROZEN);
            faction.resetRegenTime();

            new BukkitRunnable() {
                public void run() {
                    Faction updatedFaction = FactionUtils.getFaction(player);
                    if(System.currentTimeMillis() / 1000 == updatedFaction.getRegenTime() / 1000) {
                        //TODO: dtr regen logic here
                    }
                }
            }.runTaskLater(plugin, DeathbanUtils.REGEN_TICKS);
        }
        plugin.getPlayerConfig().addDeathBan(player);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerConfig config = plugin.getPlayerConfig();
        if(config.isDeathBanned(player)) {
            player.kickPlayer(ChatColor.RED + "You are deathbanned for " +
                    DeathbanUtils.formatDeathbanTime(config.getDeathBanTimeLeft(player)));
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        PlayerConfig config = plugin.getPlayerConfig();
        if(config.isDeathBanned(player)) {
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ChatColor.RED + "You are deathbanned for " +
                    DeathbanUtils.formatDeathbanTime(config.getDeathBanTimeLeft(player)));
        }
    }

}
