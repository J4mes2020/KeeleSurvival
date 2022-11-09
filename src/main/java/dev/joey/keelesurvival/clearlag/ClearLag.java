package dev.joey.keelesurvival.clearlag;

import dev.joey.keelesurvival.KeeleSurvival;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.ArrayList;

public class ClearLag {

    int clearTimer = 18000;
    int ticksToRemoval = 3000;
    ArrayList<Entity> entities = new ArrayList<>();
    int entityRemovalCount;

    public ClearLag(KeeleSurvival keeleSurvival) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(keeleSurvival, () -> {
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

            if (entityRemovalCount > 1) {
                Bukkit.getServer().sendMessage(Component.text().content("**************************************************************")
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text("\n\n"))
                        .append(Component.text().content("                    Cleared " + entityRemovalCount + " Entities"))
                        .append(Component.text("\n\n"))
                        .append(Component.text().content("**************************************************************")
                                .decorate(TextDecoration.BOLD)).color(TextColor.color(217, 36, 33)).build());
            }
            else {
                Bukkit.getServer().sendMessage(Component.text().content("**************************************************************")
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text("\n\n"))
                        .append(Component.text().content("                    Cleared " + entityRemovalCount + " Entity"))
                        .append(Component.text("\n\n"))
                        .append(Component.text().content("**************************************************************")
                                .decorate(TextDecoration.BOLD)).color(TextColor.color(217, 36, 33)).build());
            }


        }, 0, clearTimer);

    }

}
