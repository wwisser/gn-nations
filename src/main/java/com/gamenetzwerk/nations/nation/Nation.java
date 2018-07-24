package com.gamenetzwerk.nations.nation;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.nation.race.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Nation {

    NORMAL("Normales", new ItemStack(Material.APPLE), "§e"),
    SUBTERRANEAN("Unterirdisches", new ItemStack(Material.BEDROCK), "§5"),
    WATER("Wasser", new ItemStack(Material.WATER_BUCKET), "§b"),
    FOREST("Wald", new ItemStack(Material.SAPLING), "§2"),
    AIR("Luft", new ItemStack(Material.FEATHER), "§f");

    private String name;
    private ItemStack displayItem;
    private String color;

    public List<Race> getRaces() {
        return NationsPlugin.getPluginInstance().getNationManager().getRaces()
                .values()
                .stream()
                .filter(race -> race.getNationOrdinal() == this.ordinal())
                .collect(Collectors.toList());
    }

    public static Nation fromMaterial(Material material) {
        return Arrays.stream(Nation.values()).filter(nation ->
                nation.getDisplayItem().getType() == material).collect(Collectors.toList()).get(0);
    }

}
