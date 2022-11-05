package com.k5na.getcrateskeys_main.events;

import com.k5na.getcrateskeys_main.GetCratesKeys_main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.k5na.getcrateskeys_main.GetCratesKeys_main.conLog;
import static com.k5na.getcrateskeys_main.GetCratesKeys_main.getLineNumber;
import static org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH;

public class GCK_events implements Listener {
    public static GetCratesKeys_main gck;

    public GCK_events(GetCratesKeys_main plugin) {
        gck = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        File ceilingFile = new File(gck.getDataFolder().getPath() + "/", "ceiling.yml");
        YamlConfiguration ciFileConfig = YamlConfiguration.loadConfiguration(ceilingFile);

        String ceiling_excavation_path = "ceiling" + uuid + ".excavation";
        String ceiling_farming_path = "ceiling" + uuid + ".farming";
        String ceiling_fishing_path = "ceiling" + uuid + ".fishing";
        String ceiling_mining_path = "ceiling" + uuid + ".mining";

        if (!ciFileConfig.isSet(ceiling_excavation_path)) {
            ciFileConfig.set(ceiling_excavation_path, 0);
        }
        if (!ciFileConfig.isSet(ceiling_farming_path)) {
            ciFileConfig.set(ceiling_farming_path, 0);
        }
        if (!ciFileConfig.isSet(ceiling_fishing_path)) {
            ciFileConfig.set(ceiling_fishing_path, 0);
        }
        if (!ciFileConfig.isSet(ceiling_mining_path)) {
            ciFileConfig.set(ceiling_mining_path, 0);
        }

        try {
            ciFileConfig.save(ceilingFile);
        } catch (IOException e) {
            conLog("File ceilingFile could not overwritten or created. (Fe:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String block_name = event.getBlock().getBlockData().getMaterial().name();

        File keyFile = new File(gck.getDataFolder().getPath() + "/", "keys.yml");
        YamlConfiguration kyFileConfig = YamlConfiguration.loadConfiguration(keyFile);
        File actionFile = new File(gck.getDataFolder().getPath() + "/", "actions.yml");
        YamlConfiguration atFileConfig = YamlConfiguration.loadConfiguration(actionFile);
        File ceilingFile = new File(gck.getDataFolder().getPath() + "/", "ceiling.yml");
        YamlConfiguration ciFileConfig = YamlConfiguration.loadConfiguration(ceilingFile);

        boolean keys_drop_enabled = kyFileConfig.getBoolean("key_config.enabled");
        int max_chance = kyFileConfig.getInt("key_config.max_chance");

        boolean key_drop_boost_enabled = kyFileConfig.getBoolean("key_config.drop_boost.enabled");
        int key_drop_boost_amount = kyFileConfig.getInt("key_config.drop_boost.amount");
        int key_drop_chance_fixed = kyFileConfig.getInt("key_config.drop_boost.chance_fixed");
        int key_drop_chance_multiplier = kyFileConfig.getInt("key_config.drop_boost.chance_multiplier");

        boolean ceiling_enabled = kyFileConfig.getBoolean("key_config.ceiling.enabled");
        int ceiling_excavation_max = kyFileConfig.getInt("key_config.ceiling.excavation_max");
        int ceiling_farming_max = kyFileConfig.getInt("key_config.ceiling.farming_max");
        int ceiling_mining_max = kyFileConfig.getInt("key_config.ceiling.mining_max");

        int total_key_num = kyFileConfig.getIntegerList("keys").size();

        List<String> excavation_block_list = atFileConfig.getStringList("excavation");
        boolean excavation_enabled = atFileConfig.getBoolean("excavation.enabled");
        List<String> farming_block_list = atFileConfig.getStringList("farming");
        boolean farming_enabled = atFileConfig.getBoolean("farming.enabled");
        List<String> mining_block_list = atFileConfig.getStringList("mining");
        boolean mining_enabled = atFileConfig.getBoolean("mining.enabled");

        String ceiling_excavation_path = "ceiling" + uuid + ".excavation";
        String ceiling_farming_path = "ceiling" + uuid + ".farming";
        String ceiling_mining_path = "ceiling" + uuid + ".mining";

        if (!ciFileConfig.isSet(ceiling_excavation_path)) {
            ciFileConfig.set(ceiling_excavation_path, 0);
        }
        if (!ciFileConfig.isSet(ceiling_farming_path)) {
            ciFileConfig.set(ceiling_farming_path, 0);
        }
        if (!ciFileConfig.isSet(ceiling_mining_path)) {
            ciFileConfig.set(ceiling_mining_path, 0);
        }

        try {
            ciFileConfig.save(ceilingFile);
        } catch (IOException e) {
            conLog("File ceilingFile could not overwritten or created. (Fe:L" + getLineNumber() + ")");
            throw new RuntimeException(e);
        }

        if (keys_drop_enabled) {
            int key_num;

            if (excavation_enabled && excavation_block_list.contains(block_name) && atFileConfig.getBoolean("excavation." + block_name + ".enabled")) {
                String excavation_path = "excavation." + block_name;
                int chance = atFileConfig.getInt(excavation_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_excavation%"));

                if (atFileConfig.getBoolean(excavation_path + ".multiplier_enabled")) {
                    int multiplier = atFileConfig.getInt(excavation_path + ".multiplier");

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
                    if (atFileConfig.getInt(excavation_path + ".only_key") == -1) {
                        key_num = (int) (Math.random() * total_key_num + 1);
                    } else {
                        key_num = atFileConfig.getInt(excavation_path + ".only_key");
                    }
                    // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                    if (ceiling_enabled) {
                        ciFileConfig.set(ceiling_excavation_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!ciFileConfig.isSet(ceiling_excavation_path)) {
                            ciFileConfig.set(ceiling_excavation_path, 1);
                        } else {
                            ciFileConfig.set(ceiling_excavation_path, ciFileConfig.getInt(ceiling_excavation_path) + 1);

                            if (ciFileConfig.getInt(ceiling_excavation_path) >= ceiling_excavation_max) {
                                if (atFileConfig.getInt(excavation_path + ".only_key") == -1) {
                                    key_num = (int) (Math.random() * total_key_num + 1);
                                } else {
                                    key_num = atFileConfig.getInt(excavation_path + ".only_key");
                                }
                                // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                                ciFileConfig.set(ceiling_excavation_path, ciFileConfig.getInt(ceiling_excavation_path) - ceiling_excavation_max);
                                if (ciFileConfig.getInt(ceiling_excavation_path) < 0) {
                                    ciFileConfig.set(ceiling_excavation_path, 0);
                                }
                            }
                        }
                    }
                }

                try {
                    ciFileConfig.save(ceilingFile);
                } catch (IOException e) {
                    conLog("File ceilingFile could not overwritten or created. (Fe:L" + getLineNumber() + ")");
                    throw new RuntimeException(e);
                }
            } else if (farming_enabled && farming_block_list.contains(block_name) && atFileConfig.getBoolean("farming." + block_name + ".enabled")) {
                String farming_path = "farming." + block_name;
                int chance = atFileConfig.getInt(farming_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_farming%"));

                if (atFileConfig.getBoolean(farming_path + ".multiplier_enabled")) {
                    int multiplier = atFileConfig.getInt(farming_path + ".multiplier");

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
                    if (atFileConfig.getInt(farming_path + ".only_key") == -1) {
                        key_num = (int) (Math.random() * total_key_num + 1);
                    } else {
                        key_num = atFileConfig.getInt(farming_path + ".only_key");
                    }
                    // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                    if (ceiling_enabled) {
                        ciFileConfig.set(ceiling_farming_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!ciFileConfig.isSet(ceiling_farming_path)) {
                            ciFileConfig.set(ceiling_farming_path, 1);
                        } else {
                            ciFileConfig.set(ceiling_farming_path, ciFileConfig.getInt(ceiling_farming_path) + 1);

                            if (ciFileConfig.getInt(ceiling_farming_path) >= ceiling_farming_max) {
                                if (atFileConfig.getInt(farming_path + ".only_key") == -1) {
                                    key_num = (int) (Math.random() * total_key_num + 1);
                                } else {
                                    key_num = atFileConfig.getInt(farming_path + ".only_key");
                                }
                                // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                                ciFileConfig.set(ceiling_farming_path, ciFileConfig.getInt(ceiling_farming_path) - ceiling_farming_max);
                                if (ciFileConfig.getInt(ceiling_farming_path) < 0) {
                                    ciFileConfig.set(ceiling_farming_path, 0);
                                }
                            }
                        }
                    }
                }

                try {
                    ciFileConfig.save(ceilingFile);
                } catch (IOException e) {
                    conLog("File ceilingFile could not overwritten or created. (Fe:L" + getLineNumber() + ")");
                    throw new RuntimeException(e);
                }
            } else if (mining_enabled && mining_block_list.contains(block_name) && atFileConfig.getBoolean("mining." + block_name + ".enabled")) {
                String mining_path = "mining." + block_name;
                int chance = atFileConfig.getInt(mining_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_mining%"));

                if (atFileConfig.getBoolean(mining_path + ".multiplier_enabled")) {
                    int multiplier = atFileConfig.getInt(mining_path + ".multiplier");

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
                    if (atFileConfig.getInt(mining_path + ".only_key") == -1) {
                        key_num = (int) (Math.random() * total_key_num + 1);
                    } else {
                        key_num = atFileConfig.getInt(mining_path + ".only_key");
                    }
                    // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                    if (ceiling_enabled) {
                        ciFileConfig.set(ceiling_mining_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!ciFileConfig.isSet(ceiling_mining_path)) {
                            ciFileConfig.set(ceiling_mining_path, 1);
                        } else {
                            ciFileConfig.set(ceiling_mining_path, ciFileConfig.getInt(ceiling_mining_path) + 1);

                            if (ciFileConfig.getInt(ceiling_mining_path) >= ceiling_mining_max) {
                                if (atFileConfig.getInt(mining_path + ".only_key") == -1) {
                                    key_num = (int) (Math.random() * total_key_num + 1);
                                } else {
                                    key_num = atFileConfig.getInt(mining_path + ".only_key");
                                }
                                // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                                ciFileConfig.set(ceiling_mining_path, ciFileConfig.getInt(ceiling_mining_path) - ceiling_mining_max);
                                if (ciFileConfig.getInt(ceiling_mining_path) < 0) {
                                    ciFileConfig.set(ceiling_mining_path, 0);
                                }
                            }
                        }
                    }
                }

                try {
                    ciFileConfig.save(ceilingFile);
                } catch (IOException e) {
                    conLog("File ceilingFile could not overwritten or created. (Fe:L" + getLineNumber() + ")");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @EventHandler
    public void onFishingSuccess(PlayerFishEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerFishEvent.State state = event.getState();

        File keyFile = new File(gck.getDataFolder().getPath() + "/", "keys.yml");
        YamlConfiguration kyFileConfig = YamlConfiguration.loadConfiguration(keyFile);
        File actionFile = new File(gck.getDataFolder().getPath() + "/", "actions.yml");
        YamlConfiguration atFileConfig = YamlConfiguration.loadConfiguration(actionFile);
        File ceilingFile = new File(gck.getDataFolder().getPath() + "/", "ceiling.yml");
        YamlConfiguration ciFileConfig = YamlConfiguration.loadConfiguration(ceilingFile);

        boolean keys_drop_enabled = kyFileConfig.getBoolean("key_config.enabled");
        int max_chance = kyFileConfig.getInt("key_config.max_chance");

        boolean key_drop_boost_enabled = kyFileConfig.getBoolean("key_config.drop_boost.enabled");
        int key_drop_boost_amount = kyFileConfig.getInt("key_config.drop_boost.amount");
        int key_drop_chance_fixed = kyFileConfig.getInt("key_config.drop_boost.chance_fixed");
        int key_drop_chance_multiplier = kyFileConfig.getInt("key_config.drop_boost.chance_multiplier");

        boolean ceiling_enabled = kyFileConfig.getBoolean("key_config.ceiling.enabled");
        int ceiling_fishing_max = kyFileConfig.getInt("key_config.ceiling.fishing_max");

        int total_key_num = kyFileConfig.getIntegerList("keys").size();

        boolean fishing_enabled = atFileConfig.getBoolean("fishing.enabled");

        String fishing_path = "fishing.fishing";
        String ceiling_fishing_path = "ceiling" + uuid + ".fishing";

        if (!ciFileConfig.isSet(ceiling_fishing_path)) {
            ciFileConfig.set(ceiling_fishing_path, 0);
        }

        if (keys_drop_enabled) {
            int key_num;
            if (fishing_enabled && state.equals(CAUGHT_FISH) && atFileConfig.getBoolean(fishing_path + ".enabled")) {
                int chance = atFileConfig.getInt(fishing_path + ".base_chance");
                int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_fishing%"));

                if (atFileConfig.getBoolean(fishing_path + ".multiplier_enabled")) {
                    int multiplier = atFileConfig.getInt(fishing_path + ".multiplier");

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
                    if (atFileConfig.getInt(fishing_path + ".only_key") == -1) {
                        key_num = (int) (Math.random() * total_key_num + 1);
                    } else {
                        key_num = atFileConfig.getInt(fishing_path + ".only_key");
                    }
                    // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                    if (ceiling_enabled) {
                        ciFileConfig.set(ceiling_fishing_path, 0);
                    }
                } else {
                    if (ceiling_enabled) {
                        if (!ciFileConfig.isSet(ceiling_fishing_path)) {
                            ciFileConfig.set(ceiling_fishing_path, 1);
                        } else {
                            ciFileConfig.set(ceiling_fishing_path, ciFileConfig.getInt(ceiling_fishing_path) + 1);

                            if (ciFileConfig.getInt(ceiling_fishing_path) >= ceiling_fishing_max) {
                                if (atFileConfig.getInt(fishing_path + ".only_key") == -1) {
                                    key_num = (int) (Math.random() * total_key_num + 1);
                                } else {
                                    key_num = atFileConfig.getInt(fishing_path + ".only_key");
                                }
                                // key_num에 맞는 열쇠를 max_drop 적용시키고 amount 적용 시켜서 지급

                                ciFileConfig.set(ceiling_fishing_path, ciFileConfig.getInt(ceiling_fishing_path) - ceiling_fishing_max);
                                if (ciFileConfig.getInt(ceiling_fishing_path) < 0) {
                                    ciFileConfig.set(ceiling_fishing_path, 0);
                                }
                            }
                        }
                    }

                    try {
                        ciFileConfig.save(ceilingFile);
                    } catch (IOException e) {
                        conLog("File ceilingFile could not overwritten or created. (Fe:L" + getLineNumber() + ")");
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        player.sendMessage(uuid);
    }
}
