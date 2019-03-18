package tinkersurvival.tools.tool;

import java.util.List;

import net.minecraft.block.state.IBlockState;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.AoeToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.tools.TinkerTools;

public class Saw extends AoeToolCore {

    public Saw(PartMaterialType... requiredComponents) {
        super(requiredComponents);

        addCategory(Category.HARVEST);
    }

    public Saw(String name) {
        this(PartMaterialType.handle(TinkerTools.toolRod),
            PartMaterialType.head(TinkerTools.axeHead),
            PartMaterialType.head(TinkerTools.axeHead),
            PartMaterialType.extra(TinkerTools.binding));

        setTranslationKey(name);
        setRegistryName(name);
        this.name = name;
    }

    public String name;

    @Override
    public float damagePotential() {
        return 0.01f;
    }

    @Override
    public double attackSpeed() {
        return 1;
    }

    @Override
    public boolean isEffective(IBlockState state) {
        return false;
    }

    @Override
    protected ToolNBT buildTagData(List<Material> materials) {
        return buildDefaultTag(materials);
    }

}
