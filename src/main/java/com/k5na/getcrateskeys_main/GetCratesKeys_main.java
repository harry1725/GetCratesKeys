package com.k5na.getcrateskeys_main;

import com.k5na.getcrateskeys_main.commands.GCK_commands;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;
import java.util.logging.Logger;

public final class GetCratesKeys_main extends JavaPlugin implements Listener {

    public final Logger logger = Logger.getLogger("Minecraft");

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


    @Override
    public void onEnable() {
        GCK_commands cmd_gck = new GCK_commands(this, "gck");

        Objects.requireNonNull(getCommand(cmd_gck.getLabel())).setExecutor(cmd_gck);
        Objects.requireNonNull(getCommand(cmd_gck.getLabel())).setTabCompleter(cmd_gck);
        console(ChatColor.WHITE + "Commands " + ChatColor.YELLOW + "/gck" + ChatColor.WHITE + "has been added!");

        pManager.registerEvents(new GCK_events(this), this);
        console(ChatColor.WHITE + "GCK_events" + ChatColor.WHITE + " have been added!");

        //.key 파일 불러오기

        gck.console(ChatColor.YELLOW + pfName + ChatColor.WHITE + " is now enabled!");

        super.onEnable();
    }

    @Override
    public void onDisable() {
        //.key 파일 저장

        gck.console(ChatColor.YELLOW + pfName + ChatColor.WHITE + " is now disabled!");
        super.onDisable();
    }
}
