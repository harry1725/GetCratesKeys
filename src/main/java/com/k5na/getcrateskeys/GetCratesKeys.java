package com.k5na.getcrateskeys;

import com.k5na.getcrateskeys.commands.GCK_commands;
import com.k5na.getcrateskeys.events.GCK_events;
import com.k5na.getcrateskeys.expansions.GCK_expansions;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class GetCratesKeys extends JavaPlugin implements Listener {
    public static void conLog(final String msg) {
        GetCratesKeys.getPlugin(GetCratesKeys.class).getLogger().info(msg);
    }

    PluginDescriptionFile pdfFile = this.getDescription();

    PluginManager pManager = Bukkit.getPluginManager();

    String pfName = pdfFile.getName() + " v" + pdfFile.getVersion();

    public PluginDescriptionFile getPdfFile() {
        return pdfFile;
    }

    public String getFullName() {
        return pfName;
    }

    public static void console(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

    public File keysConfigFile;
    public File actsConfigFile;
    public File ceilConfigFile;

    public FileConfiguration keysConfig;
    public FileConfiguration actsConfig;
    public FileConfiguration ceilConfig;

    public void createKeysConfig() {
        keysConfigFile = new File(getDataFolder(), "keys.yml");

        if (!keysConfigFile.exists()) {
            keysConfigFile.getParentFile().mkdirs();
            saveResource("keys.yml", false);
        }

        keysConfig = new YamlConfiguration();

        try {
            keysConfig.load(keysConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void createActsConfig() {
        actsConfigFile = new File(getDataFolder(), "actions.yml");

        if (!actsConfigFile.exists()) {
            actsConfigFile.getParentFile().mkdirs();
            saveResource("actions.yml", false);
        }

        actsConfig = new YamlConfiguration();

        try {
            actsConfig.load(actsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void createCeilConfig() {
        ceilConfigFile = new File(getDataFolder(), "ceiling.yml");

        if (!ceilConfigFile.exists()) {
            ceilConfigFile.getParentFile().mkdirs();
            saveResource("ceiling.yml", false);
        }

        ceilConfig = new YamlConfiguration();

        try {
            ceilConfig.load(ceilConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
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

    public void saveKeysConfig() {
        try {
            getKeysConfig().save(keysConfigFile);
        } catch (IOException e){
            conLog("An Error Occurred During Saving keys.yml Config File");
        }
    }

    public void saveActsConfig() {
        try {
            getActsConfig().save(actsConfigFile);
        } catch (IOException e){
            conLog("An Error Occurred During Saving actions.yml Config File");
        }
    }

    public void saveCeilConfig() {
        try {
            getCeilConfig().save(ceilConfigFile);
        } catch (IOException e){
            conLog("An Error Occurred During Saving ceiling.yml Config File");
        }
    }

    @Override
    public void onEnable() {
        GCK_commands cmd_gck = new GCK_commands(this, "gck");

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
            saveConfig();
        } else {
            saveConfig();
        }

        createKeysConfig();
        createActsConfig();
        createCeilConfig();

        Objects.requireNonNull(getCommand(cmd_gck.getLabel())).setExecutor(cmd_gck);
        Objects.requireNonNull(getCommand(cmd_gck.getLabel())).setTabCompleter(cmd_gck);
        console(ChatColor.WHITE + "Commands " + ChatColor.YELLOW + "/gck" + ChatColor.WHITE + " has been added!");

        GetCratesKeys.console(ChatColor.YELLOW + getFullName() + ChatColor.WHITE + " is now enabled!");

        super.onEnable();
    }

    @Override
    public void onDisable() {
        saveKeysConfig();
        saveActsConfig();
        saveCeilConfig();

        GetCratesKeys.console(ChatColor.YELLOW + getFullName() + ChatColor.WHITE + " is now disabled!");
        super.onDisable();
    }
}
