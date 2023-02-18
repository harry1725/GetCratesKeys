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
        return "Show pages of help of GCK plugin.";
    }

    @Override
    public String getSyntax() {
        return "/gck help [arguments]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1 || (args.length == 2 && args[1].equalsIgnoreCase("1"))) {
            player.sendMessage(ChatColor.AQUA + "=============== GCK Help: Index (1/3) ===============");
            player.sendMessage(ChatColor.GRAY + "/gck help #을 통해 help의 # 페이지로 이동하세요.");
            player.sendMessage(ChatColor.GOLD + "플러그인 명령어 목록" + ChatColor.WHITE + " :");
            player.sendMessage(ChatColor.GREEN + "초록색 명령어" + ChatColor.YELLOW + "는 누구나 입력할 수 있고, ");
            player.sendMessage(ChatColor.RED + "빨간색 명령어" + ChatColor.YELLOW + "는 입력할 때 OP가 필요합니다.");
            player.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
            player.sendMessage(ChatColor.GREEN + "/gck boost" + ChatColor.WHITE + ": 현재 드랍 부스트의 상태를 불러옵니다.");
            player.sendMessage(ChatColor.GREEN + "/gck ceiling" + ChatColor.WHITE + ": 플레이어의 현재 천장 수치를 불러옵니다.");
            player.sendMessage(ChatColor.RED + "/gck disable" + ChatColor.WHITE + ": 열쇠 관련 설정을 비활성화합니다.");
            player.sendMessage(ChatColor.RED + "/gck enable" + ChatColor.WHITE + ": 열쇠 관련 설정을 활성화합니다.");
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("2")) {
                player.sendMessage(ChatColor.AQUA + "=============== GCK Help: Index (2/3) ===============");
                player.sendMessage(ChatColor.GRAY + "/gck help #을 통해 help의 # 페이지로 이동하세요.");
                player.sendMessage(ChatColor.GOLD + "플러그인 명령어 목록" + ChatColor.WHITE + " :");
                player.sendMessage(ChatColor.GREEN + "초록색 명령어" + ChatColor.YELLOW + "는 누구나 입력할 수 있고, ");
                player.sendMessage(ChatColor.RED + "빨간색 명령어" + ChatColor.YELLOW + "는 입력할 때 OP가 필요합니다.");
                player.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
                player.sendMessage(ChatColor.GREEN + "/gck help" + ChatColor.WHITE + ": 플러그인의 명령어 도움말을 불러옵니다.");
                player.sendMessage(ChatColor.GREEN + "/gck info" + ChatColor.WHITE + ": 플러그인의 정보를 불러옵니다.");
                player.sendMessage(ChatColor.GREEN + "/gck keys" + ChatColor.WHITE + ": 현재 등록되어 있는 모든 열쇠 목록을 불러옵니다.");
                player.sendMessage(ChatColor.RED + "/gck reload" + ChatColor.WHITE + ": 플러그인의 자원 파일들을 리로드합니다.");
            } else if (args[1].equalsIgnoreCase("3")) {
                player.sendMessage(ChatColor.AQUA + "=============== GCK Help: Index (3/3) ===============");
                player.sendMessage(ChatColor.GRAY + "/gck help #을 통해 help의 # 페이지로 이동하세요.");
                player.sendMessage(ChatColor.GOLD + "플러그인 명령어 목록" + ChatColor.WHITE + " :");
                player.sendMessage(ChatColor.GREEN + "초록색 명령어" + ChatColor.YELLOW + "는 누구나 입력할 수 있고, ");
                player.sendMessage(ChatColor.RED + "빨간색 명령어" + ChatColor.YELLOW + "는 입력할 때 OP가 필요합니다.");
                player.sendMessage(ChatColor.GRAY + "-----------------------------------------------------");
                player.sendMessage(ChatColor.RED + "/gck set" + ChatColor.WHITE + ": 열쇠 관련 수치를 설정합니다.");
            } else if (args[1].equalsIgnoreCase("info")) {
                player.sendMessage(ChatColor.GREEN + "/gck info" + ChatColor.WHITE + ": 플러그인의 정보를 불러옵니다.");
                player.sendMessage(ChatColor.GREEN + "이 명령어를 사용하기 위해서는 OP가 필요하지 않습니다.");
            } else if (args[1].equalsIgnoreCase("keys")) {
                player.sendMessage(ChatColor.GREEN + "/gck keys" + ChatColor.WHITE + ": 현재 등록되어 있는 모든 열쇠 목록을 불러옵니다.");
                player.sendMessage(ChatColor.GREEN + "이 명령어를 사용하기 위해서는 OP가 필요하지 않습니다.");
            } else if (args[1].equalsIgnoreCase("reload")) {
                player.sendMessage(ChatColor.GREEN + "/gck reload" + ChatColor.WHITE + ": 플러그인의 자원 파일들을 리로드합니다.");
                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
            } else if (args[1].equalsIgnoreCase("enable")) {
                player.sendMessage(ChatColor.RED + "/gck enable < drop_boost / key_drop >");
                player.sendMessage(ChatColor.WHITE + ": 열쇠의 드랍이나 열쇠 드랍 부스트를 활성화합니다.");
                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
            } else if (args[1].equalsIgnoreCase("disable")) {
                player.sendMessage(ChatColor.RED + "/gck disable < drop_boost / key_drop >");
                player.sendMessage(ChatColor.WHITE + ": 열쇠의 드랍이나 열쇠 드랍 부스트를 비활성화합니다.");
                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
            } else if (args[1].equalsIgnoreCase("set")) {
                player.sendMessage(ChatColor.RED + "/gck set < drop_boost_amount / drop_boost_chance > < # >");
                player.sendMessage(ChatColor.WHITE + ": 열쇠 갯수 드랍 부스트나 열쇠 드랍 확률 부스트의 수치를 설정합니다.");
                player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
            } else if (args[1].equalsIgnoreCase("ceiling")) {
                player.sendMessage(ChatColor.GREEN + "/gck ceiling" + ChatColor.WHITE + ": 플레이어의 현재 천장 수치를 불러옵니다.");
                player.sendMessage(ChatColor.GREEN + "이 명령어를 사용하기 위해서는 OP가 필요하지 않습니다.");
            } else if (args[1].equalsIgnoreCase("boost")) {
                player.sendMessage(ChatColor.GREEN + "/gck boost" + ChatColor.WHITE + ": 현재 드랍 부스트의 상태를 불러옵니다.");
                player.sendMessage(ChatColor.GREEN + "이 명령어를 사용하기 위해서는 OP가 필요하지 않습니다.");
            } else {
                player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
            }
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("enable")) {
                if (args[2].equalsIgnoreCase("key_drop")) {
                    player.sendMessage(ChatColor.RED + "/gck enable key_drop" + ChatColor.WHITE + ": 열쇠의 드랍을 활성화합니다.");
                    player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                } else if (args[2].equalsIgnoreCase("drop_boost")) {
                    player.sendMessage(ChatColor.RED + "/gck enable drop_boost" + ChatColor.WHITE + ": 열쇠 드랍 부스트를 활성화합니다.");
                    player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                } else {
                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                }
            } else if (args[1].equalsIgnoreCase("disable")) {
                if (args[2].equalsIgnoreCase("key_drop")) {
                    player.sendMessage(ChatColor.RED + "/gck disable key_drop" + ChatColor.WHITE + ": 열쇠의 드랍을 비활성화합니다.");
                    player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                } else if (args[2].equalsIgnoreCase("drop_boost")) {
                    player.sendMessage(ChatColor.RED + "/gck disable drop_boost" + ChatColor.WHITE + ": 열쇠 드랍 부스트를 비활성화합니다.");
                    player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                } else {
                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                }
            } else if (args[1].equalsIgnoreCase("set")) {
                if (args[2].equalsIgnoreCase("drop_boost_amount")) {
                    player.sendMessage(ChatColor.RED + "/gck set drop_boost_amount < # >" + ChatColor.WHITE + ": 열쇠 갯수 드랍 부스트의 수치를 설정합니다.");
                    player.sendMessage(ChatColor.WHITE + "0 이하를 입력할 시 비활성화 됩니다.");
                    player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                } else if (args[2].equalsIgnoreCase("drop_boost_chance")) {
                    player.sendMessage(ChatColor.RED + "/gck set drop_boost_chance < # >" + ChatColor.WHITE + ": 열쇠 드랍 확률 부스트의 수치를 설정합니다.");
                    player.sendMessage(ChatColor.WHITE + "-100 이상의 정수만 입력이 가능하며, 0을 입력할 시 비활성화 됩니다.");
                    player.sendMessage(ChatColor.RED + "이 명령어를 사용하기 위해서는 OP가 필요합니다.");
                } else {
                    player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "알 수 없는 값이 입력되었습니다. /gck help를 통해 입력 가능한 명령어를 확인해주세요.");
            }
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        List<String> subcommandArgument = new ArrayList<>();

        if (args.length == 2) {
            subcommandArgument.add("boost");
            subcommandArgument.add("ceiling");
            subcommandArgument.add("disable");
            subcommandArgument.add("enable");
            subcommandArgument.add("info");
            subcommandArgument.add("keys");
            subcommandArgument.add("reload");
            subcommandArgument.add("set");

            return subcommandArgument;
        } else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("enable")) {
                subcommandArgument.add("drop_boost");
                subcommandArgument.add("key_drop");

                return subcommandArgument;
            } else if (args[1].equalsIgnoreCase("set")) {
                subcommandArgument.add("drop_boost_amount");
                subcommandArgument.add("drop_boost_chance");

                return subcommandArgument;
            }
        }

        return null;
    }
}
