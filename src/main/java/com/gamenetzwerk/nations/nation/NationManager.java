package com.gamenetzwerk.nations.nation;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.constant.sql.Statement;
import com.gamenetzwerk.nations.mysql.MysqlManager;
import com.gamenetzwerk.nations.mysql.StatementBuilder;
import com.gamenetzwerk.nations.nation.race.Race;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NationManager {

    private Map<Player, NationPlayer> cachedPlayers = new HashMap<>();
    private Map<Integer, Race> races = new HashMap<>();
    private MysqlManager mysqlManager = NationsPlugin.getPluginInstance().getMysqlManager();

    @SneakyThrows
    public void loadRaces() {
        ResultSet resultSet = this.mysqlManager.query(this.mysqlManager.prepareStatement(Statement.SELECT_RACES));

        while (resultSet.next()) {
            int id = resultSet.getInt("id");

            this.races.put(id, new Race(
                    id,
                    resultSet.getString("name"),
                    resultSet.getInt("nation"),
                    resultSet.getString("effect"),
                    resultSet.getInt("amplifier")
            ));
        }
    }

    public void addPlayer(Player player, NationPlayer nationPlayer) {
        this.cachedPlayers.put(player, nationPlayer);
    }

    public void removePlayer(Player player) {
        this.cachedPlayers.remove(player);
    }

    @SneakyThrows
    public boolean loadNationPlayer(Player player) {
        ResultSet resultSet = this.mysqlManager.query(new StatementBuilder(Statement.SELECT_PLAYER)
                .string(player.getUniqueId().toString()).build());
        boolean registered = false;

        if (resultSet.next()) {
            registered = true;
            Object race = resultSet.getObject("id");

            this.cachedPlayers.put(player, new NationPlayer(player, Nation.values()[resultSet.getInt("nation")],
                    race == null ? null : this.getRace((int) race)));
        }

        return registered;
    }

    public void saveNationPlayer(Player player) {
        NationPlayer nationPlayer = this.cachedPlayers.get(player);
        String uuid = player.getUniqueId().toString();

        if (!this.loadNationPlayer(player)) {
            if (nationPlayer.getRace() == null) {
                this.mysqlManager.queryUpdate(new StatementBuilder(Statement.INSERT_PLAYER_NATION)
                        .string(uuid)
                        .integer(nationPlayer.getNation().ordinal())
                        .string(uuid)
                        .build());
            } else {
                this.mysqlManager.queryUpdate(new StatementBuilder(Statement.INSERT_PLAYER)
                        .string(uuid)
                        .integer(nationPlayer.getNation().ordinal())
                        .integer(nationPlayer.getRace().getId())
                        .string(uuid)
                        .build());
            }
        } else {
            if (nationPlayer.getRace() == null) {
                this.mysqlManager.queryUpdate(new StatementBuilder(Statement.UPDATE_PLAYER_NATION)
                        .string(uuid)
                        .integer(nationPlayer.getNation().ordinal())
                        .build());
            } else {
                this.mysqlManager.queryUpdate(new StatementBuilder(Statement.UPDATE_PLAYER)
                        .string(uuid)
                        .integer(nationPlayer.getNation().ordinal())
                        .integer(nationPlayer.getRace().getId())
                        .build());
            }
        }
    }

    public NationPlayer getNationPlayer(Player player) {
        return this.cachedPlayers.getOrDefault(player, null);
    }

    public boolean isRegistered(Player player) {
        return this.containsNationPlayer(player) && this.loadNationPlayer(player);
    }

    public boolean containsNationPlayer(Player player) {
        return this.getNationPlayer(player) != null;
    }

    private Race getRace(int id) {
        return this.races.get(id);
    }

}
