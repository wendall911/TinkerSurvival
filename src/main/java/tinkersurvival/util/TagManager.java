package tinkersurvival.util;

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
        public static final Tag.Named<Item> SHOVEL_TOOLS = create("shovel_tools");
        public static final Tag.Named<Item> HOE_TOOLS = create("hoe_tools");
        public static final Tag.Named<Item> SHARP_TOOLS = create("sharp_tools");
        public static final Tag.Named<Item> LOOSE_ROCKS = create("loose_rocks");

        private static Tag.Named<Item> create(String id) {
            return ItemTags.createOptional(identifier(id));
        }
    }

    public static final class Blocks {
        public static final Tag.Named<Block> ALWAYS_BREAKABLE = create("always_breakable");
        public static final Tag.Named<Block> ALWAYS_DROPS = create("always_drops");
        public static final Tag.Named<Block> LOOSE_ROCK_PLACEABLE_ON = create("loose_rock_placeable_on");

        private static Tag.Named<Block> create(String id) {
            return BlockTags.createOptional(identifier(id));
        }
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(TinkerSurvival.MODID, path);
    }

}
