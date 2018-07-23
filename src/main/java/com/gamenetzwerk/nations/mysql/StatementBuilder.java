package com.gamenetzwerk.nations.mysql;

import com.gamenetzwerk.nations.NationsPlugin;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;

public class StatementBuilder {

    private PreparedStatement preparedStatement;
    private int index = 1;

    public StatementBuilder(String query) {
        this.preparedStatement = NationsPlugin.getPluginInstance().getMysqlManager().prepareStatement(query);
    }

    @SneakyThrows
    public StatementBuilder string(String value) {
        this.preparedStatement.setString(this.index++, value);
        return this;
    }

    @SneakyThrows
    public StatementBuilder integer(int value) {
        this.preparedStatement.setInt(this.index++, value);
        return this;
    }

    public PreparedStatement build() {
        return this.preparedStatement;
    }

}

