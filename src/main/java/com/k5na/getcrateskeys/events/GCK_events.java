package com.k5na.getcrateskeys.events;

import com.k5na.getcrateskeys.GetCratesKeys;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;

import static org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH;

public class GCK_events implements Listener {
    public static GetCratesKeys gck;

    public GCK_events(GetCratesKeys plugin) {
        gck = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        String ceiling_excavation_path = "ceiling" + uuid + ".excavation";
        String ceiling_farming_path = "ceiling" + uuid + ".farming";
        String ceiling_fishing_path = "ceiling" + uuid + ".fishing";
        String ceiling_mining_path = "ceiling" + uuid + ".mining";

        if (!gck.getCeilConfig().isSet(ceiling_excavation_path)) {
            gck.getCeilConfig().set(ceiling_excavation_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_farming_path)) {
            gck.getCeilConfig().set(ceiling_farming_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_fishing_path)) {
            gck.getCeilConfig().set(ceiling_fishing_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_mining_path)) {
            gck.getCeilConfig().set(ceiling_mining_path, 0);
        }

        gck.saveCeilConfig();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String block_name = event.getBlock().getBlockData().getMaterial().name();

        boolean keys_drop_enabled = gck.getKeysConfig().getBoolean("key_config.enabled");
        int max_chance = gck.getKeysConfig().getInt("key_config.max_chance");

        boolean key_drop_boost_enabled = gck.getKeysConfig().getBoolean("key_config.drop_boost.enabled");
        int key_drop_boost_amount = gck.getKeysConfig().getInt("key_config.drop_boost.amount");
        int key_drop_chance_fixed = gck.getKeysConfig().getInt("key_config.drop_boost.chance_fixed");
        int key_drop_chance_multiplier = gck.getKeysConfig().getInt("key_config.drop_boost.chance_multiplier");

        boolean ceiling_enabled = gck.getKeysConfig().getBoolean("key_config.ceiling.enabled");
        int ceiling_excavation_max = gck.getKeysConfig().getInt("key_config.ceiling.excavation_max");
        int ceiling_farming_max = gck.getKeysConfig().getInt("key_config.ceiling.farming_max");
        int ceiling_mining_max = gck.getKeysConfig().getInt("key_config.ceiling.mining_max");

        int total_key_num = gck.getKeysConfig().getIntegerList("keys").size();
        int[] enabled_keys = new int[total_key_num];
        int total_enabled_key_num = 0;

        for (int i = 1; i <= total_key_num; i++) {
            if (gck.getKeysConfig().getBoolean("keys." + i + ".enabled")) {
                for (int j = 0; j <= i - 1; j++) {
                    if (enabled_keys[j] == 0) {
                        enabled_keys[j] = i;
                        total_enabled_key_num++;
                        break;
                    }
                }
            }
        }

        List<String> excavation_block_list = gck.getActsConfig().getStringList("excavation");
        boolean excavation_enabled = gck.getActsConfig().getBoolean("excavation.enabled");
        List<String> farming_block_list = gck.getActsConfig().getStringList("farming");
        boolean farming_enabled = gck.getActsConfig().getBoolean("farming.enabled");
        List<String> mining_block_list = gck.getActsConfig().getStringList("mining");
        boolean mining_enabled = gck.getActsConfig().getBoolean("mining.enabled");

        String ceiling_excavation_path = "ceiling" + uuid + ".excavation";
        String ceiling_farming_path = "ceiling" + uuid + ".farming";
        String ceiling_mining_path = "ceiling" + uuid + ".mining";

        if (!gck.getCeilConfig().isSet(ceiling_excavation_path)) {
            gck.getCeilConfig().set(ceiling_excavation_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_farming_path)) {
            gck.getCeilConfig().set(ceiling_farming_path, 0);
        }
        if (!gck.getCeilConfig().isSet(ceiling_mining_path)) {
            gck.getCeilConfig().set(ceiling_mining_path, 0);
        }

        gck.saveCeilConfig();

        if (keys_drop_enabled) {
            int key_num = 0;

            if (excavation_enabled && excavation_block_list.contains(block_name) && gck.getActsConfig().getBoolean("excavation." + block_name + ".enabled")) {
                String excavation_path = "excavation." + block_name;
                int chance = gck.getActsConfig().getInt(excavation_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_excavation%"));

                if (gck.getActsConfig().getBoolean(excavation_path + ".multiplier_enabled")) {
                    int multiplier = gck.getActsConfig().getInt(excavation_path + ".multiplier");

                    chance += multiplier * level;
                }

                if (key_drop_boost_enabled) {
                    if (!(key_drop_chance_fixed == -1)) {
                        chance += key_drop_chance_fixed;
                    }
                    if (!(key_drop_chance_multiplier == -1)) {
                        chance = Math.round(chance * key_drop_chance_multiplier);
                    }
                }

                if (chance > max_chance) {
                    chance = max_chance;
                }

                int random_chance = (int)(Math.random() * max_chance + 1);
                if (random_chance <= chance) {
                    int only_key = gck.getActsConfig().getInt(excavation_path + ".only_key");

                    if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                        int random = (int) (Math.random() * total_enabled_key_num + 1);

                        for (int i = 0; i <= total_enabled_key_num; i++) {
                            if (enabled_keys[i] == random) {
                                key_num = i;
                                break;
                            }
                        }
                    } else {
                        key_num = only_key;
                    }

                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                    int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                    int drop = (int) (Math.random() * max_drop + 1);
                    if (key_drop_boost_enabled && key_drop_boost_amount != -1) {
                        drop += key_drop_boost_amount;
                    }

                    for (int i = 1; i <= drop; i++) {
                        GetCratesKeys.console(command);
                    }

                    if (ceiling_enabled) {
                        gck.getCeilConfig().set(ceiling_excavation_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!gck.getCeilConfig().isSet(ceiling_excavation_path)) {
                            gck.getCeilConfig().set(ceiling_excavation_path, 1);
                        } else {
                            gck.getCeilConfig().set(ceiling_excavation_path, gck.getCeilConfig().getInt(ceiling_excavation_path) + 1);

                            if (gck.getCeilConfig().getInt(ceiling_excavation_path) >= ceiling_excavation_max) {
                                int only_key = gck.getActsConfig().getInt(excavation_path + ".only_key");

                                if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                                    int random = (int) (Math.random() * total_enabled_key_num + 1);

                                    for (int i = 0; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i] == random) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount != -1) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    GetCratesKeys.console(command);
                                }

                                gck.getCeilConfig().set(ceiling_excavation_path, gck.getCeilConfig().getInt(ceiling_excavation_path) - ceiling_excavation_max);
                                if (gck.getCeilConfig().getInt(ceiling_excavation_path) < 0) {
                                    gck.getCeilConfig().set(ceiling_excavation_path, 0);
                                }
                            }
                        }
                    }
                }

