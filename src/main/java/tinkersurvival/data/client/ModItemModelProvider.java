package tinkersurvival.data.client;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.tconstruct.common.registration.CastItemObject;

import tinkersurvival.items.TConItems;
import tinkersurvival.items.TinkerSurvivalItems;
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
        blockItem(TinkerSurvivalWorld.ANDESITE_LOOSE_ROCK);
        blockItem(TinkerSurvivalWorld.DIORITE_LOOSE_ROCK);
        blockItem(TinkerSurvivalWorld.GRANITE_LOOSE_ROCK);
        blockItem(TinkerSurvivalWorld.STONE_LOOSE_ROCK);
        blockItem(TinkerSurvivalWorld.SANDSTONE_LOOSE_ROCK);
        blockItem(TinkerSurvivalWorld.RED_SANDSTONE_LOOSE_ROCK);

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));

        build(itemGenerated, TinkerSurvivalWorld.ROCK_STONE);
        build(itemGenerated, TinkerSurvivalItems.FLINT_SHARD);
        build(itemGenerated, TinkerSurvivalItems.PLANT_FIBER);
        build(itemGenerated, TinkerSurvivalItems.PLANT_STRING);
        build(itemGenerated, TinkerSurvivalItems.OINTMENT);
        build(itemGenerated, TinkerSurvivalItems.PLANT_PASTE);
        build(itemGenerated, TinkerSurvivalItems.CLOTH);
        build(itemGenerated, TinkerSurvivalItems.CRUDE_SAW_BLADE);
        build(itemHandheld, TinkerSurvivalItems.CRUDE_KNIFE);
        build(itemHandheld, TinkerSurvivalItems.CRUDE_HATCHET);
        build(itemHandheld, TinkerSurvivalItems.CRUDE_SAW_HANDLE);
        build(itemHandheld, TinkerSurvivalItems.CRUDE_SAW);
        build(itemGenerated, TinkerSurvivalItems.MORTAR_AND_PESTLE);
        build(itemGenerated, TinkerSurvivalItems.CRUDE_BANDAGE);
        build(itemGenerated, TinkerSurvivalItems.BANDAGE);
        build(itemGenerated, TinkerSurvivalItems.WOODEN_CUP);

        addCastModels(TConItems.SAW_BLADE_CAST);
    }

    private void build(ModelFile itemGenerated, Item item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();

        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

    protected void blockItem(Block block) {
        String type = Objects.requireNonNull(block.getRegistryName()).getPath().replace("_loose_rock", "");

        ItemModelBuilder builder = Optional.ofNullable(block)
            .map(Block::getRegistryName)
            .map(ResourceLocation::getPath)
            .map(path -> {
                return withExistingParent(path, modLoc("block/" + path));
            })
            .orElseThrow(() -> new IllegalStateException("Failed to create model for Block Item"));

        builder.texture("all", mcLoc("block/" + type));
    }

    private void addCastModels(CastItemObject cast) {
        ResourceLocation idGold = cast.getRegistryName();
        String path = idGold.getPath().replace("_cast", "");
        ResourceLocation textureLocationGold = new ResourceLocation(idGold.getNamespace(), "item/cast/" + path);
        ResourceLocation idSand = cast.getSand().getRegistryName();
        ResourceLocation textureLocationSand = new ResourceLocation(Objects.requireNonNull(idSand).getNamespace(), "item/sand_cast/" + path);
        ResourceLocation idSandRed = cast.getRedSand().getRegistryName();
        ResourceLocation textureLocationSandRed = new ResourceLocation(Objects.requireNonNull(idSandRed).getNamespace(), "item/red_sand_cast/" + path);
        ResourceLocation loc = mcLoc("item/generated");

        singleTexture(idGold.getPath(), loc, "layer0", textureLocationGold);
        singleTexture(idSand.getPath(), loc, "layer0", textureLocationSand);
        singleTexture(idSandRed.getPath(), loc, "layer0", textureLocationSandRed);        
    }

}
