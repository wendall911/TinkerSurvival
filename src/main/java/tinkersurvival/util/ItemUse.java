package tinkersurvival.util;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;

import tinkersurvival.config.Config;
import tinkersurvival.TinkerSurvival;

public class ItemUse {

    private static Map<String, String> whitelistToolsMap = new HashMap<>();
    private static Set<String> modids = Sets.newHashSet();
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
        "sword"
    };
    private static Set<String> toolTypes = Sets.newHashSet(TOOL_TYPES);

    public static void init() {
        for (String modid : Config.Tools.MOD_TOOL_WHITELIST) {
            if (Loader.isModLoaded(modid)) {
                modids.add(modid);
            }
        }
        for (String item : Config.Tools.TOOLS_WHITELIST) {
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
    }

    public static boolean isWhitelistItem(ItemStack stack) {
        String item = stack.getItem().getRegistryName().toString();
        String[] nameParts = item.split(":");
        String modid = item;
        if (nameParts.length == 2) {
            modid = nameParts[0];
        }
        return modids.contains(modid) || whitelistToolsMap.get(item) != null;
    }

    public static String getToolClass(ItemStack stack) {
        String item = stack.getItem().getRegistryName().toString();
        String type = whitelistToolsMap.get(item);

        if (type == null) {
            for (String toolClass : stack.getItem().getToolClasses(stack)) {
                type = toolClass;
            }

            if (type == null) {
                for (String toolType : TOOL_TYPES) {
                    if (item.contains(toolType) && type == null) {
                        type = toolType;
                    }
                }
            }
        }

        return type;
    }

}
