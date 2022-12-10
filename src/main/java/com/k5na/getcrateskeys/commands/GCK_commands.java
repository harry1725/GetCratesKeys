package com.k5na.getcrateskeys.commands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.subcomands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GCK_commands extends AbstractCommand implements TabExecutor {
    protected GetCratesKeys gck;

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public GCK_commands(GetCratesKeys plugin, String commandLabel) {
        super(plugin, commandLabel);

        subcommands.add(new infoCommand());
        subcommands.add(new helpCommand());
        subcommands.add(new keysCommand());
        subcommands.add(new reloadCommand());
        subcommands.add(new enableCommand());
        subcommands.add(new disableCommand());
        subcommands.add(new setCommand());
        subcommands.add(new ceilingCommand());
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        if (args.length == 1) { //gck <subcommand> <args1> <args2>
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;
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
                        super.gck.reloadConfig();
                        gck.saveDefaultConfig();
                        gck.getConfig().options().copyDefaults(true);
                        gck.saveConfig();

                        gck.reloadKeysConfig();
                        gck.reloadActsConfig();
                        gck.reloadCeilConfig();

                        sender.sendMessage(ChatColor.AQUA + "Config has been reloaded. If it doesn't apply, please restart the server to make it sure working.");
                    }
                }
            }
        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subcommands;
    }
}
