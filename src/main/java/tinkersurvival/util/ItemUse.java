package tinkersurvival.util;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.Arrays;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;

public class ItemUse {

    private static Map<String, String> whitelistToolsMap = new HashMap<>();
    private static Set<String> toolModids = Sets.newHashSet();
    private static Set<String> armorModids = Sets.newHashSet();
    private static Set<String> armorWhitelist = Sets.newHashSet();
    // Order is important here. Ex. Checking for pickaxe before axe in name as a type fallback.
    private static String[] TOOL_TYPES = new String[] {
        "pickaxe",
        "axe",
        "crossbow",
        "bow",
        "hoe",
        "mattock",
        "shears",
        "shovel",
        "sword",
        "weapon",
        "hammer",
        "wirecutter",
        "wrench",
    };
    private static Set<String> toolTypes = Sets.newHashSet(TOOL_TYPES);

    public static void init() {
        for (String modid : ConfigHandler.tools.MOD_TOOL_WHITELIST) {
            if (Loader.isModLoaded(modid)) {
                toolModids.add(modid);
            }
        }

        for (String item : ConfigHandler.tools.TOOLS_WHITELIST) {
            boolean valid = false;

            if (item.contains("-")) {
                String[] nameParts = item.split("-");

                if (nameParts.length == 2) {
                    String toolType = nameParts[0];
                    Item tool = Item.getByNameOrId(nameParts[1]);
                    if (toolTypes.contains(toolType)) {
                        if (tool != null) {
                            valid = true;
                            whitelistToolsMap.put(nameParts[1], nameParts[0]);
                        }
                    }
                }
            }

            if (!valid) {
                TinkerSurvival.logger.warn("TOOLS_WHITELIST item "
                        + item + " has an incorrect format.");
            }
        }

        for (String modid : ConfigHandler.armor.MOD_ARMOR_WHITELIST) {
            if (Loader.isModLoaded(modid)) {
                armorModids.add(modid);
            }
        }

        for (String armorName : ConfigHandler.armor.ARMOR_WHITELIST) {
            boolean valid = false;

            Item armor = Item.getByNameOrId(armorName);
            if (armor != null) {
                valid = true;
                armorWhitelist.add(armorName);
            }

            if (!valid) {
                TinkerSurvival.logger.warn("ARMOR_WHITELIST item "
                        + armorName + " has an incorrect format.");
            }
        }

    }

    public static boolean isWhitelistItem(ItemStack stack) {
        String itemName = stack.getItem().getRegistryName().toString();
        String modid = getModId(itemName);
        return toolModids.contains(modid) || whitelistToolsMap.get(itemName) != null;
    }

    private static String getModId(String name) {
        String[] nameParts = name.split(":");
        return nameParts.length == 2 ? nameParts[0] : name;
    }

    public static String getToolClass(ItemStack stack) {
        String itemName = stack.getItem().getRegistryName().toString();
        String type = whitelistToolsMap.get(itemName);

        if (type == null) {
            for (String toolClass : stack.getItem().getToolClasses(stack)) {
                type = toolClass;
            }

            if (type == null) {
                String[] nameParts = itemName.split("[^a-z]+");
                for (String toolType : TOOL_TYPES) {
                    if (itemName.contains(toolType)
                            && type == null
                            && Arrays.asList(nameParts).contains(toolType)) {
                        type = toolType;
                    }
                }
            }
        }

        return type;
    }

    public static boolean isArmor(ItemStack stack) {
        // Disable check if conarm isn't loaded.
        if (Loader.isModLoaded("conarm")) {
            return stack.getItem() instanceof ItemArmor ? true : false;
        }
        else {
            return false;
        }
    }

    public static boolean isWhitelistArmor(ItemStack stack) {
        String itemName = stack.getItem().getRegistryName().toString();
        String modid = getModId(itemName);
        return armorModids.contains(modid) || armorWhitelist.contains(itemName);
    }

}
