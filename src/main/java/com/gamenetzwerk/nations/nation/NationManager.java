package com.gamenetzwerk.nations.nation;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.constant.sql.Statement;
import com.gamenetzwerk.nations.mysql.MysqlManager;
import com.gamenetzwerk.nations.mysql.StatementBuilder;
import com.gamenetzwerk.nations.nation.race.Race;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NationManager {

    @Getter
    private Map<Integer, Race> races = new HashMap<>();
    private Map<Player, NationPlayer> cachedPlayers = new HashMap<>();
    private MysqlManager mysqlManager = NationsPlugin.getPluginInstance().getMysqlManager();

    @SneakyThrows
    public void loadRaces() {
        this.races.clear();
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

    @SneakyThrows
    public void addRace(String name, int nationOrdinal, String effect, int amplifier) {
        ResultSet resultSet = this.mysqlManager.queryUpdateResult(new StatementBuilder(Statement.INSERT_RACE,
                java.sql.Statement.RETURN_GENERATED_KEYS)
                .string(name)
                .integer(nationOrdinal)
                .string(effect)
                .integer(amplifier)
                .build());

        if (resultSet.next()) {
            int id = resultSet.getInt(1);

            this.races.put(id, new Race(id, name, nationOrdinal, effect, amplifier));
        }
    }

    public Race getRace(String name) {
        return this.races.values().stream().filter(race ->
                race.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
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
            Object race = resultSet.getObject("race");

            this.cachedPlayers.put(player, new NationPlayer(player, Nation.values()[resultSet.getInt("nation")],
                    race == null ? null : this.getRace((int) race)));
        }

        resultSet.close();
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
                        .build());
            } else {
                this.mysqlManager.queryUpdate(new StatementBuilder(Statement.INSERT_PLAYER)
                        .string(uuid)
                        .integer(nationPlayer.getNation().ordinal())
                        .integer(nationPlayer.getRace().getId())
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
        return this.containsNationPlayer(player) || this.loadNationPlayer(player);
    }

    public boolean containsNationPlayer(Player player) {
        return this.getNationPlayer(player) != null;
    }

    private Race getRace(int id) {
        return this.races.get(id);
    }

}
