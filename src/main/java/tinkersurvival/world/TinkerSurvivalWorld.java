package tinkersurvival.world;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.block.LooseRockBlock;
import tinkersurvival.world.feature.LooseRocks;
import tinkersurvival.world.effect.StopBleeding;
import tinkersurvival.world.effect.ZombieEssence;
import tinkersurvival.world.item.RockStone;

public final class TinkerSurvivalWorld extends TinkerSurvivalModule {

    public static ConfiguredFeature<?, ?> LOOSE_ROCKS_CONFIGURED;
    public static PlacedFeature LOOSE_ROCKS_PLACED;

    public static CreativeTabBase TAB_GROUP;

    public static RegistryObject<Item> ROCK_STONE;

    public static RegistryObject<Block> ANDESITE_LOOSE_ROCK;
    public static RegistryObject<Block> DIORITE_LOOSE_ROCK;
    public static RegistryObject<Block> GRANITE_LOOSE_ROCK;
    public static RegistryObject<Block> STONE_LOOSE_ROCK;
    public static RegistryObject<Block> SANDSTONE_LOOSE_ROCK;
    public static RegistryObject<Block> RED_SANDSTONE_LOOSE_ROCK;
    public static RegistryObject<Block> ROCK_STONE_BLOCK;

    public static Feature<NoneFeatureConfiguration> LOOSE_ROCKS_FEATURE;

    public static RegistryObject<MobEffect> STOP_BLEEDING;
    public static RegistryObject<MobEffect> ZOMBIE_ESSENCE;

    public static void init() {
        TAB_GROUP = new CreativeTabBase(TinkerSurvival.MODID + ".world", () -> new ItemStack(ROCK_STONE.get()));

        // Blocks
        ANDESITE_LOOSE_ROCK = registerBlock("andesite_loose_rock", LooseRockBlock::new);
        DIORITE_LOOSE_ROCK = registerBlock("diorite_loose_rock", LooseRockBlock::new);
        GRANITE_LOOSE_ROCK = registerBlock("granite_loose_rock", LooseRockBlock::new);
        STONE_LOOSE_ROCK = registerBlock("stone_loose_rock", LooseRockBlock::new);
        SANDSTONE_LOOSE_ROCK = registerBlock("sandstone_loose_rock", LooseRockBlock::new);
        RED_SANDSTONE_LOOSE_ROCK = registerBlock("red_sandstone_loose_rock", LooseRockBlock::new);
        ROCK_STONE_BLOCK = registerBlock("rock_stone_block", LooseRockBlock::new);

        // Items
        ROCK_STONE = registerRockStone("rock_stone");

        // Effects
        STOP_BLEEDING = MOBEFFECT_REGISTRY.register(
            "stop_bleeding", () -> new StopBleeding()
        );
        ZOMBIE_ESSENCE = MOBEFFECT_REGISTRY.register(
            "zombie_essence", () -> new ZombieEssence()
        );

    }

    public static void setup(IEventBus bus) {
        /*
         * Setup feature registry during setup, since Forge doesn't quite have the correct
         * implementation for this just yet, it is controlled by Minecraft
         *
         * In the future, may be able to move to initRegistries
         */
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

    private static RegistryObject<Item> registerRockStone(String name) {
        return registerItem(name, () -> new RockStone(ROCK_STONE_BLOCK.get(), new Item.Properties().tab(TAB_GROUP)));
    }

    private static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, () -> new Item(new Item.Properties().tab(TAB_GROUP)));
    }

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEM_REGISTRY.register(name, item);
    }

    public static Collection<RegistryObject<Block>> getBlockEntries() {
        return BLOCK_REGISTRY.getEntries();
    }

}
