package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;

public class enableCommand extends SubCommand {
    public enableCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "enable";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/gck enable <argument>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
        } else {
            String username = player.getName();
            UUID uuid = player.getUniqueId();

            if (player.isOp()) {
                if (args[1].equalsIgnoreCase("key_drop")) {
                    if (gck.getConfig().getBoolean("config.enabled")) {
                        player.sendMessage(ChatColor.RED + "이미 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.RED + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.RED + " 되어있습니다!");
                    } else {
                        gck.getConfig().set("config.enabled", true);

                        gck.saveConfig();

                        player.sendMessage(ChatColor.AQUA + "이제 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.AQUA + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.AQUA + " 되었습니다!");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.AQUA + username + ChatColor.WHITE + " 님에 의해 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.WHITE + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되었습니다!");
                            if (gck.getConfig().getBoolean("config.drop_boost.enable")) {
                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                if (gck.getConfig().getInt("config.drop_boost.amount") < 1) {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                } else {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.WHITE + " 개의 " + ChatColor.YELLOW + "열쇠 추가 드랍" + ChatColor.WHITE + "이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                }
                                if (gck.getConfig().getInt("config.drop_boost.chance_fixed") <= 0) {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                } else {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + (gck.getConfig().getInt("config.drop_boost.chance_fixed") / gck.getConfig().getInt("config.max_chance") * 100) + ChatColor.WHITE + "% 만큼의 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                }
                                if (gck.getConfig().getInt("config.drop_boost.chance_multiplier") < 0) {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                } else {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance_fixed") + ChatColor.WHITE + "배 만큼의 " + ChatColor.YELLOW + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                    p.sendMessage(ChatColor.RED + "[경고] 1 미만의 배수" + ChatColor.WHITE + "를 " + ChatColor.RED + "조심" + ChatColor.WHITE + "하세요!");
                                }
                            } else {
                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE + "열쇠 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                            }
                        }
                        conLog("key_drop enabled by OP Player " + username + ". (UUID : " + uuid + ")");
                    }
                } else if (args[1].equalsIgnoreCase("drop_boost")) {
                    if (gck.getConfig().getBoolean("config.drop_boost.enabled")) {
                        player.sendMessage(ChatColor.RED + "이미 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.RED + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.RED + " 되어있습니다!");
                    } else {
                        gck.getConfig().set("config.drop_boost.enabled", true);

                        gck.saveConfig();

                        player.sendMessage(ChatColor.AQUA + "이제 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.AQUA + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.AQUA + " 되었습니다!");
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.AQUA + username + ChatColor.WHITE + " 님에 의해 " + ChatColor.YELLOW + "열쇠 드랍 부스트" + ChatColor.WHITE + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되었습니다!");
                            if (gck.getConfig().getBoolean("config.enabled")) {
                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.WHITE + "이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                if (gck.getConfig().getInt("config.drop_boost.amount") < 1) {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                } else {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.WHITE + " 개의 " + ChatColor.YELLOW + "열쇠 추가 드랍" + ChatColor.WHITE + "이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                }
                                if (gck.getConfig().getInt("config.drop_boost.chance_fixed") <= 0) {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                } else {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + (gck.getConfig().getInt("config.drop_boost.chance_fixed") / gck.getConfig().getInt("config.max_chance") * 100) + ChatColor.WHITE + "% 만큼의 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                }
                                if (gck.getConfig().getInt("config.drop_boost.chance_multiplier") < 0) {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                } else {
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance_fixed") + ChatColor.WHITE + "배 만큼의 " + ChatColor.YELLOW + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                    p.sendMessage(ChatColor.RED + "[경고] 1 미만의 배수" + ChatColor.WHITE + "를 " + ChatColor.RED + "조심" + ChatColor.WHITE + "하세요!");
                                }
                            } else {
                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE + "열쇠 드랍" + ChatColor.WHITE + "은 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                            }
                        }
                        conLog("drop_chance enabled by OP Player " + username + ". (UUID : " + uuid + ")");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                conLog(username + "(UUID :" + uuid + ") tried to use /gck enable without OP.");
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
