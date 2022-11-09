package dev.joey.keelesurvival;

import dev.joey.keelesurvival.clearlag.ClearLag;
import dev.joey.keelesurvival.events.PlayerEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleSurvival extends JavaPlugin {

    @Override
    public void onEnable() {
        new PlayerEvents(this);
        new ClearLag(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
