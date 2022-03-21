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
        public static final Tag.Named<Item> flintKnappable = create("flint_knappable");
        public static final Tag.Named<Item> pickaxeTools = create("pickaxe_tools");
        public static final Tag.Named<Item> axeTools = create("axe_tools");
        public static final Tag.Named<Item> shovelTools = create("shovel_tools");
        public static final Tag.Named<Item> hoeTools = create("hoe_tools");
        public static final Tag.Named<Item> sharpTools = create("sharp_tools");

        private static Tag.Named<Item> create(String id) {
            return ItemTags.createOptional(identifier(id));
        }
    }

    public static final class Blocks {
        public static final Tag.Named<Block> alwaysBreakable = create("always_breakable");
        public static final Tag.Named<Block> alwaysDrops = create("always_drops");
        public static final Tag.Named<Block> looseRockPlaceableOn = create("loose_rock_placeable_on");

        private static Tag.Named<Block> create(String id) {
            return BlockTags.createOptional(identifier(id));
        }
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(TinkerSurvival.MODID, path);
    }

}
