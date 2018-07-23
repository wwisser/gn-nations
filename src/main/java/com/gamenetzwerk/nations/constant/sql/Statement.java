package com.gamenetzwerk.nations.constant.sql;

public final class Statement {

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `nations` " +
            "(`uuid` VARCHAR(36) PRIMARY KEY, `nation` INT DEFAULT -1, `race` INT DEFAULT -1)";

    public static final String INSERT_PLAYER = "INSERT INTO `nations` (`uuid`, `nation`, `race`)" +
            " VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE `uuid` = ?";

    public static final String UPDATE_PLAYER = "UPDATE `nations` SET `nation` = ?, `race` = ? WHERE `uuid` = ?";

    public static final String SELECT_PLAYER = "SELECT * FROM `nations` WHERE `uuid` = ?";

}