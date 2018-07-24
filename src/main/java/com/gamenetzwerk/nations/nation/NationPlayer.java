package com.gamenetzwerk.nations.nation;

import com.gamenetzwerk.nations.nation.race.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class NationPlayer {

    private Player player;
    private Nation nation;
    @Setter @Nullable private Race race;

    public boolean hasRace() {
        return this.race != null;
    }

    public String getName() {
        return this.player.getName();
    }

    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

}
