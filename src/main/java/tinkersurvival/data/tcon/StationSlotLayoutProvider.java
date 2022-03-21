package tinkersurvival.data.tcon;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.TinkerToolParts;

import tinkersurvival.items.TinkerSurvivalItems;

public class StationSlotLayoutProvider extends AbstractStationSlotLayoutProvider {

    public StationSlotLayoutProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "TinkerSurvival Tinker Station Slot Layouts";
    }

    /*
     * Order matters here!!!
     * Must match order of ToolDefinitionDataProvider or recipe will be invalid.
     * Handle and tool order is to match TCon tools, as they have this order.
     */
    @Override
    protected void addLayouts() {
        defineModifiable(TinkerSurvivalItems.KNIFE)
            .sortIndex(6)
            .addInputItem(TinkerToolParts.smallBlade, 48, 27)
            .addInputItem(TinkerToolParts.toughHandle,   12, 62)
            .addInputItem(TinkerToolParts.toolBinding,  30, 44)
            .build();

        defineModifiable(TinkerSurvivalItems.SAW)
            .sortIndex(6)
            .addInputItem(TinkerSurvivalItems.SAW_BLADE, 52, 22)
            .addInputItem(TinkerToolParts.toolHandle,   13, 59)
            .addInputItem(TinkerToolParts.toolBinding,  31, 42)
            .build();
    }

}
