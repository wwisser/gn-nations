package com.gamenetzwerk.nations.nation.race;

import com.gamenetzwerk.nations.nation.Nation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
@Getter
public class Race {

    private String name;
    private int nationOrdinal;
    private String potionEffectName;
    private int amplifier;

    private Nation getNation() {
        return Nation.values()[this.nationOrdinal];
    }

    private PotionEffect getPotionEffect() {
        return new PotionEffect(PotionEffectType.getByName(this.potionEffectName), 0, this.amplifier);
    }

}
