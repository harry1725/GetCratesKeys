package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ceilingCommand extends SubCommand {
    @Override
    public String getName() {
        return "ceiling";
    }

    @Override
    public String getDescription() {
        return "Check the current ceiling state of the player.";
    }

    @Override
    public String getSyntax() {
        return "/gck ceiling";
    }

    @Override
    public void perform(Player player, String[] args) {
        UUID uuid = player.getUniqueId();

        if (gck.getConfig().getBoolean("config.ceiling.enabled")) {
            player.sendMessage(ChatColor.GREEN + "현재 서버에서 천장 시스템이 활성화 되어있습니다.");
        } else {
            player.sendMessage(ChatColor.RED + "현재 서버에서 천장 시스템이 비활성화 되어있습니다.");
        }

        if (gck.getActsConfig().getBoolean("farming.enabled")) {
            player.sendMessage(ChatColor.GOLD + "농사" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".farming") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.farming_max") + ChatColor.WHITE + " 회");
        }
        if (gck.getActsConfig().getBoolean("excavation.enabled")) {
            player.sendMessage(ChatColor.DARK_AQUA + "삽질" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".excavation") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.excavation_max") + ChatColor.WHITE + " 회");
        }
        if (gck.getActsConfig().getBoolean("mining.enabled")) {
            player.sendMessage(ChatColor.GRAY + "광질" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".mining") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.mining_max") + ChatColor.WHITE + " 회");
        }
        if (gck.getActsConfig().getBoolean("foraging.enabled")) {
            player.sendMessage(ChatColor.DARK_GREEN + "벌목" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".foraging") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.foraging_max") + ChatColor.WHITE + " 회");
        }
        if (gck.getActsConfig().getBoolean("fishing.enabled")) {
            player.sendMessage(ChatColor.AQUA + "낚시" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".fishing") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.fishing_max") + ChatColor.WHITE + " 회");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
