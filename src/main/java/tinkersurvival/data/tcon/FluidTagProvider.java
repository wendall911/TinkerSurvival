package tinkersurvival.data.tcon;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;

import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.FluidObject;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.TinkerTags.Fluids;
import slimeknights.tconstruct.fluids.TinkerFluids;

import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;

@SuppressWarnings("unchecked")
public class FluidTagProvider extends FluidTagsProvider {

    public FluidTagProvider(DataGenerator generatorIn, ExistingFileHelper helper) {
        super(generatorIn, TinkerSurvival.MODID, helper);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - TCon Fluid Tags";
    }

    @Override
    public void addTags() {
        tagAll(TinkerSurvivalItems.MANASTEEL);

        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS).addTags(
            TinkerSurvivalItems.MANASTEEL.getForgeTag()
        );

        this.tag(TinkerTags.Fluids.AVERAGE_METAL_SPILLING)
            .addTag(TinkerSurvivalItems.MANASTEEL.getForgeTag());
    }

    /** Tags this fluid using local tags */
    private void tagLocal(FluidObject<?> fluid) {
        tag(fluid.getLocalTag()).add(fluid.getStill(), fluid.getFlowing());
    }

    private void tagAll(FluidObject<?> fluid) {
        tagLocal(fluid);
        tag(fluid.getForgeTag()).addTag(fluid.getLocalTag());
    }

}
