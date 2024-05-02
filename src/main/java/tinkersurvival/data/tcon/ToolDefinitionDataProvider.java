package tinkersurvival.data.tcon;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.materials.RandomMaterial;
import slimeknights.tconstruct.library.tools.definition.module.build.MultiplyStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.DefaultMaterialsModule;
import slimeknights.tconstruct.library.tools.definition.module.material.PartStatsModule;
import slimeknights.tconstruct.library.tools.nbt.MultiplierNBT;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerToolParts;

import tinkersurvival.items.TConItems;
import tinkersurvival.items.ToolDefinitions;
import tinkersurvival.TinkerSurvival;

public class ToolDefinitionDataProvider extends AbstractToolDefinitionDataProvider {

    public ToolDefinitionDataProvider(DataGenerator generatorIn) {
        super(generatorIn, TinkerSurvival.MODID);
    }

    @Override
    public String getName() {
        return "TinkerSurvival Tool Definition Data Generator";
    }

    /*
     * Order matters here!!!
     * Must match order of StationSlotLayoutProvider or recipe will be invalid.
     * Handle and tool order is to match TCon tools, as they have this order.
     */
    @Override
    protected void addToolDefinitions() {
        RandomMaterial tier1Material = RandomMaterial.random().tier(1).build();
        DefaultMaterialsModule defaultThreeParts = DefaultMaterialsModule.builder().material(tier1Material, tier1Material, tier1Material).build();

        define(ToolDefinitions.KNIFE_DEFINITION)
            .module(PartStatsModule.meleeHarvest()
                .part(TinkerToolParts.smallBlade)
                .part(TinkerToolParts.toughHandle)
                .part(TinkerToolParts.toolBinding).build())
            .module(defaultThreeParts)
            .module(new SetStatsModule(StatsNBT.builder()
                .set(ToolStats.ATTACK_DAMAGE, 1.5f)
                .set(ToolStats.ATTACK_SPEED, 1.0f).build()))
            .module(new MultiplyStatsModule(MultiplierNBT.builder()
                .set(ToolStats.ATTACK_DAMAGE, 0.35f).build()))
            .smallToolStartingSlots();

        define(ToolDefinitions.SAW_DEFINITION)
            .module(PartStatsModule.meleeHarvest()
                .part(TConItems.SAW_BLADE)
                .part(TinkerToolParts.toolHandle)
                .part(TinkerToolParts.toolBinding).build())
            .module(new SetStatsModule(StatsNBT.builder()
                .set(ToolStats.ATTACK_DAMAGE, 0.0f)
                .set(ToolStats.ATTACK_SPEED, -8.0f).build()))
            .module(new MultiplyStatsModule(MultiplierNBT.builder()
                .set(ToolStats.ATTACK_DAMAGE, 0.0f).build()))
            .smallToolStartingSlots();
    }

}
