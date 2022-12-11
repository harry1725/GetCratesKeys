package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class boostCommand extends SubCommand {
    public boostCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "boost";
    }

    @Override
    public String getDescription() {
        return "Show the current state of Key Drops.";
    }

    @Override
    public String getSyntax() {
        return "/gck boost";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (gck.getConfig().getBoolean("config.enabled")) {
            player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.WHITE + "이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
            if (gck.getConfig().getBoolean("config.drop_boost.enabled")) {
                player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");

                if (gck.getConfig().getInt("config.drop_boost.amount") < 1) {
                    player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                } else {
                    player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                    player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.WHITE + " 개의 " + ChatColor.YELLOW + "열쇠 추가 드랍" + ChatColor.WHITE + "이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                }
                if (gck.getConfig().getInt("config.drop_boost.chance") == 0) {
                    player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                } else {
                    player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                    if (gck.getConfig().getInt("config.drop_boost.chance") < 0) {
                        player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.RED + gck.getConfig().getInt("config.drop_boost.chance") + ChatColor.RED + "%" + ChatColor.WHITE + "만큼의 " + ChatColor.YELLOW + "열쇠 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                    } else {
                        player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.GREEN + "+" + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance") + ChatColor.GREEN + "%" + ChatColor.WHITE + "만큼의 " + ChatColor.YELLOW + "열쇠 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                    }
                }
            } else {
                player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.WHITE + "열쇠 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
            }
        } else {
            player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.WHITE + "열쇠 드랍" + ChatColor.WHITE + "은 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
