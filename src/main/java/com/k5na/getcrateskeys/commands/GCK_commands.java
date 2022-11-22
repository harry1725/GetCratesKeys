package com.k5na.getcrateskeys.commands;

import com.k5na.getcrateskeys.GetCratesKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;
import static com.k5na.getcrateskeys.GetCratesKeys.getLineNumber;

public class GCK_commands extends AbstractCommand {
    public GCK_commands(GetCratesKeys plugin, String commandLabel) {
        super(plugin, commandLabel);
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> tabs = new ArrayList<>();

        if (alias.length() == 0) {
            tabs.add("gck");
        }

        if (args[0].equalsIgnoreCase("gck")) {
            tabs.add("info");
            tabs.add("help");
            tabs.add("reload");
            tabs.add("chance");
            tabs.add("enable");
            tabs.add("disable");
            tabs.add("set");

            if (args[1].equalsIgnoreCase("help")) {
                tabs.add("info");
                tabs.add("reload");
                tabs.add("chance");
                tabs.add("enable");
                tabs.add("disable");
                tabs.add("set");
            } else if (args[1].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("disable")) {
                tabs.add("key_drop");
                tabs.add("drop_boost");
            } else if (args[1].equalsIgnoreCase("set")) {
                tabs.add("drop_boost_amount");
                tabs.add("drop_boost_chance_fixed");
                tabs.add("drop_boost_chance_multiplier");
            }
        }

        return tabs;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String username = player.getName();
            UUID uuid = player.getUniqueId();

            if (label.equalsIgnoreCase("gck")) {    // 베이스 명령어
                if (args.length < 1) {
                    player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                } else {
                    if (args[0].equalsIgnoreCase("info")) { // 플러그인 정보
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
                    } else if (args[0].equalsIgnoreCase("help")) {    // 명령어 목록
                        if (args.length < 2) {
                            player.sendMessage("");
                            player.sendMessage(ChatColor.GRAY + "=====================================================");
                            player.sendMessage("");
                            player.sendMessage(ChatColor.GOLD + gck.getFullName());
                            player.sendMessage(ChatColor.GOLD + "플러그인 명령어 목록" + ChatColor.WHITE + " :");
                            player.sendMessage(ChatColor.GREEN + "초록색 명령어" + ChatColor.YELLOW + "는 누구나 입력할 수 있고, ");
                            player.sendMessage(ChatColor.RED + "빨간색 명령어" + ChatColor.YELLOW + "는 입력할 때 OP가 필요합니다.");
                            player.sendMessage("");
                            player.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
                            player.sendMessage("");
                            player.sendMessage(ChatColor.GREEN + "/gck info");
                            player.sendMessage(ChatColor.GREEN + "/gck help [ keys / reload / enable / disable / set ]");
                            player.sendMessage(ChatColor.RED + "/gck reload");
                            player.sendMessage(ChatColor.RED + "/gck enable < key_drop / drop_boost >");
                            player.sendMessage(ChatColor.RED + "/gck disable < key_drop / drop_boost >");
                            player.sendMessage(ChatColor.RED + "/gck set < drop_boost_amount / drop_boost_chance_fixed / drop_boost_chance_multiplier > < # >");
                            player.sendMessage("");
                            player.sendMessage(ChatColor.GRAY + "=====================================================");
                            player.sendMessage("");
                        } else {
                            if (args[1].equalsIgnoreCase("keys")) {
                                player.sendMessage(ChatColor.GREEN + "사용 방법" + ChatColor.WHITE + " :");
                                player.sendMessage(ChatColor.WHITE + "/gck keys");
                                player.sendMessage(ChatColor.GREEN + "이 명령어를 사용하기 위해서는 OP가 필요하지 않습니다.");
                            } else if (args[1].equalsIgnoreCase("reload")) {
                                player.sendMessage(ChatColor.RED + "사용 방법" + ChatColor.WHITE + " :");
                                player.sendMessage(ChatColor.WHITE + "/gck reload");
                                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                            } else if (args[1].equalsIgnoreCase("enable")) {
                                player.sendMessage(ChatColor.RED + "사용 방법" + ChatColor.WHITE + " :");
                                player.sendMessage(ChatColor.WHITE + "/gck enable < key_drop / drop_boost >");
                                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                            } else if (args[1].equalsIgnoreCase("disable")) {
                                player.sendMessage(ChatColor.RED + "사용 방법" + ChatColor.WHITE + " :");
                                player.sendMessage(ChatColor.WHITE + "/gck disable < key_drop / drop_boost >");
                                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                            } else if (args[1].equalsIgnoreCase("set")) {
                                player.sendMessage(ChatColor.RED + "사용 방법" + ChatColor.WHITE + " :");
                                player.sendMessage(ChatColor.WHITE + "/gck set < drop_boost_amount / drop_boost_chance_fixed / drop_boost_chance_multiplier > < # >");
                                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                            } else {
                                player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        if (player.isOp()) {
                            super.gck.reloadConfig();
                            gck.saveDefaultConfig();
                            gck.getConfig().options().copyDefaults(true);
                            gck.saveConfig();

                            gck.reloadKeysConfig();
                            gck.reloadActsConfig();
                            gck.reloadCeilConfig();

                            player.sendMessage(ChatColor.AQUA + "config가 리로드되었습니다. 미적용시 서버 재시작을 시도해보시기 바랍니다.");
                            conLog("Config reloaded by " + username + " (UUID : " + uuid + ")");
                        } else {
                            player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                            conLog(username + "(UUID :" + uuid + ") tried to use /gck reload without OP.");
                        }
                    } else if (args[0].equalsIgnoreCase("keys")) {  // 등록된 열쇠를 보여줌
                        int total_key_num = gck.getKeysConfig().getStringList("keys").size();

                        if (gck.getConfig().getBoolean("config.enabled")) {
                            player.sendMessage(ChatColor.GREEN + "현재 등록되어 있는 모든 열쇠 목록" + ChatColor.WHITE + ":");
                            for (int i = 0; i <= total_key_num; i++) {
                                if (gck.getKeysConfig().getBoolean("keys.\"" + i + "\".enabled")) {
                                    player.sendMessage(ChatColor.GREEN + gck.getKeysConfig().getString("keys.\"" + i + "\".display_name"));
                                } else {
                                    player.sendMessage(ChatColor.RED + gck.getKeysConfig().getString("keys.\"" + i + "\".display_name"));
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "현재 열쇠 드랍이 비활성화 되어있습니다! 블럭 당 열쇠 드랍 확률은 볼 수 있지만 실제로는 적용되지 않습니다.");
                        }
                    } else if (args[0].equalsIgnoreCase("enable")) {  // 활성화 설정
                        if (args.length < 2) {
                            player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                        } else {
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
                                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                if (gck.getConfig().getInt("config.drop_boost.amount") < 1) {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE +"는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                                } else {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.WHITE + " 개의 " + ChatColor.YELLOW + "열쇠 추가 드랍" + ChatColor.WHITE + "이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                                }
                                                if (gck.getConfig().getInt("config.drop_boost.chance_fixed") <= 0) {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE +"는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                                } else {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW +"열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + (gck.getConfig().getInt("config.drop_boost.chance_fixed") / gck.getConfig().getInt("config.max_chance") * 100) + ChatColor.WHITE + "% 만큼의 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                                }
                                                if (gck.getConfig().getInt("config.drop_boost.chance_multiplier") < 0) {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW +"열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE +"는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                                } else {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE +"열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance_fixed") + ChatColor.WHITE + "배 만큼의 " + ChatColor.YELLOW +"열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                                    p.sendMessage(ChatColor.RED + "[경고] 1 미만의 배수" + ChatColor.WHITE + "를 " + ChatColor.RED + "조심" + ChatColor.WHITE +"하세요!");
                                                }
                                            } else {
                                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE +"열쇠 드랍 부스트" + ChatColor.WHITE +"는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                            }
                                        }
                                        conLog("key_drop enabled by OP Player " + username + ". (UUID : " + uuid + ")");
                                    }
                                } else if (args[1].equalsIgnoreCase("drop_boost")) {
                                    if (gck.getConfig().getBoolean("config.drop_boost.enabled")) {
                                        player.sendMessage(ChatColor.YELLOW + "이미 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.YELLOW + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.YELLOW + " 되어있습니다!");
                                    } else {
                                        gck.getConfig().set("config.drop_boost.enabled", true);

                                        gck.saveConfig();

                                        player.sendMessage(ChatColor.AQUA + "이제 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.AQUA + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.AQUA + " 되었습니다!");
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.AQUA + username + ChatColor.WHITE + " 님에 의해 " + ChatColor.YELLOW + "열쇠 드랍 부스트" + ChatColor.WHITE + " 설정이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되었습니다!");
                                            if (gck.getConfig().getBoolean("config.enabled")) {
                                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.WHITE +"이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                if (gck.getConfig().getInt("config.drop_boost.amount") < 1) {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE +"는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                                } else {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.WHITE + " 개의 " + ChatColor.YELLOW + "열쇠 추가 드랍" + ChatColor.WHITE + "이 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                                }
                                                if (gck.getConfig().getInt("config.drop_boost.chance_fixed") <= 0) {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE +"는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                                } else {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW +"열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + (gck.getConfig().getInt("config.drop_boost.chance_fixed") / gck.getConfig().getInt("config.max_chance") * 100) + ChatColor.WHITE + "% 만큼의 " + ChatColor.YELLOW + "열쇠 고정 드랍 확률 부스트" + ChatColor.WHITE + "가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                                }
                                                if (gck.getConfig().getInt("config.drop_boost.chance_multiplier") < 0) {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW +"열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE +"는 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
                                                } else {
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE +"열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 상태입니다.");
                                                    p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance_fixed") + ChatColor.WHITE + "배 만큼의 " + ChatColor.YELLOW +"열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE +"가 " + ChatColor.GREEN + "활성화" + ChatColor.WHITE + " 되어있습니다!");
                                                    p.sendMessage(ChatColor.RED + "[경고] 1 미만의 배수" + ChatColor.WHITE + "를 " + ChatColor.RED + "조심" + ChatColor.WHITE +"하세요!");
                                                }
                                            } else {
                                                p.sendMessage(ChatColor.YELLOW + "[알림] " + ChatColor.WHITE + "현재 " + ChatColor.WHITE +"열쇠 드랍" + ChatColor.WHITE +"은 " + ChatColor.RED + "비활성화" + ChatColor.WHITE + " 상태입니다.");
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
                    } else if (args[0].equalsIgnoreCase("disable")) {  // 비활성화 설정
                        if (args.length < 2) {
                            player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                        } else {
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
                    } else if (args[0].equalsIgnoreCase("set")) {   // drop_boost 값 세부 수정
                        if (args.length < 2) {
                            player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                        } else {
                            if (player.isOp()) {
                                if (args[1].equalsIgnoreCase("drop_boost_amount")) {
                                    if (args.length < 3) {
                                        player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                                    } else {
                                        try {
                                            int value = Integer.parseInt(args[2]);

                                            gck.getConfig().set("config.drop_boost.amount", value);

                                            gck.saveConfig();

                                            player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.WHITE + " 입니다. 입력한 값이 맞는지 확인해 주세요.");
                                        } catch (NumberFormatException e) {
                                            player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
                                            conLog("NOT-INTEGER-TYPE value was entered by " + username + ". (UUID :" + uuid + ") (Fc:L" + getLineNumber() + ")");
                                        }
                                    }
                                } else if (args[1].equalsIgnoreCase("drop_boost_chance_fixed")) {
                                    if (args.length < 3) {
                                        player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                                    } else {
                                        try {
                                            int value = Integer.parseInt(args[2]);

                                            gck.getConfig().set("config.drop_boost.chance_fixed", value);

                                            gck.saveConfig();

                                            player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 고정 확률 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance_fixed") + ChatColor.WHITE + " 입니다. 총 추가 확률은 " + ChatColor.GREEN + (gck.getConfig().getInt("config.drop_boost.chance_fixed") / gck.getConfig().getInt("config.max_chance") * 100) + ChatColor.GREEN + "%" + ChatColor.WHITE + " 입니다. 입력한 값이 맞는지 확인해 주세요.");
                                        } catch (NumberFormatException e) {
                                            player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
                                            conLog("NOT-INTEGER-TYPE value was entered by " + username + ". (UUID :" + uuid + ") (Fc:L" + getLineNumber() + ")");
                                        }
                                    }
                                } else if (args[1].equalsIgnoreCase("drop_boost_chance_multiplier")) {
                                    if (args.length < 3) {
                                        player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                                    } else {
                                        try {
                                            double value = Double.parseDouble(args[2]);

                                            gck.getConfig().set("config.drop_boost.chance_multiplier", value);

                                            gck.saveConfig();

                                            player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance_multiplier") + ChatColor.WHITE + " 입니다. 입력한 값이 맞는지 확인해 주세요.");
                                        } catch (NumberFormatException e) {
                                            player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 실수만 입력해 주세요.");
                                            conLog("NOT-INTEGER-TYPE value was entered by " + username + ". (UUID :" + uuid + ") (Fc:L" + getLineNumber() + ")");
                                        }
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                                conLog(username + "(UUID : " + uuid + ") tried to use /gck set without OP.");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("ceiling")) {
                        if (gck.getConfig().getBoolean("config.ceiling.enabled")) {
                            player.sendMessage(ChatColor.GREEN + "현재 서버에서 천장 시스템이 활성화 되어있습니다.");
                        } else {
                            player.sendMessage(ChatColor.RED + "현재 서버에서 천장 시스템이 비활성화 되어있습니다.");
                        }
                        player.sendMessage(ChatColor.GREEN + "발굴" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".excavation") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.excavation_max") + ChatColor.WHITE + " 회");
                        player.sendMessage(ChatColor.GOLD + "농사" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".farming") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.farming_max") + ChatColor.WHITE + " 회");
                        player.sendMessage(ChatColor.AQUA + "낚시" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".fishing") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.fishing_max") + ChatColor.WHITE + " 회");
                        player.sendMessage(ChatColor.GRAY + "광질" + ChatColor.WHITE + " : " + ChatColor.GOLD + gck.getCeilConfig().getInt("ceiling." + uuid + ".mining") + ChatColor.WHITE + " / " + gck.getConfig().getInt("config.ceiling.mining_max") + ChatColor.WHITE + " 회");
                    } else {
                        player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
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

        return false;
    }
}
