package tinkersurvival;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.proxy.ClientProxy;
import tinkersurvival.proxy.ServerProxy;
import tinkersurvival.recipe.RecipeHelper;
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
    }

    @SubscribeEvent
    public void onBiomesLoaded(BiomeLoadingEvent evt) {
        BiomeGenerationSettingsBuilder gen = evt.getGeneration();

        if (ConfigHandler.Server.enableRockGen()) {
            gen.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TinkerSurvivalWorld.LOOSE_ROCKS_PLACED);
        }
    }

}
