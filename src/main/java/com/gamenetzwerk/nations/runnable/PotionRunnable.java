package com.gamenetzwerk.nations.runnable;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.nation.NationManager;
import com.gamenetzwerk.nations.nation.race.Race;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionRunnable extends BukkitRunnable {

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.nationManager.containsNationPlayer(player)) {
                Race race = this.nationManager.getNationPlayer(player).getRace();

                if (race != null) {
                    player.addPotionEffect(race.getPotionEffect());
                }
            }
        });
    }

}
