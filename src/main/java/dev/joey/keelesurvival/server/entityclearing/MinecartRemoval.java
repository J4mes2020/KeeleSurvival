package dev.joey.keelesurvival.server.entityclearing;

import dev.joey.keelesurvival.KeeleSurvival;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class MinecartRemoval implements Listener {

    public MinecartRemoval() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {

        if (event.getVehicle() instanceof RideableMinecart) {

            RideableMinecart minecart = (RideableMinecart) event.getVehicle();
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
                            entity.getWorld().dropItem(new Location(
                                            entity.getWorld(),
                                            entity.getLocation().getX(),
                                            entity.getLocation().getY() + 1,
                                            entity.getLocation().getZ()),
                                    new ItemStack(Material.MINECART));
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

}
