package tinkersurvival.items;

import net.minecraft.world.item.Item;

import slimeknights.mantle.registration.object.ItemObject;

import tinkersurvival.common.CreativeTabs;
import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.items.item.TinkerSurvivalBookItem;
import tinkersurvival.items.item.TinkerSurvivalBookItem.BookType;

public final class TinkerSurvivalBooks extends TinkerSurvivalModule {

    public static ItemObject<TinkerSurvivalBookItem> INTRO_BOOK = ITEM_TCON_REGISTRY.register(
        "tinkers_survival",
        () -> new TinkerSurvivalBookItem(
            (new Item.Properties()).stacksTo(1).tab(CreativeTabs.ITEM_TAB_GROUP),
            BookType.TINKERS_SURVIVAL
        )
    );

    public static ItemObject<TinkerSurvivalBookItem> MP_BOOK = ITEM_TCON_REGISTRY.register(
        "tinkers_survival_modpack",
        () -> new TinkerSurvivalBookItem(
            (new Item.Properties()).stacksTo(1).tab(CreativeTabs.ITEM_TAB_GROUP),
            BookType.TINKERS_SURVIVAL_MODPACK
        )
    );

}
