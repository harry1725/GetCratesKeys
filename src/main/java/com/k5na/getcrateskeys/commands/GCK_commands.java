package com.k5na.getcrateskeys.commands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.subcomands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GCK_commands implements TabExecutor {
    public static GetCratesKeys gck;

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public GCK_commands(GetCratesKeys plugin) {
        gck = plugin;

        subCommands.add(new infoCommand(plugin));
        subCommands.add(new helpCommand(plugin));
        subCommands.add(new keysCommand(plugin));
        subCommands.add(new reloadCommand(plugin));
        subCommands.add(new enableCommand(plugin));
        subCommands.add(new disableCommand(plugin));
        subCommands.add(new setCommand(plugin));
        subCommands.add(new ceilingCommand(plugin));
        subCommands.add(new boostCommand(plugin));
        subCommands.add(new saveCommand(plugin));
        //subCommands.add(new autoSaveCommand(plugin));
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        if (args.length == 1) { //gck <subCommand> <args1> <args2>
            ArrayList<String> subCommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++) {
                subCommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subCommandsArguments;
        } else if (args.length >= 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubCommandArguments((Player) sender, args);
                }
            }
        }

        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).perform(player, args);
                    }
                }
            }
        } else {
            if (command.getName().equalsIgnoreCase("gck")) {
                if (args.length == 0) {
                    sender.sendMessage(ChatColor.RED + "Too few arguments inserted! Please retry or try other commands.");
                } else {
                    if (args[0].equalsIgnoreCase("test")) {
                        sender.sendMessage(ChatColor.GREEN + "Console command is working properly.");
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        gck.reloadConfig();
                        gck.saveDefaultConfig();
                        gck.getConfig().options().copyDefaults(true);
                        gck.saveConfig();

                        gck.saveKeysConfig();
                        gck.saveActsConfig();
                        gck.saveCeilConfig();
                        gck.savePlcdConfig();

                        gck.reloadKeysConfig();
                        gck.reloadActsConfig();
                        gck.reloadCeilConfig();
                        gck.reloadPlcdConfig();

                        sender.sendMessage(ChatColor.AQUA + "Config has been reloaded. If it doesn't apply, please restart the server to make it sure working.");
                    }
                }
            }
        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
