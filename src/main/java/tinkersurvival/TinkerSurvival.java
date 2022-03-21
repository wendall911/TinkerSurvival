package tinkersurvival;

import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tinkersurvival.common.HarvestBlock;
import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.proxy.ClientProxy;
import tinkersurvival.proxy.ServerProxy;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod(TinkerSurvival.MODID)
public class TinkerSurvival {

    public static final String MODID = "tinkersurvival";
    public static final Logger LOGGER = LogManager.getFormatterLogger(TinkerSurvival.MODID);

    public static TinkerSurvival INSTANCE;
    public static IEventBus BUS;

    public TinkerSurvival() {
        BUS = FMLJavaModLoadingContext.get().getModEventBus();
        INSTANCE = this;

        MinecraftForge.EVENT_BUS.register(INSTANCE);

        BUS.addListener(INSTANCE::setup);

        TinkerSurvivalModule.initRegistries(TinkerSurvival.BUS);

        DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }

    public void setup(final FMLCommonSetupEvent event) {
        if (ConfigHandler.Server.enableRockGen()) {
            TinkerSurvivalWorld.setup(BUS);
        }
        HarvestBlock.setup();
    }

    @SubscribeEvent
    public void onBiomesLoaded(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generator = event.getGeneration();

        if (ConfigHandler.Server.enableRockGen()) {
            generator.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                TinkerSurvivalWorld.LOOSE_ROCKS_PLACED
            );
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (ConfigHandler.Server.logModpackData()) {
            LOGGER.warn("needs_wood_tool - level 0");
            Tags.Blocks.NEEDS_WOOD_TOOL.getValues().forEach((block) -> {
                LOGGER.warn(block);
            });
            LOGGER.warn("needs_gold_tool - level 0.1");
            Tags.Blocks.NEEDS_GOLD_TOOL.getValues().forEach((block) -> {
                LOGGER.warn(block);
            });
            LOGGER.warn("needs_stone_tool - level 1");
            BlockTags.NEEDS_STONE_TOOL.getValues().forEach((block) -> {
                LOGGER.warn(block);
            });
            LOGGER.warn("needs_iron_tool - level 2");
            BlockTags.NEEDS_IRON_TOOL.getValues().forEach((block) -> {
                LOGGER.warn(block);
            });
            LOGGER.warn("needs_diamond_tool - level 3");
            BlockTags.NEEDS_DIAMOND_TOOL.getValues().forEach((block) -> {
                LOGGER.warn(block);
            });
            LOGGER.warn("needs_netherite_tool - level 4");
            Tags.Blocks.NEEDS_NETHERITE_TOOL.getValues().forEach((block) -> {
                LOGGER.warn(block);
            });
        }
    }



}
