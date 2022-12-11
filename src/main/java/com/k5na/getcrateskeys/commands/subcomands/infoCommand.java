package com.k5na.getcrateskeys.commands.subcomands;


import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class infoCommand extends SubCommand {
    public infoCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Show information of GCK plugin.";
    }

    @Override
    public String getSyntax() {
        return "/gck info";
    }

    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "=====================================================");
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + " - 플러그인 이름" + ChatColor.WHITE + " : " + gck.getPdfFile().getName());
        player.sendMessage(ChatColor.GOLD + " - 플러그인 버전" + ChatColor.WHITE + " : " + gck.getPdfFile().getVersion());
        player.sendMessage(ChatColor.GOLD + " - 플러그인 만든 사람" + ChatColor.WHITE + " : " + gck.getPdfFile().getAuthors());
        player.sendMessage(ChatColor.GOLD + " - 설명" + ChatColor.WHITE + " : " + gck.getPdfFile().getDescription());
        player.sendMessage("");
        player.sendMessage(ChatColor.GRAY + "=====================================================");
        player.sendMessage("");
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
