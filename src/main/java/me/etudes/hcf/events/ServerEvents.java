package me.etudes.hcf.events;

import me.etudes.hcf.main.HCF;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ServerEvents implements Listener {

    private HCF plugin;

    public ServerEvents(HCF plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
    }

}
