package com.k5na.getcrateskeys.expansions;

import com.k5na.getcrateskeys.GetCratesKeys;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static com.k5na.getcrateskeys.events.GCK_events.gck;

public class GCK_expansions extends PlaceholderExpansion {
    private GetCratesKeys plugin;

    public GCK_expansions(GetCratesKeys plugin) {
        this.plugin = plugin;
    }

    File keyFile = new File(plugin.getDataFolder(), "/keys.yml");
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
