package tinkersurvival.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;

import tinkersurvival.data.tcon.material.MaterialIds;
import tinkersurvival.items.TinkerSurvivalItems;

public class MaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {

    public MaterialTraitsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
        super(gen, materials);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - TCon Material Traits";
    }

    @Override
    protected void addMaterialTraits() {
        addDefaultTraits(MaterialIds.manaSteel, TinkerModifiers.ductile.get(), TinkerSurvivalItems.MANA_MODIFIER.get());
    }

}
