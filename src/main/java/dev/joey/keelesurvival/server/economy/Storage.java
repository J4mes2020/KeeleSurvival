package dev.joey.keelesurvival.server.economy;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.server.economy.provider.EconomyProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.UUID;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class Storage {

    public static String getPrefix() {
        return keeleSurvival.getConfig().get("currency").toString();
    }

    private static final HashMap<UUID, Double> playerBalance = new HashMap<>();

    public static HashMap<UUID, Double> getPlayerBalance() {
        return Storage.playerBalance;
    }

    public static boolean isValidAmount(String amount) {
        return amount.matches("^\\d*(\\.\\d+)?$");
    }

    public static void checkAndCreateAccount(Player player) {

        if (!getEconomy().hasAccount(player)) {
            getEconomy().createPlayerAccount(player);
        }

    }


}

