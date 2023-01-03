package dev.joey.keelesurvival.server.advancedsurvival.bounties;

import dev.joey.keelesurvival.util.ConfigFileHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Bounty {
    static HashMap<UUID, Double> playerBounties = new HashMap<>();

    public static HashMap<UUID, Double> getPlayerBounties() {
        return playerBounties;
    }

    public static boolean hasBounty(Player player) {
        return playerBounties.containsKey(player.getUniqueId());
    }

    public static void setBounty(UUID uuid, Double amount) {
        playerBounties.put(uuid, amount);

    }

    public static double getBounty(UUID uuid) {
        return getPlayerBounties().get(uuid);
    }

    public static void removeBounty(UUID uuid) {
        playerBounties.remove(uuid);
    }

    public static void loadBountyData() {

        ConfigFileHandler configFileHandler = new ConfigFileHandler();

        if (configFileHandler.getPlayerFile().getConfigurationSection("player.bounties") == null) {
            return;
        }
        configFileHandler.getPlayerFile().getConfigurationSection("player.bounties").getKeys(false).stream().toList()
                .forEach(player -> {
                    playerBounties.put(UUID.fromString(player), configFileHandler.getPlayerFile().getDouble("player.bounties." + player));

                });
    }
}
