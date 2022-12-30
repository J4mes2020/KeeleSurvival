package dev.joey.keelesurvival.server.advancedsurvival.bounties;

import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BountyCommand extends SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        if (strings.length != 3) {
            UtilClass.sendPlayerMessage(player, "Invalid Syntax, /bounty [set/add] <Player> <Amount>", UtilClass.error);
            return true;
        }

        Player target = Bukkit.getPlayer(strings[1]);

        if (playerNullCheck(target, player)) return true;

        if (!isAlphanumeric(strings[2], player)) return true;

        if (strings[0].equalsIgnoreCase("set")) {



        }

        return false;
    }
}
