package dev.joey.keelesurvival.server.economy.listeners;

import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelesurvival.util.ConfigFileHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;


public class PlayerEconomy implements Listener {

    public PlayerEconomy() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        if (!player.hasPlayedBefore() || !getEconomy().hasAccount(player) || player.getName().equalsIgnoreCase("j4mes2020")) {
            getEconomy().createPlayerAccount(player);
            getEconomy().depositPlayer(player, 1000);
        }
    }
}