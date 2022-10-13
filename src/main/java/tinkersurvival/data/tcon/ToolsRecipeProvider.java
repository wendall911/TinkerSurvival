package tinkersurvival.data.tcon;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IToolRecipeHelper;

import tinkersurvival.items.TConItems;
import tinkersurvival.TinkerSurvival;

public class ToolsRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper, IMaterialRecipeHelper, IToolRecipeHelper {

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