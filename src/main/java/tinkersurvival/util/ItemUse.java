package tinkersurvival.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.fml.ModList;

import tinkersurvival.common.HarvestBlock;
import tinkersurvival.common.TagManager;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.mixin.AbstractBlockStateAccessor;
import tinkersurvival.TinkerSurvival;

public class ItemUse {

    private static final Map<String, String> whitelistToolsMap = new HashMap<>();

    private static final List<String> TOOL_TYPES = new ArrayList<>(
        Arrays.asList(
            "pickaxe",
            "pickadze",
            "axe",
            "hoe",
            "mattock",
            "kama",
            "shears",
            "shovel",
            "sword",
            "weapon",
            "hammer",
            "wirecutter",
            "wrench",
            "drill",
            "building",
            "revolver",
            "saw",
            "crook"
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
        String itemName = Objects.requireNonNull(stack.getItem().getRegistryName()).toString();
        String modid = getModId(itemName);

        return ConfigHandler.Server.whitelistMods().contains(modid)
                || whitelistToolsMap.get(itemName) != null;
    }

    public static String getModId(Block block) {
        return getModId(Objects.requireNonNull(block.getRegistryName()).toString());
    }

    public static String getModId(ItemStack stack) {
        return getModId(Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
    }

    public static String getModId(String name) {
        String[] nameParts = name.split(":");

        return nameParts.length == 2 ? nameParts[0] : name;
    }

    public static boolean hasTinkerBow() {
        return ItemUse.TOOL_TYPES.contains("bow");
    }

    public static boolean alwaysDrops(BlockState state) {
        if (((AbstractBlockStateAccessor) state).getDestroySpeed() == 0) {
            return true;
        }
        else {
            return state.is(TagManager.Blocks.ALWAYS_DROPS);
        }
    }

    public static String getToolClass(ItemStack stack) {
        String itemName = Objects.requireNonNull(stack.getItem().getRegistryName()).toString();
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

        if (type != null) {
            return type;
        }
        else {
            return "unknown";
        }
    }

    public static boolean isCorrectToolType(String type, ItemStack handStack) {
        boolean isCorrectToolType = false;
        String toolClass = getToolClass(handStack);

        switch(type) {
            case "pickaxe":
                isCorrectToolType = toolClass.equals(type)
                        || toolClass.equals("drill")
                        || toolClass.equals("pickadze")
                        || toolClass.equals("building")
                        || ToolType.PICKAXE.is(handStack.getItem());
                break;
            case "axe":
                isCorrectToolType = toolClass.equals(type)
                        || toolClass.equals("mattock")
                        || toolClass.equals("building")
                        || ToolType.AXE.is(handStack.getItem());
                break;
            case "shovel":
                isCorrectToolType = toolClass.equals(type)
                        || toolClass.equals("mattock")
                        || toolClass.equals("drill")
                        || toolClass.equals("pickadze")
                        || toolClass.equals("building")
                        || ToolType.SHOVEL.is(handStack.getItem());
                break;
            case "hoe":
                isCorrectToolType = toolClass.equals(type)
                        || toolClass.equals("mattock")
                        || toolClass.equals("building")
                        || toolClass.equals("crook")
                        || ToolType.HOE.is(handStack.getItem());
                break;
        }

        return isCorrectToolType;
    }

    public static boolean isCorrectTool(BlockState state, Player player, ItemStack handStack) {
        // Always allow destroySpeed == 0
        if (((AbstractBlockStateAccessor) state).getDestroySpeed() == 0) {
            return true;
        }

        // Check tagged tool uses
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) && isCorrectToolType("pickaxe", handStack)) {
            return true;
        }
        else if (state.is(BlockTags.MINEABLE_WITH_AXE) && isCorrectToolType("axe", handStack)) {
            return true;
        }
        else if (state.is(BlockTags.MINEABLE_WITH_SHOVEL) && isCorrectToolType("shovel", handStack)) {
            return true;
        }
        else if (state.is(BlockTags.MINEABLE_WITH_HOE) && isCorrectToolType("hoe", handStack)) {
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

    public static boolean isArmor(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem;
    }

    public static boolean isWhitelistArmor(ItemStack stack) {
        String itemName = Objects.requireNonNull(stack.getItem().getRegistryName()).toString();
        String modid = getModId(itemName);

        return ConfigHandler.Server.armorWhitelistMods().contains(modid) || ConfigHandler.Server.armorWhitelistItems().contains(itemName);
    }

}
