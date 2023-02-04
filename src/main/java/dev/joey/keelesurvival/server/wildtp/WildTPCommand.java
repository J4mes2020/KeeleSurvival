package dev.joey.keelesurvival.server.wildtp;

import dev.joey.keelesurvival.managers.supers.SuperCommand;
import dev.joey.keelesurvival.util.UtilClass;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class WildTPCommand extends SuperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSenderCheck(commandSender)) return true;

        Player player = (Player) commandSender;

        double worldBorderSize = player.getWorld().getWorldBorder().getSize();


        double x = ThreadLocalRandom.current().nextDouble(0, worldBorderSize/ 2);
        double z = ThreadLocalRandom.current().nextDouble(0, worldBorderSize / 2);

        World world = player.getWorld();


        player.teleport(new Location(world, x, world.getHighestBlockYAt((int) x, (int) z) + 1, z));
        UtilClass.sendPlayerMessage(player, "Teleporting!", UtilClass.success);
        return false;
    }
}