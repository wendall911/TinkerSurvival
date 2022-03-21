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
    public String getName() {
        return "TinkerSurvival - Item Models";
    }

    @Override
    protected void registerModels() {
        TinkerSurvivalWorld.BLOCK_REGISTRY.getEntries().stream()
                .forEach(this::blockItem);

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));

        builder(itemGenerated, TinkerSurvivalWorld.ROCK_STONE.get());
        builder(itemGenerated, TinkerSurvivalWorld.FLINT_SHARD.get());
        builder(itemGenerated, TinkerSurvivalWorld.PLANT_FIBER.get());
        builder(itemGenerated, TinkerSurvivalWorld.PLANT_STRING.get());
        builder(itemGenerated, TinkerSurvivalWorld.OINTMENT.get());
        builder(itemGenerated, TinkerSurvivalWorld.PLANT_PASTE.get());
        builder(itemGenerated, TinkerSurvivalWorld.CLOTH.get());
        builder(itemGenerated, TinkerSurvivalWorld.CRUDE_SAW_BLADE.get());
        builder(itemHandheld, TinkerSurvivalWorld.CRUDE_KNIFE.get());
        builder(itemHandheld, TinkerSurvivalWorld.CRUDE_HATCHET.get());
        builder(itemHandheld, TinkerSurvivalWorld.CRUDE_SAW_HANDLE.get());
        builder(itemHandheld, TinkerSurvivalWorld.CRUDE_SAW.get());
        builder(itemGenerated, TinkerSurvivalWorld.MORTAR_AND_PESTLE.get());
        builder(itemGenerated, TinkerSurvivalWorld.CRUDE_BANDAGE.get());
        builder(itemGenerated, TinkerSurvivalWorld.BANDAGE.get());
        builder(itemGenerated, TinkerSurvivalWorld.WOODEN_CUP.get());
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
            .map(path -> {
                if (path.contains("rock_stone")) {
                    path = "stone_loose_rock";
                }
                return withExistingParent(path, modLoc("block/" + path));
            })
            .orElseThrow(() -> new IllegalStateException("Failed to create model for Block Item"));

        if (type.contains("rock_stone")) {
            type = "stone";
        }

        builder.texture("all", mcLoc("block/" + type));
    }

}
