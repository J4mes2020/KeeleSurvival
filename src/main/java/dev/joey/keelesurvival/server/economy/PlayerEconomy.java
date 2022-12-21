package dev.joey.keelesurvival.server.economy;

import dev.joey.keelesurvival.KeeleSurvival;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEconomy implements Listener {

    public PlayerEconomy(KeeleSurvival keeleSurvival) {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            Storage.playerBalance.put(player.getUniqueId(), 1000.00);
        }

    }


}
