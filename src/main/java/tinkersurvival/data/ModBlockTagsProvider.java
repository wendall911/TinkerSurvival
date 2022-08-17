package tinkersurvival.data;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;

import tinkersurvival.data.integration.ModIntegration;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.common.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, TinkerSurvival.MODID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return "TinkerSurvival - Block Tags";
    }

    @Override 
    protected void addTags() {
        this.tag(TagManager.Blocks.ALWAYS_BREAKABLE)
            .addTag(TagManager.Blocks.LOOSE_ROCKS)
            .addTag(BlockTags.BEDS)
            .add(Blocks.GRAVEL)
            .addTag(Tags.Blocks.GRAVEL)
            .addTag(BlockTags.LEAVES)
            .addOptional(ModIntegration.sgcLoc("avocado_leaves"))
            .addOptional(ModIntegration.exnihiloLoc("infested_leaves"))
            .addOptional(ModIntegration.exnihiloLoc("infesting_leaves"))
            .addTag(BlockTags.DIRT)
            .addTag(Tags.Blocks.SAND)
            .addTag(BlockTags.SAND)
            .addTag(TagManager.Blocks.FIBER_PLANTS);

        this.tag(TagManager.Blocks.ALWAYS_DROPS)
            .addTag(TagManager.Blocks.LOOSE_ROCKS)
            .addTag(BlockTags.BEDS)
            .add(Blocks.GRAVEL)
            .addTag(Tags.Blocks.GRAVEL)
            .addTag(BlockTags.LEAVES)
            .addTag(BlockTags.DIRT)
            .addTag(Tags.Blocks.SAND)
            .addTag(BlockTags.SAND);

        this.tag(TagManager.Blocks.LOOSE_ROCK_PLACEABLE_ON)
            .add(Blocks.GRAVEL)
            .addTag(Tags.Blocks.GRAVEL)
            .add(Blocks.STONE)
            .add(Blocks.CALCITE)
            .add(Blocks.GRANITE)
            .add(Blocks.DIORITE)
            .add(Blocks.ANDESITE)
            .add(Blocks.COAL_ORE)
            .addTag(Tags.Blocks.ORES_COAL)
            .add(Blocks.SANDSTONE)
            .add(Blocks.IRON_BLOCK)
            .addTag(Tags.Blocks.ORES_IRON)
            .add(Blocks.COPPER_ORE)
            .addTag(Tags.Blocks.ORES_COPPER)
            .add(Blocks.MOSSY_COBBLESTONE)
            .add(Blocks.RED_SANDSTONE)
            .addTag(BlockTags.DIRT)
            .addTag(BlockTags.SAND)
            .addTag(Tags.Blocks.SAND)
            .addTag(BlockTags.TERRACOTTA);

        this.tag(TagManager.Blocks.LOOSE_ROCKS)
            .add(TinkerSurvivalWorld.ANDESITE_LOOSE_ROCK)
            .add(TinkerSurvivalWorld.DIORITE_LOOSE_ROCK)
            .add(TinkerSurvivalWorld.GRANITE_LOOSE_ROCK)
            .add(TinkerSurvivalWorld.STONE_LOOSE_ROCK)
            .add(TinkerSurvivalWorld.SANDSTONE_LOOSE_ROCK)
            .add(TinkerSurvivalWorld.RED_SANDSTONE_LOOSE_ROCK);

        this.tag(TagManager.Blocks.FIBER_PLANTS)
            .addTag(BlockTags.LEAVES)
            .add(Blocks.VINE)
            .add(Blocks.FERN)
            .add(Blocks.LARGE_FERN)
            .add(Blocks.GRASS)
            .add(Blocks.TALL_GRASS)
            .addOptionalTag(TagManager.forgeLoc("grass"))
            .addOptionalTag(TagManager.forgeLoc("bushes"));

        this.tag(TagManager.Blocks.BRANCHES)
            .addOptionalTag(ModIntegration.dynamictreesLoc("branches"));
    }

}
