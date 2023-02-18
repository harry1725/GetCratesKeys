package com.k5na.getcrateskeys;

import com.k5na.getcrateskeys.commands.GCK_commands;
import com.k5na.getcrateskeys.events.GCK_events;
import com.k5na.getcrateskeys.expansions.GCK_expansions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class GetCratesKeys extends JavaPlugin implements Listener {
    public File keysConfigFile;
    public File actsConfigFile;
    public File ceilConfigFile;
    public File plcdConfigFile;
    public FileConfiguration keysConfig;
    public FileConfiguration actsConfig;
    public FileConfiguration ceilConfig;
    public FileConfiguration plcdConfig;
    PluginDescriptionFile pdfFile = this.getDescription();
    PluginManager pManager = Bukkit.getPluginManager();
    String pfName = pdfFile.getName() + " v" + pdfFile.getVersion();
    boolean wasSuccessful;

    public static void conLog(final String msg) {
        GetCratesKeys.getPlugin(GetCratesKeys.class).getLogger().info(msg);
    }

    public static void console(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    public PluginDescriptionFile getPdfFile() {
        return pdfFile;
    }

    public String getFullName() {
        return pfName;
    }

    public void createKeysConfig() {
        keysConfigFile = new File(getDataFolder(), "keys.yml");

        if (!keysConfigFile.exists()) {
            wasSuccessful = keysConfigFile.getParentFile().mkdirs();
            saveResource("keys.yml", true);
        }

        keysConfig = YamlConfiguration.loadConfiguration(keysConfigFile);
    }

    public void createActsConfig() {
        actsConfigFile = new File(getDataFolder(), "actions.yml");

        if (!actsConfigFile.exists()) {
            wasSuccessful = actsConfigFile.getParentFile().mkdirs();
            saveResource("actions.yml", true);
        }

        actsConfig = YamlConfiguration.loadConfiguration(actsConfigFile);
    }

    public void createCeilConfig() {
        ceilConfigFile = new File(getDataFolder(), "ceiling.yml");

        if (!ceilConfigFile.exists()) {
            wasSuccessful = ceilConfigFile.getParentFile().mkdirs();
            saveResource("ceiling.yml", true);
        }

        ceilConfig = YamlConfiguration.loadConfiguration(ceilConfigFile);
    }

    public void createPlcdConfig() {
        plcdConfigFile = new File(getDataFolder(), "placed.yml");

        if (!plcdConfigFile.exists()) {
            wasSuccessful = plcdConfigFile.getParentFile().mkdirs();
            saveResource("placed.yml", true);
        }

        plcdConfig = YamlConfiguration.loadConfiguration(plcdConfigFile);
    }

    public FileConfiguration getKeysConfig() {
        return this.keysConfig;
    }

    public FileConfiguration getActsConfig() {
        return this.actsConfig;
    }

    public FileConfiguration getCeilConfig() {
        return this.ceilConfig;
    }

    public FileConfiguration getPlcdConfig() {
        return this.plcdConfig;
    }

    public void reloadKeysConfig() {
        if (keysConfigFile == null) {
            keysConfigFile = new File(getDataFolder(), "keys.yml");
        }

        keysConfig = YamlConfiguration.loadConfiguration(keysConfigFile);
    }

    public void reloadActsConfig() {
        if (actsConfigFile == null) {
            actsConfigFile = new File(getDataFolder(), "actions.yml");
        }

        actsConfig = YamlConfiguration.loadConfiguration(actsConfigFile);
    }

    public void reloadCeilConfig() {
        if (ceilConfigFile == null) {
            ceilConfigFile = new File(getDataFolder(), "ceiling.yml");
        }

        ceilConfig = YamlConfiguration.loadConfiguration(ceilConfigFile);
    }

    public void reloadPlcdConfig() {
        if (plcdConfigFile == null) {
            plcdConfigFile = new File(getDataFolder(), "placed.yml");
        }

        plcdConfig = YamlConfiguration.loadConfiguration(plcdConfigFile);
    }

    public void saveKeysConfig() {
        try {
            getKeysConfig().save(keysConfigFile);
        } catch (IOException e) {
            conLog("An Error Occurred During Saving keys.yml Config File");
        }
    }

    public void saveActsConfig() {
        try {
            getActsConfig().save(actsConfigFile);
        } catch (IOException e) {
            conLog("An Error Occurred During Saving actions.yml Config File");
        }
    }

    public void saveCeilConfig() {
        try {
            getCeilConfig().save(ceilConfigFile);
        } catch (IOException e) {
            conLog("An Error Occurred During Saving ceiling.yml Config File");
        }
    }

    public void savePlcdConfig() {
        try {
            getPlcdConfig().save(plcdConfigFile);
        } catch (IOException e) {
            conLog("An Error Occurred During Saving placed.yml Config File");
        }
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new GCK_expansions(this).register();
            console(ChatColor.GOLD + "GCK_expansions" + ChatColor.WHITE + " have been added!");

            pManager.registerEvents(new GCK_events(this), this);
            console(ChatColor.GOLD + "GCK_events" + ChatColor.WHITE + " have been added!");
        } else {
            conLog("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (!getDataFolder().exists()) {
            getConfig().options().copyDefaults(true);
        }
        saveConfig();

        createKeysConfig();
        createActsConfig();
        createCeilConfig();
        createPlcdConfig();

        Objects.requireNonNull(getCommand("gck")).setExecutor(new GCK_commands(this));
        console(ChatColor.WHITE + "Commands " + ChatColor.YELLOW + "/gck" + ChatColor.WHITE + " has been added!");

        GetCratesKeys.console(ChatColor.YELLOW + getFullName() + ChatColor.WHITE + " is now enabled!");

        super.onEnable();
    }

    @Override
    public void onDisable() {
        saveConfig();
        saveKeysConfig();
        saveActsConfig();
        saveCeilConfig();
        savePlcdConfig();

        GetCratesKeys.console(ChatColor.YELLOW + getFullName() + ChatColor.WHITE + " is now disabled!");
        super.onDisable();
    }
}
