package tinkersurvival.event;

/*
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Loader;
*/

/*
import tinkersurvival.recipe.RecipeHelper;
*/
import tinkersurvival.TinkerSurvival;

public class WorldLoadEventHandler {

    /*
    @SubscribeEvent
    public void loadWorld(WorldEvent.Load event) {
        if (Loader.isModLoaded("tinkeredhegemony")) {
            String crudeHatchet = "tinkersurvival:crude_hatchet";
            ItemStack crudeHatchetStack = new ItemStack(Item.getByNameOrId(crudeHatchet));
            ResourceLocation hatchetRecipe = RecipeHelper.getSafeNameForRecipe(crudeHatchetStack);

            String crudeSaw = "tinkersurvival:crude_saw";
            ItemStack crudeSawStack = new ItemStack(Item.getByNameOrId(crudeSaw));
            ResourceLocation sawRecipe = RecipeHelper.getSafeNameForRecipe(crudeSawStack);

            String msg = " has been removed by Tinkered Hegemony, please re-enable in the configuration.";

            if (!CraftingManager.REGISTRY.containsKey(hatchetRecipe)) {
                TinkerSurvival.logger.error(crudeHatchet + msg);
            }

            if (!CraftingManager.REGISTRY.containsKey(sawRecipe)) {
                TinkerSurvival.logger.error(crudeSaw + msg);
            }
        }
    }
    */

}
