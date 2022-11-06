package com.k5na.getcrateskeys.commands;

import com.k5na.getcrateskeys.GetCratesKeys;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;
import static com.k5na.getcrateskeys.GetCratesKeys.getLineNumber;
import static me.clip.placeholderapi.util.Msg.broadcast;

public class GCK_commands extends AbstractCommand {
    public GCK_commands(GetCratesKeys plugin, String commandLabel) {
        super(plugin, commandLabel);
    }

    File keyFile = new File(gck.getDataFolder(), "/keys.yml");
    YamlConfiguration kyFileConfig = YamlConfiguration.loadConfiguration(keyFile);
    File actionFile = new File(gck.getDataFolder(), "/actions.yml");
    YamlConfiguration atFileConfig = YamlConfiguration.loadConfiguration(actionFile);

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        List<String> tabs = new ArrayList<>();

        if (alias.length() == 0) {
            tabs.add("gck");
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("gck")) {
                tabs.add("info");
                tabs.add("help");
                tabs.add("reload");
                tabs.add("chance");
                tabs.add("enable");
                tabs.add("disable");
                tabs.add("set");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("gck")) {
                if (args[1].equalsIgnoreCase("chance")) {
                    tabs.add("excavation");
                    tabs.add("farming");
                    tabs.add("fishing");
                    tabs.add("mining");
                } else if (args[1].equalsIgnoreCase("enable") || args[1].equalsIgnoreCase("disable")) {
                    tabs.add("key_drop");
                    tabs.add("drop_boost");
                } else if (args[1].equalsIgnoreCase("set")) {
                    tabs.add("drop_boost_amount");
                    tabs.add("drop_boost_chance_fixed");
                    tabs.add("drop_boost_chance_multiplier");
                }
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
                if (args.length <= 0) {
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
                        if (args[1].equals("")) {
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
                            player.sendMessage(ChatColor.GREEN + "/gck help [ chance / enable / disable / set ]");
                            player.sendMessage(ChatColor.RED + "/gck reload");
                            player.sendMessage(ChatColor.GREEN + "/gck chance [ excavation / farming / fishing / mining ]");
                            player.sendMessage(ChatColor.RED + "/gck enable < key_drop / drop_boost >");
                            player.sendMessage(ChatColor.RED + "/gck disable < key_drop / drop_boost >");
                            player.sendMessage(ChatColor.RED + "/gck set < drop_boost_amount / drop_boost_chance_fixed / drop_boost_chance_multiplier > < # >");
                        } else if (args[1].equalsIgnoreCase("reload")) {
                            if (player.isOp()) {
                                gck.reloadConfig();
                                player.sendMessage(ChatColor.AQUA + "config가 리로드되었습니다. 미적용시 서버 재시작을 시도해보시기 바랍니다.");
                                conLog("Config reloaded by " + username + " (UUID : " + uuid + ")");
                            } else {
                                player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                                conLog(username + "(UUID :" + uuid + ") tried to use /gck reload without OP.");
                            }
                        } else if (args[1].equalsIgnoreCase("chance")) {
                            player.sendMessage(ChatColor.GREEN + "사용 방법" + ChatColor.WHITE + " :");
                            player.sendMessage(ChatColor.WHITE + "/gck chance [ excavation / farming / fishing / mining ]");
                            player.sendMessage(ChatColor.GREEN + "맨 뒤 요소를 입력하지 않고 명령어를 사용하면 최대 확률 단위가 출력됩니다.");
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
                        }
                    } else if (args[0].equalsIgnoreCase("keys")) {  // 등록된 열쇠를 보여줌
                        int total_key_num = kyFileConfig.getIntegerList("keys").size();

                        if (kyFileConfig.getBoolean("key_config.enabled")) {
                            player.sendMessage(ChatColor.GREEN + "현재 활성화되어 있는 모든 열쇠 목록" + ChatColor.WHITE + ":");
                            for (int i = 0; i <= total_key_num; i++) {
                                if (kyFileConfig.getBoolean("keys." + i + ".enabled")) {
                                    player.sendMessage(ChatColor.WHITE + kyFileConfig.getString("keys." + i + ".display_name"));
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "현재 열쇠 드랍이 비활성화되어있습니다! 블럭 당 열쇠 드랍 확률은 볼 수 있지만 실제로는 적용되지 않습니다.");
                        }
                    } else if (args[0].equalsIgnoreCase("chance")) {    // 현재 설정된 열쇠 드랍 확률을 보여줌
                        int max_chance = kyFileConfig.getInt("key_config.max_chance");

                        if (args[1].equals("")) {
                            if (kyFileConfig.getBoolean("key_config.enabled")) {
                                player.sendMessage(ChatColor.GREEN + "최대 확률 단위 (max_chance 분의 base_chance + multiplier * level)" + ChatColor.WHITE + ":");
                                player.sendMessage(ChatColor.WHITE + "" + max_chance + "분의 base_chance + multiplier * level");
                            } else {
                                player.sendMessage(ChatColor.RED + "현재 열쇠 드랍이 비활성화되어있습니다! 블럭 당 열쇠 드랍 확률은 볼 수 있지만 실제로는 적용되지 않습니다.");
                            }
                        } else if (args[1].equalsIgnoreCase("excavation")) {
                            List<String> excavation_list = atFileConfig.getStringList("excavation");

                            if (atFileConfig.getBoolean("excavation.enabled")) {
                                int total_excavation_num = excavation_list.size();

                                player.sendMessage(ChatColor.GREEN + "블럭 당 열쇠 드랍 확률" + ChatColor.WHITE + ":");
                                for (int i = 0; i <= total_excavation_num; i++) {
                                    if (atFileConfig.getBoolean("excavation." + excavation_list.get(i) + ".enabled")) {
                                        player.sendMessage(ChatColor.AQUA + excavation_list.get(i) + ":");
                                        player.sendMessage(ChatColor.WHITE + "기본 확률: " + atFileConfig.getInt("excavation." + excavation_list.get(i) + ".base_chance"));

                                        if (atFileConfig.getBoolean("excavation." + excavation_list.get(i) + ".multiplier_enabled")) {
                                            player.sendMessage(ChatColor.YELLOW + "레벨 당 배수" + ChatColor.WHITE + ": " + atFileConfig.getInt("excavation." + excavation_list.get(i) + ".multiplier"));
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "삽질 항목에 대해 현재 열쇠 드랍이 비활성화 되어있습니다!");
                            }
                        } else if (args[1].equalsIgnoreCase("farming")) {   // 농사 항목
                            List<String> farming_list = atFileConfig.getStringList("farming");

                            if (atFileConfig.getBoolean("farming.enabled")) {
                                int total_farming_num = farming_list.size();

                                player.sendMessage(ChatColor.GREEN + "블럭 당 열쇠 드랍 확률" + ChatColor.WHITE + ":");
                                for (int i = 0; i <= total_farming_num; i++) {
                                    if (atFileConfig.getBoolean("farming." + farming_list.get(i) + ".enabled")) {
                                        player.sendMessage(ChatColor.AQUA + farming_list.get(i) + ":");
                                        player.sendMessage(ChatColor.WHITE + "기본 확률: " + atFileConfig.getInt("farming." + farming_list.get(i) + ".base_chance"));

                                        if (atFileConfig.getBoolean("farming." + farming_list.get(i) + ".multiplier_enabled")) {
                                            player.sendMessage(ChatColor.YELLOW + "레벨 당 배수" + ChatColor.WHITE + ": " + atFileConfig.getInt("farming." + farming_list.get(i) + ".multiplier"));
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "농사 항목에 대해 현제 열쇠 드랍이 비활성화 되어있습니다!");
                            }
                        } else if (args[1].equalsIgnoreCase("fishing")) {   // 낚시 항목
                            if (atFileConfig.getBoolean("fishing.enabled")) {
                                player.sendMessage(ChatColor.GREEN + "낚시 성공 횟수 당 열쇠 드랍 확률" + ChatColor.WHITE + ":");
                                player.sendMessage(ChatColor.WHITE + "기본 확률: " + atFileConfig.getInt("fishing.fishing.base_chance"));
                                if (atFileConfig.getBoolean("fishing.fishing.multiplier_enabled")) {
                                    player.sendMessage(ChatColor.YELLOW + "레벨 당 배수" + ChatColor.WHITE + ": " + atFileConfig.getInt("fishing.fishing.multiplier"));
                                }
                            }
                        } else if (args[1].equalsIgnoreCase("mining")) {    // 광질 항목
                            List<String> mining_list = atFileConfig.getStringList("mining");

                            if (atFileConfig.getBoolean("mining.enabled")) {
                                int total_mining_num = mining_list.size();

                                player.sendMessage(ChatColor.GREEN + "블럭 당 열쇠 드랍 확률" + ChatColor.WHITE + ":");
                                for (int i = 0; i <= total_mining_num; i++) {
                                    if (atFileConfig.getBoolean("mining." + mining_list.get(i) + ".enabled")) {
                                        player.sendMessage(ChatColor.AQUA + mining_list.get(i) + ":");
                                        player.sendMessage(ChatColor.WHITE + "기본 확률: " + atFileConfig.getInt("mining." + mining_list.get(i) + ".base_chance"));

                                        if (atFileConfig.getBoolean("mining." + mining_list.get(i) + ".multiplier_enabled")) {
                                            player.sendMessage(ChatColor.YELLOW + "레벨 당 배수" + ChatColor.WHITE + ": " + atFileConfig.getInt("mining." + mining_list.get(i) + ".multiplier"));
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "광질 항목에 대해 현재 열쇠 드랍이 비활성화 되어있습니다!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "알 수 없는 항목입니다. 명령어를 확인 후 다시 시도해주세요.");
                        }
                    } else if (args[0].equalsIgnoreCase("enable")) {  // 활성화 설정
                        if (player.isOp()) {
                            if (args[1].equalsIgnoreCase("key_drop")) {
                                if (kyFileConfig.getBoolean("key_config.enabled")) {
                                    player.sendMessage(ChatColor.RED + "이미 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.RED + "설정이 활성화되어있습니다!");
                                } else {
                                    kyFileConfig.set("key_config.enabled", true);
                                    try {
                                        kyFileConfig.save(keyFile);

                                        broadcast(ChatColor.WHITE + username + ChatColor.AQUA + "님에 의해 이제 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.AQUA + "설정이 " + ChatColor.GREEN + "활성화" + ChatColor.AQUA + "되었습니다!");
                                    } catch (IOException e) {
                                        player.sendMessage(ChatColor.RED + "설정을 저장하지 못했습니다! 플러그인 제작자에게 오류가 발생했다는 것을 알려주세요. Ln:" + getLineNumber());
                                        conLog("File keyFile could not overwritten or created. (Fc:L" + getLineNumber() + ")");
                                        throw new RuntimeException(e);
                                    }
                                }
                            } else if (args[1].equalsIgnoreCase("drop_boost")) {
                                if (kyFileConfig.getBoolean("key_config.drop_boost.enabled")) {
                                    player.sendMessage(ChatColor.RED + "이미 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.RED + "설정이 활성화되어있습니다!");
                                } else {
                                    kyFileConfig.set("key_config.drop_boost.enabled", true);
                                    try {
                                        kyFileConfig.save(keyFile);

                                        broadcast(ChatColor.WHITE + username + ChatColor.AQUA + "님에 의해 이제 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.AQUA + "설정이 " + ChatColor.GREEN + "활성화" + ChatColor.AQUA + "되었습니다!");
                                        player.sendMessage(ChatColor.YELLOW + "드랍 부스트 세부 내용은 추가 명령어 입력을 통해 설정해 주세요. :");
                                        player.sendMessage(ChatColor.WHITE + "/gck set < drop_boost_amount / drop_boost_chance_fixed / drop_boost_chance_multiplier > < # >");
                                    } catch (IOException e) {
                                        player.sendMessage(ChatColor.RED + "설정을 저장하지 못했습니다! 플러그인 제작자에게 오류가 발생했다는 것을 알려주세요. Ln:" + getLineNumber());
                                        conLog("File keyFile could not overwritten or created. (Fc:L" + getLineNumber() + ")");
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                            conLog(username + "(UUID :" + uuid + ") tried to use /gck enable without OP.");
                        }
                    } else if (args[0].equalsIgnoreCase("disable")) {  // 비활성화 설정
                        if (player.isOp()) {
                            if (args[1].equalsIgnoreCase("key_drop")) {
                                if (!kyFileConfig.getBoolean("key_config.enabled")) {
                                    player.sendMessage(ChatColor.RED + "이미 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.RED + "설정이 비활성화되어있습니다!");
                                } else {
                                    kyFileConfig.set("key_config.enabled", false);
                                    try {
                                        kyFileConfig.save(keyFile);

                                        broadcast(ChatColor.WHITE + username + ChatColor.AQUA + "님에 의해 이제 " + ChatColor.YELLOW + "열쇠 드랍" + ChatColor.AQUA + "설정이 " + ChatColor.RED + "비활성화" + ChatColor.AQUA + "되었습니다!");
                                    } catch (IOException e) {
                                        player.sendMessage(ChatColor.RED + "설정을 저장하지 못했습니다! 플러그인 제작자에게 오류가 발생했다는 것을 알려주세요. Ln:" + getLineNumber());
                                        conLog("File keyFile could not overwritten or created. (Fc:L" + getLineNumber() + ")");
                                        throw new RuntimeException(e);
                                    }
                                }
                            } else if (args[1].equalsIgnoreCase("drop_boost")) {
                                if (!kyFileConfig.getBoolean("key_config.drop_boost.enabled")) {
                                    player.sendMessage(ChatColor.RED + "이미 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.RED + "설정이 비활성화되어있습니다!");
                                } else {
                                    kyFileConfig.set("key_config.drop_boost.enabled", false);
                                    try {
                                        kyFileConfig.save(keyFile);

                                        player.sendMessage(ChatColor.AQUA + "이제 " + ChatColor.YELLOW + "드랍 부스트" + ChatColor.AQUA + "설정이 " + ChatColor.RED + "비활성화" + ChatColor.AQUA + "되었습니다!");
                                    } catch (IOException e) {
                                        player.sendMessage(ChatColor.RED + "설정을 저장하지 못했습니다! 플러그인 제작자에게 오류가 발생했다는 것을 알려주세요. Ln:" + getLineNumber());
                                        conLog("File keyFile could not overwritten or created. (Fc:L" + getLineNumber() + ")");
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                            conLog(username + "(UUID :" + uuid + ") tried to use /gck disable without OP.");
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {   // drop_boost 값 세부 수정
                        if (player.isOp()) {
                            if (args[1].equalsIgnoreCase("drop_boost_amount")) {
                                try {
                                    int value = Integer.parseInt(args[2]);

                                    kyFileConfig.set("key_config.drop_boost.amount", value);

                                    try {
                                        kyFileConfig.save(keyFile);

                                        player.sendMessage(ChatColor.WHITE + username + ChatColor.AQUA + "님에 의해 이제 " + ChatColor.YELLOW + "추가 열쇠 드랍 부스트 갯수" + ChatColor.AQUA + "설정이 " + ChatColor.GREEN + value + "개" + ChatColor.AQUA + "로 설정되었습니다!");
                                    } catch (IOException e) {
                                        player.sendMessage(ChatColor.RED + "설정을 저장하지 못했습니다! 플러그인 제작자에게 오류가 발생했다는 것을 알려주세요. Ln:" + getLineNumber());
                                        conLog("File keyFile could not overwritten or created. (Fc:L" + getLineNumber() + ")");
                                        throw new RuntimeException(e);
                                    }
                                } catch (NumberFormatException e) {
                                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
                                    conLog("Not-INTEGER-TYPE value was entered by " + username + ". (Fc:L" + getLineNumber() + ")");
                                    throw new RuntimeException(e);
                                }
                            } else if (args[1].equalsIgnoreCase("drop_boost_chance_fixed")) {
                                try {
                                    int value = Integer.parseInt(args[2]);

                                    kyFileConfig.set("key_config.drop_boost.chance_fixed", value);

                                    try {
                                        kyFileConfig.save(keyFile);

                                        player.sendMessage(ChatColor.WHITE + username + ChatColor.AQUA + "님에 의해 이제 " + ChatColor.YELLOW + "열쇠 드랍 고정 확률 추가" + ChatColor.AQUA + "설정이 " + ChatColor.GREEN + value + ChatColor.AQUA + "로 설정되었습니다!");
                                    } catch (IOException e) {
                                        player.sendMessage(ChatColor.RED + "설정을 저장하지 못했습니다! 플러그인 제작자에게 오류가 발생했다는 것을 알려주세요. Ln:" + getLineNumber());
                                        conLog("File keyFile could not overwritten or created. (Fc:L" + getLineNumber() + ")");
                                        throw new RuntimeException(e);
                                    }
                                } catch (NumberFormatException e) {
                                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
                                    conLog("Not-INTEGER-TYPE value was entered by " + username + ". (Fc:L" + getLineNumber() + ")");
                                    throw new RuntimeException(e);
                                }
                            } else if (args[1].equalsIgnoreCase("drop_boost_chance_multiplier")) {
                                try {
                                    float value = Integer.parseInt(args[2]);

                                    kyFileConfig.set("key_config.drop_boost.chance_multiplier", value);

                                    try {
                                        kyFileConfig.save(keyFile);

                                        broadcast(ChatColor.WHITE + username + ChatColor.AQUA + "님에 의해 이제 " + ChatColor.YELLOW + "열쇠 드랍 확률 배수 증가" + ChatColor.AQUA + "설정이 " + ChatColor.GREEN + value + "배" + ChatColor.AQUA + "로 설정되었습니다!");
                                        player.sendMessage(ChatColor.YELLOW + "확률 계산 중 chance가 실수 형태가 되면 소숫점 첫 번째 자리에서 반올림됩니다.");
                                    } catch (IOException e) {
                                        player.sendMessage(ChatColor.RED + "설정을 저장하지 못했습니다! 플러그인 제작자에게 오류가 발생했다는 것을 알려주세요. Ln:" + getLineNumber());
                                        conLog("File keyFile could not overwritten or created. (Fc:L" + getLineNumber() + ")");
                                        throw new RuntimeException(e);
                                    }
                                } catch (NumberFormatException e) {
                                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
                                    conLog("Not-INTEGER-TYPE value was entered by " + username + ". (Fc:L" + getLineNumber() + ")");
                                    throw new RuntimeException(e);
                                }
                            } else {
                                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
                            conLog(username + "(UUID : " + uuid + ") tried to use /gck set without OP.");
                        }
                    }
                }
            }
        } else {
            if (command.getName().equalsIgnoreCase("gck")) {
                if (args.length <= 0) {
                    sender.sendMessage(ChatColor.YELLOW + "Too few arguments inserted! Please retry or try other commands.");
                } else {
                    if (args[0].equalsIgnoreCase("test")) {
                        sender.sendMessage(ChatColor.GREEN + "Console command is working properly.");
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        gck.reloadConfig();
                        sender.sendMessage(ChatColor.AQUA + "Config has been reloaded. If it doesn't apply, please restart the server to make it sure working.");
                    }
                }
            }
        }

        return false;
    }
}
