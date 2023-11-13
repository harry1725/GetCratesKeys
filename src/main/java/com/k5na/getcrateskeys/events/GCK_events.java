package com.k5na.getcrateskeys.events;

import com.k5na.getcrateskeys.GetCratesKeys;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
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

    public static int changes;
    public static int connections;

    public void ceilInit(UUID uuid) {
        if (!gck.getCeilConfig().isSet("ceiling." + uuid + ".excavation")) {
            gck.getCeilConfig().set("ceiling." + uuid + ".excavation", 0);
        }
        if (!gck.getCeilConfig().isSet("ceiling." + uuid + ".farming")) {
            gck.getCeilConfig().set("ceiling." + uuid + ".farming", 0);
        }
        if (!gck.getCeilConfig().isSet("ceiling." + uuid + ".fishing")) {
            gck.getCeilConfig().set("ceiling." + uuid + ".fishing", 0);
        }
        if (!gck.getCeilConfig().isSet("ceiling." + uuid + ".foraging")) {
            gck.getCeilConfig().set("ceiling." + uuid + ".foraging", 0);
        }
        if (!gck.getCeilConfig().isSet("ceiling." + uuid + ".mining")) {
            gck.getCeilConfig().set("ceiling." + uuid + ".mining", 0);
        }
        if (!gck.getCeilConfig().isSet("ceiling." + uuid + ".fighting")) {
            gck.getCeilConfig().set("ceiling." + uuid + ".fighting", 0);
        }

        gck.saveCeilConfig();
    }

    public ArrayList<String> placedBlockBlackList() {
        ArrayList<String> blockList = new ArrayList<>();

        Set<String> excavationBlockList = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("excavation")).getKeys(false);
        Set<String> foragingBlockList = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("foraging")).getKeys(false);
        Set<String> miningBlockList = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("mining")).getKeys(false);

        blockList.addAll(excavationBlockList);
        blockList.addAll(foragingBlockList);
        blockList.add("BAMBOO");
        blockList.add("CACTUS");
        blockList.add("KELP");
        blockList.add("KELP_PLANT");
        blockList.add("MELON");
        blockList.add("PUMPKIN");
        blockList.add("SUGAR_CANE");
        blockList.add("SWEET_BERRY_BUSH");
        blockList.addAll(miningBlockList);

        return blockList;
    }

    public Set<String> getBlockList(String name) {
        switch (name) {
            case "ex":
                return Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("excavation")).getKeys(false);
            case "fa":
                return Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("farming")).getKeys(false);
            case "fo":
                return Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("foraging")).getKeys(false);
            case "mi":
                return Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("mining")).getKeys(false);
            default:
                return null;
        }
    }

    public int getNum(String name) {
        switch (name) {
            case "mc":
                return gck.getConfig().getInt("config.maxChance");
            case "ba":
                return gck.getConfig().getInt("config.dropBoost.amount");
            case "bc":
                return gck.getConfig().getInt("config.dropBoost.chance");
            case "kn":
                return Objects.requireNonNull(gck.getKeysConfig().getConfigurationSection("keys")).getKeys(false).size();
            default:
                return 0;
        }
    }

    public boolean isChangeSaveOn() {
        return gck.getConfig().getInt("config.changeSave") > 0;
    }

    public boolean isConnectionSaveOn() {
        return gck.getConfig().getInt("config.connectionSave") > 0;
    }

    public boolean isEnabled(String name) {
        switch (name) {
            case "kd":
                return gck.getConfig().getBoolean("config.enabled");
            case "mu":
                return gck.getConfig().getBoolean("config.multiplierEnabled");
            case "be":
                return gck.getConfig().getBoolean("config.dropBoost.enabled");
            case "ce":
                return gck.getConfig().getBoolean("config.ceiling.enabled");
            case "ex":
                return gck.getActsConfig().getBoolean("excavation.enabled");
            case "fa":
                return gck.getActsConfig().getBoolean("farming.enabled");
            case "fo":
                return gck.getActsConfig().getBoolean("foraging.enabled");
            case "mi":
                return gck.getActsConfig().getBoolean("mining.enabled");
            case "st":
                return gck.getActsConfig().getBoolean("mining.silkTouchDrop");
            case "fi":
                return gck.getActsConfig().getBoolean("fighting.enabled");
            default:
                return false;
        }
    }

    public boolean isBlockEnabled(String name, String block) {
        switch (name) {
            case "ex":
                return gck.getActsConfig().getBoolean("excavation." + block + ".enabled");
            case "fa":
                return gck.getActsConfig().getBoolean("farming." + block + ".enabled");
            case "fo":
                return gck.getActsConfig().getBoolean("foraging." + block + ".enabled");
            case "mi":
                return gck.getActsConfig().getBoolean("mining." + block + ".enabled");
            default:
                return false;
        }
    }

    public void setBlockPlaced(BlockState blockState, boolean placed) {
        Location location = blockState.getLocation();

        if (placed) {
            gck.getPlcdConfig().set("placed." + location.getWorld() + "." + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ(), true);
        } else {
            gck.getPlcdConfig().set("placed." + location.getWorld() + "." + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ(), false);
        }
    }

    public boolean isNatural(BlockState blockState) {
        return !gck.getPlcdConfig().getBoolean("placed." + blockState.getLocation().getWorld() + "." + blockState.getLocation().getBlockX() + "," + blockState.getLocation().getBlockY() + "," + blockState.getLocation().getBlockZ());
    }

    public void keyDrop(String skill, Player player, String target) {
        UUID uuid = player.getUniqueId();

        int maxChance = getNum("mc");
        boolean multiplierEnabled = isEnabled("mu");

        boolean keyDropBoostEnabled = isEnabled("be");
        int keyDropBoostAmount = getNum("ba");
        int keyDropBoostChance = getNum("bc");

        boolean ceilingEnabled = isEnabled("ce");
        int ceilingSkillMax = gck.getConfig().getInt("config.ceiling." + skill + "Max");

        int totalKeyNum = getNum("kn");
        String[] enabledKeys = new String[totalKeyNum + 1];
        int totalEnabledKeyNum = 0;

        for (int i = 1; i <= totalKeyNum; i++) {
            if (gck.getKeysConfig().getBoolean("keys._" + i + ".enabled")) {
                for (int j = 1; j <= i; j++) {
                    if (enabledKeys[j] == null) {
                        enabledKeys[j] = String.valueOf(i);
                        totalEnabledKeyNum++;
                        break;
                    }
                }
            }
        }

        String ceilingSkillPath = "ceiling." + uuid + "." + skill;
        String skillPath = skill + "." + target;

        int chance = gck.getActsConfig().getInt(skillPath + ".baseChance");
        int extraDrop = 0;

        if (multiplierEnabled && gck.getActsConfig().getBoolean(skillPath + ".multiplierEnabled")) {
            int multiplier = gck.getActsConfig().getInt(skillPath + ".multiplier");
            int level = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, "%aureliumskills_" + skill + "%"));

            chance += multiplier * level;
        }
        if (keyDropBoostEnabled && keyDropBoostChance != 0) {
            chance *= (int) Math.round((((double) keyDropBoostChance + 100) / 100));
        }

        if (chance >= maxChance) {
            do {
                chance -= maxChance;
                extraDrop++;
            } while (chance >= maxChance);
        }

        int keyNum = 0;
        int randomChance = (int) (Math.random() * maxChance + 1);

        if (extraDrop > 0) {
            for (int i = 0; i <= extraDrop; i++) {
                int onlyKey = gck.getActsConfig().getInt(skillPath + ".onlyKey");

                if (onlyKey < 1) {
                    String random = String.valueOf((int) (Math.random() * totalEnabledKeyNum + 1));

                    for (int j = 1; j <= totalEnabledKeyNum; j++) {
                        if (enabledKeys[j].equalsIgnoreCase(random)) {
                            keyNum = j;
                            break;
                        }
                    }
                } else {
                    keyNum = onlyKey;
                }

                String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + keyNum + "%");

                int maxDrop = gck.getKeysConfig().getInt("keys._" + keyNum + ".maxDrop");
                int drop = (int) (Math.random() * maxDrop + 1);
                if (keyDropBoostEnabled && keyDropBoostAmount > 0) {
                    drop += keyDropBoostAmount;
                }

                for (int j = 1; j <= drop; j++) {
                    Bukkit.dispatchCommand(console, command);

                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + keyNum + ".displayName") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                }
            }
        }

        if (randomChance <= chance) {
            int onlyKey = gck.getActsConfig().getInt(skillPath + ".onlyKey");

            if (onlyKey < 1) {
                String random = String.valueOf((int) (Math.random() * totalEnabledKeyNum + 1));

                for (int i = 1; i <= totalEnabledKeyNum; i++) {
                    if (enabledKeys[i].equalsIgnoreCase(random)) {
                        keyNum = i;
                        break;
                    }
                }
            } else {
                keyNum = onlyKey;
            }

            String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + keyNum + "%");

            int maxDrop = gck.getKeysConfig().getInt("keys._" + keyNum + ".maxDrop");
            int drop = (int) (Math.random() * maxDrop + 1);

            if (keyDropBoostEnabled && keyDropBoostAmount > 0) {
                drop += keyDropBoostAmount;
            }

            for (int i = 1; i <= drop; i++) {
                Bukkit.dispatchCommand(console, command);

                player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + keyNum + ".displayName") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
            }

            if (ceilingEnabled) {
                gck.getCeilConfig().set(ceilingSkillPath, 0);
            }
        } else {
            if (ceilingEnabled) {
                gck.getCeilConfig().set(ceilingSkillPath, gck.getCeilConfig().getInt(ceilingSkillPath) + 1);

                if (gck.getCeilConfig().getInt(ceilingSkillPath) >= ceilingSkillMax) {
                    int onlyKey = gck.getActsConfig().getInt(skillPath + ".onlyKey");

                    if (onlyKey < 1) {
                        String random = String.valueOf((int) (Math.random() * totalEnabledKeyNum + 1));

                        for (int i = 1; i <= totalEnabledKeyNum; i++) {
                            if (enabledKeys[i].equalsIgnoreCase(random)) {
                                keyNum = i;
                                break;
                            }
                        }
                    } else {
                        keyNum = onlyKey;
                    }

                    String command = PlaceholderAPI.setPlaceholders(player, "%gck_" + keyNum + "%");

                    int maxDrop = gck.getKeysConfig().getInt("keys._" + keyNum + ".maxDrop");
                    int drop = (int) (Math.random() * maxDrop + 1);
                    if (keyDropBoostEnabled && keyDropBoostAmount > 0) {
                        drop += keyDropBoostAmount;
                    }

                    for (int i = 1; i <= drop; i++) {
                        Bukkit.dispatchCommand(console, command);

                        player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "[" + ChatColor.GREEN + ChatColor.BOLD + gck.getKeysConfig().getString("keys._" + keyNum + ".displayName") + ChatColor.GOLD + ChatColor.BOLD + "]" + ChatColor.DARK_GREEN + ChatColor.BOLD + " 을(를) 열어 보상을 획득하세요!");
                    }

                    gck.getCeilConfig().set(ceilingSkillPath, gck.getCeilConfig().getInt(ceilingSkillPath) - ceilingSkillMax);
                    if (gck.getCeilConfig().getInt(ceilingSkillPath) < 0) {
                        gck.getCeilConfig().set(ceilingSkillPath, 0);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ceilInit(player.getUniqueId());

        if (isConnectionSaveOn()) {
            connections++;

            if (connections >= gck.getConfig().getInt("config.connectionSave")) {
                gck.saveCeilConfig();
                gck.savePlcdConfig();

                connections = 0;
            }
        }
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        String blockName = event.getBlock().getBlockData().getMaterial().name();
        BlockState blockState = event.getBlock().getState();

        ArrayList<String> blockList = placedBlockBlackList();

        if (blockList.contains(blockName)) setBlockPlaced(blockState, true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        BlockState blockState = event.getBlock().getState();
        BlockData blockData = event.getBlock().getBlockData();
        String blockName = blockData.getMaterial().name();

        ArrayList<String> blockList = placedBlockBlackList();

        boolean keyDropEnabled = isEnabled("kd");

        Set<String> excavationBlockList = getBlockList("ex");
        boolean excavationEnabled = isEnabled("ex");
        Set<String> farmingBlockList = getBlockList("fa");
        boolean farmingEnabled = isEnabled("fa");
        Set<String> foragingBlockList = getBlockList("fo");
        boolean foragingEnabled = isEnabled("fo");
        Set<String> miningBlockList = getBlockList("mi");
        boolean miningEnabled = isEnabled("mi");

        if (keyDropEnabled) {
            if (excavationEnabled && excavationBlockList.contains(blockName) && isBlockEnabled("ex", blockName)) {
                if (isNatural(blockState)) {
                    keyDrop("excavation", player, blockName);
                }
            } else if (farmingEnabled && farmingBlockList.contains(blockName) && isBlockEnabled("fa", blockName)) {
                if (blockData instanceof Ageable && !blockList.contains(blockName)) {
                    int currentAge = ((Ageable) blockData).getAge();
                    int maxAge = ((Ageable) blockData).getMaximumAge();

                    if (currentAge == maxAge) {
                        keyDrop("farming", player, blockName);
                    }
                } else {
                    if (isNatural(blockState)) {
                        keyDrop("farming", player, blockName);
                    }
                }
            } else if (foragingEnabled && foragingBlockList.contains(blockName) && isBlockEnabled("fo", blockName)) {
                if (isNatural(blockState)) {
                    keyDrop("foraging", player, blockName);
                }
            } else if (miningEnabled && miningBlockList.contains(blockName) && isBlockEnabled("mi", blockName)) {
                if (isNatural(blockState)) {
                    ItemStack mainHand = player.getInventory().getItemInMainHand();

                    if (isEnabled("st") || (!isEnabled("st") && !mainHand.containsEnchantment(SILK_TOUCH))) {
                        keyDrop("mining", player, blockName);
                    }
                }
            }
        }

        setBlockPlaced(blockState, false);

        if (isChangeSaveOn()) {
            changes++;

            if (changes >= gck.getConfig().getInt("config.changeSave")) {
                gck.saveCeilConfig();
                gck.savePlcdConfig();

                changes = 0;
            }
        }
    }

    @EventHandler
    public void onPlayerHarvestBerries(EntityInteractEvent event) {
        Entity entity = event.getEntity();
        Player player;
        BlockData blockData = event.getBlock().getBlockData();
        String blockName = blockData.getMaterial().name();

        if (entity instanceof Player) {
            player = (Player) entity;

            boolean keyDropEnabled = gck.getConfig().getBoolean("config.enabled");

            Set<String> farmingBlockList = Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("farming")).getKeys(false);
            boolean farmingEnabled = gck.getActsConfig().getBoolean("farming.enabled");

            if (keyDropEnabled) {
                if (farmingEnabled && farmingBlockList.contains(blockName) && gck.getActsConfig().getBoolean("farming." + blockName + ".enabled")) {
                    if (blockData instanceof Ageable) {
                        int currentAge = ((Ageable) blockData).getAge();
                        int maxAge = ((Ageable) blockData).getMaximumAge();

                        if (currentAge >= maxAge - 1) {   // 현재는 Ageable 중 상호작용 가능한 농작물은 달콤한 열매 뿐임
                            keyDrop("farming", player, blockName);
                        }
                    } else if (blockData instanceof CaveVinesPlant) {
                        boolean isBerries = ((CaveVinesPlant) blockData).isBerries();

                        if (isBerries) {
                            keyDrop("farming", player, blockName);
                        }
                    }
                }
            }

            if (isChangeSaveOn()) {
                changes++;

                if (changes >= gck.getConfig().getInt("config.changeSave")) {
                    gck.saveCeilConfig();
                    gck.savePlcdConfig();

                    changes = 0;
                }
            }
        }
    }

    @EventHandler
    public void onFishingSuccess(PlayerFishEvent event) {
        Player player = event.getPlayer();
        PlayerFishEvent.State state = event.getState();

        boolean keyDropEnabled = gck.getConfig().getBoolean("config.enabled");

        boolean fishingEnabled = gck.getActsConfig().getBoolean("fishing.enabled");

        if (keyDropEnabled) {
            if (fishingEnabled && state.equals(CAUGHT_FISH) && gck.getActsConfig().getBoolean("fishing.fishing.enabled")) {
                keyDrop("fishing", player, "fishing");
            }
        }


        if (isChangeSaveOn()) {
            changes++;

            if (changes >= gck.getConfig().getInt("config.changeSave")) {
                gck.saveCeilConfig();
                gck.savePlcdConfig();

                changes = 0;
            }
        }
    }

    public Set<String> getMobList() {
        return Objects.requireNonNull(gck.getActsConfig().getConfigurationSection("fighting")).getKeys(false);
    }

    public boolean isMobEnabled(String mob) {
        return gck.getActsConfig().getBoolean("fighting." + mob + ".enabled");
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)  {
        String mob = event.getEntity().getType().toString();
        LivingEntity entity = event.getEntity();

        if (entity.getKiller() == null) return;

        if (!(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
            return;
        }
        //check whether the last damage is by player
        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) entity.getLastDamageCause();
        if (!(entityDamageByEntityEvent.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) entityDamageByEntityEvent.getDamager();

        Set<String> MobList = getMobList();

        boolean keyDropEnabled = gck.getConfig().getBoolean("config.enabled");
        boolean fightingEnabled = isEnabled("fi");

        if (keyDropEnabled) {
            if (fightingEnabled && MobList.contains(mob) && isMobEnabled(mob)) {
                System.out.println("ENABLED");
                keyDrop("fighting", player, mob);
            }
        }

        if (isChangeSaveOn()) {
            changes++;

            if (changes >= gck.getConfig().getInt("config.changeSave")) {
                gck.saveCeilConfig();
                gck.savePlcdConfig();

                changes = 0;
            }
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        if (isConnectionSaveOn()) {
            connections++;

            if (connections >= gck.getConfig().getInt("config.connectionSave")) {
                gck.saveCeilConfig();
                gck.savePlcdConfig();

                connections = 0;
            }
        }
    }
}
