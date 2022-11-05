package com.k5na.getcrateskeys_main;

import com.k5na.getcrateskeys_main.commands.GCK_commands;
import com.k5na.getcrateskeys_main.events.GCK_events;
import com.k5na.getcrateskeys_main.expansions.GCK_expansions;
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

public final class GetCratesKeys_main extends JavaPlugin implements Listener {
    public static void conLog(final String msg) {
        GetCratesKeys_main.getPlugin(GetCratesKeys_main.class).getLogger().info(msg);
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

    File keyFile = new File(getDataFolder().getPath() + "/", "keys.yml");
    YamlConfiguration kyFileConfig = YamlConfiguration.loadConfiguration(keyFile);
    File actionFile = new File(getDataFolder().getPath() + "/", "action.yml");
    YamlConfiguration atFileConfig = YamlConfiguration.loadConfiguration(actionFile);
    File ceilingFile = new File(getDataFolder().getPath() + "/", "ceiling.yml");
    YamlConfiguration ciFileConfig = YamlConfiguration.loadConfiguration(ceilingFile);

    @Override
    public void onEnable() {
        GCK_commands cmd_gck = new GCK_commands(this, "gck");

        Objects.requireNonNull(getCommand(cmd_gck.getLabel())).setExecutor(cmd_gck);
        Objects.requireNonNull(getCommand(cmd_gck.getLabel())).setTabCompleter(cmd_gck);
        console(ChatColor.WHITE + "Commands " + ChatColor.YELLOW + "/gck" + ChatColor.WHITE + "has been added!");

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new GCK_expansions(this).register();
            console(ChatColor.GOLD + "GCK_expansions" + ChatColor.WHITE + " have been added!");

            pManager.registerEvents(new GCK_events(this), this);
            console(ChatColor.GOLD + "GCK_events" + ChatColor.WHITE + " have been added!");
        } else {
            conLog("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        kyFileConfig.options().copyDefaults(true);
        try {
            kyFileConfig.save(keyFile);
        } catch (IOException e) {
            conLog("File keyFile could not overwritten or created. Fm:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }
        atFileConfig.options().copyDefaults(true);
        try {
            atFileConfig.save(actionFile);
        } catch (IOException e) {
            conLog("File actionFile could not overwritten or created. Fm:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }
        ciFileConfig.options().copyDefaults(true);
        try {
            ciFileConfig.save(ceilingFile);
        } catch (IOException e) {
            conLog("File ceilingFile could not overwritten or created. Fm:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }

        GetCratesKeys_main.console(ChatColor.YELLOW + getFullName() + ChatColor.WHITE + " is now enabled!");

        super.onEnable();
    }

    @Override
    public void onDisable() {
        kyFileConfig.options().copyDefaults(true);
        try {
            kyFileConfig.save(keyFile);
        } catch (IOException e) {
            conLog("File keyFile could not overwritten or created. Fm:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }
        atFileConfig.options().copyDefaults(true);
        try {
            atFileConfig.save(actionFile);
        } catch (IOException e) {
            conLog("File actionFile could not overwritten or created. Fm:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }
        ciFileConfig.options().copyDefaults(true);
        try {
            ciFileConfig.save(ceilingFile);
        } catch (IOException e) {
            conLog("File ceilingFile could not overwritten or created. Fm:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }

        GetCratesKeys_main.console(ChatColor.YELLOW + getFullName() + ChatColor.WHITE + " is now disabled!");
        super.onDisable();
    }
}
