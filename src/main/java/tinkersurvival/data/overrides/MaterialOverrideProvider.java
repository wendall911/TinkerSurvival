package tinkersurvival.data.overrides;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;

import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

public class MaterialOverrideProvider extends AbstractMaterialDataProvider {
  public MaterialOverrideProvider(DataGenerator gen) {
    super(gen);
  }

  @Override
  public String getName() {
    return "Tinker's Construct Material Overrides";
  }

  @Override
  protected void addMaterials() {
    // tier 1
    addMaterial(MaterialIds.wood,   0, ORDER_GENERAL, true);
    addMaterial(MaterialIds.rock,   0, ORDER_HARVEST, true);
    addMaterial(MaterialIds.flint,  0, ORDER_WEAPON,  true);
    addMaterial(MaterialIds.copper, 0, ORDER_SPECIAL, true);
    addMaterial(MaterialIds.bone,   0, ORDER_SPECIAL, true);
    // tier 1 - binding
    addMaterial(MaterialIds.string,  0, ORDER_BINDING, true);
    addMaterial(MaterialIds.leather, 0, ORDER_BINDING, true);
    addMaterial(MaterialIds.vine,    0, ORDER_BINDING, true);

    // tier 2
    addMaterial(MaterialIds.iron,        0, ORDER_GENERAL, false);
    addMaterial(MaterialIds.searedStone, 0, ORDER_HARVEST, false);
    addMaterial(MaterialIds.bloodbone,   0, ORDER_WEAPON,  false);
    addMaterial(MaterialIds.slimewood,   0, ORDER_SPECIAL, true);
    // tier 2 - nether
    addMaterial(MaterialIds.scorchedStone, 0, ORDER_NETHER, false);
    addMaterial(MaterialIds.necroticBone,  0, ORDER_NETHER, true);
    // tier 2 - binding
    addMaterial(MaterialIds.chain,        0, ORDER_BINDING, true);
    addMaterial(MaterialIds.skyslimeVine, 0, ORDER_BINDING, true);

    // tier 3
    addMaterial(MaterialIds.slimesteel,     0, ORDER_GENERAL, false);
    addMaterial(MaterialIds.amethystBronze, 0, ORDER_HARVEST, false);
    addMaterial(MaterialIds.nahuatl,        0, ORDER_WEAPON,  false);
    addMaterial(MaterialIds.roseGold,       0, ORDER_SPECIAL, false);
    addMaterial(MaterialIds.pigIron,        0, ORDER_SPECIAL, false);
    // tier 3 (nether)
    addMaterial(MaterialIds.cobalt, 0, ORDER_NETHER, false);
    // tier 3 - binding
    addMaterial(MaterialIds.darkthread, 0, ORDER_BINDING, false);

    // tier 4
    addMaterial(MaterialIds.queensSlime, 0, ORDER_GENERAL, false);
    addMaterial(MaterialIds.hepatizon,   0, ORDER_HARVEST, false);
    addMaterial(MaterialIds.manyullyn,   0, ORDER_WEAPON,  false);
    addMaterial(MaterialIds.blazingBone, 0, ORDER_SPECIAL, false);
    //addMetalMaterial(MaterialIds.soulsteel, 4, ORDER_SPECIAL, false, 0x6a5244);
    // tier 4 - binding
    addMaterial(MaterialIds.ancientHide, 0, ORDER_BINDING, false);

    // tier 5 binding, temporarily in book 4
    addMaterial(MaterialIds.enderslimeVine, 0, ORDER_BINDING, true);

    // tier 2 (end)
    //addMaterialNoFluid(MaterialIds.endstone, 2, ORDER_END, true, 0xe0d890);

    // tier 2 (mod integration)
    addCompatMetalMaterial(MaterialIds.osmium,     0, ORDER_COMPAT + ORDER_GENERAL);
    addCompatMetalMaterial(MaterialIds.tungsten,   0, ORDER_COMPAT + ORDER_HARVEST);
    addCompatMetalMaterial(MaterialIds.platinum,   0, ORDER_COMPAT + ORDER_HARVEST);
    addCompatMetalMaterial(MaterialIds.silver,     0, ORDER_COMPAT + ORDER_WEAPON);
    addCompatMetalMaterial(MaterialIds.lead,       0, ORDER_COMPAT + ORDER_WEAPON);
    ICondition condition = new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,
                                           tagExistsCondition("ingots/aluminum"),
                                           tagExistsCondition("ingots/tin"),
                                           tagExistsCondition("ingots/zinc"));
    addMaterial(MaterialIds.whitestone, 0, ORDER_COMPAT + ORDER_SPECIAL, false, false, condition);
    // tier 3 (mod integration)
    addCompatMetalMaterial(MaterialIds.steel,           0, ORDER_COMPAT + ORDER_GENERAL);
    addCompatMetalMaterial(MaterialIds.bronze,          0, ORDER_COMPAT + ORDER_HARVEST);
    addCompatMetalMaterial(MaterialIds.constantan,      0, ORDER_COMPAT + ORDER_HARVEST);
    addCompatMetalMaterial(MaterialIds.invar,           0, ORDER_COMPAT + ORDER_WEAPON);
    addCompatMetalMaterial(MaterialIds.necronium,       0, ORDER_COMPAT + ORDER_WEAPON, "uranium");
    addCompatMetalMaterial(MaterialIds.electrum,        0, ORDER_COMPAT + ORDER_SPECIAL);
    addCompatMetalMaterial(MaterialIds.platedSlimewood, 0, ORDER_COMPAT + ORDER_SPECIAL, "brass");
  }
}
