package com.k5na.getcrateskeys.expansions;

import com.k5na.getcrateskeys.GetCratesKeys;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

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
        String username = player.getName();
        Set<String> key_nums = Objects.requireNonNull(gck.getKeysConfig().getConfigurationSection("keys")).getKeys(false);

        String key_num = "_" + params;

        if (key_nums.contains(key_num)) {
            return "scrates givekey " + gck.getKeysConfig().getString("keys." + key_num + ".crate_name") + " " + username + " 1";
        }

        return null;
    }
}
