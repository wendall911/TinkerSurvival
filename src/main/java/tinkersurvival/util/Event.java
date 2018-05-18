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

    public static boolean isValidTool(ItemStack stack) {
        final Set<String> set = Sets.newHashSet(Config.Tools.MOD_TOOL_WHITELIST);
        return set.contains(stack.getItem().getRegistryName().toString().split(":")[0]);
    }

    public static boolean isUselessAxe(ItemStack stack) {
        return !isValidTool(stack) && stack.getItem() instanceof ItemAxe;
    }

    public static boolean isUselessBow(ItemStack stack) {
        return !isValidTool(stack) && stack.getItem() instanceof ItemBow;
    }

    public static boolean isUselessHoe(ItemStack stack) {
        return !isValidTool(stack) && stack.getItem() instanceof ItemHoe;
    }

    public static boolean isUselessPickaxe(ItemStack stack) {
        return !isValidTool(stack) && stack.getItem() instanceof ItemPickaxe;
    }

    public static boolean isUselessSpade(ItemStack stack) {
        return !isValidTool(stack) && stack.getItem() instanceof ItemSpade;
    }

    public static boolean isUselessSword(ItemStack stack) {
        return !isValidTool(stack) && stack.getItem() instanceof ItemSword;
    }

    private static boolean _isUselessTool(ItemStack stack) {
        return !isValidTool(stack) && stack.getItem() instanceof ItemTool;
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
