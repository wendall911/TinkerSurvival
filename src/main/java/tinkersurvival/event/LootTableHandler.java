package tinkersurvival.event;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;

import net.minecraftforge.event.LootTableLoadEvent; 
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import tinkersurvival.TinkerSurvival;

public class LootTableHandler {
    
    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent event) {
        if (event.getName().toString().equals("minecraft:gameplay/fishing/treasure")) {
            LootTable customTable = event.getLootTableManager().getLootTableFromLocation(new ResourceLocation(TinkerSurvival.MODID, "gameplay/fishing/treasure"));
            LootPool customPool = customTable.getPool("grail");
            event.getTable().addPool(customPool);
        }
    }

}
