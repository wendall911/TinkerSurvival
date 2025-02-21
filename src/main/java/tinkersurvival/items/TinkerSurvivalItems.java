package tinkersurvival.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegisterEvent;

import survivalistessentials.items.item.SurvivalistEssentialsBook;

import tinkersurvival.TinkerSurvival;

public class TinkerSurvivalItems {

    private static RegisterEvent.RegisterHelper<Item> ITEM_REGISTRY;

    public static Item MODPACK_BOOK;

    public static void init(RegisterEvent.RegisterHelper<Item> registry) {
        ITEM_REGISTRY = registry;

        MODPACK_BOOK = registerBook("modpack_book");
    }

    private static Item registerItem(String name, Item item) {
        ITEM_REGISTRY.register(name, item);

        return item;
    }

    public static Item registerBook(String name) {
        return registerItem(name, new SurvivalistEssentialsBook(
            new Item.Properties(),
            name,
            TinkerSurvival.MODID
        ));
    }

}
