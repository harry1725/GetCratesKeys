package com.k5na.getcrateskeys.commands;

import com.k5na.getcrateskeys.GetCratesKeys;
import org.bukkit.command.TabExecutor;

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
}
