package tinkersurvival.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

import tinkersurvival.data.tcon.material.MaterialIds;
import tinkersurvival.items.TinkerSurvivalItems;

public class MaterialDataProvider extends AbstractMaterialDataProvider {

    public MaterialDataProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - TCon Materials";
    }

    @Override
    protected void addMaterials() {
        addCompatMetalMaterial(MaterialIds.manaSteel, 3, ORDER_COMPAT + ORDER_GENERAL);
    }

}
