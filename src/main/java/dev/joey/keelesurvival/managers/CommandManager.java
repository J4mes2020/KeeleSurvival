package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.server.economy.commands.BalanceCommand;
import dev.joey.keelesurvival.server.economy.commands.PayCommand;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class CommandManager {

    public CommandManager() {

        keeleSurvival.getCommand("pay").setExecutor(new PayCommand());
        keeleSurvival.getCommand("balance").setExecutor(new BalanceCommand());
        //keeleSurvival.getCommand("balancetop").setExecutor(new BalanceListCommand());

    }
}
