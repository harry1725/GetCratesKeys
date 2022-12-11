package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class keysCommand extends SubCommand {
    public keysCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "keys";
    }

    @Override
    public String getDescription() {
        return "Show a list of registered keys.";
    }

    @Override
    public String getSyntax() {
        return "/gck keys";
    }

    @Override
    public void perform(Player player, String[] args) {
        int total_key_num = Objects.requireNonNull(gck.getKeysConfig().getConfigurationSection("keys")).getKeys(false).size();

        if (gck.getConfig().getBoolean("config.enabled")) {
            player.sendMessage(ChatColor.AQUA + "현재 열쇠 드랍이 + " + ChatColor.GREEN + "활성화" + ChatColor.AQUA + "되어있습니다!");
        } else {
            player.sendMessage(ChatColor.YELLOW + "현재 열쇠 드랍이 + " + ChatColor.RED + "비활성화" + ChatColor.YELLOW + "되어있습니다! 등록되어 있는 모든 열쇠 목록을 볼 수는 있지만 실제로 드랍되지는 않습니다.");
        }

        player.sendMessage(ChatColor.GREEN + "현재 등록되어 있는 모든 열쇠 목록" + ChatColor.WHITE + ":");
        for (int i = 1; i <= total_key_num; i++) {
            if (gck.getKeysConfig().getBoolean("keys._" + i + ".enabled")) {
                player.sendMessage(ChatColor.GREEN + gck.getKeysConfig().getString("keys._" + i + ".display_name"));
            } else {
                player.sendMessage(ChatColor.RED + gck.getKeysConfig().getString("keys._" + i + ".display_name"));
            }
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
