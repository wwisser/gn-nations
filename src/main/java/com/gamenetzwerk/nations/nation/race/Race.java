package com.gamenetzwerk.nations.nation.race;

import com.gamenetzwerk.nations.nation.Nation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
@Getter
public class Race {

    private static final int DURATION = 500;

    private int id;
    private String name;
    private int nationOrdinal;
    private String potionEffectName;
    private int amplifier;

    public Nation getNation() {
        return Nation.values()[this.nationOrdinal];
    }

    public PotionEffect getPotionEffect() {
        return new PotionEffect(PotionEffectType.getByName(this.potionEffectName), DURATION, this.amplifier);
    }

}
