package tinkersurvival.jei;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;

import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

import java.util.Objects;

@SuppressWarnings("unused")
@JeiPlugin
public class TinkerSurvivalJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(TinkerSurvival.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        addIngredientInfo(registry, TinkerSurvivalWorld.ROCK_STONE);
        addIngredientInfo(registry, TinkerSurvivalItems.PLANT_FIBER);
        addIngredientInfo(registry, TinkerSurvivalItems.FLINT_SHARD);
        addIngredientInfo(registry, Items.STICK);

    }

    private void addIngredientInfo(IRecipeRegistration registry, Item item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();

        registry.addIngredientInfo(
            new ItemStack(item),
            VanillaTypes.ITEM_STACK,
            new TranslatableComponent("jei." + TinkerSurvival.MODID + ".description." + name)
        );
    }

}
