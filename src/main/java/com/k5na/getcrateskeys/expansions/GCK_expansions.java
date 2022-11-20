package com.k5na.getcrateskeys.expansions;

import com.k5na.getcrateskeys.GetCratesKeys;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GCK_expansions extends PlaceholderExpansion {
    private final GetCratesKeys gck;

    public GCK_expansions(GetCratesKeys plugin) {
        gck = plugin;
    }

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
        List<Integer> key_nums = gck.getKeysConfig().getIntegerList("keys");
        int key_num;

        try {
            key_num = Integer.parseInt(params);
        } catch (NumberFormatException e) {
            return null;
        }

        if (key_nums.contains(key_num)) {
            return "scrates givekey " + gck.getKeysConfig().getString("keys." + key_num + ".crate_name") + " " + player.getName() + " 1 -v";
        }

        return null;
    }
}
