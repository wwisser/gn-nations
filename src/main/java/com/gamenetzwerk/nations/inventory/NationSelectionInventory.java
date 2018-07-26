package com.gamenetzwerk.nations.inventory;

import com.gamenetzwerk.nations.nation.Nation;
import com.gamenetzwerk.nations.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class NationSelectionInventory {

    public static final Inventory inventory = Bukkit.createInventory(null, 3 * 9, "§0Wähle dein Volk!");

    static {
        int count = 9;
        for (Nation nation : Nation.values()) {
            NationSelectionInventory.inventory.setItem(count++, new ItemBuilder(nation.getDisplayItem())
                    .name(nation.getColor() + "§l" + nation.getName() + " §7Volk").build());
        }
    }

    public static void openInventory(Player player) {
        player.openInventory(inventory);
    }

}
