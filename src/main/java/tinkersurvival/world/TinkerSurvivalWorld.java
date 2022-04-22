package tinkersurvival.world;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.registries.IForgeRegistry;

import tinkersurvival.common.CreativeTabs;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.block.LooseRockBlock;
import tinkersurvival.world.item.RockStone;

public final class TinkerSurvivalWorld {

    private static IForgeRegistry<Block> BLOCK_REGISTRY;
    private static IForgeRegistry<Item> ITEM_REGISTRY;

    // Blocks
    public static Block ANDESITE_LOOSE_ROCK;
    public static Block DIORITE_LOOSE_ROCK;
    public static Block GRANITE_LOOSE_ROCK;
    public static Block STONE_LOOSE_ROCK;
    public static Block SANDSTONE_LOOSE_ROCK;
    public static Block RED_SANDSTONE_LOOSE_ROCK;
    public static Block ROCK_STONE_BLOCK;

    // Items
    public static Item ROCK_STONE;

    public static void initBlocks(IForgeRegistry<Block> registry) {
        BLOCK_REGISTRY = registry;

        ANDESITE_LOOSE_ROCK = registerBlock("andesite_loose_rock");
        DIORITE_LOOSE_ROCK = registerBlock("diorite_loose_rock");
        GRANITE_LOOSE_ROCK = registerBlock("granite_loose_rock");
        STONE_LOOSE_ROCK = registerBlock("stone_loose_rock");
        SANDSTONE_LOOSE_ROCK = registerBlock("sandstone_loose_rock");
        RED_SANDSTONE_LOOSE_ROCK = registerBlock("red_sandstone_loose_rock");
        ROCK_STONE_BLOCK = registerBlock("rock_stone_block");
    }

    public static void initItems(IForgeRegistry<Item> registry) {
        ITEM_REGISTRY = registry;

        registerItem("andesite_loose_rock", ANDESITE_LOOSE_ROCK);
        registerItem("diorite_loose_rock", DIORITE_LOOSE_ROCK);
        registerItem("granite_loose_rock", GRANITE_LOOSE_ROCK);
        registerItem("stone_loose_rock", STONE_LOOSE_ROCK);
        registerItem("sandstone_loose_rock", SANDSTONE_LOOSE_ROCK);
        registerItem("red_sandstone_loose_rock", RED_SANDSTONE_LOOSE_ROCK);
        registerItem("rock_stone_block", ROCK_STONE_BLOCK);
    }

    private static Block registerBlock(String name) {
        Block blockConfigured = (new LooseRockBlock()).setRegistryName(new ResourceLocation(TinkerSurvival.MODID, name));

        BLOCK_REGISTRY.register(blockConfigured);

        return blockConfigured;
    }

    private static void registerItem(String name, Block block) {
        registerItem(name, new BlockItem(block, new Item.Properties().tab(CreativeTabs.WORLD_TAB_GROUP)));

        if (name.contains("rock_stone_block")) {
            ROCK_STONE = registerItem("rock_stone", new RockStone(block, new Item.Properties().tab(CreativeTabs.WORLD_TAB_GROUP)));
        }
    }

    private static Item registerItem(String name, Item item) {
        Item itemConfigured = item.setRegistryName(new ResourceLocation(TinkerSurvival.MODID, name));

        ITEM_REGISTRY.register(itemConfigured);

        return item;
    }

}
