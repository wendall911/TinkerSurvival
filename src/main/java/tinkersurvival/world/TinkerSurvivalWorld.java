package tinkersurvival.world;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.client.CreativeTabBase;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.tools.tool.CrudeHatchet;
import tinkersurvival.tools.tool.CrudeKnife;
import tinkersurvival.tools.tool.CrudeSaw;
import tinkersurvival.util.DynamicItemTier;
import tinkersurvival.world.block.LooseRockBlock;
import tinkersurvival.world.item.Bandage;
import tinkersurvival.world.item.CrudeBandage;
import tinkersurvival.world.item.Mortar;
import tinkersurvival.world.item.RockStone;
import tinkersurvival.world.item.WoodenCup;
import tinkersurvival.world.feature.LooseRocks;
import tinkersurvival.world.effect.StopBleeding;
import tinkersurvival.world.effect.ZombieEssence;

public class TinkerSurvivalWorld {

    public static DeferredRegister<Block> BLOCK_REGISTRY;
    public static DeferredRegister<Item> ITEM_REGISTRY;
    public static DeferredRegister<Feature<?>> FEATURE_REGISTRY;
    public static DeferredRegister<MobEffect> MOBEFFECT_REGISTRY;

    public static ConfiguredFeature<?, ?> LOOSE_ROCKS_CONFIGURED;
    public static PlacedFeature LOOSE_ROCKS_PLACED;

    public static CreativeTabBase TAB_GROUP;

    public static RegistryObject<Item> FLINT_SHARD;
    public static RegistryObject<Item> ROCK_STONE;
    public static RegistryObject<Item> PLANT_FIBER;
    public static RegistryObject<Item> PLANT_STRING;
    public static RegistryObject<Item> OINTMENT;
    public static RegistryObject<Item> PLANT_PASTE;
    public static RegistryObject<Item> CLOTH;

    public static RegistryObject<Item> CRUDE_SAW_BLADE;

    public static RegistryObject<Item> CRUDE_KNIFE;
    public static RegistryObject<Item> CRUDE_HATCHET;
    public static RegistryObject<Item> CRUDE_SAW_HANDLE;
    public static RegistryObject<Item> CRUDE_SAW;
    public static RegistryObject<Item> MORTAR_AND_PESTLE;

    public static RegistryObject<Item> CRUDE_BANDAGE;
    public static RegistryObject<Item> BANDAGE;

    public static RegistryObject<Item> WOODEN_CUP;

    public static RegistryObject<Block> ANDESITE_LOOSE_ROCK;
    public static RegistryObject<Block> DIORITE_LOOSE_ROCK;
    public static RegistryObject<Block> GRANITE_LOOSE_ROCK;
    public static RegistryObject<Block> STONE_LOOSE_ROCK;
    public static RegistryObject<Block> SANDSTONE_LOOSE_ROCK;
    public static RegistryObject<Block> RED_SANDSTONE_LOOSE_ROCK;
    public static RegistryObject<Block> ROCK_STONE_BLOCK;

    public static Feature<NoneFeatureConfiguration> LOOSE_ROCKS_FEATURE;

    public static Tier FLINT_TIER;
    public static Tier STONE_TIER;
    public static Tier WOOD_TIER;

    public static RegistryObject<MobEffect> STOP_BLEEDING;
    public static RegistryObject<MobEffect> ZOMBIE_ESSENCE;

