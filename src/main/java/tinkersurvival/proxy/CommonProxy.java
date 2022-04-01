package tinkersurvival.proxy;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.data.integration.ModIntegration;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.loot.TinkerSurvivalLootTables;
import tinkersurvival.sound.Sounds;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class CommonProxy {

    CommonProxy() {
        ConfigHandler.init();
        TinkerSurvivalItems.init();
        TinkerSurvivalWorld.init();
        TinkerSurvivalLootTables.init();
        ModIntegration.init();
        Sounds.init(TinkerSurvival.BUS);
    }

}
