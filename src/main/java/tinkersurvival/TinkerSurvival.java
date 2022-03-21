package tinkersurvival;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
    /*
    @SidedProxy(clientSide = "tinkersurvival.proxy.ClientProxy", serverSide = "tinkersurvival.proxy.ServerProxy")
    public static CommonProxy proxy;
    */

    public static Logger logger = LogManager.getFormatterLogger(TinkerSurvival.MODID);

    public TinkerSurvival() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CONFIG_SPEC);

        TinkerSurvivalWorld.init(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class SetupEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            TinkerSurvivalWorld.setup();
        }
    }

}
