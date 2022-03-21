package tinkersurvival.data;

import java.util.Arrays;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.Tags;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, TinkerSurvival.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Tinker Survival - Item Tags";
    }

    @Override
    protected void addTags() {
        builder(TagManager.Items.FLINT_KNAPPABLE, Items.FLINT);
        builder(TagManager.Items.PICKAXE_TOOLS);
        builder(TagManager.Items.AXE_TOOLS);
        builder(TagManager.Items.SHOVEL_TOOLS);
        builder(TagManager.Items.HOE_TOOLS);
        builder(TagManager.Items.SHARP_TOOLS);
        builder(TagManager.Items.LOOSE_ROCKS, TinkerSurvivalWorld.rockStone.get());
    }

    private void builder(Tag.Named<Item> tag) {
        getBuilder(tag);
    }

    private void builder(Tag.Named<Item> tag, ItemLike... items) {
        getBuilder(tag).add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
    }

    protected TagsProvider.TagAppender<Item> getBuilder(Tag.Named<Item> tag) {
        return tag(tag);
    }

}