    public static void init(IEventBus bus) {
        MOBEFFECT_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TinkerSurvival.MODID);
        BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TinkerSurvival.MODID);
        ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TinkerSurvival.MODID);

        MOBEFFECT_REGISTRY.register(bus);
        BLOCK_REGISTRY.register(bus);
        ITEM_REGISTRY.register(bus);

        TAB_GROUP = new CreativeTabBase(TinkerSurvival.MODID + ".items", () -> new ItemStack(FLINT_SHARD.get()));

        // Blocks
        ANDESITE_LOOSE_ROCK = registerBlock("andesite_loose_rock", LooseRockBlock::new);
        DIORITE_LOOSE_ROCK = registerBlock("diorite_loose_rock", LooseRockBlock::new);
        GRANITE_LOOSE_ROCK = registerBlock("granite_loose_rock", LooseRockBlock::new);
        STONE_LOOSE_ROCK = registerBlock("stone_loose_rock", LooseRockBlock::new);
        SANDSTONE_LOOSE_ROCK = registerBlock("sandstone_loose_rock", LooseRockBlock::new);
        RED_SANDSTONE_LOOSE_ROCK = registerBlock("red_sandstone_loose_rock", LooseRockBlock::new);
        ROCK_STONE_BLOCK = registerBlock("rock_stone_block", LooseRockBlock::new);

        // Items
        FLINT_SHARD = registerItem("flint_shard");
        ROCK_STONE = registerRockStone("rock_stone");
        PLANT_FIBER = registerItem("plant_fiber");
        PLANT_STRING = registerItem("plant_string");
        OINTMENT = registerItem("ointment");
        PLANT_PASTE = registerItem("plant_paste");
        CLOTH = registerItem("cloth");

        // Tools
        FLINT_TIER = new DynamicItemTier().setMaxUses(20).setEfficiency(1.5F)
            .setAttackDamage(0.5F).setHarvestLvl(0).setEnchantability(0).setRepairMats(Items.FLINT);
        STONE_TIER = new DynamicItemTier().setMaxUses(5).setEfficiency(1.5F)
            .setAttackDamage(0.5F).setHarvestLvl(0).setEnchantability(0).setRepairMats(Items.FLINT);
        WOOD_TIER = new DynamicItemTier().setMaxUses(20).setEfficiency(0.0F)
            .setAttackDamage(0.0F).setHarvestLvl(0).setEnchantability(0).setRepairMats(Items.FLINT);

        CRUDE_SAW_BLADE = registerItem("crude_saw_blade");
        CRUDE_KNIFE = registerKnifeTool("crude_knife", FLINT_TIER);
        CRUDE_HATCHET = registerHatchetTool("crude_hatchet", STONE_TIER);
        CRUDE_SAW_HANDLE = registerSawTool("crude_saw_handle", WOOD_TIER, 0, -8.0F);
        CRUDE_SAW = registerSawTool("crude_saw", FLINT_TIER, 3, -4.0F);
        MORTAR_AND_PESTLE = registerMortar("mortar_and_pestle");

        // Effects
        STOP_BLEEDING = MOBEFFECT_REGISTRY.register(
            "stop_bleeding", () -> new StopBleeding()
        );
        ZOMBIE_ESSENCE = MOBEFFECT_REGISTRY.register(
            "zombie_essence", () -> new ZombieEssence()
        );

        // Bandages
        CRUDE_BANDAGE = registerItem("crude_bandage", () -> new CrudeBandage(
            (new Item.Properties()).stacksTo(8).tab(TAB_GROUP)
        ));
        BANDAGE = registerItem("bandage", () -> new Bandage(
            (new Item.Properties()).stacksTo(16).tab(TAB_GROUP)
        ));

        // Zombie Jesus
        WOODEN_CUP = registerItem("wooden_cup", () -> new WoodenCup(
            (new Item.Properties()).stacksTo(1).tab(TAB_GROUP)
        ));
    }

    public static void setup(IEventBus bus) {
        FEATURE_REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, TinkerSurvival.MODID);
        FEATURE_REGISTRY.register(bus);

        // Worldgen Features
        LOOSE_ROCKS_FEATURE = new LooseRocks();

        // Worldgen Feature Configuration
        LOOSE_ROCKS_CONFIGURED = LOOSE_ROCKS_FEATURE
            .configured(NoneFeatureConfiguration.INSTANCE);

        LOOSE_ROCKS_PLACED = LOOSE_ROCKS_CONFIGURED.placed(
                    CountPlacement.of(ConfigHandler.Server.rockGenFrequency()),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP_WORLD_SURFACE
                );

        FEATURE_REGISTRY.register("loose_rocks", () -> LOOSE_ROCKS_FEATURE);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockFactory) {
        return registerBlock(name, blockFactory, block -> new BlockItem(block, new Item.Properties().tab(TAB_GROUP)));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockFactory, Function<T, BlockItem> blockItemFactory) {
        RegistryObject<T> block = BLOCK_REGISTRY.register(name, blockFactory);

        registerItem(name, () -> blockItemFactory.apply(block.get()));

        return block;
    }

    private static RegistryObject<Item> registerKnifeTool(String name, Tier tier) {
        Item knifeTool = new CrudeKnife(tier, 1, -1.4F, new Item.Properties().tab(TAB_GROUP));

        return registerItem(name, () -> knifeTool);
    }

    private static RegistryObject<Item> registerHatchetTool(String name, Tier tier) {
        Item hatchetTool = new CrudeHatchet(tier, 4, -3.0F, new Item.Properties().tab(TAB_GROUP));

        return registerItem(name, () -> hatchetTool);
    }

    private static RegistryObject<Item> registerSawTool(String name, Tier tier, int damage, float speed) {
        Item sawTool = new CrudeSaw(name, tier, damage, speed, new Item.Properties().tab(TAB_GROUP));

        return registerItem(name, () -> sawTool);
    }

    private static RegistryObject<Item> registerRockStone(String name) {
        return registerItem(name, () -> new RockStone(ROCK_STONE_BLOCK.get(), new Item.Properties().tab(TAB_GROUP)));
    }

    private static RegistryObject<Item> registerMortar(String name) {
        return registerItem(name, () -> new Mortar(new Item.Properties().tab(TAB_GROUP)));
    }

    private static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, () -> new Item(new Item.Properties().tab(TAB_GROUP)));
    }

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEM_REGISTRY.register(name, item);
    }

    /*
    private static final List<Item> all = new ArrayList<>();

    public static ArmorMaterial reinforced_wool_armor_material;
    public static ArmorMaterial reinforced_jelled_slime_armor_material;
    
    public static TinkerSurvivalArmor reinforced_wool_helmet;
    public static TinkerSurvivalArmor reinforced_wool_chestplate;
    public static TinkerSurvivalArmor reinforced_wool_leggings;
    public static TinkerSurvivalArmor reinforced_wool_boots;
    public static TinkerSurvivalArmor reinforced_jelled_slime_helmet;
    public static TinkerSurvivalArmor reinforced_jelled_slime_chestplate;
    public static TinkerSurvivalArmor reinforced_jelled_slime_leggings;
    public static TinkerSurvivalArmor reinforced_jelled_slime_boots;

    public static BlockRock looseRock;
    public static ItemBase ROCK_STONE;
    public static ItemBase cloth;
    public static ItemBase FLINT_SHARD;
    public static ItemBase plantPaste;
    public static ItemBase plantFiber;
    public static ItemBase plantString;
    public static ItemBase ointment;
    
    public static ItemBandage bandageItem;
    public static ItemWoodenCup woodenCup;

    public static StopBleeding stopBleeding;
    public static ZombieEssence zombieEssence;

    public static void initItemRepairMaterials() {
        if (Loader.isModLoaded("basemetals")) {
            ItemStack material = new ItemStack(Item.getByNameOrId("basemetals:adamantine_ingot"));
            reinforced_wool_armor_material.setRepairItem(material);
            reinforced_jelled_slime_armor_material.setRepairItem(material);
        }
    }

    public static void init() {
        float reinforced_hardness = 12.0f;
        float reinforced_strength = 100.0f;
        int reinforced_durability = 200;
        int reinforced_enchantability = 25;
        float reinforced_toughness = (reinforced_hardness / 5);

        reinforced_wool_armor_material = EnumHelper.addArmorMaterial(
            "REINFORCED_WOOL",
            TinkerSurvival.MODID + ":reinforced_wool_armor",
            reinforced_durability,
            getDamageReduction(reinforced_hardness),
            reinforced_enchantability,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            reinforced_toughness
        );

        reinforced_jelled_slime_armor_material = EnumHelper.addArmorMaterial(
            "REINFORCED_JELLED_SLIME",
            TinkerSurvival.MODID + ":reinforced_jelled_slime_armor",
            reinforced_durability,
            getDamageReduction(reinforced_hardness),
            reinforced_enchantability,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
            reinforced_toughness
        );

        reinforced_wool_helmet = getArmor(reinforced_wool_helmet, reinforced_wool_armor_material, EntityEquipmentSlot.HEAD, "reinforced_wool_helmet");
        reinforced_wool_chestplate = getArmor(reinforced_wool_chestplate, reinforced_wool_armor_material, EntityEquipmentSlot.CHEST, "reinforced_wool_chestplate");
        reinforced_wool_leggings = getArmor(reinforced_wool_leggings, reinforced_wool_armor_material, EntityEquipmentSlot.LEGS, "reinforced_wool_leggings");
        reinforced_wool_boots = getArmor(reinforced_wool_boots, reinforced_wool_armor_material, EntityEquipmentSlot.FEET, "reinforced_wool_boots");
        reinforced_jelled_slime_helmet = getArmor(reinforced_jelled_slime_helmet, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.HEAD, "reinforced_jelled_slime_helmet");
        reinforced_jelled_slime_chestplate = getArmor(reinforced_jelled_slime_chestplate, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.CHEST, "reinforced_jelled_slime_chestplate");
        reinforced_jelled_slime_leggings = getArmor(reinforced_jelled_slime_leggings, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.LEGS, "reinforced_jelled_slime_leggings");
        reinforced_jelled_slime_boots = getArmor(reinforced_jelled_slime_boots, reinforced_jelled_slime_armor_material, EntityEquipmentSlot.FEET, "reinforced_jelled_slime_boots");

        looseRock = getLooseRock(looseRock, "loose_rock");
        ROCK_STONE = getItem(ROCK_STONE, "rock_stone");
        cloth = getItem(cloth, "cloth");
        plantFiber = getItem(plantFiber, "plant_fiber");
        plantString = getItem(plantString, "plant_string");
        ointment = getItem(ointment, "ointment");
        plantPaste = getItem(plantPaste, "plant_paste");
        FLINT_SHARD = getItem(FLINT_SHARD, "flint_shard");
        bandageItem = getBandage(bandageItem, "bandage");
        woodenCup = getCup(woodenCup, "wooden_cup");

        stopBleeding = new StopBleeding();
        zombieEssence = new ZombieEssence();

    }

    private static TinkerSurvivalArmor getArmor(TinkerSurvivalArmor armor, ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        armor = new TinkerSurvivalArmor(material, slot, name);
        all.add(armor);
        return armor;
    }

    private static BlockRock getLooseRock(BlockRock block, String name) {
        return new BlockRock(name);
    }

    private static ItemBandage getBandage(ItemBandage bandage, String name) {
        bandage = new ItemBandage(name);
        all.add(bandage);
        return bandage;
    }

    private static ItemWoodenCup getCup(ItemWoodenCup cup, String name) {
        cup = new ItemWoodenCup(name, 600);
        all.add(cup);
        return cup;
    }

    private static ItemBase getItem(ItemBase item, String name) {
        item = new ItemBase(name, 1);
        all.add(item);
        return item;
    }

    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        all.forEach(registry::register);

        event.getRegistry().registerAll(
            new ItemMultiTexture(looseRock, looseRock, looseRock::getStoneName).setRegistryName(looseRock.name)
        );
    }

    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
            looseRock
        );
    }

    public static void registerItemModels() {
        for (int i = 0; i < ItemBandage.Type.values().length; i++) {
            ItemStack bandage = new ItemStack(bandageItem, 1, i);
            TinkerSurvival.proxy.registerItemModelWithVariant(
                bandageItem,
                i,
                bandageItem.getBandageName(bandage),
                "inventory"
            );
        }

        all.forEach(item -> {
            if (item != bandageItem) {
                TinkerSurvival.proxy.registerItemModel(item);
            }
        });
    }

    public static void registerBlockModels() {
        for (int i=0; i < BlockRock.EnumMineralType.values().length; i++) {
            TinkerSurvival.proxy.registerItemModelWithVariant(
                Item.getItemFromBlock(looseRock),
                i,
                looseRock.name,
                "type=" + looseRock.getStoneName(i)
            );
        }
    }

    public static int[] getDamageReduction(float hardness) {
        int[] protection = new int[4];
        float minimum = 5f;
        float hardnessFactor = 1.25f;
        float total = (hardnessFactor * hardness) + minimum;
        int feetIndex = EntityEquipmentSlot.FEET.getIndex();
        int legsIndex = EntityEquipmentSlot.LEGS.getIndex();
        int chestIndex = EntityEquipmentSlot.CHEST.getIndex();
        int headIndex = EntityEquipmentSlot.HEAD.getIndex();
        protection[headIndex] = Math.round(0.1f * total); // head
        protection[chestIndex] = Math.round(0.4f * total); // torso
        protection[legsIndex] = Math.round(0.35f * total); // legs
        protection[feetIndex] = Math.round(0.15f * total); // feet
        return protection;
    }
    */
}
