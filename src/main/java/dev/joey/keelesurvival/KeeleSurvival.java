package dev.joey.keelesurvival;

import dev.joey.keelesurvival.managers.CommandManager;
import dev.joey.keelesurvival.managers.ListenerManager;
import dev.joey.keelesurvival.server.economy.Storage;
import org.bukkit.plugin.java.JavaPlugin;

public final class KeeleSurvival extends JavaPlugin {

    @Override
    public void onEnable() {
        Storage.keeleSurvival = this;
        saveDefaultConfig();
        new CommandManager(this);
        new ListenerManager(this);

    }

    @Override
    public void onDisable() {
        saveConfig();
    }


}
