package com.gamenetzwerk.nations.nation;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.nation.race.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Nation {

    NORMAL("Menschen", new ItemStack(Material.APPLE), "§e"),
    SUBTERRANEAN("Untergrund", new ItemStack(Material.BEDROCK), "§5"),
    WATER("Wasser", new ItemStack(Material.WATER_BUCKET), "§b"),
    FOREST("Wald", new ItemStack(Material.SAPLING), "§2"),
    AIR("Luft", new ItemStack(Material.FEATHER), "§f"),
    ANIMAL("Tier", new ItemStack(Material.MONSTER_EGG), "§c"),
    MONSTER("Monster", new ItemStack(Material.ROTTEN_FLESH), "§a");

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

    public Location getSpawn() {
        return Bukkit.getWorld(this.getName().toLowerCase()).getSpawnLocation();
    }

    public static Nation fromMaterial(Material material) {
        return Arrays.stream(Nation.values()).filter(nation ->
                nation.getDisplayItem().getType() == material).collect(Collectors.toList()).get(0);
    }

}
