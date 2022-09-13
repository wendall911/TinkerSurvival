package tinkersurvival.data.client;

import java.util.Objects;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TinkerSurvival.MODID, exFileHelper);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - Block State and Models";
    }

    @Override
    protected void registerStatesAndModels() {
        generateLooseRockBaseModel();

        generateLooseRockVariants(TinkerSurvivalWorld.ANDESITE_LOOSE_ROCK);
        generateLooseRockVariants(TinkerSurvivalWorld.DIORITE_LOOSE_ROCK);
        generateLooseRockVariants(TinkerSurvivalWorld.GRANITE_LOOSE_ROCK);
        generateLooseRockVariants(TinkerSurvivalWorld.STONE_LOOSE_ROCK);
        generateLooseRockVariants(TinkerSurvivalWorld.SANDSTONE_LOOSE_ROCK);
        generateLooseRockVariants(TinkerSurvivalWorld.RED_SANDSTONE_LOOSE_ROCK);
        generateLooseRockVariants(TinkerSurvivalWorld.ROCK_STONE_BLOCK);
    }

    private void generateLooseRockBaseModel() {
        BlockModelBuilder base = models()
            .withExistingParent("block/loose_rock", mcLoc("block/block"))
            .texture("particle", "#all");

        addFaces(base.element().from(4, 0, 4).to(5, 1, 5));
        addFaces(base.element().from(7, 0, 3).to(8, 1, 4));
        addFaces(base.element().from(5, 0, 7).to(6, 1, 8));
        addFaces(base.element().from(5, 0, 10).to(6, 1, 11));
        addFaces(base.element().from(11, 0, 6).to(12, 1, 7));
        addFaces(base.element().from(10, 0, 10).to(11, 1, 11));
        addFaces(base.element().from(9, 0, 8).to(10, 1, 9));
        addFaces(base.element().from(7, 0, 9).to(8, 1, 10));
        addFaces(base.element().from(7, 1, 6).to(8, 2, 7));
        addFaces(base.element().from(6, 1, 7).to(7, 2, 8));
        addFaces(base.element().from(8, 1, 8).to(9, 2, 9));
        addFaces(base.element().from(6, 0, 6).to(9, 1, 9));
    }

    private void addFaces(ModelBuilder<BlockModelBuilder>.ElementBuilder elementBuilder) {
        elementBuilder.allFaces((direction, faceBuilder) -> {
            ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder f = faceBuilder
                .uvs(0, 0, 12, 12)
                .texture("#all");
        }).end();
    }

    private void generateLooseRockVariants(Block block) {
        ResourceLocation name = block.getRegistryName();
        String type = Objects.requireNonNull(name).getPath().toString().replace("_loose_rock", "");

        if (type.contains("rock_stone")) {
            type = "stone";
        }

        ModelFile modelFile = models()
            .withExistingParent(name.toString(), modLoc("block/loose_rock"))
            .texture("all", modLoc("block/loose/9p_loose_" + type));

        getVariantBuilder(block)
            .forAllStates(state -> ConfiguredModel.builder().modelFile(modelFile).build());
    }

}
