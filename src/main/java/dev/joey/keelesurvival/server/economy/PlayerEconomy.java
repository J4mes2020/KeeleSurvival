package dev.joey.keelesurvival.server.economy;

import dev.joey.keelesurvival.KeeleSurvival;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerEconomy implements Listener {

    KeeleSurvival keeleSurvival;

    public PlayerEconomy(KeeleSurvival keeleSurvival) {
        this.keeleSurvival = keeleSurvival;
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
//        if (!player.hasPlayedBefore()) {
        if (Storage.isValidAmount(keeleSurvival.getConfig().get("starting-balance").toString())) {
            Storage.getPlayerBalance().put(player.getUniqueId(), Double.valueOf(keeleSurvival.getConfig().get("starting-balance").toString()));
        }
        else {
            Logger.getLogger("command").log(Level.SEVERE, "The starting balance you specified isn't valid");
        }

    }


}
