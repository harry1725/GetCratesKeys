package com.k5na.getcrateskeys_main.expansions;

import com.k5na.getcrateskeys_main.GetCratesKeys_main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

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

        /*File crateFile = new File(Objects.requireNonNull(kyFileConfig.getString("keys." + key_num + ".path")));
        YamlConfiguration ctFileConfig = YamlConfiguration.loadConfiguration(crateFile);

        String material = ctFileConfig.getString("key.material");
        String name = ctFileConfig.getString("key.name");
        List<String> lore = ctFileConfig.getStringList("key.lore");

        ItemStack key_IS = new ItemStack(Material.valueOf(material));
        ItemMeta key_IM = key_IS.getItemMeta();

        key_IM.setDisplayName(name);
        key_IM.setLore(lore);
        key_IS.setItemMeta(key_IM);

        Objects.requireNonNull(player.getPlayer()).getInventory().addItem(key_IS);*/

        /*String command = "sc give virtual_key " + params;

        return command;*/

        return null;
    }
}
