package com.k5na.getcrateskeys.events;

import com.k5na.getcrateskeys.GetCratesKeys;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.bukkit.enchantments.Enchantment.SILK_TOUCH;
import static org.bukkit.event.player.PlayerFishEvent.State.CAUGHT_FISH;

public class GCK_events implements Listener {
    public static GetCratesKeys gck;
    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public GCK_events(GetCratesKeys plugin) {
        gck = plugin;
    }

    public ArrayList<String> placedBlockBlackList() {
        ArrayList<String> block_list = new ArrayList<>();

        Set<String> excavation_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("excavation")).getKeys(false);
        Set<String> foraging_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("foraging")).getKeys(false);
        Set<String> mining_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("mining")).getKeys(false);

        block_list.addAll(excavation_block_list);
        block_list.addAll(foraging_block_list);
        block_list.add("BAMBOO");
        block_list.add("CACTUS");
        block_list.add("KELP");
        block_list.add("KELP_PLANT");
        block_list.add("MELON");
        block_list.add("PUMPKIN");
        block_list.add("SUGAR_CANE");
        block_list.add("SWEET_BERRY_BUSH");
        block_list.addAll(mining_block_list);

        return block_list;
    }

    public boolean isNatural(BlockState block_state) {
        return !gck.getPlcdConfig().getBoolean("placed." + block_state.getLocation().getWorld() + "." + block_state.getLocation().getBlockX() + "," + block_state.getLocation().getBlockY() + "," + block_state.getLocation().getBlockZ());
    }

    public void keyDrop(String skill, Player player, String target) {
        UUID uuid = player.getUniqueId();

        int max_chance = gck.getConfig().getInt("config.max_chance");
        boolean multiplier_enabled = gck.getConfig().getBoolean("config.multiplier_enabled");

        boolean key_drop_boost_enabled = gck.getConfig().getBoolean("config.drop_boost.enabled");
        int key_drop_boost_amount = gck.getConfig().getInt("config.drop_boost.amount");
        int key_drop_boost_chance = gck.getConfig().getInt("config.drop_boost.chance");

        boolean ceiling_enabled = gck.getConfig().getBoolean("config.ceiling.enabled");
        int ceiling_skill_max = gck.getConfig().getInt("config.ceiling." + skill + "_max");

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

        String ceiling_skill_path = "ceiling." + uuid + "." + skill;
        String skill_path = skill + "." + target;

        int chance = gck.getActsConfig().getInt(skill_path + ".base_chance");
        int extra_drop = 0;

        if (multiplier_enabled && gck.getActsConfig().getBoolean(skill_path + ".multiplier_enabled")) {
            int multiplier = gck.getActsConfig().getInt(skill_path + ".multiplier");
            int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_" + skill + "%"));

            chance += multiplier * level;
        }
        if (key_drop_boost_enabled && key_drop_boost_chance != 0) {
            chance *= Math.round((((double) key_drop_boost_chance + 100) / 100));
        }

        if (chance >= max_chance) {
            do {
                chance -= max_chance;
                extra_drop++;
            } while (chance >= max_chance);
        }

        int key_num = 0;
        int random_chance = (int) (Math.random() * max_chance + 1);

        if (extra_drop > 0) {
            for (int i = 0; i <= extra_drop; i++) {
                int only_key = gck.getActsConfig().getInt(skill_path + ".only_key");

                if (only_key < 1) {
                    String random = String.valueOf((int) (Math.random() * total_enabled_key_num + 1));

                    for (int j = 1; j <= total_enabled_key_num; j++) {
                        if (enabled_keys[j].equalsIgnoreCase(random)) {
                            key_num = j;
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

                for (int j = 1; j <= drop; j++) {
                    Bukkit.dispatchCommand(console, command);

                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + key_num + ".display_name") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                }
            }
        }

        if (random_chance <= chance) {
            int only_key = gck.getActsConfig().getInt(skill_path + ".only_key");

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
                gck.getCeilConfig().set(ceiling_skill_path, 0);
            }
        } else {
            if (ceiling_enabled) {
                gck.getCeilConfig().set(ceiling_skill_path, gck.getCeilConfig().getInt(ceiling_skill_path) + 1);

                if (gck.getCeilConfig().getInt(ceiling_skill_path) >= ceiling_skill_max) {
                    int only_key = gck.getActsConfig().getInt(skill_path + ".only_key");

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

                    gck.getCeilConfig().set(ceiling_skill_path, gck.getCeilConfig().getInt(ceiling_skill_path) - ceiling_skill_max);
                    if (gck.getCeilConfig().getInt(ceiling_skill_path) < 0) {
                        gck.getCeilConfig().set(ceiling_skill_path, 0);
                    }
                }
            }
        }

        gck.saveCeilConfig();
    }

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
        String block_name = event.getBlock().getBlockData().getMaterial().name();
        BlockState block_state = event.getBlock().getState();

        ArrayList<String> block_list = placedBlockBlackList();

        if (block_list.contains(block_name)) {
            gck.getPlcdConfig().set("placed." + block_state.getLocation().getWorld() + "." + block_state.getLocation().getBlockX() + "," + block_state.getLocation().getBlockY() + "," + block_state.getLocation().getBlockZ(), true);

            gck.savePlcdConfig();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        BlockState block_state = event.getBlock().getState();
        BlockData block_data = event.getBlock().getBlockData();
        String block_name = block_data.getMaterial().name();

        ArrayList<String> block_list = placedBlockBlackList();

        boolean keys_drop_enabled = gck.getConfig().getBoolean("config.enabled");

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
            if (excavation_enabled && excavation_block_list.contains(block_name) && gck.getActsConfig().getBoolean("excavation." + block_name + ".enabled")) {
                if (isNatural(block_state)) {
                    keyDrop("excavation", player, block_name);
                }
            } else if (farming_enabled && farming_block_list.contains(block_name) && gck.getActsConfig().getBoolean("farming." + block_name + ".enabled")) {
                if (block_data instanceof Ageable && !block_list.contains(block_name)) {
                    int current_age = ((Ageable) block_data).getAge();
                    int max_age = ((Ageable) block_data).getMaximumAge();

                    if (current_age == max_age) {
                        keyDrop("farming", player, block_name);
                    }
                } else {
                    if (isNatural(block_state)) {
                        keyDrop("farming", player, block_name);
                    }
                }
            } else if (foraging_enabled && foraging_block_list.contains(block_name) && gck.getActsConfig().getBoolean("foraging." + block_name + ".enabled")) {
                if (isNatural(block_state)) {
                    keyDrop("foraging", player, block_name);
                }
            } else if (mining_enabled && mining_block_list.contains(block_name) && gck.getActsConfig().getBoolean("mining." + block_name + ".enabled")) {
                if (isNatural(block_state)) {
                    ItemStack mainHand = player.getInventory().getItemInMainHand();

                    if (gck.getActsConfig().getBoolean("mining.silk_touch_drop") || (!gck.getActsConfig().getBoolean("mining.silk_touch_drop") && !mainHand.containsEnchantment(SILK_TOUCH))) {
                        keyDrop("mining", player, block_name);
                    }
                }
            }
        }

        gck.getPlcdConfig().set("placed." + block_state.getLocation().getWorld() + "." + block_state.getLocation().getBlockX() + "," + block_state.getLocation().getBlockY() + "," + block_state.getLocation().getBlockZ(), false);
        gck.savePlcdConfig();
    }

    @EventHandler
    public void onPlayerHarvestBerries(EntityInteractEvent event) {
        Entity entity = event.getEntity();
        Player player;
        UUID uuid;
        BlockData block_data = event.getBlock().getBlockData();
        String block_name = block_data.getMaterial().name();

        if (entity instanceof Player) {
            player = (Player) entity;
            uuid = player.getUniqueId();

            boolean keys_drop_enabled = gck.getConfig().getBoolean("config.enabled");

            Set<String> farming_block_list = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("farming")).getKeys(false);
            boolean farming_enabled = gck.getActsConfig().getBoolean("farming.enabled");

            String ceiling_farming_path = "ceiling." + uuid + ".farming";

            if (!gck.getCeilConfig().isSet(ceiling_farming_path)) {
                gck.getCeilConfig().set(ceiling_farming_path, 0);
            }

            gck.saveCeilConfig();

            if (keys_drop_enabled) {
                if (farming_enabled && farming_block_list.contains(block_name) && gck.getActsConfig().getBoolean("farming." + block_name + ".enabled")) {
                    if (block_data instanceof Ageable) {
                        int current_age = ((Ageable) block_data).getAge();
                        int max_age = ((Ageable) block_data).getMaximumAge();

                        if (current_age >= max_age - 1) {   // 현재는 Ageable 중 상호작용 가능한 농작물은 달콤한 열매 뿐임
                            keyDrop("farming", player, block_name);
                        }
                    } else if (block_data instanceof CaveVinesPlant) {
                        boolean isBerries = ((CaveVinesPlant) block_data).isBerries();

                        if (isBerries) {
                            keyDrop("farming", player, block_name);
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

        boolean fishing_enabled = gck.getActsConfig().getBoolean("fishing.enabled");

        String fishing_path = "fishing.fishing";
        String ceiling_fishing_path = "ceiling." + uuid + ".fishing";

        if (!gck.getCeilConfig().isSet(ceiling_fishing_path)) {
            gck.getCeilConfig().set(ceiling_fishing_path, 0);
        }

        if (keys_drop_enabled) {
            if (fishing_enabled && state.equals(CAUGHT_FISH) && gck.getActsConfig().getBoolean(fishing_path + ".enabled")) {
                keyDrop("fishing", player, "fishing");
            }
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        gck.saveCeilConfig();
        gck.savePlcdConfig();
    }
}
