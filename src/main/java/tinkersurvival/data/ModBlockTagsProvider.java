package tinkersurvival.data;

import java.util.Arrays;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.IBlockProvider;
import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, TinkerSurvival.MODID, existingFileHelper);
    }

    @Override 
    protected void addTags() {
        getBuilder(TagManager.Blocks.ALWAYS_BREAKABLE)
            .add(Blocks.GRAVEL)
            .addTag(Tags.Blocks.GRAVEL)
			.addTag(BlockTags.LEAVES)
            .addTag(BlockTags.DIRT)
			.addTag(Tags.Blocks.SAND)
            .addTag(BlockTags.SAND);

        getBuilder(TagManager.Blocks.ALWAYS_DROPS)
            .add(Blocks.GRAVEL)
            .addTag(Tags.Blocks.GRAVEL)
			.addTag(BlockTags.LEAVES)
            .addTag(BlockTags.DIRT)
			.addTag(Tags.Blocks.SAND)
            .addTag(BlockTags.SAND);

        getBuilder(TagManager.Blocks.LOOSE_ROCK_PLACEABLE_ON)
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
    }

    private void builder(Tag.Named<Block> tag, IBlockProvider... items) {
        getBuilder(tag).add(Arrays.stream(items).map(IBlockProvider::asBlock).toArray(Block[]::new));
    }

    protected TagsProvider.TagAppender<Block> getBuilder(Tag.Named<Block> tag) {
        return tag(tag);
    }

}
