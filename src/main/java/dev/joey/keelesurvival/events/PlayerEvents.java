package dev.joey.keelesurvival.events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.events.headdrop.HeadSelector;
import dev.joey.keelesurvival.util.Chance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;


public class PlayerEvents implements Listener {

    String encodedTexture;

    public PlayerEvents(KeeleSurvival keeleSurvival) {

        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);

    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getPlayer();

        dropPlayerHeadOnDeath(player.getKiller(), player);

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        dropMobHeadOnDeath(event.getEntity().getKiller(), event.getEntity());

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


    private void dropPlayerHeadOnDeath(Player killer, Player victim) {

        if (killer != null && Chance.percentageChance(0.02D) && killer.getUniqueId() != victim.getUniqueId()) {

            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(victim);
            head.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);


        }

    }

    private void dropMobHeadOnDeath(Player killer, LivingEntity victim) {


        if (killer != null && Chance.percentageChance(0.02D) && !(victim instanceof Player)) {
            if (victim instanceof Ageable) {
                Ageable ageable = (Ageable) victim;
                if (!ageable.isAdult()) {
                    return;
                }

            }

            if (victim instanceof EnderDragon) {
                victim.getWorld().dropItemNaturally(victim.getLocation(), new ItemStack(Material.DRAGON_HEAD));
                return;
            }
            HeadSelector headSelector = new HeadSelector(victim);
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", headSelector.getEncodedTexture()));
            Field profileField;
            try {
                profileField = meta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(meta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
                e1.printStackTrace();
            }


            meta.displayName(Component.text().content(victim.getName() + " Head").style(Style.style(TextColor.color(255, 255, 85), TextDecoration.ITALIC)).build());
            head.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);


        }

    }
}


