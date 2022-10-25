package com.k5na.getcrateskeys_main.events;

import com.k5na.getcrateskeys_main.GetCratesKeys_main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.logging.Logger;

public class GCK_events implements Listener {
    public final Logger logger = Logger.getLogger("Minecraft");
    public static GetCratesKeys_main gck;

    public GCK_events(GetCratesKeys_main plugin) {
        gck = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String block_name = block.getBlockData().getMaterial().name();

        boolean keys_drop_enabled = gck.getConfig().getBoolean("keys_drop_enabled");

        if (keys_drop_enabled) {
            int max_chance = gck.getConfig().getInt("max_chance");
            int keys_total = gck.getConfig().getInt("keys.total");

            //if (block_name == gck.getConfig().getStringList("digging")) {

            //int random_chance = (int)(Math.random() * max_chance + 1);    // 1 ~ max_chance 중 정수 하나 선택
            //if (random_chance <= (chance + multiplier * level)) {         // 만약 확률에 걸리면
                //int random_key = (int)(Math.random() * keys_total + 1);   // 1 ~ keys_total 중 정수 하나 선택
                    //gck.getConfig().get                                   // 정수에 따른 열쇠 지급
            //}
        }
    }
}
