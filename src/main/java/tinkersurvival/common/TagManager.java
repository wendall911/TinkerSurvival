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
        public static final Tag.Named<Item> ROCK = create("rock");
        public static final Tag.Named<Item> SAW_PARTS = create("saw_parts");
        public static final Tag.Named<Item> BANDAGES = create("bandages");
        public static final Tag.Named<Item> SAW_BLADE_CAST = ItemTags.bind(TinkerSurvival.MODID +  ":casts/multi_use/saw_blade");
        public static final Tag.Named<Item> SAW_BLADE_CAST_SINGLE = ItemTags.bind(TinkerSurvival.MODID +  ":casts/single_use/saw_blade");

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
