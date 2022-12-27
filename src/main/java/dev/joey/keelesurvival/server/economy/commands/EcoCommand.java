package dev.joey.keelesurvival.server.economy.commands;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;

import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelesurvival.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EcoCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;


        if (noPermission(player, "ks.eco", "ks.admin")) return true;

        if (strings.length == 3) {

            if (playerNullCheck(strings[1], player)) return true;
            if (validAmountCheck(strings[2], player)) return true;

            double amount = Double.parseDouble(strings[2]);
            Player target = Bukkit.getPlayer(strings[1]);
            Storage.checkAndCreateAccount(target);

            if (strings[0].equalsIgnoreCase("set")) {

                Storage.getPlayerBalance().put(target.getUniqueId(), amount);
                UtilClass.sendPlayerMessage(
                        player, "Set " + target.getName() +
                                " balance to " + Storage.getPrefix() + amount, UtilClass.success);
                return true;
            }

            if (strings[0].equalsIgnoreCase("add")) {
                getEconomy().depositPlayer(target, amount);
                UtilClass.sendPlayerMessage(
                        player, "Added " + Storage.getPrefix() + amount
                               + " to " + target.getName() + "'s account", UtilClass.success);
                return true;

            }
            UtilClass.sendPlayerMessage(player, "Invalid Syntax /eco [set/add] <Player> <Amount>", UtilClass.error);

        }
        else {
            UtilClass.sendPlayerMessage(player, "Invalid Syntax /eco [set/add] <Player> <Amount>", UtilClass.error);
        }
        return false;
    }
}
