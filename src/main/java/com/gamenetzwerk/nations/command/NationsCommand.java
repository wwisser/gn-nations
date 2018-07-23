package com.gamenetzwerk.nations.command;

import com.gamenetzwerk.nations.NationsPlugin;
import com.gamenetzwerk.nations.nation.Nation;
import com.gamenetzwerk.nations.nation.NationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NationsCommand implements CommandExecutor {

    private static final String COMMAND_USAGE = "§cUsage: /nations <reload|§baddrace§c> §b<name> <nation> <effect> <amplifier>";

    private NationManager nationManager = NationsPlugin.getPluginInstance().getNationManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(COMMAND_USAGE);
        } else {
            switch (args[0].toLowerCase()) {
                case "reload":
                    commandSender.sendMessage("§7Reloading races....");
                    this.nationManager.loadRaces();
                    commandSender.sendMessage("§aSuccessfully reloaded races.");
                    break;
                case "addrace":
                    if (args.length < 4) {
                        commandSender.sendMessage(COMMAND_USAGE);
                    } else {
                        String name = args[1].toLowerCase();
                        int nationOrdinal;
                        String effect = args[3];
                        int amplifier;

                        if (this.nationManager.getRaces().values().stream().anyMatch(race -> race.getName().equalsIgnoreCase(name))) {
                            commandSender.sendMessage("§cThis race already exists.");
                            break;
                        }
                        if (Arrays.stream(Nation.values()).noneMatch(nationEnum -> nationEnum.getName().equalsIgnoreCase(args[2]))) {
                            commandSender.sendMessage("§cThis nation doesn't exist.");
                            break;
                        } else {
                            nationOrdinal = Arrays.stream(Nation.values())
                                    .filter(nationEnum -> nationEnum.getName().equalsIgnoreCase(args[2]))
                                    .collect(Collectors.toList()).get(0).ordinal();
                        }
                        if (PotionEffectType.getByName(effect) == null) {
                            commandSender.sendMessage("§cThis potion effect doesn't exist.");
                            break;
                        }
                        try {
                            amplifier = Integer.parseInt(args[4]);
                        } catch (NumberFormatException e) {
                            commandSender.sendMessage("§cInvalid number.");
                            break;
                        }

                        this.nationManager.addRace(name, nationOrdinal, effect, amplifier);
                        commandSender.sendMessage("§aSuccessfully added race '" + name + "' with " + effect + ":" + amplifier);
                    }
                    break;
                default:
                    commandSender.sendMessage(COMMAND_USAGE);
                    break;
            }
        }

        return true;
    }

}
