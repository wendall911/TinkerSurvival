package tinkersurvival.common;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import tinkersurvival.client.CreativeTabBase;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class CreativeTabs {

    public static final CreativeTabBase WORLD_TAB_GROUP = new CreativeTabBase(TinkerSurvival.MODID + ".world", () -> new ItemStack(TinkerSurvivalWorld.ROCK_STONE));
    public static final CreativeTabBase ITEM_TAB_GROUP = new CreativeTabBase(TinkerSurvival.MODID + ".items", () -> new ItemStack(TinkerSurvivalItems.FLINT_SHARD));
    public static final CreativeTabBase TOOL_TAB_GROUP = new CreativeTabBase(TinkerSurvival.MODID + ".tools", () -> new ItemStack(TinkerSurvivalItems.CRUDE_HATCHET));
    public static final CreativeModeTab INTEGRATION_TAB_GROUP = new CreativeTabBase(
        TinkerSurvival.MODID + ".items", () -> new ItemStack(TinkerSurvivalItems.FLINT_SHARD)
    ).setRecipeFolderName("building_blocks");

}
