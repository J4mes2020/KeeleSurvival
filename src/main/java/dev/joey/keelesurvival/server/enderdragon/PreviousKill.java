package dev.joey.keelesurvival.server.enderdragon;

import dev.joey.keelesurvival.KeeleSurvival;
import dev.joey.keelesurvival.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class PreviousKill implements Listener {

    HashMap<UUID, Double> playerDamageDragonMap = new HashMap<>();

    public PreviousKill() {
        keeleSurvival.getServer().getPluginManager().registerEvents(this, keeleSurvival);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {

        if (event.getEntity() instanceof EnderDragon dragon) {
            if (dragon.getWorld().getEnvironment() == World.Environment.THE_END && dragon.getWorld().getEnderDragonBattle().hasBeenPreviouslyKilled()) {
                event.setDroppedExp(3000);
                List<UUID> topPlayers = UtilClass.sortByValue(playerDamageDragonMap, false).keySet().stream().toList();
                int potionEffectTime = 72000;

                for (int i = 0; i < topPlayers.size(); i++) {

                    Player player = Bukkit.getPlayer(topPlayers.get(i));

                    switch (i) {
                        case 0 -> {
                            player.getInventory().addItem(new ItemStack(Material.DRAGON_EGG));
                            for (int breathCount = 0; breathCount < 3; breathCount++) {
                                player.getInventory().addItem(new ItemStack(Material.DRAGON_BREATH));
                            }
                            player.giveExp(3000, true);
                            KeeleSurvival.getEconomy().depositPlayer(player, 30000);
                            player.sendMessage(Component.text().content("You did the most damage and received the dragon egg plus loot & extra perks")
                                    .color(TextColor.color(new Color(0, 255, 255).getRGB()))
                                    .decorate(TextDecoration.UNDERLINED));
                            player.addPotionEffects(List.of(
                                    new PotionEffect(PotionEffectType.HEALTH_BOOST, potionEffectTime, 2, true, false),
                                    new PotionEffect(PotionEffectType.LUCK, potionEffectTime, 2, true, false),
                                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE, keeleSurvival.getConfig().getInt("ticksToRespawn"), 1, true, false))
                            );
                            return;
                        }
                        case 1 -> {
                            player.getInventory().addItem(new ItemStack(Material.DRAGON_BREATH));
                            player.getInventory().addItem(new ItemStack(Material.DRAGON_BREATH));
                            player.giveExp(2000, true);
                            KeeleSurvival.getEconomy().depositPlayer(player, 20000);
                            player.sendMessage(Component.text().content("You did the 2nd most damage and received some loot & extra perks")
                                    .color(TextColor.color(new Color(0, 255, 255).getRGB()))
                                    .decorate(TextDecoration.UNDERLINED));

                            player.addPotionEffects(List.of(
                                    new PotionEffect(PotionEffectType.HEALTH_BOOST, potionEffectTime / 2, 1, true, false),
                                    new PotionEffect(PotionEffectType.LUCK, potionEffectTime, 2, true, false),
                                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE, keeleSurvival.getConfig().getInt("ticksToRespawn"), 1, true, false))
                            );
                            return;
                        }

                        case 2 -> {
                            player.getInventory().addItem(new ItemStack(Material.DRAGON_BREATH));
                            player.giveExp(1000, true);
                            KeeleSurvival.getEconomy().depositPlayer(player, 10000);
                            player.sendMessage(Component.text().content("You did the 3rd most damage and received some loot & extra perks")
                                    .color(TextColor.color(new Color(0, 255, 255).getRGB()))
                                    .decorate(TextDecoration.UNDERLINED));

                            player.addPotionEffects(List.of(
                                    new PotionEffect(PotionEffectType.HEALTH_BOOST, potionEffectTime / 3, 1, true, false),
                                    new PotionEffect(PotionEffectType.LUCK, potionEffectTime / 3, 1, true, false),
                                    new PotionEffect(PotionEffectType.INCREASE_DAMAGE, keeleSurvival.getConfig().getInt("ticksToRespawn"), 1, true, false))
                            );
                            return;
                        }
                        default -> {
                            player.giveExp(200, true);
                            KeeleSurvival.getEconomy().depositPlayer(player, 5000);
                            player.sendMessage(Component.text().content("You were in the top 10 for damages so you've received some loot")
                                    .color(TextColor.color(new Color(0, 255, 255).getRGB()))
                                    .decorate(TextDecoration.UNDERLINED));

                            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, keeleSurvival.getConfig().getInt("ticksToRespawn"), 1, true, false));

                        }
                    }
                }
            }

        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof EnderDragon dragon) {
            if (dragon.getWorld().getEnvironment() == World.Environment.THE_END && dragon.getWorld().getEnderDragonBattle().hasBeenPreviouslyKilled()) {
                if (event.getDamager() instanceof Player player) {

                    if (playerDamageDragonMap.containsKey(player.getUniqueId())) {
                        playerDamageDragonMap.replace(player.getUniqueId(), playerDamageDragonMap.get(player.getUniqueId()) + event.getDamage());
                    } else {
                        playerDamageDragonMap.put(player.getUniqueId(), event.getDamage());
                    }

                }

            }
        }
    }

    @EventHandler
    public void onPlace(PlayerInteractEvent event) {

        if (event.getItem() != null) {
            if (event.getPlayer().getWorld().getEnvironment() == World.Environment.THE_END &&
                    event.getAction().isRightClick() && event.getItem().getType() == Material.END_CRYSTAL) {
                event.setCancelled(true);
            }
        }

    }

}
