package com.gamenetzwerk.nations.listener;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.constant.sql.Statement;
import com.gamenetzwerk.nations.mysql.MysqlManager;
import com.gamenetzwerk.nations.mysql.StatementBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private MysqlManager mysqlManager = NationsPlugin.getPluginInstance().getMysqlManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String uuid = event.getPlayer().getUniqueId().toString();

        this.mysqlManager.queryUpdate(new StatementBuilder(Statement.INSERT_PLAYER)
                .string(uuid)
                .integer(1)
                .integer(2)
                .string(uuid)
                .build());
    }

}
