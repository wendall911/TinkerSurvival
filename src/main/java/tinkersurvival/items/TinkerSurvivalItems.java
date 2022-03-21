package tinkersurvival.items;

import java.util.function.Supplier;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import net.minecraftforge.registries.RegistryObject;

import slimeknights.mantle.registration.object.ItemObject;

import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.TinkerTools;
import slimeknights.tconstruct.tools.TinkerToolParts;

import tinkersurvival.client.CreativeTabBase;
import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.items.item.Bandage;
import tinkersurvival.items.item.CrudeBandage;
import tinkersurvival.items.CrudeItemTier;
import tinkersurvival.items.item.Mortar;
import tinkersurvival.items.item.WoodenCup;
import tinkersurvival.items.tool.CrudeHatchet;
import tinkersurvival.items.tool.CrudeKnife;
import tinkersurvival.items.tool.CrudeSaw;
import tinkersurvival.items.tool.Knife;
import tinkersurvival.items.tool.Saw;
import tinkersurvival.TinkerSurvival;

public final class TinkerSurvivalItems extends TinkerSurvivalModule {

    public static CreativeTabBase ITEM_TAB_GROUP;
    public static CreativeTabBase TOOL_TAB_GROUP;

    public static RegistryObject<Item> FLINT_SHARD;
    public static RegistryObject<Item> PLANT_FIBER;
    public static RegistryObject<Item> PLANT_STRING;
    public static RegistryObject<Item> OINTMENT;
    public static RegistryObject<Item> PLANT_PASTE;
    public static RegistryObject<Item> CLOTH;

    public static RegistryObject<Item> CRUDE_SAW_BLADE;
    public static ItemObject<ToolPartItem> SAW_BLADE;
    public static CastItemObject SAW_BLADE_CAST;

    public static Tier FLINT_TIER;
    public static Tier STONE_TIER;
    public static Tier WOOD_TIER;

    public static RegistryObject<Item> CRUDE_KNIFE;
    public static RegistryObject<Item> CRUDE_HATCHET;
    public static RegistryObject<Item> CRUDE_SAW_HANDLE;
    public static RegistryObject<Item> CRUDE_SAW;
    public static RegistryObject<Item> MORTAR_AND_PESTLE;
    public static ItemObject<ModifiableItem> KNIFE;
    public static ToolDefinition KNIFE_DEFINITION;
    public static ItemObject<ModifiableItem> SAW;
    public static ToolDefinition SAW_DEFINITION;

    public static RegistryObject<Item> CRUDE_BANDAGE;
    public static RegistryObject<Item> BANDAGE;

    public static RegistryObject<Item> WOODEN_CUP;

    public static void init() {
        TOOL_TAB_GROUP = new CreativeTabBase(TinkerSurvival.MODID + ".tools", () -> new ItemStack(CRUDE_HATCHET.get()));
        ITEM_TAB_GROUP = new CreativeTabBase(TinkerSurvival.MODID + ".items", () -> new ItemStack(FLINT_SHARD.get()));

        // Items
        FLINT_SHARD = registerItem("flint_shard");
        PLANT_FIBER = registerItem("plant_fiber");
        PLANT_STRING = registerItem("plant_string");
        OINTMENT = registerItem("ointment");
        PLANT_PASTE = registerItem("plant_paste");
        CLOTH = registerItem("cloth");

        // Tools
        FLINT_TIER = new CrudeItemTier().setMaxUses(20).setEfficiency(1.5F)
            .setAttackDamage(0.5F).setRepairMat(Items.FLINT);
        STONE_TIER = new CrudeItemTier().setMaxUses(5).setEfficiency(1.5F)
            .setAttackDamage(0.5F).setRepairMat(Items.COBBLESTONE);
        WOOD_TIER = new CrudeItemTier().setMaxUses(20).setEfficiency(0.0F)
            .setAttackDamage(0.0F).setRepairMat(ItemTags.PLANKS);

        CRUDE_SAW_BLADE = registerItem("crude_saw_blade", () -> new Item(
            new Item.Properties().tab(TOOL_TAB_GROUP)
        ));
        CRUDE_KNIFE = registerKnifeTool("crude_knife", FLINT_TIER);
        CRUDE_HATCHET = registerHatchetTool("crude_hatchet", STONE_TIER);
        CRUDE_SAW_HANDLE = registerSawTool("crude_saw_handle", WOOD_TIER, 0, -8.0F);
        CRUDE_SAW = registerSawTool("crude_saw", FLINT_TIER, 3, -4.0F);
        MORTAR_AND_PESTLE = registerMortar("mortar_and_pestle");
        KNIFE = TOOL_REGISTRY.register("knife", () -> new Knife(
            (new Item.Properties()).stacksTo(1).tab(TinkerTools.TAB_TOOLS),
            KNIFE_DEFINITION
        ));
        KNIFE_DEFINITION = ToolDefinition.builder(KNIFE).meleeHarvest().build();
        SAW = TOOL_REGISTRY.register("saw", () -> new Saw(
            (new Item.Properties()).stacksTo(1).tab(TinkerTools.TAB_TOOLS),
            SAW_DEFINITION
        ));
        SAW_BLADE = TOOL_REGISTRY.register("saw_blade", () -> new ToolPartItem(
            new Item.Properties().tab(TinkerToolParts.TAB_TOOL_PARTS),
            HeadMaterialStats.ID
        ));
        SAW_BLADE_CAST = TOOL_REGISTRY.registerCast(
            "saw_blade",
            new Item.Properties().tab(TinkerSmeltery.TAB_SMELTERY)
        );
        SAW_DEFINITION = ToolDefinition.builder(SAW).meleeHarvest().build();

        // Bandages
        CRUDE_BANDAGE = registerItem("crude_bandage", () -> new CrudeBandage(
            (new Item.Properties()).stacksTo(8).tab(ITEM_TAB_GROUP)
        ));
        BANDAGE = registerItem("bandage", () -> new Bandage(
            (new Item.Properties()).stacksTo(16).tab(ITEM_TAB_GROUP)
        ));

        // Zombie Jesus
        WOODEN_CUP = registerItem("wooden_cup", () -> new WoodenCup(
            (new Item.Properties()).stacksTo(1).tab(ITEM_TAB_GROUP)
        ));
    }

    private static RegistryObject<Item> registerItem(String name) {
        return registerItem(name, () -> new Item(new Item.Properties().tab(ITEM_TAB_GROUP)));
    }

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEM_REGISTRY.register(name, item);
    }

    private static RegistryObject<Item> registerKnifeTool(String name, Tier tier) {
        Item knifeTool = new CrudeKnife(tier, 1, -1.4F, new Item.Properties().tab(TOOL_TAB_GROUP));

        return registerItem(name, () -> knifeTool);
    }

    private static RegistryObject<Item> registerHatchetTool(String name, Tier tier) {
        Item hatchetTool = new CrudeHatchet(tier, 4, -3.0F, new Item.Properties().tab(TOOL_TAB_GROUP));

        return registerItem(name, () -> hatchetTool);
    }

    private static RegistryObject<Item> registerSawTool(String name, Tier tier, int damage, float speed) {
        Item sawTool = new CrudeSaw(name, tier, damage, speed, new Item.Properties().tab(TOOL_TAB_GROUP));

        return registerItem(name, () -> sawTool);
    }

    private static RegistryObject<Item> registerMortar(String name) {
        return registerItem(name, () -> new Mortar(new Item.Properties().tab(TOOL_TAB_GROUP)));
    }

}
