package tinkersurvival.util;

import com.google.common.collect.Sets;

import java.util.Set;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import tinkersurvival.config.Config;

public class Event {

    public static boolean isValidTool(ItemStack stack, String toolClass) {
        String item = stack.getItem().getRegistryName().toString();
        return _isValidTool(stack) || isWhitelistTool(item, toolClass);
    }

    public static boolean isWhitelistTool(String itemName, String toolClass) {
        final Set<String> tools = Sets.newHashSet(Config.Tools.TOOLS_WHITELIST);
        return tools.contains(toolClass + "-" + itemName);
    }

    public static boolean isUselessAxe(ItemStack stack) {
        return !_isValidTool(stack) && stack.getItem() instanceof ItemAxe;
    }

    public static boolean isUselessBow(ItemStack stack) {
        return !_isValidTool(stack) && stack.getItem() instanceof ItemBow;
    }

    public static boolean isUselessHoe(ItemStack stack) {
        return !_isValidTool(stack) && stack.getItem() instanceof ItemHoe;
    }

    public static boolean isUselessPickaxe(ItemStack stack) {
        return !_isValidTool(stack) && stack.getItem() instanceof ItemPickaxe;
    }

    public static boolean isUselessSpade(ItemStack stack) {
        return !_isValidTool(stack) && stack.getItem() instanceof ItemSpade;
    }

    public static boolean isUselessSword(ItemStack stack) {
        return !_isValidTool(stack) && stack.getItem() instanceof ItemSword;
    }

    private static boolean _isUselessTool(ItemStack stack) {
        return !_isValidTool(stack) && stack.getItem() instanceof ItemTool;
    }

    private static boolean _isValidTool(ItemStack stack) {
        final Set<String> modids = Sets.newHashSet(Config.Tools.MOD_TOOL_WHITELIST);
        String item = stack.getItem().getRegistryName().toString();
        String[] nameParts = item.split(":");
        String modid = item;
        if (nameParts.length > 0) {
            modid = nameParts[0];
        }
        return modids.contains(modid);
    }

    public static boolean isUselessTool(ItemStack stack) {
        return (isUselessAxe(stack)
                || isUselessBow(stack)
                || isUselessHoe(stack)
                || isUselessPickaxe(stack)
                || isUselessSpade(stack)
                || isUselessSword(stack)
                || _isUselessTool(stack));
    }

}
