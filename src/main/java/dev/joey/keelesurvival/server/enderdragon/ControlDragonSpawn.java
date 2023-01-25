package dev.joey.keelesurvival.server.enderdragon;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.DragonBattle;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class ControlDragonSpawn {

    static int dragonSpawnSecondTimer = keeleSurvival.getConfig().getInt("dragonSpawnSeconds");
    public static int secondsElapsed = keeleSurvival.getConfig().getInt("dragonProgressSeconds");

    public ControlDragonSpawn() {

        Bukkit.getScheduler().runTaskTimer(keeleSurvival, () -> {

            if (secondsElapsed < dragonSpawnSecondTimer) {
                secondsElapsed++;
            } else {
                spawnDragon(Bukkit.getWorlds().get(2));
                secondsElapsed = 0;
            }

        }, 0, 20);

    }


    private void spawnDragon(World world) {
        DragonBattle battle = world.getEnderDragonBattle();

        if (battle == null) {
            return;
        }


//        if (battle.hasBeenPreviouslyKilled()) {
//
//            if (battle.getEnderDragon() != null) {
//                EnderDragon dragon = battle.getEnderDragon();
//                dragon.damage(dragon.getHealth() + 100);
//            }

        battle.setRespawnPhase(DragonBattle.RespawnPhase.START);

        Bukkit.broadcast(Component.text().content("The Ender Dragon has respawned, slay it to get valuable loot and buffs")
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.UNDERLINED, true)
                .color(TextColor.color(174, 51, 191)).build());

        //}

    }


}
