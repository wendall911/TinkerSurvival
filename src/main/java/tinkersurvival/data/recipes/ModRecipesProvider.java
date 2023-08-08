package tinkersurvival.data.recipes;

import java.util.Objects;
import java.util.function.Consumer;

import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;

import slimeknights.tconstruct.tables.TinkerTables;

import tinkersurvival.common.TagManager;
import tinkersurvival.data.integration.ModIntegration;
import tinkersurvival.items.TConItems;
import tinkersurvival.items.TinkerSurvivalBooks;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public @NotNull String getName() {
        return "TinkerSurvival - Recipies";
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ItemLike rockStone = TinkerSurvivalWorld.ROCK_STONE;
        ItemLike flintShard = TinkerSurvivalItems.FLINT_SHARD;
        ItemLike plantFiber = TinkerSurvivalItems.PLANT_FIBER;
        ItemLike plantString = TinkerSurvivalItems.PLANT_STRING;
        ItemLike mortar = TinkerSurvivalItems.MORTAR_AND_PESTLE;
        ItemLike plantPaste = TinkerSurvivalItems.PLANT_PASTE;
        ItemLike ointment = TinkerSurvivalItems.OINTMENT;
        ItemLike cloth = TinkerSurvivalItems.CLOTH;
        ItemLike crudeKnife = TinkerSurvivalItems.CRUDE_KNIFE;
        ItemLike introBook = TinkerSurvivalBooks.INTRO_BOOK;
        Consumer<FinishedRecipe> wrapped;

        // Material Recipes
        ShapedRecipeBuilder.shaped(Blocks.COBBLESTONE)
                .define('R', rockStone)
                .pattern("RR")
                .pattern("RR")
                .unlockedBy("has_loose_rock", has(rockStone))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "cobblestone_from_rocks"));

        ShapedRecipeBuilder.shaped(Items.FLINT)
                .define('S', flintShard)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_flint_shard", has(flintShard))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "flint_from_shards"));

        ShapedRecipeBuilder.shaped(plantString)
                .define('F', plantFiber)
                .pattern("FF")
                .pattern("F ")
                .unlockedBy("has_plant_fiber", has(plantFiber))
                .save(consumer);

        ShapedRecipeBuilder.shaped(plantPaste)
                .define('F', plantFiber)
                .define('U', mortar)
                .pattern("F")
                .pattern("U")
                .unlockedBy("has_plant_fiber", has(plantFiber))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ointment)
                .define('P', plantPaste)
                .pattern("PP")
                .pattern("PP")
                .unlockedBy("has_plant_paste", has(plantPaste))
                .save(consumer);

        ShapedRecipeBuilder.shaped(cloth)
                .define('S', Items.STRING)
                .pattern("SSS")
                .unlockedBy("has_string", has(Items.STRING))
                .save(consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(plantString), Items.STRING, 0.1F, 50)
                .unlockedBy("has_plant_string", has(plantString))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "string_from_plant_string"));


        // Saw Blades
        ShapedRecipeBuilder.shaped(TinkerSurvivalItems.CRUDE_SAW_BLADE)
                .define('D', flintShard)
                .define('S', plantString)
                .define('I', Items.STICK)
                .pattern("ID")
                .pattern("SD")
                .unlockedBy("has_plant_string", has(plantString))
                .save(consumer);
        
        // Tool Recipes
        ShapedRecipeBuilder.shaped(crudeKnife)
                .define('S', flintShard)
                .define('T', Items.STICK)
                .pattern("S")
                .pattern("T")
                .unlockedBy("has_flint_shard", has(flintShard))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalItems.CRUDE_HATCHET)
                .define('R', rockStone)
                .define('S', plantString)
                .define('I', Items.STICK)
                .pattern("SR")
                .pattern("I ")
                .unlockedBy("has_loose_rock", has(rockStone))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalItems.CRUDE_SAW_HANDLE)
                .define('S', plantString)
                .define('I', Items.STICK)
                .pattern("IS")
                .pattern(" I")
                .unlockedBy("has_plant_string", has(plantString))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalItems.CRUDE_SAW)
                .define('H', TinkerSurvivalItems.CRUDE_SAW_HANDLE)
                .define('B', TinkerSurvivalItems.CRUDE_SAW_BLADE)
                .define('S', plantString)
                .pattern("BS")
                .pattern(" H")
                .unlockedBy("has_crude_saw_handle", has(TinkerSurvivalItems.CRUDE_SAW_HANDLE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(mortar)
                .define('I', Items.STICK)
                .define('P', ItemTags.PLANKS)
                .define('R', rockStone)
                .pattern("  I")
                .pattern("PRP")
                .pattern(" P ")
                .unlockedBy("has_plant_fiber", has(plantFiber))
                .save(consumer);

        // Knife Recipes
        ShapelessRecipeBuilder.shapeless(Items.STICK)
                .requires(ItemTags.SAPLINGS)
                .requires(TagManager.Items.KNIFE_TOOLS)
                .group("sticks")
                .unlockedBy("has_sapling", has(ItemTags.SAPLINGS))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "stick_from_sapling"));

        ShapelessRecipeBuilder.shapeless(Items.STRING, 2)
                .requires(ItemTags.WOOL)
                .requires(crudeKnife)
                .group("string")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "string_from_wool"));

        ShapelessRecipeBuilder.shapeless(Items.STRING, 4)
                .requires(ItemTags.WOOL)
                .requires(TConItems.KNIFE)
                .group("string")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "string_from_wool_advanced"));

        ShapelessRecipeBuilder.shapeless(flintShard, 2)
                .requires(TagManager.Items.FLINT_KNAPPABLE)
                .requires(TagManager.Items.KNIFE_TOOLS)
                .group("flint_shards")
                .unlockedBy("has_crude_knife", has(crudeKnife))
                .save(consumer);

        //Bandages
        ShapedRecipeBuilder.shaped(TinkerSurvivalItems.CRUDE_BANDAGE)
                .define('P', plantString)
                .define('S', Items.STICK)
                .define('F', plantFiber)
                .pattern("SF")
                .pattern("PF")
                .unlockedBy("has_plant_string", has(plantString))
                .save(consumer);

        ShapedRecipeBuilder.shaped(TinkerSurvivalItems.BANDAGE)
                .define('P', plantString)
                .define('S', Items.STICK)
                .define('C', cloth)
                .define('O', ointment)
                .pattern("SC")
                .pattern("PO")
                .unlockedBy("has_ointment", has(ointment))
                .save(consumer);

        // Book
        ShapelessRecipeBuilder.shapeless(introBook)
                .requires(Items.DIRT)
                .requires(Items.DIRT)
                .group("books")
                .unlockedBy("has_loose_rock", has(rockStone))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "book_from_dirt"));

        ShapelessRecipeBuilder.shapeless(Items.DIRT, 2)
                .requires(introBook)
                .group("books")
                .unlockedBy("has_intro_book", has(introBook))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, "dirt_from_book"));

        // Saw Recipes
        // Minecraft
        plankRecipeBuilder(consumer, Blocks.OAK_PLANKS, ItemTags.OAK_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.ACACIA_PLANKS, ItemTags.ACACIA_LOGS, "has_log");
        plankRecipeBuilder(consumer, Blocks.BIRCH_PLANKS, ItemTags.BIRCH_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS, "has_log");
        plankRecipeBuilder(consumer, Blocks.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.WARPED_PLANKS, ItemTags.WARPED_STEMS, "has_logs");
        plankRecipeBuilder(consumer, Blocks.CRIMSON_PLANKS, ItemTags.CRIMSON_STEMS, "has_logs");

        // Fruit Trees
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.FT_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.CHERRY_PLANKS, TagManager.Items.CHERRY_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.CITRUS_PLANKS, TagManager.Items.CITRUS_LOGS, "has_logs");

        // Biome Makeover
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.BMO_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.BMO_ANCIENT_OAK_PLANKS, TagManager.Items.BMO_ANCIENT_OAK_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BMO_BLIGHTED_BALSA_PLANKS, TagManager.Items.BMO_BLIGHTED_BALSA_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BMO_SWAMP_CYPRESS_PLANKS, TagManager.Items.BMO_SWAMP_CYPRESS_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BMO_WILLOW_PLANKS, TagManager.Items.BMO_WILLOW_LOGS, "has_logs");

        //Biomes O' Plenty
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.BOP_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.BOP_CHERRY_PLANKS, TagManager.Items.BOP_CHERRY_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_DEAD_PLANKS, TagManager.Items.BOP_DEAD_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_FIR_PLANKS, TagManager.Items.BOP_FIR_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_HELLBARK_PLANKS, TagManager.Items.BOP_HELLBARK_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_JACARANDA_PLANKS, TagManager.Items.BOP_JACARANDA_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_MAGIC_PLANKS, TagManager.Items.BOP_MAGIC_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_MAHOGANY_PLANKS, TagManager.Items.BOP_MAHOGANY_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_PALM_PLANKS, TagManager.Items.BOP_PALM_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_REDWOOD_PLANKS, TagManager.Items.BOP_REDWOOD_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_UMBRAN_PLANKS, TagManager.Items.BOP_UMBRAN_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOP_WILLOW_PLANKS, TagManager.Items.BOP_WILLOW_LOGS, "has_logs");

        //Quark
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.QUARK_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.QUARK_AZALEA_PLANKS, TagManager.Items.QUARK_AZALEA_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.QUARK_BLOSSOM_PLANKS, TagManager.Items.QUARK_BLOSSOM_LOGS, "has_logs");

        //All You Can Eat
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.AYCE_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.AYCE_HAZEL_PLANKS, TagManager.Items.AYCE_HAZEL_LOGS, "has_logs");

        // Tinkers' Construct
        plankRecipeBuilder(consumer, ModIntegration.TCON_BLOODSHROOM_PLANKS, TagManager.Items.TCON_BLOODSHROOM_LOGS, "has_logs");
        plankRecipeBuilder(consumer, ModIntegration.TCON_GREENHEART_PLANKS, TagManager.Items.TCON_GREENHEART_LOGS, "has_logs");
        plankRecipeBuilder(consumer, ModIntegration.TCON_SKYROOT_PLANKS, TagManager.Items.TCON_SKYROOT_LOGS, "has_logs");

        // Water Source
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.WS_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.WS_PALM_TREE_PLANKS, TagManager.Items.WS_PALM_TREE_LOGS, "has_logs");

        // Botania
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.BOTANIA_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.BOTANIA_DREAMWOOD_PLANKS, TagManager.Items.BOTANIA_DREAMWOOD_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BOTANIA_LIVINGWOOD_PLANKS, TagManager.Items.BOTANIA_LIVINGWOOD_LOGS, "has_logs");

        // Ars Nouveau
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.AN_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.AN_ARCHWOOD_PLANKS, ItemTags.create(new ResourceLocation("forge:logs/archwood")), "has_logs");

        // Undergarden
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.UNDERGARDEN_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.UNDERGARDEN_GRONGLE_PLANKS, TagManager.Items.UNDERGARDEN_GRONGLE_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.UNDERGARDEN_SMOGSTEM_PLANKS, TagManager.Items.UNDERGARDEN_SMOGSTEM_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.UNDERGARDEN_WIGGLEWOOD_PLANKS, TagManager.Items.UNDERGARDEN_WIGGLEWOOD_LOGS, "has_logs");

        // BYG
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.BYG_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.BYG_ETHER_PLANKS, TagManager.Items.BYG_ETHER_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_MANGROVE_PLANKS, TagManager.Items.BYG_MANGROVE_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_REDWOOD_PLANKS, TagManager.Items.BYG_REDWOOD_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_BLUE_ENCHANTED_PLANKS, TagManager.Items.BYG_BLUE_ENCHANTED_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_GREEN_ENCHANTED_PLANKS, TagManager.Items.BYG_GREEN_ENCHANTED_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_LAMENT_PLANKS, TagManager.Items.BYG_LAMENT_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_WITHERING_OAK_PLANKS, TagManager.Items.BYG_WITHERING_OAK_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_MAHOGANY_PLANKS, TagManager.Items.BYG_MAHOGANY_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_CHERRY_PLANKS, TagManager.Items.BYG_CHERRY_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_PALO_VERDE_PLANKS, TagManager.Items.BYG_PALO_VERDE_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_BAOBAB_PLANKS, TagManager.Items.BYG_BAOBAB_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_JACARANDA_PLANKS, TagManager.Items.BYG_JACARANDA_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_CYPRESS_PLANKS, TagManager.Items.BYG_CYPRESS_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_PALM_PLANKS, TagManager.Items.BYG_PALM_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_EBONY_PLANKS, TagManager.Items.BYG_EBONY_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_IMBUED_NIGHTSHADE_PLANKS, TagManager.Items.BYG_IMBUED_NIGHTSHADE_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_NIGHTSHADE_PLANKS, TagManager.Items.BYG_NIGHTSHADE_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_RAINBOW_EUCALYPTUS_PLANKS, TagManager.Items.BYG_RAINBOW_EUCALYPTUS_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_ASPEN_PLANKS, TagManager.Items.BYG_ASPEN_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_SKYROOT_PLANKS, TagManager.Items.BYG_SKYROOT_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_FIR_PLANKS, TagManager.Items.BYG_FIR_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_SKYRIS_PLANKS, TagManager.Items.BYG_SKYRIS_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_CIKA_PLANKS, TagManager.Items.BYG_CIKA_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_HOLLY_PLANKS, TagManager.Items.BYG_HOLLY_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_MAPLE_PLANKS, TagManager.Items.BYG_MAPLE_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_PINE_PLANKS, TagManager.Items.BYG_PINE_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_WILLOW_PLANKS, TagManager.Items.BYG_WILLOW_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_WITCH_HAZEL_PLANKS, TagManager.Items.BYG_WITCH_HAZEL_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_ZELKOVA_PLANKS, TagManager.Items.BYG_ZELKOVA_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_BULBIS_PLANKS, TagManager.Items.BYG_BULBIS_STEMS, "has_stems");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_IMPARIUS_PLANKS, TagManager.Items.BYG_IMPARIUS_STEMS, "has_stems");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_SYTHIAN_PLANKS, TagManager.Items.BYG_SYTHIAN_STEMS, "has_stems");
        plankRecipeBuilder(wrapped, ModIntegration.BYG_EMBUR_PLANKS, TagManager.Items.BYG_EMBUR_PEDU, "has_pedu");

        // Twilight Forest
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.TF_MODID));
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_CANOPY_PLANKS, ModIntegration.TF_CANOPY_LOG, "has_logs", "wood/", "canopy_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_CANOPY_PLANKS, ModIntegration.TF_CANOPY_STRIPPED_LOG, "has_logs", "wood/", "canopy_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_CANOPY_PLANKS, ModIntegration.TF_CANOPY_WOOD, "has_logs", "wood/", "canopy_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_CANOPY_PLANKS, ModIntegration.TF_CANOPY_STRIPPED_WOOD, "has_logs", "wood/", "canopy_from_stripped_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_DARK_PLANKS, ModIntegration.TF_DARK_LOG, "has_logs", "wood/", "dark_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_DARK_PLANKS, ModIntegration.TF_DARK_STRIPPED_LOG, "has_logs", "wood/", "dark_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_DARK_PLANKS, ModIntegration.TF_DARK_WOOD, "has_logs", "wood/", "dark_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_DARK_PLANKS, ModIntegration.TF_DARK_STRIPPED_WOOD, "has_logs", "wood/", "dark_from_stripped_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MANGROVE_PLANKS, ModIntegration.TF_MANGROVE_LOG, "has_logs", "wood/", "mangrove_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MANGROVE_PLANKS, ModIntegration.TF_MANGROVE_STRIPPED_LOG, "has_logs", "wood/", "mangrove_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MANGROVE_PLANKS, ModIntegration.TF_MANGROVE_WOOD, "has_logs", "wood/", "mangrove_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MANGROVE_PLANKS, ModIntegration.TF_MANGROVE_STRIPPED_WOOD, "has_logs", "wood/", "mangrove_from_stripped_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MINING_PLANKS, ModIntegration.TF_MINING_LOG, "has_logs", "wood/", "mining_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MINING_PLANKS, ModIntegration.TF_MINING_STRIPPED_LOG, "has_logs", "wood/", "mining_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MINING_PLANKS, ModIntegration.TF_MINING_WOOD, "has_logs", "wood/", "mining_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_MINING_PLANKS, ModIntegration.TF_MINING_STRIPPED_WOOD, "has_logs", "wood/", "mining_from_stripped_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_SORTING_PLANKS, ModIntegration.TF_SORTING_LOG, "has_logs", "wood/", "sorting_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_SORTING_PLANKS, ModIntegration.TF_SORTING_STRIPPED_LOG, "has_logs", "wood/", "sorting_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_SORTING_PLANKS, ModIntegration.TF_SORTING_WOOD, "has_logs", "wood/", "sorting_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_SORTING_PLANKS, ModIntegration.TF_SORTING_STRIPPED_WOOD, "has_logs", "wood/", "sorting_from_stripped_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TIME_PLANKS, ModIntegration.TF_TIME_LOG, "has_logs", "wood/", "time_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TIME_PLANKS, ModIntegration.TF_TIME_STRIPPED_LOG, "has_logs", "wood/", "time_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TIME_PLANKS, ModIntegration.TF_TIME_WOOD, "has_logs", "wood/", "time_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TIME_PLANKS, ModIntegration.TF_TIME_STRIPPED_WOOD, "has_logs", "wood/", "time_from_stripped_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TRANSFORMATION_PLANKS, ModIntegration.TF_TRANSFORMATION_LOG, "has_logs", "wood/", "transformation_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TRANSFORMATION_PLANKS, ModIntegration.TF_TRANSFORMATION_STRIPPED_LOG, "has_logs", "wood/", "transformation_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TRANSFORMATION_PLANKS, ModIntegration.TF_TRANSFORMATION_WOOD, "has_logs", "wood/", "transformation_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TRANSFORMATION_PLANKS, ModIntegration.TF_TRANSFORMATION_STRIPPED_WOOD, "has_logs", "wood/", "transformation_from_stripped_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TWILIGHT_OAK_PLANKS, ModIntegration.TF_TWILIGHT_OAK_LOG, "has_logs", "wood/", "twilight_oak_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TWILIGHT_OAK_PLANKS, ModIntegration.TF_TWILIGHT_OAK_STRIPPED_LOG, "has_logs", "wood/", "twilight_oak_from_stripped_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TWILIGHT_OAK_PLANKS, ModIntegration.TF_TWILIGHT_OAK_WOOD, "has_logs", "wood/", "twilight_oak_from_wood_planks", ModIntegration.TF_MODID);
        itemPlankRecipeBuilder(wrapped, ModIntegration.TF_TWILIGHT_OAK_PLANKS, ModIntegration.TF_TWILIGHT_OAK_STRIPPED_WOOD, "has_logs", "wood/", "twilight_oak_from_stripped_wood_planks", ModIntegration.TF_MODID);

        ShapelessRecipeBuilder.shapeless(Items.OAK_PLANKS, 64)
                .requires(TagManager.Items.TF_GIANT_LOGS)
                .requires(TConItems.SAW)
                .group("planks")
                .unlockedBy("has_logs", has(TinkerTables.tinkerStation))
                .save(consumer, new ResourceLocation(ModIntegration.TF_MODID, "giant_log_to_oak_planks"));

        // Immersive Engineering
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.IE_MODID));

        ShapelessRecipeBuilder.shapeless(ModIntegration.IE_STICK_TREATED, 2)
                .requires(TagManager.Items.IE_TREATED_WOOD)
                .requires(TConItems.SAW)
                .group("treated_sticks")
                .unlockedBy("has_treated_planks", has(TagManager.Items.IE_TREATED_WOOD))
                .save(wrapped, new ResourceLocation(TinkerSurvival.MODID, "stick_treated"));

        ShapelessRecipeBuilder.shapeless(ModIntegration.IE_STICK_TREATED, 4)
                .requires(TagManager.Items.IE_TREATED_WOOD)
                .requires(TagManager.Items.IE_TREATED_WOOD)
                .requires(TConItems.SAW)
                .group("treated_sticks")
                .unlockedBy("has_treated_planks", has(TagManager.Items.IE_TREATED_WOOD))
                .save(wrapped, new ResourceLocation(ModIntegration.IE_MODID, "crafting/stick_treated"));

        // Ecologics
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.ECO_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.ECO_COCONUT_PLANKS, TagManager.Items.ECO_COCONUT_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.ECO_WALNUT_PLANKS, TagManager.Items.ECO_WALNUT_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.ECO_AZALEA_PLANKS, TagManager.Items.ECO_AZALEA_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.ECO_FLOWERING_AZALEA_PLANKS, TagManager.Items.ECO_FLOWERING_AZALEA_LOGS, "has_logs");

        ShapelessRecipeBuilder.shapeless(Items.STICK, 2)
                .requires(ItemTags.PLANKS)
                .requires(TagManager.Items.SAW_TOOLS)
                .group("sticks")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);

        // Malum
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.MALUM_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.MALUM_RUNEWOOD_PLANKS, TagManager.Items.MALUM_RUNEWOOD_LOGS, "has_logs");
        plankRecipeBuilder(wrapped, ModIntegration.MALUM_SOULWOOD_PLANKS, TagManager.Items.MALUM_SOULWOOD_LOGS, "has_logs");

        // Ice and Fire; Dragons
        wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.IFD_MODID));
        plankRecipeBuilder(wrapped, ModIntegration.IFD_DREADWOOD_PLANKS, TagManager.Items.IFD_DREADWOOD_LOGS, "has_logs");
    }

    private static void plankRecipeBuilder(Consumer<FinishedRecipe> consumer, ItemLike item, TagKey<Item> itemTag, String label) {
        ShapelessRecipeBuilder plankOverrideRecipe = ShapelessRecipeBuilder.shapeless(item, 2)
                .requires(itemTag)
                .requires(TinkerSurvivalItems.CRUDE_SAW)
                .group("planks")
                .unlockedBy(label, has(itemTag));

        String name = item.asItem().getRegistryName().getPath();
        String modid = ItemUse.getModId(Objects.requireNonNull(item.asItem().getRegistryName()).toString());

        if (modid.contains(ModIntegration.TCON_MODID)) {
            plankOverrideRecipe.save(consumer, new ResourceLocation(ModIntegration.TCON_MODID, "world/wood/" + name.split("_")[0] + "/planks"));
        }
        else if (modid.contains(ModIntegration.MALUM_MODID)) {
            plankOverrideRecipe.save(consumer, new ResourceLocation(ModIntegration.MALUM_MODID, name));
        }
        else if (modid.contains(ModIntegration.IFD_MODID)) {
            plankOverrideRecipe.save(consumer, new ResourceLocation(ModIntegration.IFD_MODID, "dread_wood_planks"));
        }
        else {
            plankOverrideRecipe.save(consumer);
        }

        ShapelessRecipeBuilder.shapeless(item, 4)
                .requires(itemTag)
                .requires(TConItems.SAW)
                .group("planks")
                .unlockedBy(label, has(TinkerTables.tinkerStation))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, modid + "_" + name));
    }

    private static void itemPlankRecipeBuilder(Consumer<FinishedRecipe> consumer, ItemLike output, ItemLike input, String label, String path, String name, String modid) {
        ShapelessRecipeBuilder plankOverrideRecipe = ShapelessRecipeBuilder.shapeless(output, 2)
                .requires(input)
                .requires(TinkerSurvivalItems.CRUDE_SAW)
                .group("planks")
                .unlockedBy(label, has(input));

        plankOverrideRecipe.save(consumer, new ResourceLocation(modid, path + name));

        ShapelessRecipeBuilder.shapeless(output, 4)
                .requires(input)
                .requires(TConItems.SAW)
                .group("planks")
                .unlockedBy(label, has(TinkerTables.tinkerStation))
                .save(consumer, new ResourceLocation(TinkerSurvival.MODID, modid + "_" + name));
    }

    private static Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}
