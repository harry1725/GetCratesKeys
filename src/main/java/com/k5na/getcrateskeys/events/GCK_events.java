package com.k5na.getcrateskeys.events;

import com.k5na.getcrateskeys.GetCratesKeys;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.bukkit.enchantments.Enchantment.SILK_TOUCH;
import static org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH;

public class GCK_events implements Listener {
    public static GetCratesKeys gck;

    public GCK_events(GetCratesKeys plugin) {
        gck = plugin;
    }

    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        String ceiling_excavation_path = "ceiling." + uuid + ".excavation";
        String ceiling_farming_path = "ceiling." + uuid + ".farming";
        String ceiling_fishing_path = "ceiling." + uuid + ".fishing";
        String ceiling_foraging_path = "ceiling." + uuid + ".foraging";
        String ceiling_mining_path = "ceiling." + uuid + ".mining";

        if (!gck.getCeilConfig().isSet(ceiling_excavation_path)) {
            gck.getCeilConfig().set(ceiling_excavation_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_farming_path)) {
            gck.getCeilConfig().set(ceiling_farming_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_fishing_path)) {
            gck.getCeilConfig().set(ceiling_fishing_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_foraging_path)) {
            gck.getCeilConfig().set(ceiling_foraging_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_mining_path)) {
            gck.getCeilConfig().set(ceiling_mining_path, 0);
        }

        gck.saveCeilConfig();
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        BlockState block_state = event.getBlock().getState();

