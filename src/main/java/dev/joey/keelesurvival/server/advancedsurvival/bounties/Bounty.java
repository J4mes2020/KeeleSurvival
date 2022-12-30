package dev.joey.keelesurvival.server.advancedsurvival.bounties;

import dev.joey.keelesurvival.util.ConfigFileHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Bounty {
    static HashMap<String, Double> playerBounties = new HashMap<>();


    public static HashMap<String, Double> getPlayerBounties() {
        return playerBounties;
    }

    public static boolean hasBounty(Player player) {
        return playerBounties.containsKey(player.getUniqueId().toString());
    }

    public static void setBounty(String UUID, Double amount) {
        playerBounties.put(UUID, amount);

    }
}
