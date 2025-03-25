package tinkersurvival;

import java.util.Random;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tinkersurvival.proxy.ClientProxy;
import tinkersurvival.proxy.CommonProxy;

@Mod(TinkerSurvival.MODID)
public class TinkerSurvival {

    public static final String MODID = "tinkersurvival";
    public static final Logger LOGGER = LogManager.getFormatterLogger(TinkerSurvival.MODID);
    public static final Random RANDOM = new Random();

    public static CommonProxy PROXY;

    public TinkerSurvival() {
        PROXY = FMLEnvironment.dist == Dist.CLIENT ? new ClientProxy() : new CommonProxy();
        PROXY.start();
    }

}
