package tinkersurvival.proxy;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.loot.TinkerSurvivalLootTables;
import tinkersurvival.world.TinkerSurvivalWorld;

public class CommonProxy {

    CommonProxy() {
        ConfigHandler.init();
        TinkerSurvivalItems.init();
        TinkerSurvivalWorld.init();
        TinkerSurvivalLootTables.init();
    }

}
