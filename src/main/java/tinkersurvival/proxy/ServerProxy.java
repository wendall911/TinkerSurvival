package tinkersurvival.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;

public final class ServerProxy extends CommonProxy {

    public ServerProxy() {
		TinkerSurvival.BUS.addListener(this::serverSetup);
    }

	private void serverSetup(FMLDedicatedServerSetupEvent event) {
        ItemUse.init();
    }

}
