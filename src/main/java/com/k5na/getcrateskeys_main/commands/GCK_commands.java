package com.k5na.getcrateskeys_main.commands;

import com.k5na.getcrateskeys_main.GetCratesKeys_main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GCK_commands extends AbstractCommand {
    public final Logger logger = Logger.getLogger("Minecraft");

    public GCK_commands(GetCratesKeys_main plugin, String commandLabel) {
        super(plugin, commandLabel);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabs = new ArrayList<>();

        if (alias.length() == 0) {
            tabs.add("gck");
        }

        if (args.length == 1) {
            tabs.add("");
        }

        return tabs;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

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
                    }   else if (args[0].equalsIgnoreCase("help")) {    // 명령어 목록
                        if (args.length >= 2) {
                            if (args[1].equalsIgnoreCase("keys")) {
                                player.sendMessage("");
                            } else if (args[1].equalsIgnoreCase("edit")) {
                                player.sendMessage("");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("keys")) {  // 등록된 열쇠를 보여줌
                        player.sendMessage("");
                    } else if (args[0].equalsIgnoreCase("chance")) {    // 현재 설정된 열쇠 드랍 확률을 보여줌
                        if (args[1].equalsIgnoreCase("digging")) {  // 삽질 항목
                            player.sendMessage("");
                        } else if (args[1].equalsIgnoreCase("farming")) {   // 농사 항목
                            player.sendMessage("");
                        } else if (args[1].equalsIgnoreCase("fishing")) {   // 낚시 항목
                            player.sendMessage("");
                        } else if (args[1].equalsIgnoreCase("mining")) {    // 광질
                            player.sendMessage("");
                        } else {
                            player.sendMessage("");
                        }
                    } else if (args[0].equalsIgnoreCase("edit")) {  // 10,000번 중 몇 번 꼴로 열쇠가 드랍되게 할 건지 설정
                        if (args[1].equalsIgnoreCase("digging")) {  // 삽질 항목
                            player.sendMessage("");
                        } else if (args[1].equalsIgnoreCase("farming")) {   // 농사 항목
                            player.sendMessage("");
                        } else if (args[1].equalsIgnoreCase("fishing")) {   // 낚시 항목
                            player.sendMessage("");
                        } else if (args[1].equalsIgnoreCase("mining")) {    // 광질 항목
                            player.sendMessage("");
                        } else {
                            player.sendMessage("");
                        }
                    }
                }
            }
        }

        return false;
    }
}
