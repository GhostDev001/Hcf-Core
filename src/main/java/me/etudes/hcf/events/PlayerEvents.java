package me.etudes.hcf.events;

import me.etudes.hcf.api.faction.Faction;
import me.etudes.hcf.api.faction.FactionUtils;
import me.etudes.hcf.api.faction.dtr.DtrState;
import me.etudes.hcf.main.HCF;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerEvents implements Listener {

    private HCF plugin;

    public PlayerEvents(HCF plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(FactionUtils.hasFaction(player)) {
            Faction faction = FactionUtils.getFaction(player);
            double dtr = faction.getDtr();
            dtr--;
            faction.setDtr(dtr);
            faction.setDtrState(DtrState.FROZEN);
        }
    }

}
