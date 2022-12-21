package dev.joey.keelesurvival.admin.clearlag;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;

public class ClearLag implements Listener {

    int clearTimer = 18000;
    int ticksToRemoval = 3000;
    ArrayList<Entity> entities = new ArrayList<>();
    int entityRemovalCount;
    boolean enabled;



    public ClearLag(KeeleSurvival keeleSurvival) {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(keeleSurvival, () -> {

            if (isEnabled()) {
                setEnabled(false);
                for (double TPS : Bukkit.getServer().getTPS()) { //TODO: Please check this as it may need redoing
                    if (TPS <= 10) {
                        setEnabled(true);
                        clearLagNow();
                    }
                }
            }


        }, 0, 1200);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(keeleSurvival, this::clearLagNow, 0, clearTimer);

    }


    public void clearLagNow() {

        entityRemovalCount = 0;

        for (World world : Bukkit.getWorlds()) {
            entities.addAll(world.getEntities());
        }

        for (Entity entity : entities) {

            if (entity instanceof LivingEntity) {

                LivingEntity livingEntity = (LivingEntity) entity;

                if (livingEntity.customName() == null
                        && livingEntity.getTicksLived() >= ticksToRemoval
                        && livingEntity.getChunk().getEntities().length > 2
                        && !livingEntity.isLeashed()) {

                    if (!(livingEntity instanceof Player)
                            || livingEntity instanceof NPC
                            || livingEntity instanceof Illager
                            || livingEntity instanceof Boss
                            || livingEntity instanceof Warden) {

                        livingEntity.remove();
                        entityRemovalCount++;
                    }

                }

            } else {

                if (entity.customName() == null
                        && entity.getTicksLived() >= ticksToRemoval
                        && entity.getChunk().getEntities().length > 2
                        && !(entity instanceof Minecart)) {

                    entity.remove();
                    entityRemovalCount++;

                }
            }
        }

        if (entityRemovalCount > 1 || entityRemovalCount == 0) {
            Bukkit.getServer().sendMessage(Component.text().content("**************************************************************")
                    .decorate(TextDecoration.BOLD)
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("                    Cleared " + entityRemovalCount + " Entities"))
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("**************************************************************")
                            .decorate(TextDecoration.BOLD)).color(TextColor.color(UtilClass.error)).build());
        } else {
            Bukkit.getServer().sendMessage(Component.text().content("**************************************************************")
                    .decorate(TextDecoration.BOLD)
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("                    Cleared " + entityRemovalCount + " Entity"))
                    .append(Component.text("\n\n"))
                    .append(Component.text().content("**************************************************************")
                            .decorate(TextDecoration.BOLD)).color(TextColor.color(UtilClass.error)).build());
        }

    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        if (enabled) {
            event.setCancelled(true);
            UtilClass.sendPlayerMessage(event.getPlayer(), "(!) Sorry we're currently clearing some lag" +
                    " and don't want your items deleted, Please try again in a minute", UtilClass.error);
        }
    }

    @EventHandler
    public void onPrimed(TNTPrimeEvent event) {

        if (enabled) {
            event.setCancelled(true);
            if (event.getPrimerEntity() instanceof Player) {
                UtilClass.sendPlayerMessage((Player) event.getPrimerEntity(),
                        "(!) Sorry we're currently clearing some lag, Please try to ignite again in a minute",
                        UtilClass.error);
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
