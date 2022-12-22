package dev.joey.keelesurvival.server.economy.commands;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelesurvival.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Sorry only a player can run this command");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 2) {

            if (!Storage.isValidAmount(strings[1])) {
                UtilClass.sendPlayerMessage(player, "Sorry that isn't a valid amount", UtilClass.error);
                return true;
            }

            double paidAmount = UtilClass.round(Double.parseDouble(strings[1]), 2);
            Player payee = Bukkit.getPlayer(strings[0]);

            if (payee == null) {
                UtilClass.sendPlayerMessage(player, "Sorry " + strings[0] + " isn't a valid player", UtilClass.error);
                return true;
            }

            if (player == payee) {

                UtilClass.sendPlayerMessage(player, "You can't pay yourself -_-", UtilClass.error);
                return true;
            }

            if (!Storage.getPlayerBalance().containsKey(payee.getUniqueId())) {
                Storage.getPlayerBalance().put(payee.getUniqueId(), 0.00);
            }
            payPlayer(player, payee, paidAmount);


        } else {
            UtilClass.sendPlayerMessage(player, "Invalid Syntax /pay <Player> [amount]", UtilClass.error);
        }
        return false;
    }

    
    private void payPlayer(Player player, Player payee, double paidAmount) {

        if (paidAmount > Storage.getPlayerBalance().get(player.getUniqueId())) {
            UtilClass.sendPlayerMessage(player, "Sorry you don't have sufficient funds", UtilClass.error);
            return;
        }


        Storage.getPlayerBalance().put(payee.getUniqueId(), (Storage.getPlayerBalance().get(payee.getUniqueId()) + paidAmount));
        Storage.getPlayerBalance().put(player.getUniqueId(), (Storage.getPlayerBalance().get(player.getUniqueId()) - paidAmount));

        UtilClass.sendPlayerMessage(player, "You have sent " + payee.getName() + " " + Storage.getPrefix() + paidAmount, UtilClass.success);
        UtilClass.sendPlayerMessage(payee, player.getName() + " has sent you " + Storage.getPrefix() + paidAmount, UtilClass.success);

    }
}
