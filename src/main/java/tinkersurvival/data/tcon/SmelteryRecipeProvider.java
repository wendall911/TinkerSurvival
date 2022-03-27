package tinkersurvival.data.tcon;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Block;

import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.data.recipe.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import tinkersurvival.data.tcon.SmelteryCompat;
import tinkersurvival.items.TinkerSurvivalItems;

public class SmelteryRecipeProvider extends BaseRecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper {

    public SmelteryRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - TCon Smeltery Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        this.addMeltingRecipes(consumer);
        this.addCastingRecipes(consumer);
    }

    private void addCastingRecipes(Consumer<FinishedRecipe> consumer) {
        // Pure Fluid Recipes
        String folder = "smeltery/casting/";

        // Molten objects with Bucket, Block, Ingot, and Nugget forms with standard values
        String metalFolder = folder + "metal/";

        for (SmelteryCompat compat : SmelteryCompat.values()) {
            this.metalTagCasting(consumer, compat.getFluid(), compat.getName(), metalFolder, false);
        }
    }

    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";

        // ores
        String metalFolder = folder + "metal/";
        metalMelting(consumer, TinkerSurvivalItems.MANASTEEL.get(), "manasteel", true, metalFolder, false, Byproduct.IRON);
    }

}
