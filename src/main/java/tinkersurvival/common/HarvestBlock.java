/*
 * Derived from the No Tree Punching mod by AlcatrazEscapee.
 * https://github.com/alcatrazEscapee/no-tree-punching/blob/1.18.x/src/main/java/com/alcatrazescapee/notreepunching/util/HarvestBlockHandler.java
 * Work under copyright. See the project LICENSE.md for details.
 */

package tinkersurvival.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.common.TagManager;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.mixin.AbstractBlockAccessor;
import tinkersurvival.mixin.AbstractBlockStateAccessor;
import tinkersurvival.mixin.DiggerItemAccessor;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;
import tinkersurvival.util.ToolType;

public final class HarvestBlock {

    public static final Map<Material, ToolType> PRIMARY_TOOL_TYPES = new HashMap<>();
    public static final Map<Block, ToolType> BLOCK_TOOL_TYPES = new HashMap<>();
    public static final Map<Item, ToolType> ITEM_TOOL_TYPES = new HashMap<>();

    static {
        add(ToolType.PICKAXE, Material.STONE, Material.METAL, Material.HEAVY_METAL, Material.PISTON, Material.AMETHYST);
        add(ToolType.AXE, Material.WOOD, Material.NETHER_WOOD, Material.BAMBOO, Material.CACTUS, Material.MOSS, Material.VEGETABLE);
        add(ToolType.SHOVEL, Material.TOP_SNOW, Material.CLAY, Material.DIRT, Material.GRASS, Material.SAND, Material.SNOW, Material.POWDER_SNOW);
        add(ToolType.HOE, Material.PLANT, Material.WATER_PLANT, Material.REPLACEABLE_PLANT, Material.REPLACEABLE_WATER_PLANT, Material.REPLACEABLE_FIREPROOF_PLANT, Material.SCULK, Material.SPONGE, Material.BAMBOO_SAPLING, Material.LEAVES, Material.GRASS);
        add(ToolType.SHARP, Material.CLOTH_DECORATION, Material.WEB, Material.WOOL, Material.CAKE);
        add(ToolType.NONE, Material.AIR, Material.STRUCTURAL_AIR, Material.DECORATION, Material.BUILDABLE_GLASS, Material.ICE_SOLID, Material.SHULKER_SHELL, Material.GLASS, Material.ICE, Material.PORTAL, Material.WATER, Material.BUBBLE_COLUMN, Material.LAVA, Material.FIRE, Material.EXPLOSIVE, Material.BARRIER, Material.EGG);
    }

    private static void add(ToolType value, Material... keys) {
        Stream.of(keys).forEach(key -> PRIMARY_TOOL_TYPES.put(key, value));
    }

    public static void setup() {
        BLOCK_TOOL_TYPES.clear();

        final Map<Material, List<Block>> unknownMaterialBlocks = new HashMap<>();

        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            if (ConfigHandler.Server.blockWhitelistMods().contains(ItemUse.getModId(block))) {
                BLOCK_TOOL_TYPES.put(block, ToolType.NONE);

                continue;
            }
            final AbstractBlockAccessor blockAccess = (AbstractBlockAccessor) block;
            final BlockBehaviour.Properties settings = blockAccess.getProperties();

            // Infer a primary tool type for the block.
            ToolType primary = PRIMARY_TOOL_TYPES.get(blockAccess.getMaterial());

            // Forcefully set everything to require a tool
            // Need to do both the block settings and the block state since the value is copied there for every state
            settings.requiresCorrectToolForDrops();

            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                AbstractBlockStateAccessor abstractState = (AbstractBlockStateAccessor) state;

                abstractState.setRequiresCorrectToolForDrops(true);

                if (abstractState.getDestroySpeed() == 0 && primary == null) {
                    primary = ToolType.NONE;
                }
            }

            if (primary != null && primary != ToolType.NONE) {
                BLOCK_TOOL_TYPES.put(block, primary);
            }

            if (primary == null) {
                // Unknown tool type. Collect and log it later
                unknownMaterialBlocks.computeIfAbsent(blockAccess.getMaterial(), k -> new ArrayList<>()).add(block);
            }
        }

        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item instanceof DiggerItem digger) {
                final ToolType toolType = toolTypeForMineableTag(((DiggerItemAccessor) digger).getBlocks());

                if (toolType != ToolType.NONE) {
                    ITEM_TOOL_TYPES.put(item, toolType);
                }
            }
            else if (item instanceof SwordItem || item instanceof ShearsItem) {
                ITEM_TOOL_TYPES.put(item, ToolType.SHARP);
            }
        }

        if (!unknownMaterialBlocks.isEmpty()) {
            TinkerSurvival.LOGGER.error("Unable to infer primary tools for %d blocks with unknown materials. These blocks will not be affected by NTP's modifications!", unknownMaterialBlocks.values().stream().mapToInt(Collection::size).sum());
            unknownMaterialBlocks
                .forEach((mat, blocks) -> {
                    blocks.forEach(TinkerSurvival.LOGGER::warn);
                });
        }

        for (Map.Entry<Material, List<Block>> entry : unknownMaterialBlocks.entrySet()) {
            final Material material = entry.getKey();
            final List<Block> blocks = entry.getValue();

            TinkerSurvival.LOGGER.warn("Material: [isLiquid={}, isSolid={}, blocksMotion={}, isFlammable={}, isReplaceable={}, isSolidBlocking={}, getPushReaction={}, getColor=[id={}, col={}]] | Blocks: {}",
                material.isLiquid(), material.isSolid(), material.blocksMotion(), material.isFlammable(), material.isReplaceable(), material.isSolidBlocking(), material.getPushReaction(), material.getColor().id, new Color(material.getColor().col),
                blocks.stream().map(b -> b.getRegistryName().toString()).collect(Collectors.joining(", ")));
        }
    }

    private static ToolType toolTypeForMineableTag(Tag<Block> tag) {
        if (tag == BlockTags.MINEABLE_WITH_PICKAXE) {
            return ToolType.PICKAXE;
        }

        if (tag == BlockTags.MINEABLE_WITH_AXE) {
            return ToolType.AXE;
        }

        if (tag == BlockTags.MINEABLE_WITH_SHOVEL) {
            return ToolType.SHOVEL;
        }

        if (tag == BlockTags.MINEABLE_WITH_HOE) {
            return ToolType.HOE;
        }

        return ToolType.NONE;
    }

}
