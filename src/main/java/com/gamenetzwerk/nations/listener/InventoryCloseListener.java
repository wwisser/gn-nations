package com.gamenetzwerk.nations.listener;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.inventory.NationSelectionInventory;
import com.gamenetzwerk.nations.nation.NationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryCloseListener implements Listener {

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (!this.nationManager.containsNationPlayer(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    NationSelectionInventory.openInventory(player);
                }
            }.runTaskLater(NationsPlugin.getPluginInstance(), 3L);
        }
    }

}
