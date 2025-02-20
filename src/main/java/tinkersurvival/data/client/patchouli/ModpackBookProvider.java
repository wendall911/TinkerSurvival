package tinkersurvival.data.client.patchouli;

import java.util.List;
import java.util.function.Consumer;

import chargedcharms.ChargedCharms;
import chargedcharms.common.item.ChargedCharmsItems;
import homeostatic.Homeostatic;
import homeostatic.common.item.HomeostaticItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraft.world.level.block.Blocks;

import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.tools.item.IModifiableDisplay;
import slimeknights.tconstruct.library.tools.nbt.MaterialNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.tables.TinkerTables;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import slimeknights.tconstruct.world.TinkerWorld;
import survivalistessentials.items.SurvivalistEssentialsItems;
import survivalistessentials.SurvivalistEssentials;
import survivalistessentials.world.SurvivalistEssentialsWorld;

import xyz.brassgoggledcoders.patchouliprovider.BookBuilder;
import xyz.brassgoggledcoders.patchouliprovider.CategoryBuilder;
import xyz.brassgoggledcoders.patchouliprovider.EntryBuilder;
import xyz.brassgoggledcoders.patchouliprovider.PatchouliBookProvider;

import tinkersurvival.items.TConItems;
import tinkersurvival.TinkerSurvival;

public class ModpackBookProvider extends PatchouliBookProvider {

    private int categorySortNum = -1;
    private int entrySortNum = -1;

    public ModpackBookProvider(@NotNull final PackOutput packOutput) {
        super(packOutput, TinkerSurvival.MODID, "en_us");
    }

    @Override
    protected void addBooks(Consumer<BookBuilder> consumer) {
        String bookName = "item.tinkersurvival.modpack_book";
        String landingText = "info.tinkersurvival.modpack_book.intro";
        String subTitle = "info.tinkersurvival.modpack_book.subtitle";

        BookBuilder bookBuilder = createBookBuilder("modpack_book", bookName, landingText)
            .setSubtitle(subTitle)
            .setModel(TinkerSurvival.MODID + ":modpack_book")
            .setDontGenerateBook(false)
            .setShowProgress(false)
            .setUseBlockyFont(true)
            .setI18n(true);

        bookBuilder = addAbout(bookBuilder).build();
        bookBuilder = addGameplay(bookBuilder).build();
        bookBuilder = addTools(bookBuilder).build();
        bookBuilder = addMiningProgression(bookBuilder).build();
        bookBuilder = addHealth(bookBuilder).build();
        bookBuilder = addFood(bookBuilder).build();
        bookBuilder = addTips(bookBuilder).build();

        bookBuilder.build(consumer);
    }

    private CategoryBuilder addAbout(BookBuilder bookBuilder) {
        CategoryBuilder category = bookBuilder.addCategory(
            "about",
            prefix("about.name"),
            prefix("about.desc"),
            new ItemStack(SurvivalistEssentialsItems.CRUDE_HATCHET)
        )
        .setSortnum(getCategorySortNum());

        EntryBuilder introEntry = category.addEntry(
            "about/welcome",
            prefix("about.welcome.title"),
            new ItemStack(TinkerTables.tinkerStation.asItem())
        ).setSortnum(getEntrySortNum());

        introEntry.addImagePage(bookImage("logo"))
            .setTitle(prefix("about.welcome.title"))
            .setText(prefix("about.welcome.desc")).build()
        .addTextPage(prefix("about.welcome.credits")).build();

        return category;
    }

