package tinkersurvival;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import org.apache.logging.log4j.Logger;

import tinkersurvival.config.Config;
import tinkersurvival.proxy.CommonProxy;
import tinkersurvival.util.TinkerSurvivalToolLeveling;
import tinkersurvival.world.worldgen.RockGenerator;

@Mod(modid = TinkerSurvival.MODID,
     version = TinkerSurvival.MOD_VERSION,
     name = TinkerSurvival.MOD_NAME,
     dependencies = "required-after:forge@[@FORGE_VERSION@,);"
            + "after:tinkertoollevling@[@TTL_VERSION@,);"
            + "after:biomeoplenty;"
            + "after:basemetals;"
            + "after:immersiveengineering;"
            + "after:natura;"
            + "after:toughasnails;"
)

public class TinkerSurvival {

    public static final String MODID = "tinkersurvival";
    public static final String MOD_VERSION = "@MOD_VERSION@";
    public static final String MOD_NAME = "TinkerSurvival";

    public static TinkerSurvivalToolLeveling modToolLeveling = new TinkerSurvivalToolLeveling();

    @SidedProxy(clientSide = "tinkersurvival.proxy.ClientProxy", serverSide = "tinkersurvival.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static TinkerSurvival instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("Pre-init started");

        // Register Rock Generation
        MinecraftForge.EVENT_BUS.register(new RockGenerator());

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        logger.info("Init started");
        proxy.initToolGuis();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Post-init started");

        if (Config.Features.NO_GRIEFING) {
            // Disable Enderman Griefing!!!
            RegistryNamespacedDefaultedByKey<ResourceLocation, Block> griefBlock = Block.REGISTRY;
            for (Block block : griefBlock) {
                EntityEnderman.setCarriable(block, false);
            }
        }

        logger.info("Finished Loading");
    }

    @SubscribeEvent
    public void configChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
        }
    }
}

