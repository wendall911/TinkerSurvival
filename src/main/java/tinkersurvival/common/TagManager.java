package tinkersurvival.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import tinkersurvival.TinkerSurvival;

public final class TagManager {

    public static final class Items {
        public static final Tag.Named<Item> FLINT_KNAPPABLE = create("flint_knappable");
        public static final Tag.Named<Item> PICKAXE_TOOLS = create("pickaxe_tools");
        public static final Tag.Named<Item> AXE_TOOLS = create("axe_tools");
        public static final Tag.Named<Item> SAW_TOOLS = create("saw_tools");
        public static final Tag.Named<Item> SHOVEL_TOOLS = create("shovel_tools");
        public static final Tag.Named<Item> HOE_TOOLS = create("hoe_tools");
        public static final Tag.Named<Item> KNIFE_TOOLS = create("knife_tools");
        public static final Tag.Named<Item> SHARP_TOOLS = create("sharp_tools");
        public static final Tag.Named<Item> ROCK = create("rock");
        public static final Tag.Named<Item> SAW_PARTS = create("saw_parts");
        public static final Tag.Named<Item> BANDAGES = create("bandages");
        public static final Tag.Named<Item> SAW_BLADE_CAST = ItemTags.bind(TinkerSurvival.MODID +  ":casts/multi_use/saw_blade");
        public static final Tag.Named<Item> SAW_BLADE_CAST_SINGLE = ItemTags.bind(TinkerSurvival.MODID +  ":casts/single_use/saw_blade");

        // Mod Integration
        // Fruit Trees
        public static final Tag.Named<Item> CHERRY_LOGS = create("cherry_logs");
        public static final Tag.Named<Item> CITRUS_LOGS = create("citrus_logs");

        // Biome Makeover
        public static final Tag.Named<Item> BMO_ANCIENT_OAK_LOGS = create("ancient_oak_logs");
        public static final Tag.Named<Item> BMO_BLIGHTED_BALSA_LOGS = create("blighted_balsa_logs");
        public static final Tag.Named<Item> BMO_SWAMP_CYPRESS_LOGS = create("swamp_cypress_logs");
        public static final Tag.Named<Item> BMO_WILLOW_LOGS = create("bmo_willow_logs");

        // Biomes O' Plenty
        public static final Tag.Named<Item> BOP_CHERRY_LOGS = create("bop_cherry_logs");
        public static final Tag.Named<Item> BOP_DEAD_LOGS = create("dead_logs");
        public static final Tag.Named<Item> BOP_FIR_LOGS = create("fir_logs");
        public static final Tag.Named<Item> BOP_HELLBARK_LOGS = create("hellbark_logs");
        public static final Tag.Named<Item> BOP_JACARANDA_LOGS = create("jacaranda_logs");
        public static final Tag.Named<Item> BOP_MAGIC_LOGS = create("magic_logs");
        public static final Tag.Named<Item> BOP_MAHOGANY_LOGS = create("mahogany_logs");
        public static final Tag.Named<Item> BOP_PALM_LOGS = create("palm_logs");
        public static final Tag.Named<Item> BOP_REDWOOD_LOGS = create("redwood_logs");
        public static final Tag.Named<Item> BOP_UMBRAN_LOGS = create("umbran_logs");
        public static final Tag.Named<Item> BOP_WILLOW_LOGS = create("willow_logs");

        // Quark
        public static final Tag.Named<Item> QUARK_AZALEA_LOGS = create("azalea_logs");
        public static final Tag.Named<Item> QUARK_BLOSSOM_LOGS = create("blossom_logs");

        // All You Can Eat
        public static final Tag.Named<Item> AYCE_HAZEL_LOGS = create("hazel_logs");

        // Tinkers' Construct
        public static final Tag.Named<Item> TCON_BLOODSHROOM_LOGS = create("bloodshroom_logs");
        public static final Tag.Named<Item> TCON_GREENHEART_LOGS = create("greenheart_logs");
        public static final Tag.Named<Item> TCON_SKYROOT_LOGS = create("skyroot_logs");

        private static Tag.Named<Item> create(String id) {
            return ItemTags.createOptional(identifier(id));
        }
    }

    public static final class Blocks {
        public static final Tag.Named<Block> ALWAYS_BREAKABLE = create("always_breakable");
        public static final Tag.Named<Block> ALWAYS_DROPS = create("always_drops");
        public static final Tag.Named<Block> LOOSE_ROCK_PLACEABLE_ON = create("loose_rock_placeable_on");
        public static final Tag.Named<Block> LOOSE_ROCKS = create("loose_rocks");
        public static final Tag.Named<Block> FIBER_PLANTS = create("fiber_plants");

        private static Tag.Named<Block> create(String id) {
            return BlockTags.createOptional(identifier(id));
        }
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(TinkerSurvival.MODID, path);
    }

}
