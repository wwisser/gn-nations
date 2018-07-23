package com.gamenetzwerk.nations.constant.sql;

public final class Statement {

    public static final String CREATE_TABLE_PLAYERS = "CREATE TABLE IF NOT EXISTS `nation_players` " +
            "(`uuid` VARCHAR(36) PRIMARY KEY, `nation` INT, `race` VARCHAR(10))";

    public static final String CREATE_TABLE_RACES = "CREATE TABLE IF NOT EXISTS `nation_races` " +
            "(`name` VARCHAR(10) PRIMARY KEY, `nation` INT, `effect` VARCHAR(16), `amplifier` INT)";

    public static final String INSERT_PLAYER = "INSERT INTO `nation_players` (`uuid`, `nation`, `race`)" +
            " VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `uuid` = ?";

    public static final String INSERT_PLAYER_NATION = "INSERT INTO `nation_players` (`uuid`, `nation`)" +
            " VALUES (?, ?) ON DUPLICATE KEY UPDATE `uuid` = ?";

    public static final String INSERT_PLAYER_RACE = "INSERT INTO `nation_players` (`uuid`, `race`)" +
            " VALUES (?, ?) ON DUPLICATE KEY UPDATE `uuid` = ?";

    public static final String UPDATE_PLAYER = "UPDATE `nations` SET `nation` = ?, `race` = ? WHERE `uuid` = ?";

    public static final String SELECT_PLAYER = "SELECT * FROM `nation_players` WHERE `uuid` = ?";

    public static final String SELECT_RACES = "SELECT * FROM `nation_races`";

}