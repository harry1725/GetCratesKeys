package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys.GetCratesKeys.conLog;

public class reloadCommand extends SubCommand {
    public reloadCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload resource files of GCK plugin.";
    }

    @Override
    public String getSyntax() {
        return "/gck reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        String username = player.getName();
        UUID uuid = player.getUniqueId();

        if (player.isOp()) {
            gck.reloadConfig();
            gck.saveDefaultConfig();
            gck.getConfig().options().copyDefaults(true);
            gck.saveConfig();

            gck.saveKeysConfig();
            gck.saveActsConfig();
            gck.saveCeilConfig();
            gck.savePlcdConfig();

            gck.reloadKeysConfig();
            gck.reloadActsConfig();
            gck.reloadCeilConfig();
            gck.reloadPlcdConfig();

            player.sendMessage(ChatColor.AQUA + "config가 리로드되었습니다. 미적용시 서버 재시작을 시도해보시기 바랍니다.");
            conLog("Config reloaded by " + username + " (UUID : " + uuid + ")");
        } else {
            player.sendMessage(ChatColor.RED + "이 명령어를 사용할 권한이 없습니다! 서버 관리자에게 문의해보세요.");
            conLog(username + "(UUID :" + uuid + ") tried to use /gck reload without OP.");
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
