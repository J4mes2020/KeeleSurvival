package dev.joey.keelesurvival.events;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.util.Chance;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

public class PlayerEvents implements Listener {

    public PlayerEvents(KeeleSurvival keeleSurvival) {

        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);

    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getPlayer();

        dropHeadOnDeath(player.getKiller(), player);

    }

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {

        if (event.getVehicle() instanceof RideableMinecart) {

            RideableMinecart minecart = (RideableMinecart) event.getVehicle();

            //CHECK THE BELOW BLOCK IF RAILS AND USE THAT FOR VELOCITY

            if (!minecart.isEmpty()) {

                Player player = null;

                for (Entity passenger : event.getVehicle().getPassengers()) {

                    if (passenger instanceof Player) {

                        player = (Player) passenger;
                        break;
                    }

                }

                if (player != null) {


                    Location minecartLocation = event.getTo();
                    double minecartX = minecartLocation.getX();
                    double minecartZ = minecartLocation.getZ();

                    Location minecartFromLocation = event.getFrom();
                    double minecartFromX = minecartFromLocation.getX();
                    double minecartFromZ = minecartFromLocation.getZ();


                    for (Entity entity : minecartLocation.getNearbyEntities(1, 1, 1)) {

                        if (entity instanceof Mob) {

                            if (!entity.isInsideVehicle() && entity.isEmpty()) {
                                // Entity is a monster, animal, NPC, slime, or golem, is not riding in a vehicle and has no passengers.
                                // Move the entity further away from the minecart.
                                // This may kill the entity (sucks to be him!).

                                Location entityLocation = entity.getLocation();
                                double entityX = entityLocation.getX();
                                double entityZ = entityLocation.getZ();
                                double newX = entityX;
                                double newZ = entityZ;

                                if ((minecartFromX == minecartX) || (minecartFromZ == minecartZ)) {
                                    // Minecart moving straight, move entity further away on a diagonal by changing X and Z

                                    if (entityX < minecartX)
                                        newX = entityX - 1.0;
                                    else
                                        newX = entityX + 1.0;

                                    if (entityZ < minecartZ)
                                        newZ = entityZ - 1.0;
                                    else
                                        newZ = entityZ + 1.0;
                                } else {
                                    // Minecart moving on a diagonal, so move entity to the side by changing X or Z

                                    if (Math.abs(minecartX - entityX) > Math.abs(minecartZ - entityZ)) {
                                        if (entityX < minecartX)
                                            newX = entityX - 1.0;
                                        else
                                            newX = entityX + 1.0;
                                    } else {
                                        if (entityZ < minecartZ)
                                            newZ = entityZ - 1.0;
                                        else
                                            newZ = entityZ + 1.0;
                                    }
                                }

                                entityLocation.setX(newX);
                                entityLocation.setZ(newZ);
                                entity.teleport(entityLocation);

                            }

                        } else if (entity instanceof RideableMinecart && entity.isEmpty()) {
                            entity.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.MINECART));
                            entity.remove();
                        }

                    }

                }

            }
        }
    }

    @EventHandler
    public void onCreate(VehicleCreateEvent event) {

        RideableMinecart minecart = (RideableMinecart) event.getVehicle();
        minecart.setMaxSpeed(0.5D);


    }


    private void dropHeadOnDeath(Player killer, Player victim) {

        if (killer != null && Chance.percentageChance(0.02D)) {

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(victim);
            head.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);


        }

    }

}
