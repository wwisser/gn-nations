package com.gamenetzwerk.nations.nation;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.constant.sql.Statement;
import com.gamenetzwerk.nations.mysql.MysqlManager;
import com.gamenetzwerk.nations.mysql.StatementBuilder;
import com.gamenetzwerk.nations.nation.race.Race;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NationManager {

    private List<NationPlayer> cachedPlayers = new ArrayList<>();
    private List<Race> races = new ArrayList<>();
    private MysqlManager mysqlManager = NationsPlugin.getPluginInstance().getMysqlManager();

    @SneakyThrows
    public void loadRaces() {
        ResultSet resultSet = this.mysqlManager.query(this.mysqlManager.prepareStatement(Statement.SELECT_RACES));

        while (resultSet.next()) {
            this.races.add(new Race(
                    resultSet.getString("name"),
                    resultSet.getInt("nation"),
                    resultSet.getString("effect"),
                    resultSet.getInt("amplifier")
            ));
        }
    }

    @SneakyThrows
    public boolean loadNationPlayer(Player player) {
        ResultSet resultSet = this.mysqlManager.query(new StatementBuilder(Statement.SELECT_PLAYER)
                .string(player.getUniqueId().toString()).build());
        boolean registered = false;

        if (resultSet.next()) {
            registered = true;
            String race = resultSet.getString("race");

            this.cachedPlayers.add(new NationPlayer(player, Nation.values()[resultSet.getInt("nation")],
                    race == null ? null : this.getRace(race)));
        }

        return registered;
    }

    public NationPlayer getNationPlayer(Player player) {
        return this.cachedPlayers.stream().filter(nationPlayer ->
                nationPlayer.getPlayer().equals(player)).collect(Collectors.toList()).get(0);
    }

    public boolean containsNationPlayer(Player player) {
        return this.getNationPlayer(player) != null;
    }

    private Race getRace(String name) {
        return this.races.stream().filter(race ->
                race.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
    }

}
