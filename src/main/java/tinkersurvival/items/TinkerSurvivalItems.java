package tinkersurvival.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

import net.minecraftforge.registries.IForgeRegistry;

import tinkersurvival.common.CreativeTabs;
import tinkersurvival.items.CrudeItemTiers;
import tinkersurvival.items.item.Bandage;
import tinkersurvival.items.item.CrudeBandage;
import tinkersurvival.items.item.Mortar;
import tinkersurvival.items.item.WoodenCup;
import tinkersurvival.items.tool.CrudeHatchet;
import tinkersurvival.items.tool.CrudeKnife;
import tinkersurvival.items.tool.CrudeSaw;
import tinkersurvival.TinkerSurvival;

public final class TinkerSurvivalItems {

    private static IForgeRegistry<Item> ITEM_REGISTRY;

    // Items
    public static Item FLINT_SHARD;
    public static Item PLANT_FIBER;
    public static Item PLANT_STRING;
    public static Item OINTMENT;
    public static Item PLANT_PASTE;
    public static Item CLOTH;

    // Tools
    public static Item CRUDE_KNIFE;
    public static Item CRUDE_HATCHET;
    public static Item CRUDE_SAW_HANDLE;
    public static Item CRUDE_SAW_BLADE;
    public static Item CRUDE_SAW;
    public static Item MORTAR_AND_PESTLE;

    // Bandages
    public static Item CRUDE_BANDAGE;
    public static Item BANDAGE;

    // Zombie Jesus
    public static Item WOODEN_CUP;

    public static void init(IForgeRegistry<Item> registry) {
        ITEM_REGISTRY = registry;

        FLINT_SHARD = registerItem("flint_shard");
        PLANT_FIBER = registerItem("plant_fiber");
        PLANT_STRING = registerItem("plant_string");
        OINTMENT = registerItem("ointment");
        PLANT_PASTE = registerItem("plant_paste");
        CLOTH = registerItem("cloth");

        // Tools
        CRUDE_KNIFE = registerKnifeTool("crude_knife", CrudeItemTiers.FLINT_TIER);
        CRUDE_HATCHET = registerHatchetTool("crude_hatchet", CrudeItemTiers.STONE_TIER);
        CRUDE_SAW_HANDLE = registerSawTool("crude_saw_handle", CrudeItemTiers.NO_TIER, 0, -8.0F);
        CRUDE_SAW_BLADE = registerItem("crude_saw_blade", new Item(
            new Item.Properties().tab(CreativeTabs.TOOL_TAB_GROUP)
        ));
        CRUDE_SAW = registerSawTool("crude_saw", CrudeItemTiers.FLINT_TIER, 3, -4.0F);
        MORTAR_AND_PESTLE = registerMortar("mortar_and_pestle");

        // Bandages
        CRUDE_BANDAGE = registerItem("crude_bandage", new CrudeBandage(
            (new Item.Properties()).stacksTo(8).tab(CreativeTabs.ITEM_TAB_GROUP)
        ));
        BANDAGE = registerItem("bandage", new Bandage(
            (new Item.Properties()).stacksTo(16).tab(CreativeTabs.ITEM_TAB_GROUP)
        ));

        // Zombie Jesus
        WOODEN_CUP = registerItem("wooden_cup", new WoodenCup(
            (new Item.Properties()).stacksTo(1).tab(CreativeTabs.ITEM_TAB_GROUP)
        ));
    }

    private static Item registerItem(String name) {
        Item item = new Item(new Item.Properties().tab(CreativeTabs.ITEM_TAB_GROUP));

        return registerItem(name, item);
    }

    private static Item registerItem(String name, Item item) {
        Item itemConfigured = item.setRegistryName(new ResourceLocation(TinkerSurvival.MODID, name));

        ITEM_REGISTRY.register(itemConfigured);

        return item;
    }

    private static Item registerKnifeTool(String name, Tier tier) {
        Item knifeTool = new CrudeKnife(tier, 1, -1.4F, new Item.Properties().tab(CreativeTabs.TOOL_TAB_GROUP).setNoRepair());

        return registerItem(name, knifeTool);
    }

    private static Item registerHatchetTool(String name, Tier tier) {
        Item hatchetTool = new CrudeHatchet(tier, 4, -3.0F, new Item.Properties().tab(CreativeTabs.TOOL_TAB_GROUP).setNoRepair());

        return registerItem(name, hatchetTool);
    }

    private static Item registerSawTool(String name, Tier tier, int damage, float speed) {
        Item sawTool = new CrudeSaw(name, tier, damage, speed, new Item.Properties().tab(CreativeTabs.TOOL_TAB_GROUP).setNoRepair());

        return registerItem(name, sawTool);
    }

    private static Item registerMortar(String name) {
        return registerItem(name, new Mortar(new Item.Properties().tab(CreativeTabs.TOOL_TAB_GROUP).setNoRepair()));
    }

}
