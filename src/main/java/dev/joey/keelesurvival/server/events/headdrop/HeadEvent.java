package dev.joey.keelesurvival.server.events.headdrop;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.joey.keelesurvival.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class HeadEvent implements Listener {

    public HeadEvent() {
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

    private void dropPlayerHeadOnDeath(Player killer, Player victim) {

        if (killer != null && UtilClass.percentageChance(0.02D) && killer.getUniqueId() != victim.getUniqueId()) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) head.getItemMeta();
            meta.setOwningPlayer(victim);
            head.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);


        }

    }

    private void dropMobHeadOnDeath(Player killer, LivingEntity victim) {

        if (killer != null && UtilClass.percentageChance(0.02D) && !(victim instanceof Player)) {
            if (victim instanceof Ageable ageable) {
                if (!ageable.isAdult()) {
                    return;
                }
            }

            if (victim instanceof EnderDragon) {
                victim.getWorld().dropItemNaturally(victim.getLocation(), new ItemStack(Material.DRAGON_HEAD));
                return;
            }

            HeadDropping headSelector = new HeadDropping(victim);
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

            meta.displayName(Component.text()
                    .content(victim.getName() + " Head")
                    .color(TextColor.color(255, 255, 85))
                    .decoration(TextDecoration.ITALIC, false)
                    .build());
            head.setItemMeta(meta);
            victim.getWorld().dropItemNaturally(victim.getLocation(), head);


        }

    }

}
