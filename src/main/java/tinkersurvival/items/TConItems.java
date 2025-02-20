package tinkersurvival.items;

import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegisterEvent;

import slimeknights.mantle.registration.object.ItemObject;

import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.items.tool.Knife;
import tinkersurvival.items.tool.Saw;

public final class TConItems extends TinkerSurvivalModule {

    public static final CastItemObject SAW_BLADE_CAST = ITEM_TCON_REGISTRY.registerCast(
        "saw_blade",
        new Item.Properties()
    );

    public static final ItemObject<ModifiableItem> KNIFE = ITEM_TCON_REGISTRY.register("knife", () -> new Knife(
        (new Item.Properties()).stacksTo(1),
        ToolDefinitions.KNIFE_DEFINITION
    ));

    public static final ItemObject<ModifiableItem> SAW = ITEM_TCON_REGISTRY.register("saw", () -> new Saw(
        (new Item.Properties()).stacksTo(1),
        ToolDefinitions.SAW_DEFINITION
    ));

    public static final ItemObject<ToolPartItem> SAW_BLADE = ITEM_TCON_REGISTRY.register("saw_blade", () -> new ToolPartItem(
        new Item.Properties(),
        HeadMaterialStats.ID
    ));

    public static void init(RegisterEvent.RegisterHelper<Item> registry) {}

}