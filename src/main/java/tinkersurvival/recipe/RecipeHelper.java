package tinkersurvival.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.GameData;

import tinkersurvival.recipe.FakeRecipe;
import tinkersurvival.recipe.ShapedOreRecipeHelper;

public class RecipeHelper {

    public static void addShapedOreRecipe(ItemStack output, Object... params) {
        ResourceLocation location = getSafeNameForRecipe(output);
        IRecipe recipe = new ShapedOreRecipeHelper(location, output, params).setRegistryName(location);
        GameData.register_impl(recipe);
    }

    // Adds a basic fake recipe for recipes nerfed in game
    public static void addFakeRecipe(IRecipe recipe) {
        GameData.register_impl(new FakeRecipe(recipe.getGroup()).setRegistryName(recipe.getRegistryName()));
    }

    public static void addSmelting(ItemStack input, ItemStack output) {
        GameRegistry.addSmelting(input, output, 1F);
    }

    public static ResourceLocation getSafeNameForRecipe(ItemStack output) {
        ModContainer activeContainer = Loader.instance().activeModContainer();
        ResourceLocation baseLoc = new ResourceLocation(activeContainer.getModId(), output.getItem().getRegistryName().getPath());
        ResourceLocation recipeLoc = baseLoc;
        int index = 0;
        while (CraftingManager.REGISTRY.containsKey(recipeLoc)) {
            index++;
            recipeLoc = new ResourceLocation(activeContainer.getModId(), baseLoc.getPath() + "_" + index);
        }
        return recipeLoc;
    }

}
