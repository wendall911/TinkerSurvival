package tinkersurvival.data.integration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.IForgeRegistry;

import tinkersurvival.common.CreativeTabs;

public final class ModIntegration {

    public static final String AYCE_MODID = "allyoucaneat";
    public static final String BMO_MODID = "biomemakeover";
    public static final String BOP_MODID = "biomesoplenty";
    public static final String BOTANIA_MODID = "botania";
    public static final String FT_MODID = "fruittrees";
    public static final String IE_MODID = "immersiveengineering";
    public static final String QUARK_MODID = "quark";
    public static final String SGC_MODID = "sushigocrafting";
    public static final String TCON_MODID = "tconstruct";
    public static final String WS_MODID = "watersource";
    public static final String AN_MODID = "ars_nouveau";
    public static final String EXNIHILO_MODID = "exnihilosequentia";
    public static final String UNDERGARDEN_MODID = "undergarden";
    public static final String DYNAMICTREES_MODID = "dynamictrees";

    public static Item CHERRY_PLANKS;
    public static Item CITRUS_PLANKS;
    public static Item BMO_ANCIENT_OAK_PLANKS;
    public static Item BMO_BLIGHTED_BALSA_PLANKS;
    public static Item BMO_SWAMP_CYPRESS_PLANKS;
    public static Item BMO_WILLOW_PLANKS;
    public static Item BOP_CHERRY_PLANKS;
    public static Item BOP_DEAD_PLANKS;
    public static Item BOP_FIR_PLANKS;
    public static Item BOP_HELLBARK_PLANKS;
    public static Item BOP_JACARANDA_PLANKS;
    public static Item BOP_MAGIC_PLANKS;
    public static Item BOP_MAHOGANY_PLANKS;
    public static Item BOP_PALM_PLANKS;
    public static Item BOP_REDWOOD_PLANKS;
    public static Item BOP_UMBRAN_PLANKS;
    public static Item BOP_WILLOW_PLANKS;
    public static Item BOTANIA_DREAMWOOD_PLANKS;
    public static Item BOTANIA_LIVINGWOOD_PLANKS;
    public static Item IE_STICK_TREATED;
    public static Item QUARK_AZALEA_PLANKS;
    public static Item QUARK_BLOSSOM_PLANKS;
    public static Item AYCE_HAZEL_PLANKS;
    public static Item TCON_BLOODSHROOM_PLANKS;
    public static Item TCON_GREENHEART_PLANKS;
    public static Item TCON_SKYROOT_PLANKS;
    public static Item WS_PALM_TREE_PLANKS;
    public static Item AN_ARCHWOOD_PLANKS;
    public static Item UNDERGARDEN_SMOGSTEM_PLANKS;
    public static Item UNDERGARDEN_WIGGLEWOOD_PLANKS;
    public static Item UNDERGARDEN_GRONGLE_PLANKS;

    public static IForgeRegistry<Item> ITEM_REGISTRY;

