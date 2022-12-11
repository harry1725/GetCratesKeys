package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;
import static com.k5na.getcrateskeys.GetCratesKeys.getLineNumber;

public class setCommand extends SubCommand {
    public setCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "Set key-related parameters.";
    }

    @Override
    public String getSyntax() {
        return "/gck set <argument> <number>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
        } else {
            String username = player.getName();
            UUID uuid = player.getUniqueId();

            if (player.isOp()) {
                if (args[1].equalsIgnoreCase("drop_boost_amount")) {
                    if (args.length == 2) {
                        player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                    } else {
                        try {
                            int value = Integer.parseInt(args[2]);

                            gck.getConfig().set("config.drop_boost.amount", value);

                            gck.saveConfig();

                            if (value > 0) {
                                player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + "+" + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.GREEN + "개" + ChatColor.WHITE + " 입니다.");
                            } else {
                                player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + "+ 0개" + ChatColor.WHITE + " 입니다.");
                            }
                            player.sendMessage(ChatColor.YELLOW + "입력한 값과 일치하는지 확인해 주세요.");
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
                            conLog("NOT-INTEGER-TYPE value was entered by " + username + ". (UUID :" + uuid + ") (Fc:L" + getLineNumber() + ")");
                        }
                    }
                } else if (args[1].equalsIgnoreCase("drop_boost_chance")) {
                    if (args.length == 2) {
                        player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                    } else {
                        try {
                            int value = Integer.parseInt(args[2]);

                            if (value < -100) {
                                player.sendMessage(ChatColor.RED + "너무 작은 숫자입니다! 최소 숫자는 -100 입니다.");
                            } else {
                                gck.getConfig().set("config.drop_boost.chance", value);

                                gck.saveConfig();


                                if (value >= 0) {
                                    player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + "+" + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.chance") + ChatColor.GREEN + "%" + ChatColor.WHITE + " 입니다.");
                                } else {
                                    player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 부스트" + ChatColor.WHITE + "는 " + ChatColor.RED + gck.getConfig().getInt("config.drop_boost.chance") + ChatColor.RED + "%" + ChatColor.WHITE + " 입니다.");
                                }
                                player.sendMessage(ChatColor.YELLOW + "입력한 값과 일치하는지 확인해 주세요.");
                            }
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
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
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            List<String> subcommandArgument = new ArrayList<>();

            subcommandArgument.add("drop_boost_amount");
            subcommandArgument.add("drop_boost_chance");

            return subcommandArgument;
        }

        return null;
    }
}
