package tinkersurvival;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.ModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod(TinkerSurvival.MODID)
public class TinkerSurvival {

    public static final String MODID = "tinkersurvival";

    public static Logger logger = LogManager.getFormatterLogger(TinkerSurvival.MODID);

    public TinkerSurvival() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CONFIG_SPEC);

        MinecraftForge.EVENT_BUS.register(this);

        TinkerSurvivalWorld.init(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public void setup(final FMLCommonSetupEvent event) {
        if (ConfigHandler.enableRockGen()) {
            TinkerSurvivalWorld.setup(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }

    @SubscribeEvent
    public void onBiomesLoaded(BiomeLoadingEvent evt) {
        BiomeGenerationSettingsBuilder gen = evt.getGeneration();

        if (ConfigHandler.enableRockGen()) {
            gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TinkerSurvivalWorld.looseRocksPlaced);
        }
    }

}
