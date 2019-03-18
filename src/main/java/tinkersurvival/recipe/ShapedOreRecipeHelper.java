package tinkersurvival.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.oredict.ShapedOreRecipe;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.tools.tool.CrudeKnife;
import tinkersurvival.tools.tool.CrudeSaw;
import tinkersurvival.tools.tool.Knife;
import tinkersurvival.tools.tool.Saw;

public class ShapedOreRecipeHelper extends ShapedOreRecipe {

    public ShapedOreRecipeHelper(ResourceLocation group, ItemStack result, Object... recipe) {
		super(group, result, recipe);
    }

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> remains = super.getRemainingItems(inv);
        Map<Integer, ItemStack> returnItems = new HashMap<>();

		for (int i = 0; i < remains.size(); i++) {
			ItemStack slot = inv.getStackInSlot(i);
			ItemStack remain = remains.get(i);
            ItemStack tool = ItemStack.EMPTY;

			if (!slot.isEmpty()
                    && (slot.getItem() instanceof Knife
                        || slot.getItem() instanceof Saw
                        || slot.getItem() instanceof CrudeKnife
                        || slot.getItem() instanceof CrudeSaw
                        || slot.getItem() == Items.BOWL)) {
                if (slot.isItemStackDamageable()) {
                    int calculatedDamage = slot.getMaxDamage() - (slot.getItemDamage() + 1);
                    String toolName = I18n.translateToLocal(slot.getItem().getTranslationKey() + ".name");
                    
                    tool = setDamage(slot.copy(), toolName, calculatedDamage);

                    if (calculatedDamage == 0
                            && (tool.getItem() instanceof Saw || tool.getItem() instanceof Knife)) {
                        ItemStack tool2 = tool.copy();
                        tool = ItemStack.EMPTY;
                        returnItems.put(i + 1, tool2);
                    }
                }
                else {
                    tool = slot.copy();
                    tool.setCount(1);
                }
                returnItems.put(i, tool);
			}
		}
        for (Map.Entry<Integer, ItemStack> entry : returnItems.entrySet()) {
            remains.set(entry.getKey(), entry.getValue());
        }

		return remains;
	}

    private ItemStack setDamage(ItemStack tool, String toolName, int calculatedDamage) {
        if (calculatedDamage == 0
                && (tool.getItem() instanceof CrudeKnife
                    || tool.getItem() instanceof CrudeSaw)) {
            tool = ItemStack.EMPTY;
        }
        else {
            tool.setItemDamage(tool.getItemDamage() + 1);
            if (tool.getItem() instanceof Saw || tool.getItem() instanceof Knife) {
                TinkerSurvival.modToolLeveling.addCxp(tool, 1);
            }
        }

        if (calculatedDamage == 0) {
            TinkerSurvival.proxy.toastHint("message.notice", "message.tool_broke", toolName);
        }
        if (calculatedDamage == -1) {
            tool = ItemStack.EMPTY;
        }

        return tool;
    }

}
