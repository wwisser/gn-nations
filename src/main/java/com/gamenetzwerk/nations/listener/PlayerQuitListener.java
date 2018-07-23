package com.gamenetzwerk.nations.listener;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.nation.NationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.nationManager.containsNationPlayer(player)) {
            this.nationManager.saveNationPlayer(player);
            this.nationManager.removePlayer(player);
        }
    }

}
