package dev.joey.keelesurvival;

import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleSurvival extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        saveConfig();
    }


}
