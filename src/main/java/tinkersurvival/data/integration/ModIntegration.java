package tinkersurvival.data.integration;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.common.TinkerSurvivalModule;

public final class ModIntegration extends TinkerSurvivalModule {

    public static RegistryObject<Item> CHERRY_PLANKS;
    public static RegistryObject<Item> CITRUS_PLANKS;
    public static RegistryObject<Item> BMO_ANCIENT_OAK_PLANKS;
    public static RegistryObject<Item> BMO_BLIGHTED_BALSA_PLANKS;
    public static RegistryObject<Item> BMO_SWAMP_CYPRESS_PLANKS;
    public static RegistryObject<Item> BMO_WILLOW_PLANKS;
    public static RegistryObject<Item> BOP_CHERRY_PLANKS;
    public static RegistryObject<Item> BOP_DEAD_PLANKS;
    public static RegistryObject<Item> BOP_FIR_PLANKS;
    public static RegistryObject<Item> BOP_HELLBARK_PLANKS;
    public static RegistryObject<Item> BOP_JACARANDA_PLANKS;
    public static RegistryObject<Item> BOP_MAGIC_PLANKS;
    public static RegistryObject<Item> BOP_MAHOGANY_PLANKS;
    public static RegistryObject<Item> BOP_PALM_PLANKS;
    public static RegistryObject<Item> BOP_REDWOOD_PLANKS;
    public static RegistryObject<Item> BOP_UMBRAN_PLANKS;
    public static RegistryObject<Item> BOP_WILLOW_PLANKS;
    public static RegistryObject<Item> BOTANIA_DREAMWOOD_PLANKS;
    public static RegistryObject<Item> BOTANIA_LIVINGWOOD_PLANKS;
    public static RegistryObject<Item> IE_STICK_TREATED;
    public static RegistryObject<Item> QUARK_AZALEA_PLANKS;
    public static RegistryObject<Item> QUARK_BLOSSOM_PLANKS;
    public static RegistryObject<Item> AYCE_HAZEL_PLANKS;
    public static RegistryObject<Item> TCON_BLOODSHROOM_PLANKS;
    public static RegistryObject<Item> TCON_GREENHEART_PLANKS;
    public static RegistryObject<Item> TCON_SKYROOT_PLANKS;
    public static RegistryObject<Item> WS_PALM_TREE_PLANKS;

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

    public static void init() {
        String dataGen = System.getenv("DATA_GEN");
        if (dataGen != null && dataGen.contains("all")) {
            CHERRY_PLANKS = registerFruitTreesItem("cherry_planks");
            CITRUS_PLANKS = registerFruitTreesItem("citrus_planks");
            BMO_ANCIENT_OAK_PLANKS = registerBMOItem("ancient_oak_planks");
            BMO_BLIGHTED_BALSA_PLANKS = registerBMOItem("blighted_balsa_planks");
            BMO_SWAMP_CYPRESS_PLANKS = registerBMOItem("swamp_cypress_planks");
            BMO_WILLOW_PLANKS = registerBMOItem("willow_planks");
            BOP_CHERRY_PLANKS = registerBOPItem("cherry_planks");
            BOP_DEAD_PLANKS = registerBOPItem("dead_planks");
            BOP_FIR_PLANKS = registerBOPItem("fir_planks");
            BOP_HELLBARK_PLANKS = registerBOPItem("hellbark_planks");
            BOP_JACARANDA_PLANKS = registerBOPItem("jacaranda_planks");
            BOP_MAGIC_PLANKS = registerBOPItem("magic_planks");
            BOP_MAHOGANY_PLANKS = registerBOPItem("mahogany_planks");
            BOP_PALM_PLANKS = registerBOPItem("palm_planks");
            BOP_REDWOOD_PLANKS = registerBOPItem("redwood_planks");
            BOP_UMBRAN_PLANKS = registerBOPItem("umbran_planks");
            BOP_WILLOW_PLANKS = registerBOPItem("willow_planks");
            BOTANIA_DREAMWOOD_PLANKS = registerBotaniaItem("dreamwood_planks");
            BOTANIA_LIVINGWOOD_PLANKS = registerBotaniaItem("livingwood_planks");
            IE_STICK_TREATED = registerIEItem("stick_treated");
            QUARK_AZALEA_PLANKS = registerQuarkItem("azalea_planks");
            QUARK_BLOSSOM_PLANKS = registerQuarkItem("blossom_planks");
            AYCE_HAZEL_PLANKS = registerAyceItem("hazel_planks");
            TCON_BLOODSHROOM_PLANKS = registerTconItem("bloodshroom_planks");
            TCON_GREENHEART_PLANKS = registerTconItem("greenheart_planks");
            TCON_SKYROOT_PLANKS = registerTconItem("skyroot_planks");
            WS_PALM_TREE_PLANKS = registerWsItem("palm_tree_planks");
        }
    }

    private static RegistryObject<Item> registerTconItem(String name) {
        return registerItem(name, TCON_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerAyceItem(String name) {
        return registerItem(name, AYCE_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerQuarkItem(String name) {
        return registerItem(name, QUARK_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerBMOItem(String name) {
        return registerItem(name, BMO_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerBOPItem(String name) {
        return registerItem(name, BOP_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerBotaniaItem(String name) {
        return registerItem(name, BOTANIA_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerIEItem(String name) {
        return registerItem(name, IE_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerFruitTreesItem(String name) {
        return registerItem(name, FRUITTREES_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerWsItem(String name) {
        return registerItem(name, WS_ITEM_REGISTRY);
    }

    private static RegistryObject<Item> registerItem(String name, DeferredRegister<Item> registry) {
        return registerItem(name, () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)), registry);
    }

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item, DeferredRegister<Item> registry) {
        return registry.register(name, item);
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

    private static ResourceLocation getLoc(String modid, String name) {
        return new ResourceLocation(modid, name);
    }

}
