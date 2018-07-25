package com.gamenetzwerk.nations.listener;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.inventory.NationSelectionInventory;
import com.gamenetzwerk.nations.inventory.RaceSelectionInventory;
import com.gamenetzwerk.nations.nation.Nation;
import com.gamenetzwerk.nations.nation.NationManager;
import com.gamenetzwerk.nations.nation.NationPlayer;
import com.gamenetzwerk.nations.nation.race.Race;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryClickListener implements Listener {

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().equals(NationSelectionInventory.inventory)) {
            event.setCancelled(true);

            if (itemStack != null && itemStack.getType() != Material.AIR) {
                Nation nation = Nation.fromMaterial(itemStack.getType());

                this.nationManager.addPlayer(player, new NationPlayer(player, nation, null));
                player.sendMessage("§7Du bist folgendem Volk beigetreten: " + nation.getColor() + nation.getName() + " Volk ");
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                player.closeInventory();
                player.teleport(nation.getSpawn());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        RaceSelectionInventory.openInventory(player);
                    }
                }.runTaskLater(NationsPlugin.getPluginInstance(), 20L * 5);
            }
        }
        if (event.getInventory().equals(RaceSelectionInventory.inventory)) {
            event.setCancelled(true);

            if (itemStack != null && itemStack.getType() != Material.AIR) {
                String race = itemStack.getItemMeta().getDisplayName();

                this.nationManager.getNationPlayer(player).setRace(this.nationManager
                        .getRace(ChatColor.stripColor(race)));

                player.sendMessage("§7Du bist folgender Rasse beigetreten: " + race);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                player.closeInventory();
            }
        }
    }

}
