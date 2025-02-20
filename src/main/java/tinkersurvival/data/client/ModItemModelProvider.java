package tinkersurvival.data.client;

import java.util.Objects;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.mantle.registration.object.ItemObject;
import slimeknights.tconstruct.common.data.model.MaterialModelBuilder;
import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.part.MaterialItem;

import tinkersurvival.items.TConItems;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TinkerSurvival.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - Item Models";
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        addCastModels(TConItems.SAW_BLADE_CAST);
        build(itemGenerated, TinkerSurvivalItems.MODPACK_BOOK);
    }

    private void addCastModels(CastItemObject cast) {
        ResourceLocation idGold = cast.getId();
        String path = idGold.getPath().replace("_cast", "");
        ResourceLocation textureLocationGold = new ResourceLocation(idGold.getNamespace(), "item/cast/" + path);
        ResourceLocation idSand = BuiltInRegistries.ITEM.getKey(cast.getSand().asItem());
        ResourceLocation textureLocationSand = new ResourceLocation(Objects.requireNonNull(idSand).getNamespace(), "item/sand_cast/" + path);
        ResourceLocation idSandRed = BuiltInRegistries.ITEM.getKey(cast.getRedSand().asItem());
        ResourceLocation textureLocationSandRed = new ResourceLocation(Objects.requireNonNull(idSandRed).getNamespace(), "item/red_sand_cast/" + path);
        ResourceLocation loc = mcLoc("item/generated");

        singleTexture(idGold.getPath(), loc, "layer0", textureLocationGold);
        singleTexture(idSand.getPath(), loc, "layer0", textureLocationSand);
        singleTexture(idSandRed.getPath(), loc, "layer0", textureLocationSandRed);
        part(TConItems.SAW_BLADE, "saw/head").offset(-1, 1);
    }

    private MaterialModelBuilder<ItemModelBuilder> part(ResourceLocation part, String texture) {
        return withExistingParent(part.getPath(), "forge:item/default")
            .texture("texture", new ResourceLocation(TinkerSurvival.MODID, "item/tool/" + texture))
            .customLoader(MaterialModelBuilder::new);
    }

    private MaterialModelBuilder<ItemModelBuilder> part(ItemObject<? extends MaterialItem> part, String texture) {
        return part(part.getId(), texture);
}

    private void build(ModelFile itemGenerated, Item item) {
        String name = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();

        getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}
