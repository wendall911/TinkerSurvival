package tinkersurvival.data.tcon;

import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import slimeknights.mantle.recipe.data.IRecipeHelper;

import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;

import tinkersurvival.items.TConItems;
import tinkersurvival.TinkerSurvival;

public class ToolsRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper, IMaterialRecipeHelper, IToolRecipeHelper {

    public ToolsRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }


    @Override
    public String getModId() {
        return TinkerSurvival.MODID;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        this.addToolBuildingRecipes(consumer);
        this.addPartRecipes(consumer);
    }

    private void addToolBuildingRecipes(Consumer<FinishedRecipe> consumer) {
        toolBuilding(consumer, TConItems.KNIFE, "tools/building/");
        toolBuilding(consumer, TConItems.SAW, "tools/building/");
    }

    private void addPartRecipes(Consumer<FinishedRecipe> consumer) {
        String partFolder = "tools/parts/";
        String castFolder = "smeltery/casts/";

        partRecipes(
            consumer,
            TConItems.SAW_BLADE,
            TConItems.SAW_BLADE_CAST,
            4,
            partFolder,
            castFolder
        );
    }

}