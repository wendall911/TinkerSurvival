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

import slimeknights.tconstruct.common.registration.CastItemObject;

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
        TinkerSurvivalWorld.getBlockEntries().stream()
                .forEach(this::blockItem);

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));

        builder(itemGenerated, TinkerSurvivalWorld.ROCK_STONE.get());
        builder(itemGenerated, TinkerSurvivalItems.FLINT_SHARD.get());
        builder(itemGenerated, TinkerSurvivalItems.PLANT_FIBER.get());
        builder(itemGenerated, TinkerSurvivalItems.PLANT_STRING.get());
        builder(itemGenerated, TinkerSurvivalItems.OINTMENT.get());
        builder(itemGenerated, TinkerSurvivalItems.PLANT_PASTE.get());
        builder(itemGenerated, TinkerSurvivalItems.CLOTH.get());
        builder(itemGenerated, TinkerSurvivalItems.CRUDE_SAW_BLADE.get());
        builder(itemHandheld, TinkerSurvivalItems.CRUDE_KNIFE.get());
        builder(itemHandheld, TinkerSurvivalItems.CRUDE_HATCHET.get());
        builder(itemHandheld, TinkerSurvivalItems.CRUDE_SAW_HANDLE.get());
        builder(itemHandheld, TinkerSurvivalItems.CRUDE_SAW.get());
        builder(itemGenerated, TinkerSurvivalItems.MORTAR_AND_PESTLE.get());
        builder(itemGenerated, TinkerSurvivalItems.CRUDE_BANDAGE.get());
        builder(itemGenerated, TinkerSurvivalItems.BANDAGE.get());
        builder(itemGenerated, TinkerSurvivalItems.WOODEN_CUP.get());

        addCastModels(TinkerSurvivalItems.SAW_BLADE_CAST);
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

    private void addCastModels(CastItemObject cast) {
        ResourceLocation idGold = cast.get().getRegistryName();
        String path = idGold.getPath().replace("_cast", "");
        ResourceLocation textureLocationGold = new ResourceLocation(idGold.getNamespace(), "item/cast/" + path);
        ResourceLocation idSand = cast.getSand().getRegistryName();
        ResourceLocation textureLocationSand = new ResourceLocation(idSand.getNamespace(), "item/sand_cast/" + path);
        ResourceLocation idSandRed = cast.getRedSand().getRegistryName();
        ResourceLocation textureLocationSandRed = new ResourceLocation(idSandRed.getNamespace(), "item/red_sand_cast/" + path);
        ResourceLocation loc = mcLoc("item/generated");

        singleTexture(idGold.getPath(), loc, "layer0", textureLocationGold);
        singleTexture(idSand.getPath(), loc, "layer0", textureLocationSand);
        singleTexture(idSandRed.getPath(), loc, "layer0", textureLocationSandRed);        
    }

}
