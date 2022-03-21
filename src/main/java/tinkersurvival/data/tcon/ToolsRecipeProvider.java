package tinkersurvival.data.tcon;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;

import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;

public class ToolsRecipeProvider extends BaseRecipeProvider implements IMaterialRecipeHelper, IToolRecipeHelper {

    public ToolsRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "TinkerSurvival Tool Recipes";
    }

    @Override
    public String getModId() {
        return TinkerSurvival.MODID;
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        this.addToolBuildingRecipes(consumer);
        this.addPartRecipes(consumer);
    }

    private void addToolBuildingRecipes(Consumer<FinishedRecipe> consumer) {
        toolBuilding(consumer, TinkerSurvivalItems.SAW, "tools/building/");
    }

	private void addPartRecipes(Consumer<FinishedRecipe> consumer) {
        String partFolder = "tools/parts/";
        String castFolder = "smeltery/casts/";

        partRecipes(
            consumer,
            TinkerSurvivalItems.SAW_BLADE,
            TinkerSurvivalItems.SAW_BLADE_CAST,
            4,
            partFolder,
            castFolder
        );
    }

}
