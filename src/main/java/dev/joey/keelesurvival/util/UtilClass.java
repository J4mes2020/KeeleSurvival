package dev.joey.keelesurvival.util;

import dev.joey.keelesurvival.KeeleSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.logging.Logger;

public class UtilClass {

    public static KeeleSurvival keeleSurvival;
    static Logger log = Logger.getLogger("Minecraft");
    public static int success = new Color(0, 255, 8).getRGB();
    public static int error = new Color(202, 28, 26).getRGB();
    public static int information = new Color(255, 221, 0).getRGB();


    public static void sendPlayerMessage(Player player, String message, int colour) {
        player.sendMessage(Component.text().content(message).color(TextColor.color(colour)));
    }

    public static Boolean percentageChance(double chance) {

        return Math.random() <= chance;

    }

    public static boolean isAdvancedSurvivalEnabled() {

        return keeleSurvival.getConfig().getBoolean("advancedSurvival");

    }

    public static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static Logger getLogger() {
        return log;
    }

}
