package com.gamenetzwerk.nations;

import com.gamenetzwerk.nations.constant.sql.Statement;
import com.gamenetzwerk.nations.mysql.MysqlManager;
import com.gamenetzwerk.nations.util.Config;
import com.gamenetzwerk.nations.util.RegistrationHelper;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.LinkedHashMap;

@Plugin(name = "Nations", version = "1.1-SNAPSHOT")
@Author(name = "Wende2k")
@Description(desc = "Nations/Races Plugin Implementation for Game-Netzwerk.com")
@Getter
public class NationsPlugin extends JavaPlugin {

    @Getter
    private static NationsPlugin pluginInstance;

    private MysqlManager mysqlManager;

    @Override
    public void onLoad() {
        Config config = new Config("plugins/Nations", "config.yml");

        if (!config.contains("mysql")) {
            new LinkedHashMap<String, Object>() {{
                super.put("mysql.host", "127.0.0.1:3306");
                super.put("mysql.username", "root");
                super.put("mysql.password", "password");
                super.put("mysql.database", "database");
            }}.forEach(config::set);
            config.saveFile();
        }

        this.mysqlManager = new MysqlManager(config);
        this.mysqlManager.queryUpdate(this.mysqlManager.prepareStatement(Statement.CREATE_TABLE));
    }

    @Override
    public void onEnable() {
        NationsPlugin.pluginInstance = this;

        RegistrationHelper.registerListeners("com.gamenetzwerk.nations.listener", this);
    }

    @Override
    public void onDisable() {
        this.mysqlManager.closeConnection();
    }

}
