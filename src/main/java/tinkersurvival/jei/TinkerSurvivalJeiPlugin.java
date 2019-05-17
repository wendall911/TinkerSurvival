package tinkersurvival.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IIngredientType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tinkersurvival.world.TinkerSurvivalWorld;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class TinkerSurvivalJeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        // Material Info:
        final IIngredientType<ItemStack> ITEM = () -> ItemStack.class;

        registry.addIngredientInfo(new ItemStack(TinkerSurvivalWorld.rockStone), ITEM, "jei.description.rock");
        registry.addIngredientInfo(new ItemStack(TinkerSurvivalWorld.grassFiber), ITEM, "jei.description.grass_fiber");
        registry.addIngredientInfo(new ItemStack(TinkerSurvivalWorld.flintShard), ITEM, "jei.description.flint_shard");
        registry.addIngredientInfo(new ItemStack(Items.STICK), ITEM, "jei.description.stick");
    }

}