                gck.saveCeilConfig();
            } else if (farming_enabled && farming_block_list.contains(block_name) && gck.getActsConfig().getBoolean("farming." + block_name + ".enabled")) {
                String farming_path = "farming." + block_name;
                int chance = gck.getActsConfig().getInt(farming_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_farming%"));

                if (gck.getActsConfig().getBoolean(farming_path + ".multiplier_enabled")) {
                    int multiplier = gck.getActsConfig().getInt(farming_path + ".multiplier");

                    chance += multiplier * level;
                }

                if (key_drop_boost_enabled) {
                    if (!(key_drop_chance_fixed == -1)) {
                        chance += key_drop_chance_fixed;
                    }
                    if (!(key_drop_chance_multiplier == -1)) {
                        chance = Math.round(chance * key_drop_chance_multiplier);
                    }
                }

                if (chance > max_chance) {
                    chance = max_chance;
                }

                int random_chance = (int)(Math.random() * max_chance + 1);
                if (random_chance <= chance) {
                    int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                    if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                        int random = (int) (Math.random() * total_enabled_key_num + 1);

                        for (int i = 0; i <= total_enabled_key_num; i++) {
                            if (enabled_keys[i] == random) {
                                key_num = i;
                                break;
                            }
                        }
                    } else {
                        key_num = only_key;
                    }

                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                    int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                    int drop = (int) (Math.random() * max_drop + 1);
                    if (key_drop_boost_enabled && key_drop_boost_amount != -1) {
                        drop += key_drop_boost_amount;
                    }

                    for (int i = 1; i <= drop; i++) {
                        GetCratesKeys.console(command);
                    }

                    if (ceiling_enabled) {
                        gck.getCeilConfig().set(ceiling_farming_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!gck.getCeilConfig().isSet(ceiling_farming_path)) {
                            gck.getCeilConfig().set(ceiling_farming_path, 1);
                        } else {
                            gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) + 1);

                            if (gck.getCeilConfig().getInt(ceiling_farming_path) >= ceiling_farming_max) {
                                int only_key = gck.getActsConfig().getInt(farming_path + ".only_key");

                                if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                                    int random = (int) (Math.random() * total_enabled_key_num + 1);

                                    for (int i = 0; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i] == random) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount != -1) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    GetCratesKeys.console(command);
                                }

                                gck.getCeilConfig().set(ceiling_farming_path, gck.getCeilConfig().getInt(ceiling_farming_path) - ceiling_farming_max);
                                if (gck.getCeilConfig().getInt(ceiling_farming_path) < 0) {
                                    gck.getCeilConfig().set(ceiling_farming_path, 0);
                                }
                            }
                        }
                    }
                }

                gck.saveCeilConfig();
            } else if (mining_enabled && mining_block_list.contains(block_name) && gck.getActsConfig().getBoolean("mining." + block_name + ".enabled")) {
                String mining_path = "mining." + block_name;
                int chance = gck.getActsConfig().getInt(mining_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_mining%"));

                if (gck.getActsConfig().getBoolean(mining_path + ".multiplier_enabled")) {
                    int multiplier = gck.getActsConfig().getInt(mining_path + ".multiplier");

                    chance += multiplier * level;
                }

                if (key_drop_boost_enabled) {
                    if (!(key_drop_chance_fixed == -1)) {
                        chance += key_drop_chance_fixed;
                    }
                    if (!(key_drop_chance_multiplier == -1)) {
                        chance = Math.round(chance * key_drop_chance_multiplier);
                    }
                }

                if (chance > max_chance) {
                    chance = max_chance;
                }

                int random_chance = (int)(Math.random() * max_chance + 1);
                if (random_chance <= chance) {
                    int only_key = gck.getActsConfig().getInt(mining_path + ".only_key");

                    if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                        int random = (int) (Math.random() * total_enabled_key_num + 1);

                        for (int i = 0; i <= total_enabled_key_num; i++) {
                            if (enabled_keys[i] == random) {
                                key_num = i;
                                break;
                            }
                        }
                    } else {
                        key_num = only_key;
                    }

                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                    int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                    int drop = (int) (Math.random() * max_drop + 1);
                    if (key_drop_boost_enabled && key_drop_boost_amount != -1) {
                        drop += key_drop_boost_amount;
                    }

                    for (int i = 1; i <= drop; i++) {
                        GetCratesKeys.console(command);
                    }

                    if (ceiling_enabled) {
                        gck.getCeilConfig().set(ceiling_mining_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!gck.getCeilConfig().isSet(ceiling_mining_path)) {
                            gck.getCeilConfig().set(ceiling_mining_path, 1);
                        } else {
                            gck.getCeilConfig().set(ceiling_mining_path, gck.getCeilConfig().getInt(ceiling_mining_path) + 1);

                            if (gck.getCeilConfig().getInt(ceiling_mining_path) >= ceiling_mining_max) {
                                int only_key = gck.getActsConfig().getInt(mining_path + ".only_key");

                                if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                                    int random = (int) (Math.random() * total_enabled_key_num + 1);

                                    for (int i = 0; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i] == random) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                                int drop = (int) (Math.random() * max_drop + 1);
                                if (key_drop_boost_enabled && key_drop_boost_amount != -1) {
                                    drop += key_drop_boost_amount;
                                }

                                for (int i = 1; i <= drop; i++) {
                                    GetCratesKeys.console(command);
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

    @EventHandler
    public void onFishingSuccess(PlayerFishEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerFishEvent.State state = event.getState();

        boolean keys_drop_enabled = gck.getKeysConfig().getBoolean("key_config.enabled");
        int max_chance = gck.getKeysConfig().getInt("key_config.max_chance");

        boolean key_drop_boost_enabled = gck.getKeysConfig().getBoolean("key_config.drop_boost.enabled");
        int key_drop_boost_amount = gck.getKeysConfig().getInt("key_config.drop_boost.amount");
        int key_drop_chance_fixed = gck.getKeysConfig().getInt("key_config.drop_boost.chance_fixed");
        int key_drop_chance_multiplier = gck.getKeysConfig().getInt("key_config.drop_boost.chance_multiplier");

        boolean ceiling_enabled = gck.getKeysConfig().getBoolean("key_config.ceiling.enabled");
        int ceiling_fishing_max = gck.getKeysConfig().getInt("key_config.ceiling.fishing_max");

        int total_key_num = gck.getKeysConfig().getIntegerList("keys").size();
        int[] enabled_keys = new int[total_key_num];
        int total_enabled_key_num = 0;

        for (int i = 1; i <= total_key_num; i++) {
            if (gck.getKeysConfig().getBoolean("keys." + i + ".enabled")) {
                for (int j = 0; j <= i - 1; j++) {
                    if (enabled_keys[j] == 0) {
                        enabled_keys[j] = i;
                        total_enabled_key_num++;
                        break;
                    }
                }
            }
        }

        boolean fishing_enabled = gck.getActsConfig().getBoolean("fishing.enabled");

        String fishing_path = "fishing.fishing";
        String ceiling_fishing_path = "ceiling" + uuid + ".fishing";

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
                    if (!(key_drop_chance_fixed == -1)) {
                        chance += key_drop_chance_fixed;
                    }
                    if (!(key_drop_chance_multiplier == -1)) {
                        chance = Math.round(chance * key_drop_chance_multiplier);
                    }
                }

                if (chance > max_chance) {
                    chance = max_chance;
                }

                int random_chance = (int) (Math.random() * max_chance + 1);
                if (random_chance <= chance) {
                    int only_key = gck.getActsConfig().getInt(fishing_path + ".only_key");

                    if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                        int random = (int) (Math.random() * total_enabled_key_num + 1);

                        for (int i = 0; i <= total_enabled_key_num; i++) {
                            if (enabled_keys[i] == random) {
                                key_num = i;
                                break;
                            }
                        }
                    } else {
                        key_num = only_key;
                    }

                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                    int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                    int drop = (int) (Math.random() * max_drop + 1);
                    if (key_drop_boost_enabled && key_drop_boost_amount != -1) {
                        drop += key_drop_boost_amount;
                    }

                    for (int i = 1; i <= drop; i++) {
                        GetCratesKeys.console(command);
                    }

                    if (ceiling_enabled) {
                        gck.getCeilConfig().set(ceiling_fishing_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!gck.getCeilConfig().isSet(ceiling_fishing_path)) {
                            gck.getCeilConfig().set(ceiling_fishing_path, 1);
                        } else {
                            gck.getCeilConfig().set(ceiling_fishing_path, gck.getCeilConfig().getInt(ceiling_fishing_path) + 1);

                            if (gck.getCeilConfig().getInt(ceiling_fishing_path) >= ceiling_fishing_max) {
                                int only_key = gck.getActsConfig().getInt(fishing_path + ".only_key");

                                if (only_key == -1 || !gck.getKeysConfig().getBoolean("keys." + only_key + ".enabled")) {
                                    int random = (int) (Math.random() * total_enabled_key_num + 1);

                                    for (int i = 0; i <= total_enabled_key_num; i++) {
                                        if (enabled_keys[i] == random) {
                                            key_num = i;
                                            break;
                                        }
                                    }
                                } else {
                                    key_num = only_key;
                                }

                                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + key_num + "%");

                                int max_drop = gck.getKeysConfig().getInt(("keys." + key_num + ".max_drop"));
                                int drop = (int) (Math.random() * max_drop + 1);

                                for (int i = 1; i <= drop; i++) {
                                    GetCratesKeys.console(command);
                                }

                                gck.getCeilConfig().set(ceiling_fishing_path, gck.getCeilConfig().getInt(ceiling_fishing_path) - ceiling_fishing_max);
                                if (gck.getCeilConfig().getInt(ceiling_fishing_path) < 0) {
                                    gck.getCeilConfig().set(ceiling_fishing_path, 0);
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
    public void onPlayerQuit(PlayerQuitEvent event) {

        gck.saveCeilConfig();
    }
}
