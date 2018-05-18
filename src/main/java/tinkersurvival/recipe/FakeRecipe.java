package tinkersurvival.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FakeRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    private final String group;

    public FakeRecipe(String group) {
        this.group = group;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return false;
    }
}
