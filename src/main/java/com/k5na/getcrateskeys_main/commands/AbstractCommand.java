package com.k5na.getcrateskeys_main.commands;

import com.k5na.getcrateskeys_main.GetCratesKeys_main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public abstract class AbstractCommand implements TabExecutor {
    protected GetCratesKeys_main gck;
    private final String label;

    public AbstractCommand(GetCratesKeys_main plugin, String commandLabel) {
        this.gck = plugin;
        this.label = commandLabel;
    }

    public String getLabel() {
        return label;
    }

    public abstract List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);
}
