package com.k5na.getcrateskeys.commands.subcomands;

import com.k5na.getcrateskeys.GetCratesKeys;
import com.k5na.getcrateskeys.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class helpCommand extends SubCommand {
    public helpCommand(GetCratesKeys plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSyntax() {
        return "/gck help [arguments]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
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
            player.sendMessage(ChatColor.GREEN + "/gck help [ info / keys / reload / enable / disable / set ]");
            player.sendMessage(ChatColor.GREEN + "/gck keys");
            player.sendMessage(ChatColor.RED + "/gck reload");
            player.sendMessage(ChatColor.RED + "/gck enable < key_drop / drop_boost >");
            player.sendMessage(ChatColor.RED + "/gck disable < key_drop / drop_boost >");
            player.sendMessage(ChatColor.RED + "/gck set < drop_boost_amount / drop_boost_chance_fixed / drop_boost_chance_multiplier > < # >");
            player.sendMessage(ChatColor.GREEN + "/gck ceiling");
            player.sendMessage("");
            player.sendMessage(ChatColor.GRAY + "=====================================================");
            player.sendMessage("");
        } else {
            if (args[1].equalsIgnoreCase("info")) {
                player.sendMessage(ChatColor.GREEN + "사용 방법" + ChatColor.WHITE + " :");
                player.sendMessage(ChatColor.WHITE + "/gck info");
                player.sendMessage(ChatColor.GREEN + "이 명령어를 사용하기 위해서는 OP가 필요하지 않습니다.");
            } else if (args[1].equalsIgnoreCase("keys")) {
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
            } else if(args[1].equalsIgnoreCase("ceiling")) {
                player.sendMessage(ChatColor.GREEN + "사용 방법" + ChatColor.WHITE + " :");
                player.sendMessage(ChatColor.WHITE + "/gck ceiling");
                player.sendMessage(ChatColor.GREEN + "이 명령어를 사용하기 위해서는 OP가 필요하지 않습니다.");
            } else {
                player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
            }
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            List<String> subcommandArgument = new ArrayList<>();

            subcommandArgument.add("info");
            subcommandArgument.add("keys");
            subcommandArgument.add("reload");
            subcommandArgument.add("enable");
            subcommandArgument.add("disable");
            subcommandArgument.add("set");
            subcommandArgument.add("ceiling");

            return subcommandArgument;
        }

        return null;
    }
}
