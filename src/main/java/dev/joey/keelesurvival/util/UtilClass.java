package dev.joey.keelesurvival.util;

import dev.joey.keelesurvival.KeeleSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static Map<UUID, Double> sortByValue(Map<UUID, Double> unsortedMap, final boolean order) {
        List<Map.Entry<UUID, Double>> list = new java.util.ArrayList<>(unsortedMap.entrySet().stream().toList());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
}