    public static void init(IForgeRegistry<Item> registry) {
        ITEM_REGISTRY = registry;

        String dataGen = System.getenv("DATA_GEN");
        if (dataGen != null && dataGen.contains("all")) {
            CHERRY_PLANKS = registerItem(ftLoc("cherry_planks"));
            CITRUS_PLANKS = registerItem(ftLoc("citrus_planks"));
            BMO_ANCIENT_OAK_PLANKS = registerItem(bmoLoc("ancient_oak_planks"));
            BMO_BLIGHTED_BALSA_PLANKS = registerItem(bmoLoc("blighted_balsa_planks"));
            BMO_SWAMP_CYPRESS_PLANKS = registerItem(bmoLoc("swamp_cypress_planks"));
            BMO_WILLOW_PLANKS = registerItem(bmoLoc("willow_planks"));
            BOP_CHERRY_PLANKS = registerItem(bopLoc("cherry_planks"));
            BOP_DEAD_PLANKS = registerItem(bopLoc("dead_planks"));
            BOP_FIR_PLANKS = registerItem(bopLoc("fir_planks"));
            BOP_HELLBARK_PLANKS = registerItem(bopLoc("hellbark_planks"));
            BOP_JACARANDA_PLANKS = registerItem(bopLoc("jacaranda_planks"));
            BOP_MAGIC_PLANKS = registerItem(bopLoc("magic_planks"));
            BOP_MAHOGANY_PLANKS = registerItem(bopLoc("mahogany_planks"));
            BOP_PALM_PLANKS = registerItem(bopLoc("palm_planks"));
            BOP_REDWOOD_PLANKS = registerItem(bopLoc("redwood_planks"));
            BOP_UMBRAN_PLANKS = registerItem(bopLoc("umbran_planks"));
            BOP_WILLOW_PLANKS = registerItem(bopLoc("willow_planks"));
            BOTANIA_DREAMWOOD_PLANKS = registerItem(botaniaLoc("dreamwood_planks"));
            BOTANIA_LIVINGWOOD_PLANKS = registerItem(botaniaLoc("livingwood_planks"));
            IE_STICK_TREATED = registerItem(ieLoc("stick_treated"));
            QUARK_AZALEA_PLANKS = registerItem(qLoc("azalea_planks"));
            QUARK_BLOSSOM_PLANKS = registerItem(qLoc("blossom_planks"));
            AYCE_HAZEL_PLANKS = registerItem(ayceLoc("hazel_planks"));
            TCON_BLOODSHROOM_PLANKS = registerItem(tconLoc("bloodshroom_planks"));
            TCON_GREENHEART_PLANKS = registerItem(tconLoc("greenheart_planks"));
            TCON_SKYROOT_PLANKS = registerItem(tconLoc("skyroot_planks"));
            WS_PALM_TREE_PLANKS = registerItem(wsLoc("palm_tree_planks"));
            AN_ARCHWOOD_PLANKS = registerItem(anLoc("archwood_planks"));
            UNDERGARDEN_GRONGLE_PLANKS = registerItem(undergardenLoc("grongle_planks"));
            UNDERGARDEN_SMOGSTEM_PLANKS = registerItem(undergardenLoc("smogstem_planks"));
            UNDERGARDEN_WIGGLEWOOD_PLANKS = registerItem(undergardenLoc("wigglewood_planks"));
        }
    }

    private static Item registerItem(ResourceLocation loc) {
        Item item = (new Item(new Item.Properties().tab(CreativeTabs.INTEGRATION_TAB_GROUP))).setRegistryName(loc);

        ITEM_REGISTRY.register(item);

        return item;
    }

    public static ResourceLocation bmoLoc(String name) {
        return getLoc(BMO_MODID, name);
    }

    public static ResourceLocation tconLoc(String name) {
        return getLoc(TCON_MODID, name);
    }

    public static ResourceLocation ayceLoc(String name) {
        return getLoc(AYCE_MODID, name);
    }

    public static ResourceLocation qLoc(String name) {
        return getLoc(QUARK_MODID, name);
    }

    public static ResourceLocation bopLoc(String name) {
        return getLoc(BOP_MODID, name);
    }

    public static ResourceLocation botaniaLoc(String name) {
        return getLoc(BOTANIA_MODID, name);
    }

    public static ResourceLocation ftLoc(String name) {
        return getLoc(FT_MODID, name);
    }

    public static ResourceLocation ieLoc(String name) {
        return getLoc(IE_MODID, name);
    }

    public static ResourceLocation sgcLoc(String name) {
        return getLoc(SGC_MODID, name);
    }

    public static ResourceLocation wsLoc(String name) {
        return getLoc(WS_MODID, name);
    }

    public static ResourceLocation anLoc(String name) {
        return getLoc(AN_MODID, name);
    }

    public static ResourceLocation exnihiloLoc(String name) {
        return getLoc(EXNIHILO_MODID, name);
    }

    public static ResourceLocation undergardenLoc(String name) {
        return getLoc(UNDERGARDEN_MODID, name);
    }

    public static ResourceLocation dynamictreesLoc(String name) {
        return getLoc(DYNAMICTREES_MODID, name);
    }

    private static ResourceLocation getLoc(String modid, String name) {
        return new ResourceLocation(modid, name);
    }

}
