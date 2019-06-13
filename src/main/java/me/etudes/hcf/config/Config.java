package me.etudes.hcf.config;

import me.etudes.hcf.main.HCF;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Config {

    protected HCF plugin;

    protected FileConfiguration config;
    protected File file;

    protected String fileName;

    public Config(HCF plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
    }

    protected void setup() {
        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        file = new File(plugin.getDataFolder(), fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getServer().getLogger().info(fileName + " has successfully been created");
            } catch(IOException e) {
                Bukkit.getServer().getLogger().severe("Error: could not create file " + fileName);
            }
        }

        reloadConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(file);
            Bukkit.getServer().getLogger().info(fileName + " has been saved");
        } catch(IOException e) {
            Bukkit.getServer().getLogger().severe("Error: could not save file " + fileName);
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        Bukkit.getServer().getLogger().info(fileName + " has been reloaded");
    }

}
