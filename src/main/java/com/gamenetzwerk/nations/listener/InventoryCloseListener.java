package com.gamenetzwerk.nations.listener;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.inventory.NationSelectionInventory;
import com.gamenetzwerk.nations.inventory.RaceSelectionInventory;
import com.gamenetzwerk.nations.nation.Nation;
import com.gamenetzwerk.nations.nation.NationManager;
import com.gamenetzwerk.nations.nation.NationPlayer;
import com.gamenetzwerk.nations.nation.race.Race;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryCloseListener implements Listener {

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();

        if (inventory.equals(NationSelectionInventory.inventory)) {
            if (!this.nationManager.containsNationPlayer(player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        NationSelectionInventory.openInventory(player);
                    }
                }.runTaskLater(NationsPlugin.getPluginInstance(), 3L);
            }
        } else if (inventory.equals(RaceSelectionInventory.inventory)) {
            NationPlayer nationPlayer = this.nationManager.getNationPlayer(player);
            if (!nationPlayer.hasRace()) {
                List<Race> races = nationPlayer.getNation().getRaces();

                if (!races.isEmpty()) {
                    Race race = races.get(ThreadLocalRandom.current().nextInt(races.size() - 1));

                    nationPlayer.setRace(race);
                    player.sendMessage("§7Du hast dir keine Rasse ausgesucht.");
                    player.sendMessage("§7Dir wurde eine zufällige Rasse zugewiesen: "
                            + nationPlayer.getNation().getColor() + race.getName());
                }
            }
        }
    }

}
