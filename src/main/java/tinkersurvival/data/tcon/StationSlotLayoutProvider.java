package tinkersurvival.data.tcon;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.tinkering.AbstractStationSlotLayoutProvider;
import slimeknights.tconstruct.tools.TinkerToolParts;

import tinkersurvival.world.TinkerSurvivalWorld;

public class StationSlotLayoutProvider extends AbstractStationSlotLayoutProvider {

    public StationSlotLayoutProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "TinkerSurvival Tinker Station Slot Layouts";
    }

    @Override
    protected void addLayouts() {
        defineModifiable(TinkerSurvivalWorld.SAW)
            .sortIndex(6)
            .addInputItem(TinkerToolParts.smallAxeHead, 22, 20)
            .addInputItem(TinkerToolParts.smallAxeHead, 11, 39)
            .addInputItem(TinkerToolParts.toolHandle,   49, 61)
            .addInputItem(TinkerToolParts.toolBinding,  31, 44)
            .build();
    }

}
