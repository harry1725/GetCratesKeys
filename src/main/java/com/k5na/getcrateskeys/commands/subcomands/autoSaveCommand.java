package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;

public class autoSaveCommand extends SubCommand {
    public autoSaveCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    private boolean isAsOn() {
        return gck.getConfig().getBoolean("config.autoSave");
    }

    @Override
    public String getName() {
        return "autosave";
    }

    @Override
    public String getDescription() {
        return "Toggle auto saving all config files.";
    }

    @Override
    public String getSyntax() {
        return "/gck autosave <arguments>";
    }

    @Override
    public void perform(Player player, String[] args) {
        String username = player.getName();
        UUID uuid = player.getUniqueId();

        if (player.isOp()) {
            if (args.length == 1) {
                gck.getConfig().set("config.autoSave", false);
                gck.saveConfig();

                if (gck.getConfig().getBoolean("config.autoSave")) {
                    player.sendMessage(ChatColor.AQUA + "컨피그 자동 저장" + ChatColor.YELLOW + "이 " + ChatColor.RED + "비활성화" + ChatColor.YELLOW + " 되었습니다!");
                } else {
                    player.sendMessage(ChatColor.RED + "알 수 없는 이유로 컨피그 자동저장 비활성화가 실패하였습니다. 다시 시도하거나 관리자에게 문의해 주세요.");
                }
            } else if (args.length == 2){
                if (args[1].equalsIgnoreCase("on")) {
                    if (isAsOn()) {
                        player.sendMessage(ChatColor.RED + "이미 컨피그 자동저장이 활성화되어 있습니다! 명령어를 확인하고 다시 시도해주세요.");
                    } else {
                        gck.getConfig().set("config.autoSave", true);
                        gck.saveConfig();

                        if (gck.getConfig().getBoolean("config.autoSave")) {
                            player.sendMessage(ChatColor.AQUA + "컨피그 자동 저장" + ChatColor.YELLOW + "이 " + ChatColor.GREEN + "활성화" + ChatColor.YELLOW + " 되었습니다!");
                        } else {
                            player.sendMessage(ChatColor.RED + "알 수 없는 이유로 컨피그 자동저장 활성화가 실패하였습니다. 다시 시도하거나 관리자에게 문의해 주세요.");
                        }
                    }
                } else if (args[1].equalsIgnoreCase("off")) {
                    if (!isAsOn()) {
                        player.sendMessage(ChatColor.RED + "이미 컨피그 자동저장이 비활성화되어 있습니다! 명령어를 확인하고 다시 시도해주세요.");
                    } else {
                        gck.getConfig().set("config.autoSave", false);
                        gck.saveConfig();

                        if (gck.getConfig().getBoolean("config.autoSave")) {
                            player.sendMessage(ChatColor.AQUA + "컨피그 자동 저장" + ChatColor.YELLOW + "이 " + ChatColor.RED + "비활성화" + ChatColor.YELLOW + " 되었습니다!");
                        } else {
                            player.sendMessage(ChatColor.RED + "알 수 없는 이유로 컨피그 자동저장 비활성화가 실패하였습니다. 다시 시도하거나 관리자에게 문의해 주세요.");
                        }
                    }
                } else {
                    player.sendMessage("알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                }
            } else {
                    player.sendMessage("알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
            conLog(username + "(UUID : " + uuid + ") tried to use /gck save without OP.");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            List<String> subCommandArgument = new ArrayList<>();

            subCommandArgument.add("ON");
            subCommandArgument.add("OFF");

            return subCommandArgument;
        }

        return null;
    }
}
