package tinkersurvival.data.recipes;

import java.util.function.Consumer;

import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.Tags;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        /*
        ShapelessRecipeBuilder.shapeless(ModItems.WHITE_BRICK.get())
        .requires(Tags.Items.INGOTS_BRICK)
        .requires(Tags.Items.DYES_WHITE)
        .unlockedBy("has item", has(Tags.Items.INGOTS_BRICK))
        .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.PINK_BRICKS.get())
        .define('#', ModTags.Items.BRICK_PINK)
        .pattern("##")
        .pattern("##")
        .unlockedBy("has item", has(ModTags.Items.BRICK_PINK))
        .save(consumer, coloredbricks.getId("pink_bricks_alt"));
        */
    }
}
