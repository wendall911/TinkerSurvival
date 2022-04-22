package tinkersurvival.items;

import net.minecraft.world.item.Item;

import slimeknights.mantle.registration.object.ItemObject;

import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.TinkerToolParts;

import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.items.tool.Knife;
import tinkersurvival.items.tool.Saw;

public final class TConItems extends TinkerSurvivalModule {

    public static final CastItemObject SAW_BLADE_CAST = ITEM_TCON_REGISTRY.registerCast(
        "saw_blade",
        new Item.Properties().tab(TinkerSmeltery.TAB_SMELTERY)
    );

    public static final ItemObject<ModifiableItem> KNIFE = ITEM_TCON_REGISTRY.register("knife", () -> new Knife(
        (new Item.Properties()).stacksTo(1).tab(TinkerTools.TAB_TOOLS),
        ToolDefinitions.KNIFE_DEFINITION
    ));

    public static final ItemObject<ModifiableItem> SAW = ITEM_TCON_REGISTRY.register("saw", () -> new Saw(
        (new Item.Properties()).stacksTo(1).tab(TinkerTools.TAB_TOOLS),
        ToolDefinitions.SAW_DEFINITION
    ));

    public static final ItemObject<ToolPartItem> SAW_BLADE = ITEM_TCON_REGISTRY.register("saw_blade", () -> new ToolPartItem(
        new Item.Properties().tab(TinkerToolParts.TAB_TOOL_PARTS),
        HeadMaterialStats.ID
    ));

}
