package dev.joey.keelesurvival.server.economy.commands;

import dev.joey.keelesurvival.server.economy.Storage;
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

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (strings.length == 2) {
                if (strings[1].matches("[0-9]+")) {

                    double paidAmount = Double.parseDouble(strings[1]);
                    Player payee = Bukkit.getPlayer(strings[0]);

                    if (payee != null) {

                        if (getPlayerBalance().containsKey(payee.getUniqueId())) {
                            getPlayerBalance().put(payee.getUniqueId(), (getPlayerBalance().get(payee.getUniqueId()) + paidAmount));
                            getPlayerBalance().put(player.getUniqueId(), (getPlayerBalance().get(player.getUniqueId()) - paidAmount));

                        }
                    }

                }

            }

        }

        return false;
    }

    private HashMap<UUID, Double> getPlayerBalance() {
        return Storage.playerBalance;
    }
}
