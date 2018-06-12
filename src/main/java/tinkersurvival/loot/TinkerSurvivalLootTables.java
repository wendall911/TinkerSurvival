package tinkersurvival.loot;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

import tinkersurvival.TinkerSurvival;

public class TinkerSurvivalLootTables {
    
    public static ResourceLocation TREASURE;

    public static void init() {
        TREASURE = register("gameplay/fishing/treasure");
    }

    private static ResourceLocation register(String path) {
        return LootTableList.register(new ResourceLocation(TinkerSurvival.MODID + ":" + path));
	}

}