        if (block_state instanceof TileState) {
            ((TileState) block_state).getPersistentDataContainer().set(new NamespacedKey(gck, "Key"), PersistentDataType.STRING, "placed");

            block_state.update();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        BlockState block_state = event.getBlock().getState();
        BlockData block_data = event.getBlock().getBlockData();
        String block_name = block_data.getMaterial().name();

        String placed = null;

        if (block_state instanceof TileState) {
            placed = ((TileState) block_state).getPersistentDataContainer().get(new NamespacedKey(gck, "Key"), PersistentDataType.STRING);
        }

        boolean keys_drop_enabled = gck.getConfig().getBoolean("config.enabled");
        int max_chance = gck.getConfig().getInt("config.max_chance");

        boolean key_drop_boost_enabled = gck.getConfig().getBoolean("config.drop_boost.enabled");
        int key_drop_boost_amount = gck.getConfig().getInt("config.drop_boost.amount");
        int key_drop_chance_fixed = gck.getConfig().getInt("config.drop_boost.chance_fixed");
        double key_drop_chance_multiplier = gck.getConfig().getDouble("config.drop_boost.chance_multiplier");

        boolean ceiling_enabled = gck.getConfig().getBoolean("config.ceiling.enabled");
        int ceiling_excavation_max = gck.getConfig().getInt("config.ceiling.excavation_max");
        int ceiling_farming_max = gck.getConfig().getInt("config.ceiling.farming_max");
        int ceiling_foraging_max = gck.getConfig().getInt("config.ceiling.foraging_max");
        int ceiling_mining_max = gck.getConfig().getInt("config.ceiling.mining_max");

        int total_key_num = Objects.requireNonNull(gck.getKeysConfig().getConfigurationSection("keys")).getKeys(false).size();
        String[] enabled_keys = new String[total_key_num + 1];
        int total_enabled_key_num = 0;

        for (int i = 1; i <= total_key_num; i++) {
            if (gck.getKeysConfig().getBoolean("keys._" + i + ".enabled")) {
                for (int j = 1; j <= i; j++) {
                    if (enabled_keys[j] == null) {
                        enabled_keys[j] = String.valueOf(i);
                        total_enabled_key_num++;
                        break;
                    }
                }
            }
        }

        Set<String> excavation_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("excavation")).getKeys(false);
        boolean excavation_enabled = gck.getActsConfig().getBoolean("excavation.enabled");
        Set<String> farming_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("farming")).getKeys(false);
        boolean farming_enabled = gck.getActsConfig().getBoolean("farming.enabled");
        Set<String> foraging_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("foraging")).getKeys(false);
        boolean foraging_enabled = gck.getActsConfig().getBoolean("foraging.enabled");
        Set<String> mining_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("mining")).getKeys(false);
        boolean mining_enabled = gck.getActsConfig().getBoolean("mining.enabled");

        String ceiling_excavation_path = "ceiling." + uuid + ".excavation";
        String ceiling_farming_path = "ceiling." + uuid + ".farming";
        String ceiling_foraging_path = "ceiling." + uuid + ".foraging";
        String ceiling_mining_path = "ceiling." + uuid + ".mining";

        if (!gck.getCeilConfig().isSet(ceiling_excavation_path)) {
            gck.getCeilConfig().set(ceiling_excavation_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_farming_path)) {
            gck.getCeilConfig().set(ceiling_farming_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_foraging_path)) {
            gck.getCeilConfig().set(ceiling_foraging_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_mining_path)) {
            gck.getCeilConfig().set(ceiling_mining_path, 0);
        }

        gck.saveCeilConfig();

        if (keys_drop_enabled) {
            int key_num = 0;

            if (excavation_enabled && excavation_block_list.contains(block_name) && gck.getActsConfig().getBoolean("excavation." + block_name + ".enabled")) {
                if (placed == null) {
                    String excavation_path = "excavation." + block_name;
                    int chance = gck.getActsConfig().getInt(excavation_path + ".base_chance");
                    int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_excavation%"));

                    if (gck.getActsConfig().getBoolean(excavation_path + ".multiplier_enabled")) {
                        int multiplier = gck.getActsConfig().getInt(excavation_path + ".multiplier");

                        chance += multiplier * level;
                    }

                    if (key_drop_boost_enabled) {
                        if (!(key_drop_chance_fixed <= 0)) {
                            chance += key_drop_chance_fixed;
                        }
                        if (!(key_drop_chance_multiplier < 0)) {
                            chance = (int) Math.round(chance * key_drop_chance_multiplier);
                        }
                    }

                    if (chance > max_chance) {
                        chance = max_chance;
                    }

                    int random_chance = (int) (Math.random() * max_chance + 1);
                    if (random_chance <= chance) {
                        int only_key = gck.getActsConfig().getInt(excavation_path + ".only_key");

                        if (only_key < 1) {
                            String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                            for (int i = 1; i <= total_enabled_key_num; i++) {
                                if (enabled_keys[i].equalsIgnoreCase(random)) {
                                    key_num = i;
                                    break;
                                }
                            }
                        } else {
                            key_num = only_key;
                        }

                        String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                        int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                        int drop = (int) (Math.random() * max_drop + 1);
                        if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                            drop += key_drop_boost_amount;
                        }

                        for (int i = 1; i <= drop; i++) {
                            Bukkit.dispatchCommand(console, command);

                            player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                        }

                        if (ceiling_enabled) {
                            gck.getCeilConfig().set(ceiling_excavation_path, 0);
                        }
                    } else {
                        if (ceiling_enabled) {
                            gck.getCeilConfig().set(ceiling_excavation_path, gck.getCeilConfig().getInt(ceiling_excavation_path) + 1);

                            if (gck.getCeilConfig().getInt(ceiling_excavation_path) >= ceiling_excavation_max) {
                                int only_key = gck.getActsConfig().getInt(excavation_path + ".only_key");

                                if (only_key < 1) {
                                    String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                    for (int i = 1; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i].equalsIgnoreCase(random)) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    Bukkit.dispatchCommand(console, command);

                                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                }

                                gck.getCeilConfig().set(ceiling_excavation_path, gck.getCeilConfig().getInt(ceiling_excavation_path) - ceiling_excavation_max);
                                if (gck.getCeilConfig().getInt(ceiling_excavation_path) < 0) {
                                    gck.getCeilConfig().set(ceiling_excavation_path, 0);
                                }
                            }
                        }
                    }

                    gck.saveCeilConfig();
                }
            } else if (farming_enabled && farming_block_list.contains(block_name) && gck.getActsConfig().getBoolean("farming." + block_name + ".enabled")) {
                if (block_data instanceof Ageable) {
                    int current_age = ((Ageable) block_data).getAge();
                    int max_age = ((Ageable) block_data).getMaximumAge();

                    if (current_age == max_age) {
                        String farming_path = "farming." + block_name;
                        int chance = gck.getActsConfig().getInt(farming_path + ".base_chance");
                        int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_farming%"));

                        if (gck.getActsConfig().getBoolean(farming_path + ".multiplier_enabled")) {
                            int multiplier = gck.getActsConfig().getInt(farming_path + ".multiplier");

                            chance += multiplier * level;
                        }

                        if (key_drop_boost_enabled) {
                            if (!(key_drop_chance_fixed <= 0)) {
                                chance += key_drop_chance_fixed;
                            }
                            if (!(key_drop_chance_multiplier < 0)) {
                                chance = (int) Math.round(chance * key_drop_chance_multiplier);
                            }
                        }

                        if (chance > max_chance) {
                            chance = max_chance;
                        }

                        int random_chance = (int) (Math.random() * max_chance + 1);
                        if (random_chance <= chance) {
                            int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                            if (only_key < 1) {
                                String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                for (int i = 1; i <= total_enabled_key_num; i++) {
                                    if (enabled_keys[i].equalsIgnoreCase(random)) {
                                        key_num = i;
                                        break;
                                    }
                                }
                            } else {
                                key_num = only_key;
                            }

                            String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                            int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                            int drop = (int) (Math.random() * max_drop + 1);
                            if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                drop += key_drop_boost_amount;
                            }

                            for (int i = 1; i <= drop; i++) {
                                Bukkit.dispatchCommand(console, command);

                                player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                            }

                            if (ceiling_enabled) {
                                gck.getCeilConfig().set(ceiling_farming_path, 0);
                            }
                        } else {
                            if (ceiling_enabled) {
                                gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) + 1);

                                if (gck.getCeilConfig().getInt(ceiling_farming_path) >= ceiling_farming_max) {
                                    int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                                    if (only_key < 1) {
                                        String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                        for (int i = 1; i <= total_enabled_key_num; i++) {
                                            if (enabled_keys[i].equalsIgnoreCase(random)) {
                                                key_num = i;
                                                break;
                                            }
                                        }
                                    } else {
                                        key_num = only_key;
                                    }

                                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                    int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                    int drop = (int) (Math.random() * max_drop + 1);
                                    if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                            drop += key_drop_boost_amount;
                                    }

                                    for (int i = 1; i <= drop; i++) {
                                        Bukkit.dispatchCommand(console, command);

                                        player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                    }

                                    gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) - ceiling_farming_max);
                                    if (gck.getCeilConfig().getInt(ceiling_farming_path) < 0) {
                                        gck.getCeilConfig().set(ceiling_farming_path, 0);
                                    }
                                }
                            }
                        }

                        gck.saveCeilConfig();
                    }
                } else {
                    String farming_path = "farming." + block_name;
                    int chance = gck.getActsConfig().getInt(farming_path + ".base_chance");
                    int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_farming%"));

                    if (gck.getActsConfig().getBoolean(farming_path + ".multiplier_enabled")) {
                        int multiplier = gck.getActsConfig().getInt(farming_path + ".multiplier");

                        chance += multiplier * level;
                    }

                    if (key_drop_boost_enabled) {
                        if (!(key_drop_chance_fixed <= 0)) {
                            chance += key_drop_chance_fixed;
                        }
                        if (!(key_drop_chance_multiplier < 0)) {
                            chance = (int) Math.round(chance * key_drop_chance_multiplier);
                        }
                    }

                    if (chance > max_chance) {
                        chance = max_chance;
                    }

                    int random_chance = (int) (Math.random() * max_chance + 1);
                    if (random_chance <= chance) {
                        int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                        if (only_key < 1) {
                            String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                            for (int i = 1; i <= total_enabled_key_num; i++) {
                                if (enabled_keys[i].equalsIgnoreCase(random)) {
                                    key_num = i;
                                    break;
                                }
                            }
                        } else {
                            key_num = only_key;
                        }

                        String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                        int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                        int drop = (int) (Math.random() * max_drop + 1);
                        if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                            drop += key_drop_boost_amount;
                        }

                        for (int i = 1; i <= drop; i++) {
                            Bukkit.dispatchCommand(console, command);

                            player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                        }

                        if (ceiling_enabled) {
                            gck.getCeilConfig().set(ceiling_farming_path, 0);
                        }
                    } else {
                        if (ceiling_enabled) {
                            gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) + 1);

                            if (gck.getCeilConfig().getInt(ceiling_farming_path) >= ceiling_farming_max) {
                                int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                                if (only_key < 1) {
                                    String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                    for (int i = 1; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i].equalsIgnoreCase(random)) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    Bukkit.dispatchCommand(console, command);

                                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                }

                                gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) - ceiling_farming_max);
                                if (gck.getCeilConfig().getInt(ceiling_farming_path) < 0) {
                                    gck.getCeilConfig().set(ceiling_farming_path, 0);
                                }
                            }
                        }
                    }

                    gck.saveCeilConfig();
                }
            } else if (foraging_enabled && foraging_block_list.contains(block_name) && gck.getActsConfig().getBoolean("foraging." + block_name + ".enabled")) {
                if (placed == null) {
                    String foraging_path = "foraging." + block_name;
                    int chance = gck.getActsConfig().getInt(foraging_path + ".base_chance");
                    int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_foraging%"));

                    if (gck.getActsConfig().getBoolean(foraging_path + ".multiplier_enabled")) {
                        int multiplier = gck.getActsConfig().getInt(foraging_path + ".multiplier");

                        chance += multiplier * level;
                    }

                    if (key_drop_boost_enabled) {
                        if (!(key_drop_chance_fixed <= 0)) {
                            chance += key_drop_chance_fixed;
                        }
                        if (!(key_drop_chance_multiplier < 0)) {
                            chance = (int) Math.round(chance * key_drop_chance_multiplier);
                        }
                    }

                    if (chance > max_chance) {
                        chance = max_chance;
                    }

                    int random_chance = (int) (Math.random() * max_chance + 1);
                    if (random_chance <= chance) {
                        int only_key = gck.getActsConfig().getInt(foraging_path + ".only_key");

                        if (only_key < 1) {
                            String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                            for (int i = 1; i <= total_enabled_key_num; i++) {
                                if (enabled_keys[i].equalsIgnoreCase(random)) {
                                    key_num = i;
                                    break;
                                }
                            }
                        } else {
                            key_num = only_key;
                        }

                        String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                        int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                        int drop = (int) (Math.random() * max_drop + 1);
                        if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                            drop += key_drop_boost_amount;
                        }

                        for (int i = 1; i <= drop; i++) {
                            Bukkit.dispatchCommand(console, command);

                            player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                        }

                        if (ceiling_enabled) {
                            gck.getCeilConfig().set(ceiling_foraging_path, 0);
                        }
                    } else {
                        if (ceiling_enabled) {
                            gck.getCeilConfig().set(ceiling_foraging_path, gck.getCeilConfig().getInt(ceiling_foraging_path) + 1);

                            if (gck.getCeilConfig().getInt(ceiling_foraging_path) >= ceiling_foraging_max) {
                                int only_key = gck.getActsConfig().getInt(foraging_path + ".only_key");

                                if (only_key < 1) {
                                    String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                    for (int i = 1; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i].equalsIgnoreCase(random)) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    Bukkit.dispatchCommand(console, command);

                                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                }

                                gck.getCeilConfig().set(ceiling_foraging_path, gck.getCeilConfig().getInt(ceiling_foraging_path) - ceiling_foraging_max);
                                if (gck.getCeilConfig().getInt(ceiling_foraging_path) < 0) {
                                    gck.getCeilConfig().set(ceiling_foraging_path, 0);
                                }
                            }
                        }
                    }

                    gck.saveCeilConfig();
                }
            } else if (mining_enabled && mining_block_list.contains(block_name) && gck.getActsConfig().getBoolean("mining." + block_name + ".enabled")) {
                if (placed == null) {
                    ItemStack mainHand = player.getInventory().getItemInMainHand();

                    if (!mainHand.containsEnchantment(SILK_TOUCH)) {
                        String mining_path = "mining." + block_name;
                        int chance = gck.getActsConfig().getInt(mining_path + ".base_chance");
                        int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_mining%"));

                        if (gck.getActsConfig().getBoolean(mining_path + ".multiplier_enabled")) {
                            int multiplier = gck.getActsConfig().getInt(mining_path + ".multiplier");

                            chance += multiplier * level;
                        }

                        if (key_drop_boost_enabled) {
                            if (!(key_drop_chance_fixed <= 0)) {
                                chance += key_drop_chance_fixed;
                            }
                            if (!(key_drop_chance_multiplier < 0)) {
                                chance = (int) Math.round(chance * key_drop_chance_multiplier);
                            }
                        }

                        if (chance > max_chance) {
                            chance = max_chance;
                        }

                        int random_chance = (int) (Math.random() * max_chance + 1);
                        if (random_chance <= chance) {
                            int only_key = gck.getActsConfig().getInt(mining_path + ".only_key");

                            if (only_key < 1) {
                                String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                for (int i = 1; i <= total_enabled_key_num; i++) {
                                    if (enabled_keys[i].equalsIgnoreCase(random)) {
                                        key_num = i;
                                        break;
                                    }
                                }
                            } else {
                                key_num = only_key;
                            }

                            String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                            int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                            int drop = (int) (Math.random() * max_drop + 1);
                            if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                drop += key_drop_boost_amount;
                            }

                            for (int i = 1; i <= drop; i++) {
                                Bukkit.dispatchCommand(console, command);

                                player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                            }

                            if (ceiling_enabled) {
                                gck.getCeilConfig().set(ceiling_mining_path, 0);
                            }
                        } else {
                            if (ceiling_enabled) {
                                gck.getCeilConfig().set(ceiling_mining_path, gck.getCeilConfig().getInt(ceiling_mining_path) + 1);

                                if (gck.getCeilConfig().getInt(ceiling_mining_path) >= ceiling_mining_max) {
                                    int only_key = gck.getActsConfig().getInt(mining_path + ".only_key");

                                    if (only_key < 1) {
                                        String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                        for (int i = 1; i <= total_enabled_key_num; i++) {
                                            if (enabled_keys[i].equalsIgnoreCase(random)) {
                                                key_num = i;
                                                break;
                                            }
                                        }
                                    } else {
                                        key_num = only_key;
                                    }

                                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                    int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                    int drop = (int) (Math.random() * max_drop + 1);
                                    if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                        drop += key_drop_boost_amount;
                                    }

                                    for (int i = 1; i <= drop; i++) {
                                        Bukkit.dispatchCommand(console, command);

                                        player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                    }

                                    gck.getCeilConfig().set(ceiling_mining_path, gck.getCeilConfig().getInt(ceiling_mining_path) - ceiling_mining_max);
                                    if (gck.getCeilConfig().getInt(ceiling_mining_path) < 0) {
                                        gck.getCeilConfig().set(ceiling_mining_path, 0);
                                    }
                                }
                            }
                        }
                    }

                    gck.saveCeilConfig();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHarvestBerries (EntityInteractEvent event) {
        Entity entity = event.getEntity();
        Player player;
        UUID uuid;
        BlockData block_data = event.getBlock().getBlockData();
        String block_name = block_data.getMaterial().name();

        if (entity instanceof Player) {
            player = (Player) entity;
            uuid = player.getUniqueId();

            boolean keys_drop_enabled = gck.getConfig().getBoolean("config.enabled");
            int max_chance = gck.getConfig().getInt("config.max_chance");

            boolean key_drop_boost_enabled = gck.getConfig().getBoolean("config.drop_boost.enabled");
            int key_drop_boost_amount = gck.getConfig().getInt("config.drop_boost.amount");
            int key_drop_chance_fixed = gck.getConfig().getInt("config.drop_boost.chance_fixed");
            double key_drop_chance_multiplier = gck.getConfig().getDouble("config.drop_boost.chance_multiplier");

            boolean ceiling_enabled = gck.getConfig().getBoolean("config.ceiling.enabled");
            int ceiling_farming_max = gck.getConfig().getInt("config.ceiling.farming_max");

            int total_key_num = Objects.requireNonNull(gck.getKeysConfig().getConfigurationSection("keys")).getKeys(false).size();
            String[] enabled_keys = new String[total_key_num + 1];
            int total_enabled_key_num = 0;

            for (int i = 1; i <= total_key_num; i++) {
                if (gck.getKeysConfig().getBoolean("keys._" + i + ".enabled")) {
                    for (int j = 1; j <= i; j++) {
                        if (enabled_keys[j] == null) {
                            enabled_keys[j] = String.valueOf(i);
                            total_enabled_key_num++;
                            break;
                        }
                    }
                }
            }

            Set<String> farming_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("farming")).getKeys(false);
            boolean farming_enabled = gck.getActsConfig().getBoolean("farming.enabled");

            String ceiling_farming_path = "ceiling." + uuid + ".farming";

            if (!gck.getCeilConfig().isSet(ceiling_farming_path)) {
                gck.getCeilConfig().set(ceiling_farming_path, 0);
            }

            gck.saveCeilConfig();

            if (keys_drop_enabled) {
                int key_num = 0;

                if (farming_enabled && farming_block_list.contains(block_name) && gck.getActsConfig().getBoolean("farming." + block_name + ".enabled")) {
                    if (block_data instanceof Ageable) {
                        int current_age = ((Ageable) block_data).getAge();
                        int max_age = ((Ageable) block_data).getMaximumAge();

                        if (current_age >= max_age - 1) {   // 현재는 Ageable 중 상호작용 가능한 농작물은 달콤한 열매 뿐임
                            String farming_path = "farming." + block_name;
                            int chance = gck.getActsConfig().getInt(farming_path + ".base_chance");
                            int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_farming%"));

                            if (gck.getActsConfig().getBoolean(farming_path + ".multiplier_enabled")) {
                                int multiplier = gck.getActsConfig().getInt(farming_path + ".multiplier");

                                chance += multiplier * level;
                            }

                            if (key_drop_boost_enabled) {
                                if (!(key_drop_chance_fixed <= 0)) {
                                    chance += key_drop_chance_fixed;
                                }
                                if (!(key_drop_chance_multiplier < 0)) {
                                    chance = (int) Math.round(chance * key_drop_chance_multiplier);
                                }
                            }

                            if (chance > max_chance) {
                                chance = max_chance;
                            }

                            int random_chance = (int) (Math.random() * max_chance + 1);
                            if (random_chance <= chance) {
                                int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                                if (only_key < 1) {
                                    String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                    for (int i = 1; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i].equalsIgnoreCase(random)) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    Bukkit.dispatchCommand(console, command);

                                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                }

                                if (ceiling_enabled) {
                                    gck.getCeilConfig().set(ceiling_farming_path, 0);
                                }
                            } else {
                                if (ceiling_enabled) {
                                    gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) + 1);

                                    if (gck.getCeilConfig().getInt(ceiling_farming_path) >= ceiling_farming_max) {
                                        int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                                        if (only_key < 1) {
                                            String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                            for (int i = 1; i <= total_enabled_key_num; i++) {
                                                if (enabled_keys[i].equalsIgnoreCase(random)) {
                                                    key_num = i;
                                                    break;
                                                }
                                            }
                                        } else {
                                            key_num = only_key;
                                        }

                                        String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                        int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                        int drop = (int) (Math.random() * max_drop + 1);
                                        if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                            drop += key_drop_boost_amount;
                                        }

                                        for (int i = 1; i <= drop; i++) {
                                            Bukkit.dispatchCommand(console, command);

                                            player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                        }

                                        gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) - ceiling_farming_max);
                                        if (gck.getCeilConfig().getInt(ceiling_farming_path) < 0) {
                                            gck.getCeilConfig().set(ceiling_farming_path, 0);
                                        }
                                    }
                                }
                            }

                            gck.saveCeilConfig();
                        }
                    } else if (block_data instanceof CaveVinesPlant) {
                        boolean isBerries = ((CaveVinesPlant) block_data).isBerries();

                        if (isBerries) {
                            String farming_path = "farming." + block_name;
                            int chance = gck.getActsConfig().getInt(farming_path + ".base_chance");
                            int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_farming%"));

                            if (gck.getActsConfig().getBoolean(farming_path + ".multiplier_enabled")) {
                                int multiplier = gck.getActsConfig().getInt(farming_path + ".multiplier");

                                chance += multiplier * level;
                            }

                            if (key_drop_boost_enabled) {
                                if (!(key_drop_chance_fixed <= 0)) {
                                    chance += key_drop_chance_fixed;
                                }
                                if (!(key_drop_chance_multiplier < 0)) {
                                    chance = (int) Math.round(chance * key_drop_chance_multiplier);
                                }
                            }

                            if (chance > max_chance) {
                                chance = max_chance;
                            }

                            int random_chance = (int) (Math.random() * max_chance + 1);
                            if (random_chance <= chance) {
                                int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                                if (only_key < 1) {
                                    String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                    for (int i = 1; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i].equalsIgnoreCase(random)) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    Bukkit.dispatchCommand(console, command);

                                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                }

                                if (ceiling_enabled) {
                                    gck.getCeilConfig().set(ceiling_farming_path, 0);
                                }
                            } else {
                                if (ceiling_enabled) {
                                    gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) + 1);

                                    if (gck.getCeilConfig().getInt(ceiling_farming_path) >= ceiling_farming_max) {
                                        int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                                        if (only_key < 1) {
                                            String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                            for (int i = 1; i <= total_enabled_key_num; i++) {
                                                if (enabled_keys[i].equalsIgnoreCase(random)) {
                                                    key_num = i;
                                                    break;
                                                }
                                            }
                                        } else {
                                            key_num = only_key;
                                        }

                                        String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                        int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                                        int drop = (int) (Math.random() * max_drop + 1);
                                        if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                                            drop += key_drop_boost_amount;
                                        }

                                        for (int i = 1; i <= drop; i++) {
                                            Bukkit.dispatchCommand(console, command);

                                            player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                                        }

                                        gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) - ceiling_farming_max);
                                        if (gck.getCeilConfig().getInt(ceiling_farming_path) < 0) {
                                            gck.getCeilConfig().set(ceiling_farming_path, 0);
                                        }
                                    }
                                }
                            }

                            gck.saveCeilConfig();
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFishingSuccess(PlayerFishEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerFishEvent.State state = event.getState();

        boolean keys_drop_enabled = gck.getConfig().getBoolean("config.enabled");
        int max_chance = gck.getConfig().getInt("config.max_chance");

        boolean key_drop_boost_enabled = gck.getConfig().getBoolean("config.drop_boost.enabled");
        int key_drop_boost_amount = gck.getConfig().getInt("config.drop_boost.amount");
        int key_drop_chance_fixed = gck.getConfig().getInt("config.drop_boost.chance_fixed");
        double key_drop_chance_multiplier = gck.getConfig().getDouble("config.drop_boost.chance_multiplier");

        boolean ceiling_enabled = gck.getConfig().getBoolean("config.ceiling.enabled");
        int ceiling_fishing_max = gck.getConfig().getInt("config.ceiling.fishing_max");

        int total_key_num = Objects.requireNonNull(gck.getKeysConfig().getConfigurationSection("keys")).getKeys(false).size();
        String[] enabled_keys = new String[total_key_num + 1];
        int total_enabled_key_num = 0;

        for (int i = 1; i <= total_key_num; i++) {
            if (gck.getKeysConfig().getBoolean("keys._" + i + ".enabled")) {
                for (int j = 1; j <= i; j++) {
                    if (enabled_keys[j] == null) {
                        enabled_keys[j] = String.valueOf(i);
                        total_enabled_key_num++;
                        break;
                    }
                }
            }
        }

        boolean fishing_enabled = gck.getActsConfig().getBoolean("fishing.enabled");

        String fishing_path = "fishing.fishing";
        String ceiling_fishing_path = "ceiling." + uuid + ".fishing";

        if (!gck.getCeilConfig().isSet(ceiling_fishing_path)) {
            gck.getCeilConfig().set(ceiling_fishing_path, 0);
        }

        if (keys_drop_enabled) {
            int key_num = 0;
            if (fishing_enabled && state.equals(CAUGHT_FISH) && gck.getActsConfig().getBoolean(fishing_path + ".enabled")) {
                int chance = gck.getActsConfig().getInt(fishing_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_fishing%"));

                if (gck.getActsConfig().getBoolean(fishing_path + ".multiplier_enabled")) {
                    int multiplier = gck.getActsConfig().getInt(fishing_path + ".multiplier");

                    chance += multiplier * level;
                }

                if (key_drop_boost_enabled) {
                    if (!(key_drop_chance_fixed <= 0)) {
                        chance += key_drop_chance_fixed;
                    }
                    if (!(key_drop_chance_multiplier < 0)) {
                        chance = (int) Math.round(chance * key_drop_chance_multiplier);
                    }
                }

                if (chance > max_chance) {
                    chance = max_chance;
                }

                int random_chance = (int) (Math.random() * max_chance + 1);
                if (random_chance <= chance) {
                    int only_key = gck.getActsConfig().getInt(fishing_path + ".only_key");

                    if (only_key < 1) {
                        String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                        for (int i = 1; i <= total_enabled_key_num; i++) {
                            if (enabled_keys[i].equalsIgnoreCase(random)) {
                                key_num = i;
                                break;
                            }
                        }
                    } else {
                        key_num = only_key;
                    }

                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                    int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                    int drop = (int) (Math.random() * max_drop + 1);
                    if (key_drop_boost_enabled && key_drop_boost_amount > 0) {
                        drop += key_drop_boost_amount;
                    }

                    for (int i = 1; i <= drop; i++) {
                        Bukkit.dispatchCommand(console, command);

                        player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                    }

                    if (ceiling_enabled) {
                        gck.getCeilConfig().set(ceiling_fishing_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        gck.getCeilConfig().set(ceiling_fishing_path, gck.getCeilConfig().getInt(ceiling_fishing_path) + 1);

                        if (gck.getCeilConfig().getInt(ceiling_fishing_path) >= ceiling_fishing_max) {
                            int only_key = gck.getActsConfig().getInt(fishing_path + ".only_key");

                            if (only_key < 1) {
                                String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                                for (int i = 1; i <= total_enabled_key_num; i++) {
                                    if (enabled_keys[i].equalsIgnoreCase(random)) {
                                        key_num = i;
                                        break;
                                    }
                                }
                            } else {
                                key_num = only_key;
                            }

                            String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                            int max_drop = gck.getKeysConfig().getInt("keys._" + key_num + ".max_drop");
                            int drop = (int) (Math.random() * max_drop + 1);

                            for (int i = 1; i <= drop; i++) {
                                Bukkit.dispatchCommand(console, command);

                                player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                            }

                            gck.getCeilConfig().set(ceiling_fishing_path, gck.getCeilConfig().getInt(ceiling_fishing_path) - ceiling_fishing_max);
                            if (gck.getCeilConfig().getInt(ceiling_fishing_path) < 0) {
                                gck.getCeilConfig().set(ceiling_fishing_path, 0);
                            }
                        }
                    }

                    gck.saveCeilConfig();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        gck.saveCeilConfig();
    }
}
