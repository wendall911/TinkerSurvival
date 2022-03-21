package tinkersurvival.data.overrides;

import java.util.Arrays;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import tinkersurvival.common.TagManager;
import tinkersurvival.data.integration.ModIntegration;
import tinkersurvival.util.IBlockProvider;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class TagsOverrideProvider extends BlockTagsProvider {
    public TagsOverrideProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, TinkerSurvival.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - Forge Tags Overrides";
    }

    @Override 
    protected void addTags() {
        /*
        getBuilder(Tags.Blocks.NEEDS_WOOD_TOOL)
            .addOptional(ModIntegration.ieLoc("ore_lead"));
            .addTag();
        getBuilder(Tags.Blocks.NEEDS_GOLD_TOOL)
            .addOptional(ModIntegration.ieLoc("ore_lead"));
            .addTag()
            .addTag();
        getBuilder(Tags.Blocks.NEEDS_NETHERITE_TOOL)
            .addTag()
            .addOptional(ModIntegration.ieLoc("ore_lead"));
            .addTag();
        */
    }

    private void builder(Tag.Named<Block> tag, IBlockProvider... items) {
        getBuilder(tag).add(Arrays.stream(items).map(IBlockProvider::asBlock).toArray(Block[]::new));
    }

    protected TagsProvider.TagAppender<Block> getBuilder(Tag.Named<Block> tag) {
        return tag(tag);
    }

}
