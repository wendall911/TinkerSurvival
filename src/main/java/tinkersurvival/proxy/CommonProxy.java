package tinkersurvival.proxy;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

import tinkersurvival.common.HarvestBlock;
import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.data.integration.ModIntegration;
import tinkersurvival.items.TConItems;
import tinkersurvival.items.TinkerSurvivalBooks;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.loot.TinkerSurvivalLootTables;
import tinkersurvival.sound.Sounds;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.effect.TinkerSurvivalEffects;
import tinkersurvival.world.feature.TinkerSurvivalFeatures;
import tinkersurvival.world.feature.LooseRockFeatureHolders;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class CommonProxy {

    public CommonProxy() {}

    public void start() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ConfigHandler.init();
        Sounds.init(bus);
        registerListeners(bus);
    }

    public void registerListeners(IEventBus bus) {
        bus.register(RegistryListener.class);
    }

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generator = event.getGeneration();

        if (ConfigHandler.Common.enableRockGen()) {
            generator.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                LooseRockFeatureHolders.LOOSE_ROCKS_PLACEMENT
            );
        }
    }

    public static final class RegistryListener {

        private static boolean setupDone = false;

        public static IForgeRegistry<Block> BLOCK_REGISTRY;

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerItems(RegistryEvent.Register<Item> event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            TinkerSurvivalItems.init(event.getRegistry());
            ModIntegration.init(event.getRegistry());
            TinkerSurvivalWorld.initItems(event.getRegistry());

            bus.register(new TConItems());
            bus.register(new TinkerSurvivalBooks());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerLootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            bus.register(new TinkerSurvivalLootTables());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BLOCK_REGISTRY = event.getRegistry();

            TinkerSurvivalWorld.initBlocks(event.getRegistry());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            bus.register(new TinkerSurvivalFeatures());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerMobEffects(RegistryEvent.Register<MobEffect> event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            bus.register(new TinkerSurvivalEffects());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void setupRegistries(FMLConstructModEvent event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            if (setupDone) {
                return;
            }
            setupDone = true;

            TinkerSurvivalModule.initRegistries(bus);
        }

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            HarvestBlock.setup();
        }

    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (ConfigHandler.Server.logModpackData()) {
            RegistryListener.BLOCK_REGISTRY.getValues().forEach((block) -> {
                if (block.defaultBlockState().is(Tags.Blocks.NEEDS_WOOD_TOOL)) {
                    TinkerSurvival.LOGGER.warn("needs_wood_tool - level 0: %s", block);
                }
                if (block.defaultBlockState().is(Tags.Blocks.NEEDS_GOLD_TOOL)) {
                    TinkerSurvival.LOGGER.warn("needs_gold_tool - level 0.1: %s", block);
                }
                if (block.defaultBlockState().is(BlockTags.NEEDS_STONE_TOOL)) {
                    TinkerSurvival.LOGGER.warn("needs_stone_tool - level 1: %s", block);
                }
                if (block.defaultBlockState().is(BlockTags.NEEDS_IRON_TOOL)) {
                    TinkerSurvival.LOGGER.warn("needs_iron_tool - level 2: %s", block);
                }
                if (block.defaultBlockState().is(BlockTags.NEEDS_DIAMOND_TOOL)) {
                    TinkerSurvival.LOGGER.warn("needs_diamond_tool - level 3: %s", block);
                }
                if (block.defaultBlockState().is(Tags.Blocks.NEEDS_NETHERITE_TOOL)) {
                    TinkerSurvival.LOGGER.warn("needs_netherite_tool - level 4: %s", block);
                }
            });
        }
    }

}
