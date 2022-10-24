package com.k5na.getcrateskeys_main.events;

import com.k5na.getcrateskeys_main.GetCratesKeys_main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class GCK_events implements Listener {
    public final Logger logger = Logger.getLogger("Minecraft");
    public static GetCratesKeys_main gck;

    public GCK_events(GetCratesKeys_main plugin) {
        gck = plugin;
    }

    //@EventHandler
    //public void onPlayerBreak()   //플레이어가 블럭을 부쉈을 때
    //int random = (int)(Math.random() * 10000 + 1);  //1 ~ 10,000 중 정수 하나 선택
}
