package tinkersurvival.recipe;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.tools.TinkerSurvivalTools;
import tinkersurvival.tools.tool.CrudeKnife;
import tinkersurvival.tools.tool.CrudeSaw;
import tinkersurvival.tools.tool.Knife;
import tinkersurvival.world.TinkerSurvivalWorld;

public class TinkerSurvivalRecipes {

    private static void initSmeltingRecipes() {
        RecipeHelper.addSmelting(new ItemStack(TinkerSurvivalWorld.grassString), new ItemStack(Items.STRING));
    }

    private static void initSawRecipes(boolean ie) {
        TinkerSurvivalTools.listAllSaws().forEach(saw -> {
            addStickPlankRecipe(new ItemStack(Items.STICK, 2), saw);
            if (ie) {
                addTreatedStickPlankRecipe(saw);
            }
        });
    }

    private static void initKnifeRecipes() {
        TinkerSurvivalTools.listAllKnives().forEach(knife -> {
            int shards = 2;

            if (knife.getItem() instanceof Knife) {
                shards = 4;
                addKnifeRecipe(
                    new ItemStack(Blocks.WOOL),
                    new ItemStack(Items.STRING, 4),
                    knife
                );
            }

            TinkerSurvivalWorld.listAllRocks().forEach(rock -> {
                addKnifeRecipe(
                    rock,
                    new ItemStack(TinkerSurvivalWorld.flintShard),
                    knife
                );
            });

            addKnifeRecipe(
                new ItemStack(Items.FLINT),
                new ItemStack(TinkerSurvivalWorld.flintShard, shards),
                knife
            );

            addKnifeRecipe(
                new ItemStack(Blocks.SAPLING),
                new ItemStack(Items.STICK),
                knife
            );
        });
    }

    private static void addKnifeRecipe(ItemStack input, ItemStack output, ItemStack tool) {
        registerShaped(output, "T", "I", 'I', input, 'T', tool);
    }

    private static void addStickPlankRecipe(ItemStack output, ItemStack tool) {
        registerShaped(output, "T", "P", 'P', "plankWood", 'T', tool);
    }

    private static void addTreatedStickPlankRecipe(ItemStack tool) {
        registerShaped(
            getSafeItem("immersiveengineering:material", 2),
            "T",
            "W",
            'W',
            getSafeItem("immersiveengineering:treated_wood"),
            'T',
            tool
        );
    }

    private static void addLogRecipe(ItemStack output, ItemStack input, ItemStack tool) {
        registerShaped(output, "T", "P", 'P', input, 'T', tool);
    }

    private static void registerShaped(ItemStack output, Object... inputs) {
        RecipeHelper.addShapedOreRecipe(output, inputs);
    }

    private static ItemStack getSafeItem(String name) {
        return getSafeItem(name, 0, 1);
    }
    private static ItemStack getSafeItem(String name, int count) {
        return getSafeItem(name, 0, count);
    }
    private static ItemStack getSafeItem(String name, int meta, int count) {
        Item item = Item.getByNameOrId(name);
        return item == null ? ItemStack.EMPTY : new ItemStack(item, count, meta);
    }

    public static void updateRecipes() {
        Set<Item> itemSet = new HashSet<>();
        Set<IRecipe> recipeSet = new HashSet<>();
        boolean ie = Loader.isModLoaded("immersiveengineering");

        for (Item i : ForgeRegistries.ITEMS) {
            String itemName = i.getRegistryName().toString();
            if (itemName.equals("minecraft:stick")
                    || itemName.equals("natura:sticks")
                    || itemName.contains("planks")) {
                itemSet.add(i);
            }
            if (ie) {
                if (itemName.equals("immersiveengineering:material")) {
                    itemSet.add(i);
                }
            }
        }

        for (IRecipe recipe : CraftingManager.REGISTRY) {
            ItemStack output = recipe.getRecipeOutput();

            if (!output.isEmpty() && itemSet.contains(output.getItem())) {
                String outputName = output.getItem().getRegistryName().toString();
                int outputMeta = output.getMetadata();
                String msg = "Replaced recipe for: " + outputName;

                if (outputName.contains("planks")
                        && recipe.getIngredients().size() > 0
                        && recipe.getIngredients().size() < 2) {
                    ItemStack input = recipe.getIngredients().get(0).getMatchingStacks()[0];
                    String inputName = input.getItem().getRegistryName().toString();
                    int inputMeta = input.getMetadata();

                    TinkerSurvivalTools.listAllSaws().forEach(saw -> {
                        int outputCount = 4;

                        if (saw.getItem() instanceof CrudeSaw) {
                            outputCount = 2;
                        }
                        addLogRecipe(getSafeItem(outputName, outputMeta, outputCount), 
                            getSafeItem(inputName, inputMeta, 1),
                            saw
                        );
                    });
                    RecipeHelper.addFakeRecipe(recipe);
                    TinkerSurvival.logger.info(msg);

                }
                else if (outputName.equals("minecraft:stick")
                        || outputName.equals("natura:sticks")
                        || outputName.equals("immersiveengineering:material")) {
                    RecipeHelper.addFakeRecipe(recipe);
                    TinkerSurvival.logger.info("Replaced recipe for: " + outputName);
                }
            }
        }

        initSawRecipes(ie);
        initKnifeRecipes();
        initSmeltingRecipes();
    }
}
