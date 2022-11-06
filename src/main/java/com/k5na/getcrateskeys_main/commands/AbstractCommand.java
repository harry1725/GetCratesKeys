package com.k5na.getcrateskeys_main.commands;

import com.k5na.getcrateskeys_main.GetCratesKeys;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractCommand implements TabExecutor {
    protected GetCratesKeys gck;
    private final String label;

    public AbstractCommand(GetCratesKeys plugin, String commandLabel) {
        this.gck = plugin;
        this.label = commandLabel;
    }

    public String getLabel() {
        return label;
    }

    public abstract List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args);
    public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args);
}
