package dev.joey.keelesurvival.server.advancedsurvival.difficulty;

import dev.joey.keelesurvival.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;

public class SurvivalDifficulty {

    public SurvivalDifficulty() {
        if (UtilClass.isAdvancedSurvivalEnabled()) {

            for (World world : Bukkit.getWorlds()) {
                world.setDifficulty(Difficulty.HARD);
            }

        }
        else {
            for (World world : Bukkit.getWorlds()) {
                world.setDifficulty(Difficulty.NORMAL);
            }
        }
    }

}
