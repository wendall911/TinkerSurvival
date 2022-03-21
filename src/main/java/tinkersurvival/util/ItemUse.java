package tinkersurvival.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.fml.ModList;

import tinkersurvival.common.HarvestBlock;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.mixin.AbstractBlockStateAccessor;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ToolType;

public class ItemUse {

    private static Map<String, String> whitelistToolsMap = new HashMap<>();

	private static List<String> TOOL_TYPES = new ArrayList<String>(
        Arrays.asList(
            "pickaxe",
            "axe",
            "hoe",
            "mattock",
            "shears",
            "shovel",
            "sword",
            "weapon",
            "hammer",
            "wirecutter",
            "wrench"
        )
	);
    
    public static void init() {
        if (ModList.get().isLoaded("tinkersarchery")) {
            if (!TOOL_TYPES.contains("crossbow")) {
                TOOL_TYPES.add("crossbow");
            }
            if (!TOOL_TYPES.contains("bow")) {
                TOOL_TYPES.add("bow");
            }
        }

        whitelistToolsMap.clear();

        for (String item : ConfigHandler.Server.whitelistItems()) {
            String[] nameParts = item.split("-");
            String toolType = nameParts[0];

            if (TOOL_TYPES.contains(toolType)) {
                whitelistToolsMap.put(nameParts[1], nameParts[0]);
            }
        }
    }

    public static boolean isWhitelistItem(ItemStack stack) {
        String itemName = stack.getItem().getRegistryName().toString();
        String modid = getModId(itemName);

        return ConfigHandler.Server.whitelistMods().contains(modid)
                || whitelistToolsMap.get(itemName) != null;
    }

    public static String getModId(String name) {
        String[] nameParts = name.split(":");

        return nameParts.length == 2 ? nameParts[0] : name;
    }

    public static boolean hasTinkerBow() {
        return ItemUse.TOOL_TYPES.contains("bow");
    }

    public static String getToolClass(ItemStack stack) {
        String itemName = stack.getItem().getRegistryName().toString();
		String type = whitelistToolsMap.get(itemName);

        if (type == null) {
			String[] nameParts = itemName.split("[^a-z]+");
			for (String toolType : TOOL_TYPES) {
				if (itemName.contains(toolType)
						&& Arrays.asList(nameParts).contains(toolType)) {
					type = toolType;
				}
			}
		}

        return type;
    }

    public static boolean isCorrectTool(BlockState state, Player player, ItemStack handStack) {
        // Always allow destroySpeed == 0
        if (((AbstractBlockStateAccessor) state).getDestroySpeed() == 0) {
            return true;
        }

        final ToolType expectedToolType = HarvestBlock.BLOCK_TOOL_TYPES.getOrDefault(state.getBlock(), ToolType.NONE);

        // No expected tool type, so we have to return true because we don't know otherwise
        if (expectedToolType == ToolType.NONE) {
            return true;
        }

        // Now, we need to infer if the current item is of a given tool type. Try two things:
        final ToolType inferredToolType = HarvestBlock.ITEM_TOOL_TYPES.getOrDefault(handStack.getItem(), ToolType.NONE);

        if (inferredToolType == expectedToolType) {
            return true; // Correct tool type found!
        }

        // Otherwise, we check if the expected tool type can identify this item as it's tool
        return expectedToolType.is(handStack.getItem());
    }

}
