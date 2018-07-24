package com.gamenetzwerk.nations.inventory;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.nation.Nation;
import com.gamenetzwerk.nations.nation.race.Race;
import com.gamenetzwerk.nations.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class RaceSelectionInventory {

    public static final Inventory inventory = Bukkit.createInventory(null, 3 * 9, "§0Wähle deine Rasse");

    public static void openInventory(Player player) {
        Nation nation = NationsPlugin.getPluginInstance().getNationManager().getNationPlayer(player).getNation();

        inventory.setItem(4, new ItemBuilder(nation.getDisplayItem())
                .name(nation.getColor() + "§l" + nation.getName() + " §7Volk").build());

        int count = 9;
        for (Race race : nation.getRaces()) {
            inventory.setItem(count++, new ItemBuilder(Material.BOOK).name(race.getNation().getColor() + race.getName()).build());
        }

        player.openInventory(inventory);
    }

}
