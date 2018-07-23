package com.gamenetzwerk.nations.listener;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.inventory.NationSelectionInventory;
import com.gamenetzwerk.nations.nation.Nation;
import com.gamenetzwerk.nations.nation.NationManager;
import com.gamenetzwerk.nations.nation.NationPlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(NationSelectionInventory.inventory)) {
            event.setCancelled(true);

            ItemStack itemStack = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();

            if (itemStack != null && itemStack.getType() != Material.AIR) {
                Nation nation = Nation.fromMaterial(itemStack.getType());

                this.nationManager.addPlayer(player, new NationPlayer(player, nation, null));
                player.sendMessage("§7Du bist folgendem Volk beigetreten: " + nation.getColor() + nation.getName() + " Volk ");
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                player.closeInventory();
            }
        }
    }

}
