package com.gamenetzwerk.nations.constant.sql;

public final class Statement {

    public static final String CREATE_TABLE_PLAYERS = "CREATE TABLE IF NOT EXISTS `nation_players` " +
            "(`uuid` CHAR(36) PRIMARY KEY, `nation` INT, `race` INT)";

    public static final String CREATE_TABLE_RACES = "CREATE TABLE IF NOT EXISTS `nation_races` " +
            "(`id` INT PRIMARY KEY AUTO_INCREMENT, `name` VARCHAR(16), `nation` INT, `effect` VARCHAR(25), `amplifier` INT)";

    public static final String INSERT_PLAYER = "INSERT INTO `nation_players` (`uuid`, `nation`, `race`) VALUES (?, ?, ?)";

    public static final String INSERT_PLAYER_NATION = "INSERT INTO `nation_players` (`uuid`, `nation`) VALUES (?, ?)";

    public static final String INSERT_RACE = "INSERT INTO `nation_races` (`name`, `nation`, `effect`, `amplifier`) VALUES (?, ?, ?, ?)";

    public static final String UPDATE_PLAYER = "UPDATE `nation_players` SET `nation` = ?, `race` = ? WHERE `uuid` = ?";

    public static final String UPDATE_PLAYER_NATION = "UPDATE `nation_players` SET `nation` = ? WHERE `uuid` = ?";

    public static final String SELECT_PLAYER = "SELECT * FROM `nation_players` WHERE `uuid` = ?";

    public static final String SELECT_RACES = "SELECT * FROM `nation_races`";

}