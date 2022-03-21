package tinkersurvival.data.client;

import java.util.function.Supplier;
import java.util.Optional;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TinkerSurvival.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        TinkerSurvivalWorld.blockRegistry.getEntries().stream()
                .forEach(this::blockItem);

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        builder(itemGenerated, TinkerSurvivalWorld.rockStone.get());
        builder(itemGenerated, TinkerSurvivalWorld.flintShard.get());
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, Item item) {
        String name = item.getRegistryName().getPath().toString();

        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

	protected void blockItem(Supplier<? extends Block> block) {
		String type = block.get().getRegistryName().getPath().toString().replace("_loose_rock", "");

        ItemModelBuilder builder = Optional.ofNullable(block.get())
                .map(Block::asItem)
                .map(Item::getRegistryName)
                .map(ResourceLocation::getPath)
                .map(path -> withExistingParent(path, modLoc("block/" + path)))
                .orElseThrow(() -> new IllegalStateException("Failed to create model for Block Item"));

        builder.texture("all", mcLoc("block/" + type));
    }

}
