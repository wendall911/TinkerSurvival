package tinkersurvival.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

import tinkersurvival.data.tcon.material.MaterialIds;

public class MaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {

    public MaterialRenderInfoProvider(DataGenerator gen, AbstractMaterialSpriteProvider spriteProvider) {
        super(gen, spriteProvider);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - TCon Material Render Info Provider";
    }

    @Override
    protected void addMaterialRenderInfo() {

        buildRenderInfo(MaterialIds.manaSteel).color(0x3389FF).fallbacks("metal");
    }

}
