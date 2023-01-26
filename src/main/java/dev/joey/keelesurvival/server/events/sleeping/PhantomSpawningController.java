package dev.joey.keelesurvival.server.events.sleeping;

import io.papermc.paper.event.player.PlayerBedFailEnterEvent;
import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class PhantomSpawningController implements Listener {

    public PhantomSpawningController() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onSleepAttempt(PlayerBedEnterEvent event) {



    }

}
