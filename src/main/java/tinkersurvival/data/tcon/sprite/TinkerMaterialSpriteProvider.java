package tinkersurvival.data.tcon.sprite;

import net.minecraft.resources.ResourceLocation;

import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

import tinkersurvival.data.tcon.material.MaterialIds;

public class TinkerMaterialSpriteProvider extends AbstractMaterialSpriteProvider {

    @Override
    public String getName() {
        return "TinkerSurvival - TCon Materials";
    }

    @Override
    protected void addAllMaterials() {
        buildMaterial(MaterialIds.manaSteel)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF001944).addARGB(102, 0xFF00296D).addARGB(140, 0xFF0043A5).addARGB(178, 0xFF186ACE).addARGB(216, 0xFF3389FF).addARGB(255, 0xFF59A6EF).build());
    }

}
