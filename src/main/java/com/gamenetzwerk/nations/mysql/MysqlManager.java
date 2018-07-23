package com.gamenetzwerk.nations.mysql;

import com.gamenetzwerk.nations.util.Config;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlManager {

    private String host;
    private String username;
    private String password;
    private String database;

    private Connection connection;

    public MysqlManager(Config config) {
        this.host = config.getString("mysql.host");
        this.username = config.getString("mysql.username");
        this.password = config.getString("mysql.password");
        this.database = config.getString("mysql.database");
    }

    @SneakyThrows
    private void openConnection() {
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager
                .getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?autoReconnect=true",
                        this.username, this.password);
    }

    @SneakyThrows
    public void closeConnection() {
        if (!this.connection.isClosed()) {
            this.connection.close();
        }
    }

    @SneakyThrows
    private void checkConnection() {
        if (this.connection == null || this.connection.isClosed()) {
            this.openConnection();
        }
    }

    @SneakyThrows
    public PreparedStatement prepareStatement(String query) {
        this.checkConnection();

        return this.connection.prepareStatement(query);
    }

    @SneakyThrows
    public void queryUpdate(PreparedStatement preparedStatement) {
        this.checkConnection();

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @SneakyThrows
    public ResultSet query(PreparedStatement preparedStatement) {
        this.checkConnection();

        return preparedStatement.executeQuery();
    }

}
