package tinkersurvival.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
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
        registry.addIngredientInfo(new ItemStack(TinkerSurvivalWorld.rockStone),ItemStack.class,"jei.description.rock");
        registry.addIngredientInfo(new ItemStack(TinkerSurvivalWorld.grassFiber),ItemStack.class,"jei.description.grass_fiber");
        registry.addIngredientInfo(new ItemStack(TinkerSurvivalWorld.flintShard),ItemStack.class,"jei.description.flint_shard");
        registry.addIngredientInfo(new ItemStack(Items.STICK), ItemStack.class, "jei.description.stick");
    }

}
