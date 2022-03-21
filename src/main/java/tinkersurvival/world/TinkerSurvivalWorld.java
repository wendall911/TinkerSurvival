package tinkersurvival.world;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;


import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.client.CreativeTabBase;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.block.LooseRockBlock;
import tinkersurvival.world.worldgen.RockGenerator;

public class TinkerSurvivalWorld {

    public static DeferredRegister<Block> blockRegistry;
    public static DeferredRegister<Item> itemRegistry;
    public static DeferredRegister<Feature<?>> featureRegistry;

    public static Lazy<ConfiguredFeature<?, ?>> looseRocksConfigured;
    public static Lazy<PlacedFeature> looseRocksPlaced;

    public static CreativeTabBase tabGroup;

    public static RegistryObject<Item> flintShard;
    public static RegistryObject<Item> rockStone;

    public static RegistryObject<LooseRockBlock> andesiteLooseRock;
    public static RegistryObject<LooseRockBlock> dioriteLooseRock;
    public static RegistryObject<LooseRockBlock> graniteLooseRock;
    public static RegistryObject<LooseRockBlock> stoneLooseRock;
    public static RegistryObject<LooseRockBlock> sandstoneLooseRock;
    public static RegistryObject<LooseRockBlock> redSandstoneLooseRock;

    public static RegistryObject<RockGenerator> looseRocks;

    public static void init(IEventBus bus) {
        blockRegistry = DeferredRegister.create(ForgeRegistries.BLOCKS, TinkerSurvival.MODID);
        itemRegistry = DeferredRegister.create(ForgeRegistries.ITEMS, TinkerSurvival.MODID);
        featureRegistry = DeferredRegister.create(ForgeRegistries.FEATURES, TinkerSurvival.MODID);

        blockRegistry.register(bus);
        itemRegistry.register(bus);
        featureRegistry.register(bus);

        tabGroup = new CreativeTabBase(TinkerSurvival.MODID + ".items", () -> new ItemStack(flintShard.get()));

        // Blocks
        andesiteLooseRock = registerBlock("andesite_loose_rock", LooseRockBlock::new);
        dioriteLooseRock = registerBlock("diorite_loose_rock", LooseRockBlock::new);
        graniteLooseRock = registerBlock("granite_loose_rock", LooseRockBlock::new);
        stoneLooseRock = registerBlock("stone_loose_rock", LooseRockBlock::new);
        sandstoneLooseRock = registerBlock("sandstone_loose_rock", LooseRockBlock::new);
        redSandstoneLooseRock = registerBlock("red_sandstone_loose_rock", LooseRockBlock::new);

        // Items
        flintShard = registerItem("flint_shard");
        rockStone = registerItem("rock_stone");

        // Worldgen Features
        looseRocks = featureRegistry.register("loose_rocks", RockGenerator::new);

        // Worldgen Feature Configuration
        looseRocksConfigured = registerFeature(BuiltinRegistries.CONFIGURED_FEATURE, "loose_rocks",
            () -> looseRocks.get().configured(NoneFeatureConfiguration.INSTANCE));

        looseRocksPlaced = registerFeature(BuiltinRegistries.PLACED_FEATURE, "loose_rocks", () -> looseRocksConfigured.get().placed(
                    CountPlacement.of(5),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP_WORLD_SURFACE
                ));
    }

    public static void setup() {
        looseRocksConfigured.get();
        looseRocksPlaced.get();
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockFactory) {
        return registerBlock(name, blockFactory, block -> new BlockItem(block, new Item.Properties().tab(tabGroup)));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockFactory, Function<T, BlockItem> blockItemFactory) {
        RegistryObject<T> block = blockRegistry.register(name, blockFactory);

        registerItem(name, () -> blockItemFactory.apply(block.get()));

        return block;
    }

    private static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, () -> new Item(new Item.Properties().tab(tabGroup)));
    }

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return itemRegistry.register(name, item);
    }

    private static <T> Lazy<T> registerFeature(Registry<? super T> registry, String name, Supplier<T> factory) {
        ResourceLocation featureLocation = new ResourceLocation(TinkerSurvival.MODID, name);

        return Lazy.of(() -> Registry.register(registry, featureLocation, factory.get()));
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
    public static ItemBase rockStone;
    public static ItemBase cloth;
    public static ItemBase flintShard;
    public static ItemBase plantPaste;
    public static ItemBase grassFiber;
    public static ItemBase grassString;
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
        rockStone = getItem(rockStone, "rock_stone");
        cloth = getItem(cloth, "cloth");
        grassFiber = getItem(grassFiber, "grass_fiber");
        grassString = getItem(grassString, "grass_string");
        ointment = getItem(ointment, "ointment");
        plantPaste = getItem(plantPaste, "plant_paste");
        flintShard = getItem(flintShard, "flint_shard");
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
