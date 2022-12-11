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
        return null;
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

                            player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 갯수 드랍 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + gck.getConfig().getInt("config.drop_boost.amount") + ChatColor.WHITE + " 입니다. 입력한 값이 맞는지 확인해 주세요.");
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. 정수만 입력해 주세요.");
                            conLog("NOT-INTEGER-TYPE value was entered by " + username + ". (UUID :" + uuid + ") (Fc:L" + getLineNumber() + ")");
                        }
                    }
                } else if (args[1].equalsIgnoreCase("drop_boost_chance_fixed")) {
                    if (args.length == 2) {
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
                    if (args.length == 2) {
                        player.sendMessage(ChatColor.RED + "명령어에 인수가 부족합니다! 입력하신 명령어를 다시 확인해 주세요!");
                    } else {
                        try {
                            double value = Double.parseDouble(args[2]);

                            gck.getConfig().set("config.drop_boost.chance_multiplier", value);

                            gck.saveConfig();

                            player.sendMessage(ChatColor.WHITE + "현재 " + ChatColor.YELLOW + "열쇠 드랍 확률 배수 부스트" + ChatColor.WHITE + "는 " + ChatColor.GREEN + gck.getConfig().getDouble("config.drop_boost.chance_multiplier") + ChatColor.WHITE + " 입니다. 입력한 값이 맞는지 확인해 주세요.");
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
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            List<String> subcommandArgument = new ArrayList<>();

            subcommandArgument.add("drop_boost_amount");
            subcommandArgument.add("drop_boost_chance_fixed");
            subcommandArgument.add("drop_boost_chance_multiplier");

            return subcommandArgument;
        }

        return null;
    }
}
