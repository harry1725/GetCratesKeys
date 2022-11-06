package com.k5na.getcrateskeys_main.expansions;

import com.k5na.getcrateskeys_main.GetCratesKeys_main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class GCK_expansions extends PlaceholderExpansion {
    public static GetCratesKeys_main gck;

    public GCK_expansions(GetCratesKeys_main plugin) {
        gck = plugin;
    }

    File keyFile = new File(gck.getDataFolder().getPath() + "/", "keys.yml");
    YamlConfiguration kyFileConfig = YamlConfiguration.loadConfiguration(keyFile);
    List<Integer> key_nums = kyFileConfig.getIntegerList("keys");

    @Override
    public @NotNull String getAuthor() {
        return "Derman_e";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "GCK";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        int key_num;

        try {
            key_num = Integer.parseInt(params);
        } catch (NumberFormatException e) {
            return null;
        }

        if (key_nums.contains(key_num)) {
            return "scrates givekey " + kyFileConfig.getString("keys." + key_num + ".crate_name") + " " + player.getName() + " 1 -v";
        }

        return null;
    }
}
