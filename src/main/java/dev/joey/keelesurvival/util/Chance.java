package dev.joey.keelesurvival.util;

public class Chance {


    public static Boolean percentageChance(double chance) {

        return Math.random() <= chance;

    }

}
