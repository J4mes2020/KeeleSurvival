package dev.joey.keelesurvival.server.economy;

import dev.joey.keelesurvival.KeeleSurvival;

import java.util.HashMap;
import java.util.UUID;

public class Storage {

    public static KeeleSurvival keeleSurvival;


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


}
