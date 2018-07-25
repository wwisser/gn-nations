package com.gamenetzwerk.nations;

import com.gamenetzwerk.nations.command.NationsCommand;
import com.gamenetzwerk.nations.constant.sql.Statement;
import com.gamenetzwerk.nations.mysql.MysqlManager;
import com.gamenetzwerk.nations.nation.NationManager;
import com.gamenetzwerk.nations.runnable.PotionRunnable;
import com.gamenetzwerk.nations.util.Config;
import com.gamenetzwerk.nations.util.RegistrationHelper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.util.Arrays;
import java.util.LinkedHashMap;

@Plugin(name = "Nations", version = "1.1-SNAPSHOT")
@Author(name = "Wende2k")
@Description(desc = "Nations/Races Plugin Implementation for Game-Netzwerk.com")
@Command(name = "nations", permission = "nations.admin")
@Command(name = "spawn")
@Permission(name = "nations.admin", desc = "Allows the usage of the Nations administration command", defaultValue = PermissionDefault.OP)
@Getter
public class NationsPlugin extends JavaPlugin {

    @Getter
    private static NationsPlugin pluginInstance;

    private MysqlManager mysqlManager;
    private NationManager nationManager;

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

        Arrays.asList(Statement.CREATE_TABLE_PLAYERS, Statement.CREATE_TABLE_RACES)
                .forEach(query -> this.mysqlManager.queryUpdate(this.mysqlManager.prepareStatement(query)));
    }

    @Override
    public void onEnable() {
        NationsPlugin.pluginInstance = this;
        this.nationManager = new NationManager();

        this.nationManager.loadRaces();
        RegistrationHelper.registerListeners("com.gamenetzwerk.nations.listener", this);
        super.getCommand("nations").setExecutor(new NationsCommand());
        Bukkit.getOnlinePlayers().forEach(player -> this.nationManager.loadNationPlayer(player));
        new PotionRunnable().runTaskTimer(this, 0L, 25);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (this.nationManager.containsNationPlayer(player)) {
                this.nationManager.saveNationPlayer(player);
            }
        });
        this.mysqlManager.closeConnection();
    }

}
