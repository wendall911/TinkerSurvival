package tinkersurvival.integrations.patchouli;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import chargedcharms.client.integration.CharmChargingRecipeMaker;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import vazkii.patchouli.client.book.BookContentsBuilder;
import vazkii.patchouli.client.book.BookEntry;
import vazkii.patchouli.client.book.ClientBookRegistry;
import vazkii.patchouli.client.book.page.PageCrafting;

import tinkersurvival.TinkerSurvival;

public class ChargedCharmsCustomCrafting extends PageCrafting {

    private static List<CraftingRecipe> recipes;

    public static void init() {
        ClientBookRegistry registry = ClientBookRegistry.INSTANCE;

        registry.pageTypes.put(new ResourceLocation(TinkerSurvival.MODID, "chargedcharms"), ChargedCharmsCustomCrafting.class);

        recipes = CharmChargingRecipeMaker.createRecipes("patchouli");
    }

    @Override
    protected Recipe<?> loadRecipe(Level level, BookContentsBuilder builder, BookEntry entry, ResourceLocation loc) {
        AtomicReference<CraftingRecipe> craftingRecipe = new AtomicReference<>();

        if (loc != null) {
            recipes.forEach(recipe -> {
                if (recipe.getId().getPath().split("\\.")[3].equals(loc.getPath())) {
                    craftingRecipe.set(recipe);
                }
            });

            if (craftingRecipe.get() != null) {
                return craftingRecipe.get();
            }
        }

        return craftingRecipe.get();
    }

}