    private CategoryBuilder addGameplay(BookBuilder bookBuilder) {
        CategoryBuilder category = bookBuilder.addCategory(
            "gameplay",
            prefix("gameplay.name"),
            prefix("gameplay.desc"),
            new ItemStack(SurvivalistEssentialsWorld.ROCK_STONE)
        )
        .setSortnum(getCategorySortNum());

        EntryBuilder gettingStartedEntry = category.addEntry(
            "gameplay/materials",
            prefix("gameplay.materials.name"),
            new ItemStack(SurvivalistEssentialsWorld.ROCK_STONE)
        )
        .setSortnum(getEntrySortNum());

        gettingStartedEntry.addSpotlightPage(new ItemStack(SurvivalistEssentialsWorld.STONE_LOOSE_ROCK))
            .setTitle(prefix("gameplay.materials.gather_stones.title"))
            .setText(prefix("gameplay.materials.gather_stones.desc")).build()
        .addSpotlightPage(new ItemStack(Items.OAK_LEAVES))
            .setTitle(prefix("gameplay.materials.gather_sticks.title"))
            .setText(prefix("gameplay.materials.gather_sticks.desc")).build()
        .addSpotlightPage(new ItemStack(SurvivalistEssentialsItems.FLINT_SHARD))
            .setTitle(prefix("gameplay.materials.flint_shards.title"))
            .setText(prefix("gameplay.materials.flint_shards.desc")).build()
        .addSpotlightPage(new ItemStack(SurvivalistEssentialsItems.PLANT_FIBER))
            .setTitle(prefix("gameplay.materials.plant_fiber.title"))
            .setText(prefix("gameplay.materials.plant_fiber.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "plant_string"))
            .setTitle(prefix("gameplay.materials.plant_string.title"))
            .setText(prefix("gameplay.materials.plant_string.desc")).build();

        EntryBuilder gameplayEnvironmentEntry = category.addEntry(
            "gameplay/environment",
            prefix("gameplay.environment.name"),
            new ItemStack(Items.CAMPFIRE)
        ).setSortnum(getEntrySortNum());

        gameplayEnvironmentEntry.addImagePage(hsBookImage("normal_outside"))
            .setTitle(prefix("gameplay.environment.title"))
            .setText(prefix("gameplay.environment.intro")).build()
        .addTextPage(prefix("gameplay.environment.overview")).build()
        .addTextPage(prefix("gameplay.environment.details")).build();

        EntryBuilder gameplayBodyTempEntry = category.addEntry(
            "gameplay/body_temp",
            prefix("gameplay.body_temp.name"),
            new ItemStack(Items.LIGHT_GRAY_WOOL)
        ).setSortnum(getEntrySortNum());

        gameplayBodyTempEntry.addImagePage(hsBookImage("normal_body"))
            .setTitle(prefix("gameplay.body_temp.title"))
            .setText(prefix("gameplay.body_temp.intro")).build()
        .addTextPage(prefix("gameplay.body_temp.details")).build()
        .addImagePage(hsBookImage("wetness_hud"))
            .setAnchor("wetness")
            .setTitle(prefix("gameplay.body_temp.wetness.title"))
            .setText(prefix("gameplay.body_temp.wetness.intro")).build()
        .addTextPage(prefix("gameplay.body_temp.wetness.details")).build()
        .addImagePage(hsBookImage("scalding_hud"))
            .setAnchor("scalding")
            .setTitle(prefix("gameplay.body_temp.scalding.title"))
            .setText(prefix("gameplay.body_temp.scalding.intro")).build()
        .addImagePage(hsBookImage("hyperthermia_hud"))
            .setAnchor("hyperthermia")
            .setTitle(prefix("gameplay.body_temp.hyperthermia.title"))
            .setText(prefix("gameplay.body_temp.hyperthermia.intro")).build()
        .addImagePage(hsBookImage("hypothermia_hud"))
            .setAnchor("hypothermia")
            .setTitle(prefix("gameplay.body_temp.hypothermia.title"))
            .setText(prefix("gameplay.body_temp.hypothermia.intro")).build()
        .addPage(addHomeostaticRecipe(hsLoc("insulation"), gameplayBodyTempEntry))
            .setAnchor("insulation")
            .setTitle(prefix("gameplay.body_temp.insulation.title"))
            .setText(prefix("gameplay.body_temp.insulation.text")).build()
        .addPage(addHomeostaticRecipe(hsLoc("remove_insulation"), gameplayBodyTempEntry))
            .setAnchor("remove_insulation")
            .setTitle(prefix("gameplay.body_temp.remove_insulation.title"))
            .setText(prefix("gameplay.body_temp.remove_insulation.text")).build()
        .addPage(addHomeostaticRecipe(hsLoc("radiation_protection"), gameplayBodyTempEntry))
            .setAnchor("radiation_protection")
            .setTitle(prefix("gameplay.body_temp.radiation_protection.title"))
            .setText(prefix("gameplay.body_temp.radiation_protection.text")).build()
        .addPage(addHomeostaticRecipe(hsLoc("remove_radiation_protection"), gameplayBodyTempEntry))
            .setAnchor("remove_radiation_protection")
            .setTitle(prefix("gameplay.body_temp.remove_radiation_protection.title"))
            .setText(prefix("gameplay.body_temp.remove_radiation_protection.text")).build()
        .addPage(addHomeostaticRecipe(hsLoc("waterproof"), gameplayBodyTempEntry))
            .setAnchor("waterproofing")
            .setTitle(prefix("gameplay.body_temp.waterproof.title"))
            .setText(prefix("gameplay.body_temp.waterproof.text")).build()
        .addPage(addHomeostaticRecipe(hsLoc("remove_waterproof"), gameplayBodyTempEntry))
            .setAnchor("remove_waterproofing")
            .setTitle(prefix("gameplay.body_temp.remove_waterproof.title"))
            .setText(prefix("gameplay.body_temp.remove_waterproof.text")).build();

        EntryBuilder gameplayHydrationEntry = category.addEntry(
            "gameplay/hydration",
            prefix("gameplay.hydration.name"),
            new ItemStack(HomeostaticItems.LEATHER_FLASK)
        ).setSortnum(getEntrySortNum());

        gameplayHydrationEntry.addImagePage(hsBookImage("water_bar"))
            .setTitle(prefix("gameplay.hydration.title"))
            .setText(prefix("gameplay.hydration.intro")).build()
        .addTextPage(prefix("gameplay.hydration.details")).build()
        .addCraftingPage(hsLoc("leather_flask"))
            .setTitle(prefix("gameplay.hydration.leather_flask.title"))
            .setText(prefix("gameplay.hydration.leather_flask.text")).build()
        .addSmeltingPage(hsLoc("furnace_purified_leather_flask"))
            .setTitle(prefix("gameplay.hydration.leather_flask.smelting.title"))
            .setText(prefix("gameplay.hydration.leather_flask.smelting.text")).build()
        .addCraftingPage(hsLoc("water_filter"))
            .setTitle(prefix("gameplay.hydration.water_filter.title"))
            .setText(prefix("gameplay.hydration.water_filter.text")).build()
        .addPage(addHomeostaticRecipe(hsLoc("filtered_water_flask"), gameplayHydrationEntry))
            .setTitle(prefix("gameplay.hydration.leather_flask_water_filter.title"))
            .setText(prefix("gameplay.hydration.leather_flask_water_filter.text")).build();

        EntryBuilder gameplayBetterDaysEntry = category.addEntry(
            "gameplay/betterdays",
            prefix("gameplay.betterdays.name"),
            new ItemStack(Items.CLOCK)
        ).setSortnum(getEntrySortNum());

        gameplayBetterDaysEntry.addImagePage(bookImage("minecraft___first_night_by_sasoriryu_d68a3za"))
            .setTitle(prefix("gameplay.betterdays.name"))
            .setText(prefix("gameplay.betterdays.desc")).build()
        .addCraftingPage(comfortsLoc("sleeping_bag_green"))
            .setTitle(prefix("gameplay.betterdays.comforts.name"))
            .setText(prefix("gameplay.betterdays.comforts.desc")).build();

        return category;
    }

    public CategoryBuilder addMiningProgression(BookBuilder bookBuilder) {
        ItemStack pickaxe = getCopperTool(TinkerTools.pickaxe.asItem());

        CategoryBuilder category = bookBuilder.addCategory(
            "mining",
            prefix("mining.name"),
            prefix("mining.desc"),
            pickaxe
        ).setSortnum(getCategorySortNum());

        EntryBuilder miningProgressionEntry = category.addEntry(
            "mining/progression",
            prefix("mining.name"),
            pickaxe
        ).setSortnum(getEntrySortNum());

        miningProgressionEntry.addImagePage(bookImage("materials"))
            .setTitle(prefix("mining.progression.name")).build()
        .addTextPage(prefix("mining.progression.desc"), prefix("mining.progression.name")).build();

        ItemStack stoneHead = getToolHead(MaterialVariant.of(MaterialIds.rock, "rock"));
        EntryBuilder miningStoneEntry = category.addEntry(
            "mining/stone",
            prefix("mining.stone.name"),
                stoneHead
        ).setSortnum(getEntrySortNum());

        miningStoneEntry.addSpotlightPage(new ItemStack(Blocks.STONE.asItem()))
            .setTitle(prefix("mining.stone.name"))
            .setText(prefix("mining.stone.desc")).build()
        .addSpotlightPage(stoneHead)
            .setTitle(prefix("mining.stone.materials.name"))
            .setText(prefix("mining.stone.materials.desc")).build();

        ItemStack copperHead = getToolHead(MaterialVariant.of(MaterialIds.copper, "copper"));
        EntryBuilder miningCopperEntry = category.addEntry(
            "mining/copper",
            prefix("mining.copper.name"),
            copperHead
        ).setSortnum(getEntrySortNum());

        miningCopperEntry.addSpotlightPage(new ItemStack(Blocks.COPPER_ORE.asItem()))
            .setTitle(prefix("mining.copper.name"))
            .setText(prefix("mining.copper.desc")).build()
        .addSpotlightPage(copperHead)
            .setTitle(prefix("mining.copper.materials.name"))
            .setText(prefix("mining.copper.materials.desc")).build();

        ItemStack constantanHead = getToolHead(MaterialVariant.of(MaterialIds.constantan, "constantan"));
        EntryBuilder miningConstantanEntry = category.addEntry(
            "mining/constantan",
            prefix("mining.constantan.name"),
            constantanHead
        ).setSortnum(getEntrySortNum());

        miningConstantanEntry.addSpotlightPage(new ItemStack(Blocks.DEEPSLATE_IRON_ORE.asItem()))
            .setTitle(prefix("mining.constantan.name"))
            .setText(prefix("mining.constantan.desc")).build()
        .addSpotlightPage(constantanHead)
            .setTitle(prefix("mining.constantan.materials.name"))
            .setText(prefix("mining.constantan.materials.desc")).build();

        ItemStack bronzeHead = getToolHead(MaterialVariant.of(MaterialIds.bronze, "bronze"));
        EntryBuilder miningBronzeEntry = category.addEntry(
            "mining/bronze",
            prefix("mining.bronze.name"),
            bronzeHead
        ).setSortnum(getEntrySortNum());

        miningBronzeEntry.addSpotlightPage(new ItemStack(Blocks.DIAMOND_ORE.asItem()))
            .setTitle(prefix("mining.bronze.name"))
            .setText(prefix("mining.bronze.desc")).build()
        .addSpotlightPage(bronzeHead)
            .setTitle(prefix("mining.bronze.materials.name"))
            .setText(prefix("mining.bronze.materials.desc")).build();

        ItemStack steelHead = getToolHead(MaterialVariant.of(MaterialIds.steel, "steel"));
        EntryBuilder miningSteelEntry = category.addEntry(
            "mining/steel",
            prefix("mining.steel.name"),
            steelHead
        ).setSortnum(getEntrySortNum());

        miningSteelEntry.addSpotlightPage(new ItemStack(TinkerWorld.cobaltOre.asItem()))
            .setTitle(prefix("mining.steel.name"))
            .setText(prefix("mining.steel.desc")).build()
        .addSpotlightPage(steelHead)
            .setTitle(prefix("mining.steel.materials.name"))
            .setText(prefix("mining.steel.materials.desc")).build()
        .addCraftingPage(ieLoc("crafting/manual"))
            .setTitle(prefix("mining.steel.iesteel.name"))
            .setText(prefix("mining.steel.iesteel.desc")).build()
        .addCraftingPage(botaniaLoc("lexicon"))
            .setTitle(prefix("mining.steel.manasteel.name"))
            .setText(prefix("mining.steel.manasteel.desc")).build();

        ItemStack manyullynHead = getToolHead(MaterialVariant.of(MaterialIds.manyullyn, "manyullyn"));
        EntryBuilder miningNetheriteEntry = category.addEntry(
            "mining/netherite",
            prefix("mining.netherite.name"),
            manyullynHead
        ).setSortnum(getEntrySortNum());

        miningNetheriteEntry.addSpotlightPage(manyullynHead)
            .setTitle(prefix("mining.netherite.materials.name"))
            .setText(prefix("mining.netherite.materials.desc")).build();

        return category;
    }

    public CategoryBuilder addTools(BookBuilder bookBuilder) {
        ItemStack knife = getCopperTool(TConItems.KNIFE.asItem());
        ItemStack saw = getCopperTool(TConItems.SAW.asItem());

        CategoryBuilder category = bookBuilder.addCategory(
            "tools",
            prefix("tools.name"),
            prefix("tools.desc"),
            saw
        ).setSortnum(getCategorySortNum());

        EntryBuilder crudeTools = category.addEntry(
            "tools/crude_tools",
            prefix("tools.crude_tools.name"),
            new ItemStack(SurvivalistEssentialsItems.CRUDE_SAW)
        ).setSortnum(getEntrySortNum());

        crudeTools.addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "crude_knife"))
            .setAnchor("crude_knife")
            .setTitle("item.survivalistessentials.crude_knife")
            .setText(prefix("tools.crude_tools.knife.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "flint_shard"))
            .setTitle(prefix("tools.crude_tools.knife_recipes.name"))
            .setText(prefix("tools.crude_tools.knife_recipes.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "crude_hatchet"))
            .setAnchor("crude_hatchet")
            .setTitle("item.survivalistessentials.crude_hatchet")
            .setText(prefix("tools.crude_tools.hatchet.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "crude_saw_blade"))
            .setTitle("item.survivalistessentials.crude_saw_blade")
            .setText(prefix("tools.crude_tools.crude_saw_blade.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "saw_handle"))
            .setTitle("item.survivalistessentials.saw_handle")
            .setText(prefix("tools.crude_tools.saw_handle.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "crude_saw"))
            .setAnchor("crude_saw")
            .setTitle("item.survivalistessentials.crude_saw")
            .setText(prefix("tools.crude_tools.crude_saw.desc")).build()
        .addCraftingPage(new ResourceLocation("minecraft:oak_planks"))
            .setTitle(prefix("tools.crude_tools.planks.name"))
            .setText(prefix("tools.crude_tools.planks.desc")).build()
        .addCraftingPage(new ResourceLocation("stick"))
            .setTitle(prefix("tools.crude_tools.sticks.name"))
            .setText(prefix("tools.crude_tools.sticks.desc")).build()
        .build();

        EntryBuilder tconTools = category.addEntry(
            "tools/tcon_tools",
            prefix("tools.tcon_tools.name"),
            saw
        ).setSortnum(getEntrySortNum());

        tconTools.addCraftingPage(new ResourceLocation(TConstruct.MOD_ID, "common/materials_and_you"))
            .setTitle(prefix("tools.tcon_tools.name"))
            .setText(prefix("tools.tcon_tools.desc")).build()
        .addSpotlightPage(knife)
            .setTitle("item.tinkersurvival.knife")
            .setText(prefix("tools.tcon_tools.knife.desc")).build()
        .addSpotlightPage(saw)
            .setTitle("item.tinkersurvival.saw")
            .setText(prefix("tools.tcon_tools.saw.desc")).build()
        .build();

        return category;
    }

    private CategoryBuilder addHealth(BookBuilder bookBuilder) {
        CategoryBuilder category = bookBuilder.addCategory(
            "health",
            prefix("health.name"),
            prefix("health.desc"),
            new ItemStack(SurvivalistEssentialsItems.BANDAGE)
        )
        .setSortnum(getCategorySortNum());

        EntryBuilder healthIngredients = category.addEntry(
            "health/ingredients",
            prefix("health.ingredients.name"),
            new ItemStack(SurvivalistEssentialsItems.CLOTH)
        )
        .setSortnum(getEntrySortNum());

        healthIngredients.addSpotlightPage(new ItemStack(SurvivalistEssentialsItems.CLOTH))
            .setText(prefix("health.ingredients.desc"))
            .setTitle(prefix("health.ingredients.subtitle")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "cloth"))
            .setTitle("item.survivalistessentials.cloth")
            .setText(prefix("health.ingredients.cloth.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "mortar_and_pestle"))
            .setTitle("item.survivalistessentials.mortar_and_pestle")
            .setText(prefix("health.ingredients.mortar_and_pestle.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "plant_paste"))
            .setTitle("item.survivalistessentials.plant_paste")
            .setText(prefix("health.ingredients.plant_paste.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "ointment"))
            .setTitle("item.survivalistessentials.ointment")
            .setText(prefix("health.ingredients.ointment.desc")).build().build();

        EntryBuilder bandages = category.addEntry(
            "health/bandages",
            prefix("health.bandages.name"),
            new ItemStack(SurvivalistEssentialsItems.BANDAGE)
        )
        .setSortnum(getEntrySortNum());

        bandages.addSpotlightPage(new ItemStack(SurvivalistEssentialsItems.BANDAGE))
            .setText(prefix("health.bandages.desc"))
            .setTitle(prefix("health.bandages.subtitle")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "crude_bandage"))
            .setTitle("item.survivalistessentials.crude_bandage")
            .setText(prefix("health.bandages.crude_bandage.desc")).build()
        .addCraftingPage(new ResourceLocation(SurvivalistEssentials.MODID, "bandage"))
            .setTitle("item.survivalistessentials.bandage")
            .setText(prefix("health.bandages.bandage.desc")).build()
        .build();

        return category;
    }

    private CategoryBuilder addFood(BookBuilder bookBuilder) {
        CategoryBuilder category = bookBuilder.addCategory(
            "food",
            prefix("food.name"),
            prefix("food.desc"),
            new ItemStack(TinkerCommons.bacon.asItem())
        )
        .setSortnum(getCategorySortNum());

        EntryBuilder foodOverview = category.addEntry(
            "food/overview",
            prefix("food.overview.name"),
            new ItemStack(Items.CARROT)
        )
        .setSortnum(getEntrySortNum());

        foodOverview.addCraftingPage(solLoc("food_book"))
            .setTitle(prefix("food.overview.sol.name"))
            .setText(prefix("food.overview.sol.desc")).build()
        .addCraftingPage(sushiLoc("book"))
            .setTitle(prefix("food.overview.sushi.name"))
            .setText(prefix("food.overview.sushi.desc")).build()
        .addCraftingPage(culinaryLoc("culinary_station"))
            .setTitle(prefix("food.overview.culinary.name"))
            .setText(prefix("food.overview.culinary.desc")).build();

        return category;
    }

    private CategoryBuilder addTips(BookBuilder bookBuilder) {
        CategoryBuilder category = bookBuilder.addCategory(
            "tips",
            prefix("tips.name"),
            prefix("tips.desc"),
            new ItemStack(Items.NETHER_STAR)
        )
        .setSortnum(getCategorySortNum());

        EntryBuilder tipsCharms = category.addEntry(
            "tips/charms",
            "itemGroup.chargedcharms",
            new ItemStack(ChargedCharmsItems.regenerationCharm)
        )
        .setSortnum(getEntrySortNum());

        tipsCharms.addPage(addChargedCharmsRecipe(loc("regen"), tipsCharms))
            .setTitle(prefix("tips.charms.regen.title"))
            .setText(prefix("tips.charms.regen.text")).build()
        .addPage(addChargedCharmsRecipe(loc("absorption"), tipsCharms))
            .setTitle(prefix("tips.charms.absorption.title"))
            .setText(prefix("tips.charms.absorption.text")).build()
        .addCraftingPage(ccLoc("charged_glowup_charm"))
            .setTitle(prefix("tips.charms.glowup.title"))
            .setText(prefix("tips.charms.glowup.text")).build()
        .addPage(addChargedCharmsRecipe(loc("speed"), tipsCharms))
            .setTitle(prefix("tips.charms.speed.title"))
            .setText(prefix("tips.charms.speed.text")).build()
        .addPage(addChargedCharmsRecipe(loc("totem"), tipsCharms))
            .setTitle(prefix("tips.charms.totem.title"))
            .setText(prefix("tips.charms.totem.text")).build()
        .addPage(addChargedCharmsRecipe(loc("enchanted_totem"), tipsCharms))
            .setTitle(prefix("tips.charms.enchanted_totem.title"))
            .setText(prefix("tips.charms.enchanted_totem.text")).build();

        return category;
    }

    private CustomRecipePageBuilder addHomeostaticRecipe(ResourceLocation recipe, EntryBuilder parent) {
        return new CustomRecipePageBuilder(Homeostatic.MODID + ":custom_crafting", recipe, parent);
    }

    private CustomRecipePageBuilder addChargedCharmsRecipe(ResourceLocation recipe, EntryBuilder parent) {
        return new CustomRecipePageBuilder(TinkerSurvival.MODID + ":chargedcharms", recipe, parent);
    }

    private int getCategorySortNum() {
        return ++categorySortNum;
    }

    private int getEntrySortNum() {
        return ++entrySortNum;
    }

    private ResourceLocation loc(String path) {
        return new ResourceLocation(TinkerSurvival.MODID, path);
    }

    private ResourceLocation hsLoc(String path) {
        return new ResourceLocation(Homeostatic.MODID, path);
    }

    private ResourceLocation ieLoc(String path) {
        return new ResourceLocation("immersiveengineering", path);
    }

    private ResourceLocation botaniaLoc(String path) {
        return new ResourceLocation("botania", path);
    }

    private ResourceLocation solLoc(String path) {
        return new ResourceLocation("solcarrot", path);
    }

    private ResourceLocation sushiLoc(String path) {
        return new ResourceLocation("sushigocrafting", path);
    }

    private ResourceLocation culinaryLoc(String path) {
        return new ResourceLocation("culinaryconstruct", path);
    }

    private ResourceLocation comfortsLoc(String path) {
        return new ResourceLocation("comforts", path);
    }

    private ResourceLocation ccLoc(String path) {
        return new ResourceLocation(ChargedCharms.MODID, path);
    }

    private String prefix(String name) {
        return "info.tinkersurvival.modpack_book" + "." + name;
    }

    private ResourceLocation bookImage(String id) {
        return new ResourceLocation(TinkerSurvival.MODID, "textures/gui/book/" + id + ".png");
    }

    private ResourceLocation hsBookImage(String id) {
        return new ResourceLocation(Homeostatic.MODID, "textures/gui/book/" + id + ".png");
    }

    private ItemStack getCopperTool(Item toolItem) {
        return getTool(getToolStack(toolItem), MaterialVariant.of(MaterialIds.copper, "copper"));
    }

    private IModifiableDisplay getModifiableDisplayStack(Item item) {
        return (IModifiableDisplay) item;
    }

    private ToolStack getToolStack(Item toolItem) {
        IModifiableDisplay modifiableDisplayItem = getModifiableDisplayStack(toolItem);

        return ToolStack.from(modifiableDisplayItem.getRenderTool());
    }

    private ItemStack getTool(ToolStack tool, MaterialVariant head) {
        MaterialVariant handle = MaterialVariant.of(MaterialIds.wood, "wood");
        MaterialVariant binding = MaterialVariant.of(MaterialIds.clay, "clay");

        tool.setMaterials(new MaterialNBT(List.of(head, handle, binding)));

        return tool.createStack();
    }

    private ItemStack getToolHead(MaterialVariant materialVariant) {
        return TinkerToolParts.pickHead.get().withMaterialForDisplay(materialVariant.getVariant());
    }

    private static MaterialId id(String name) {
        return new MaterialId("tcintegrations", name);
    }

}
