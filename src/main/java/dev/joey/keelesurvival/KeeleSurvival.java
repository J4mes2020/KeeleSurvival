package dev.joey.keelesurvival;

import dev.joey.keelesurvival.managers.CommandManager;
import dev.joey.keelesurvival.managers.ListenerManager;
import dev.joey.keelesurvival.server.economy.provider.EconomyProvider;
import dev.joey.keelesurvival.util.UtilClass;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class KeeleSurvival extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;

    @Override
    public void onEnable() {

        UtilClass.keeleSurvival = this;

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        saveDefaultConfig();
        new CommandManager();
        new ListenerManager();

    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        getServer().getServicesManager().register(Economy.class, new EconomyProvider(), this, ServicePriority.Highest);
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public KeeleSurvival getInstance() {
        return getPlugin(KeeleSurvival.class);
    }
}
