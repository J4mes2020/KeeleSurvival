package dev.joey.keelesurvival.clearlag;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import dev.joey.keelesurvival.KeeleSurvival;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ControlLag implements Listener {

    boolean enabled;

    public ControlLag(KeeleSurvival keeleSurvival) {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        if (enabled) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(
                    Component.text().content("(!) Sorry we're currently clearing some lag" +
                                    " and don't want your items deleted, Please try again in a minute")
                            .style(Style.style(TextColor.color(217, 36, 33))).build());


        }
    }

    @EventHandler
    public void onPrimed(TNTPrimeEvent event) {

        if (enabled) {
            event.setCancelled(true);
            if (event.getPrimerEntity() instanceof Player) {
                event.getPrimerEntity().sendMessage(
                        Component.text().content("(!) Sorry we're currently clearing some lag, Please try to ignite again in a minute")
                                .style(Style.style(TextColor.color(217, 36, 33))).build());

            }

        }
    }

    @EventHandler
    public void onFlow(BlockFromToEvent event) {

        if (enabled) {
            if (!event.getBlock().isLiquid()) {
                return;
            }
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onGrow(BlockGrowEvent event) {

        if (enabled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent event) {

        if (enabled) {
            event.setCancelled(true);
        }
    }

    public void setEnabled(boolean toggle) {
        enabled = toggle;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
