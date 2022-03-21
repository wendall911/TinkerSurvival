package tinkersurvival.data.recipes;

import java.util.function.Consumer;

import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.common.Tags;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - Recipies";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        // Material Recipes
        ShapedRecipeBuilder.shaped(Blocks.COBBLESTONE)
                .define('R', TinkerSurvivalWorld.ROCK_STONE.get())
                .pattern("RR")
                .pattern("RR")
                .unlockedBy("has_loose_rock", has(TinkerSurvivalWorld.ROCK_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Items.FLINT)
                .define('S', TinkerSurvivalWorld.FLINT_SHARD.get())
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_flint_shard", has(TinkerSurvivalWorld.FLINT_SHARD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalWorld.GRASS_STRING.get())
                .define('F', TinkerSurvivalWorld.GRASS_FIBER.get())
                .pattern("FF")
                .pattern("F ")
                .unlockedBy("has_grass_fiber", has(TinkerSurvivalWorld.GRASS_FIBER.get()))
                .save(consumer);

        // Saw Blades
        ShapedRecipeBuilder.shaped(TinkerSurvivalWorld.CRUDE_SAW_BLADE.get())
                .define('D', TinkerSurvivalWorld.FLINT_SHARD.get())
                .define('S', TinkerSurvivalWorld.GRASS_STRING.get())
                .define('I', Items.STICK)
                .pattern("ID")
                .pattern("SD")
                .unlockedBy("has_grass_string", has(TinkerSurvivalWorld.GRASS_STRING.get()))
                .save(consumer);
        
        // Tool Recipes
        ShapedRecipeBuilder.shaped(TinkerSurvivalWorld.CRUDE_KNIFE.get())
                .define('S', TinkerSurvivalWorld.FLINT_SHARD.get())
                .define('T', Items.STICK)
                .pattern("S")
                .pattern("T")
                .unlockedBy("has_flint_shard", has(TinkerSurvivalWorld.FLINT_SHARD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalWorld.CRUDE_HATCHET.get())
                .define('R', TinkerSurvivalWorld.ROCK_STONE.get())
                .define('S', TinkerSurvivalWorld.GRASS_STRING.get())
                .define('I', Items.STICK)
                .pattern("SR")
                .pattern("I ")
                .unlockedBy("has_loose_rock", has(TinkerSurvivalWorld.ROCK_STONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalWorld.CRUDE_SAW_HANDLE.get())
                .define('S', TinkerSurvivalWorld.GRASS_STRING.get())
                .define('I', Items.STICK)
                .pattern("IS")
                .pattern(" I")
                .unlockedBy("has_grass_string", has(TinkerSurvivalWorld.GRASS_STRING.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalWorld.CRUDE_SAW.get())
                .define('H', TinkerSurvivalWorld.CRUDE_SAW_HANDLE.get())
                .define('B', TinkerSurvivalWorld.CRUDE_SAW_BLADE.get())
                .define('S', TinkerSurvivalWorld.GRASS_STRING.get())
                .pattern("BS")
                .pattern(" H")
                .unlockedBy("has_crude_saw_handle", has(TinkerSurvivalWorld.CRUDE_SAW_HANDLE.get()))
                .save(consumer);

        // Knife Recipes
        ShapelessRecipeBuilder.shapeless(Items.STICK)
                .requires(ItemTags.SAPLINGS)
                .requires(TinkerSurvivalWorld.CRUDE_KNIFE.get())
                .group("sticks")
                .unlockedBy("has_sapling", has(ItemTags.SAPLINGS))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.STRING, 2)
                .requires(ItemTags.WOOL)
                .requires(TinkerSurvivalWorld.CRUDE_KNIFE.get())
                .group("string")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(TinkerSurvivalWorld.FLINT_SHARD.get(), 2)
                .requires(TagManager.Items.FLINT_KNAPPABLE)
                .requires(TagManager.Items.KNIFE_TOOLS)
                .group("flint_shards")
                .unlockedBy("has_crude_knife", has(TinkerSurvivalWorld.CRUDE_KNIFE.get()))
                .save(consumer);

        // Saw Recipes
        plankRecipeBuilder(consumer, Blocks.OAK_PLANKS, ItemTags.OAK_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.ACACIA_PLANKS, ItemTags.ACACIA_LOGS, "has_log");
        plankRecipeBuilder(consumer, Blocks.BIRCH_PLANKS, ItemTags.BIRCH_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS, "has_log");
        plankRecipeBuilder(consumer, Blocks.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.WARPED_PLANKS, ItemTags.WARPED_STEMS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.CRIMSON_PLANKS, ItemTags.CRIMSON_STEMS, "has_logs");

        ShapelessRecipeBuilder.shapeless(Items.STICK, 2)
                .requires(ItemTags.PLANKS)
                .requires(TagManager.Items.SAW_TOOLS)
                .group("sticks")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "crude_saw_sticks"));

    }

    private static void plankRecipeBuilder(Consumer<FinishedRecipe> consumer, ItemLike item, Tag<Item> itemTag, String label) {
        ShapelessRecipeBuilder.shapeless(item, 2)
                .requires(itemTag)
                .requires(TinkerSurvivalWorld.CRUDE_SAW.get())
                .group("planks")
                .unlockedBy(label, has(itemTag))
                .save(consumer);
    }

}
