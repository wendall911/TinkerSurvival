package tinkersurvival.data.tcon;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.tools.definition.weapon.SweepWeaponAttack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.TinkerToolParts;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

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
        define(TinkerSurvivalWorld.SAW_DEFINITION)
            .part(TinkerSurvivalWorld.SAW_BLADE)
            .part(TinkerToolParts.toolHandle)
            .part(TinkerToolParts.toolBinding)
            .stat(ToolStats.ATTACK_DAMAGE, 0.0f)
            .stat(ToolStats.ATTACK_SPEED, -8.0f)
            .multiplier(ToolStats.ATTACK_DAMAGE, 0.0f)
            .smallToolStartingSlots();
    }

}