package dev.joey.keelesurvival.managers;

import dev.joey.keelesurvival.server.chestprotection.commands.ChestLockingCommand;
import dev.joey.keelesurvival.server.chestprotection.commands.ChestUnlockingCommand;
import dev.joey.keelesurvival.server.economy.commands.BalanceCommand;
import dev.joey.keelesurvival.server.economy.commands.EcoCommand;
import dev.joey.keelesurvival.server.economy.commands.PayCommand;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class CommandManager {

    public CommandManager() {

        keeleSurvival.getCommand("pay").setExecutor(new PayCommand());
        keeleSurvival.getCommand("balance").setExecutor(new BalanceCommand());
        keeleSurvival.getCommand("eco").setExecutor(new EcoCommand());
        keeleSurvival.getCommand("unlock").setExecutor(new ChestUnlockingCommand());
        keeleSurvival.getCommand("lock").setExecutor(new ChestLockingCommand());

    }
}
