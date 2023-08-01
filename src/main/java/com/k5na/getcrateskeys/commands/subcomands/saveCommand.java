package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;

public class saveCommand extends SubCommand {
    public saveCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "Save all or specific config files.";
    }

    @Override
    public String getSyntax() {
        return "/gck save [arguments]";
    }

    @Override
    public void perform(Player player, String[] args) {
        String username = player.getName();
        UUID uuid = player.getUniqueId();

        if (player.isOp()) {
            if (args.length == 1) {
                player.sendMessage(ChatColor.YELLOW + "모든 config.yml 파일을 저장합니다.");

                gck.saveConfig();
                gck.saveKeysConfig();
                gck.saveActsConfig();
                gck.saveCeilConfig();
                gck.savePlcdConfig();
            } else {
                if (args[1].equalsIgnoreCase("config")) {
                    player.sendMessage(ChatColor.YELLOW + "config.yml 파일을 저장합니다.");

                    gck.saveConfig();
                } else if (args[1].equalsIgnoreCase("keys")) {
                    player.sendMessage(ChatColor.YELLOW + "keys.yml 파일을 저장합니다.");

                    gck.saveKeysConfig();
                } else if (args[1].equalsIgnoreCase("actions")) {
                    player.sendMessage(ChatColor.YELLOW + "actions.yml 파일을 저장합니다.");

                    gck.saveActsConfig();
                } else if (args[1].equalsIgnoreCase("ceiling")) {
                    player.sendMessage(ChatColor.YELLOW + "ceiling.yml 파일을 저장합니다.");

                    gck.saveCeilConfig();
                } else if (args[1].equalsIgnoreCase("placed")) {
                    player.sendMessage(ChatColor.YELLOW + "placed.yml 파일을 저장합니다.");

                    gck.savePlcdConfig();
                } else {
                    player.sendMessage("알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                }
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

            subCommandArgument.add("config");
            subCommandArgument.add("keys");
            subCommandArgument.add("actions");
            subCommandArgument.add("ceiling");
            subCommandArgument.add("placed");

            return subCommandArgument;
        }

        return null;
    }
}
