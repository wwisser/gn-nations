package com.gamenetzwerk.nations.nation;

import com.gamenetzwerk.nations.nation.race.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NationPlayer {

    private Player player;
    private Nation nation;
    @Setter private Race race;

    public String getName() {
        return this.player.getName();
    }

    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

}
