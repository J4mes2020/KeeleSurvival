package dev.joey.keelesurvival.server.events.sleeping;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.LinkedList;
import java.util.UUID;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class HalfServerSleep implements Listener {

    LinkedList<UUID> playersSleeping = new LinkedList<>();

    public HalfServerSleep() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onSleep(PlayerBedEnterEvent event) {
        playersSleeping.add(event.getPlayer().getUniqueId());

        if (playersSleeping.size() >= Bukkit.getServer().getOnlinePlayers().size()) {
            skipNight(event.getBed().getWorld());
        }
    }

    @EventHandler
    public void onDeepSleep(PlayerDeepSleepEvent event) {

        H

    }

    private void skipNight(World world) {
        world.setTime(0);
        playersSleeping.clear();
    }
}
