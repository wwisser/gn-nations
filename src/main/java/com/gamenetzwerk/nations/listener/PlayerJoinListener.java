package com.gamenetzwerk.nations.listener;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.constant.sql.Statement;
import com.gamenetzwerk.nations.inventory.NationSelectionInventory;
import com.gamenetzwerk.nations.mysql.MysqlManager;
import com.gamenetzwerk.nations.mysql.StatementBuilder;
import com.gamenetzwerk.nations.nation.NationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!this.nationManager.isRegistered(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    NationSelectionInventory.openInventory(player);
                }
            }.runTaskLater(NationsPlugin.getPluginInstance(), 5L);
        }

    }

}
