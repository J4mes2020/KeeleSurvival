package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.server.economy.commands.BalanceCommand;
import dev.joey.keelesurvival.server.economy.commands.PayCommand;

public class CommandManager {

    public CommandManager(KeeleSurvival keeleSurvival) {

        keeleSurvival.getCommand("pay").setExecutor(new PayCommand());
        keeleSurvival.getCommand("balance").setExecutor(new BalanceCommand());

    }
}
