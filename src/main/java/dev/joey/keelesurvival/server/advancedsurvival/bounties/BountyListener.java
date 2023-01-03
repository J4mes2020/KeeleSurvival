package dev.joey.keelesurvival.server.advancedsurvival.bounties;

import dev.joey.keelesurvival.server.economy.Storage;
import dev.joey.keelesurvival.util.UtilClass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static dev.joey.keelesurvival.KeeleSurvival.getEconomy;
import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class BountyListener implements Listener {

    public BountyListener() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player victim = event.getPlayer();
        Player killer = victim.getKiller();

        if (!Bounty.hasBounty(victim) || killer == null) {
            return;
        }

        double bounty = Bounty.getBounty(victim.getUniqueId());
        if (getEconomy().hasAccount(killer)) {
            getEconomy().depositPlayer(killer, bounty);

            UtilClass.sendPlayerMessage(killer,
                    "You have claimed the " + Storage.getPrefix() +
                            Bounty.getBounty(victim.getUniqueId())
                            + " bounty on " + victim.getName(), UtilClass.success);

            UtilClass.sendPlayerMessage(killer,
                    killer.getName()
                            + " has claimed the " + Storage.getPrefix() +
                            Bounty.getBounty(victim.getUniqueId())
                            + " bounty on your head", UtilClass.success);
            Bounty.removeBounty(victim.getUniqueId());



        }

    }
}
