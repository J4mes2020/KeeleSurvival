package dev.joey.keelesurvival.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static dev.joey.keelesurvival.util.UtilClass.keeleSurvival;

public class ConfigFileHandler {

    static File chestFile;
    static FileConfiguration chestConfig;

    public FileConfiguration getChestFile() {
        return chestConfig;
    }

    public void createChestFile() {

        chestFile = new File(keeleSurvival.getDataFolder(), "chests.yml");
        if (!chestFile.exists()) {
            chestFile.getParentFile().mkdirs();
            keeleSurvival.saveResource("chests.yml", false);
        }

        chestConfig = new YamlConfiguration();
        try {
            chestConfig.load(chestFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void saveFile() {

        try {
            chestConfig.save(chestFile);
        } catch (IOException e) {
            System.out.println("Couldn't save the file");
        }

    }


}
