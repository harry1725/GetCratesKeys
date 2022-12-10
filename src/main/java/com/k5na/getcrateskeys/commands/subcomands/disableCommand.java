package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;

public class disableCommand extends SubCommand {
    @Override
    public String getName() {
        return "disable";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/gck disable <argument>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
        } else {
            String username = player.getName();
            UUID uuid = player.getUniqueId();

            if (player.isOp()) {
                if (args[1].equalsIgnoreCase("key_drop")) {
                    if (!gck.getConfig().getBoolean("config.enabled")) {
                        player.sendMessage(ChatColor.YELLOW + "이미 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.YELLOW + " 설정이 " + ChatColor.RED + "비활성화" + ChatColor.YELLOW + " 되어있습니다!");
                    } else {
                        gck.getConfig().set("config.enabled", false);

                        gck.saveConfig();

                        player.sendMessage(ChatColor.AQUA + "이제 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.AQUA + " 설정이 " + ChatColor.RED + "비활성화" + ChatColor.AQUA + " 되었습니다!");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.AQUA + username + ChatColor.WHITE + " 님에 의해 열쇠 드랍 설정이 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 되었습니다!");
                        }
                        conLog("key_drop disabled by OP Player " + username + ". (UUID : " + uuid + ")");
                    }
                } else if (args[1].equalsIgnoreCase("drop_boost")) {
                    if (!gck.getConfig().getBoolean("config.drop_boost.enabled")) {
                        player.sendMessage(ChatColor.YELLOW + "이미 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.YELLOW + " 설정이 " + ChatColor.RED + "비활성화" + ChatColor.YELLOW + " 되어있습니다!");
                    } else {
                        gck.getConfig().set("config.drop_boost.enabled", false);

                        gck.saveConfig();

                        player.sendMessage(ChatColor.AQUA + "이제 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.AQUA + " 설정이 " + ChatColor.RED + "비활성화" + ChatColor.AQUA + " 되었습니다!");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.AQUA + username + ChatColor.WHITE + " 님에 의해 " + ChatColor.YELLOW + "열쇠 드랍 부스트" + ChatColor.WHITE + " 설정이 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 되었습니다!");
                        }
                        conLog("drop_chance disabled by OP Player " + username + ". (UUID : " + uuid + ")");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                conLog(username + " (UUID :" + uuid + ") tried to use /gck disable without OP.");
            }
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            List<String> subcommandArgument = new ArrayList<>();

            subcommandArgument.add("key_drop");
            subcommandArgument.add("drop_boost");

            return subcommandArgument;
        }

        return null;
    }
}
