package com.k5na.getcrateskeys.commands;

import com.k5na.getcrateskeys.GetCratesKeys;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {
    public static GetCratesKeys gck;

    public SubCommand(GetCratesKeys plugin) {
        gck = plugin;
    }

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(Player player, String[] args);

    public abstract List<String> getSubCommandArguments(Player player, String[] args);
}
