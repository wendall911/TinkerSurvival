package tinkersurvival.recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

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

    private static void initBowlRecipes() {
        registerShaped(
            new ItemStack(TinkerSurvivalWorld.plantPaste),
            "I",
            "T",
            'I',
            new ItemStack(TinkerSurvivalWorld.grassFiber),
            'T',
            new ItemStack(Items.BOWL)
        );
    }

    private static void initKnifeRecipes() {
        String knife = "crudeKnife";

        for (int i = 1; i < 3; i++) {
            if (i == 2) {
                knife = "ticKnife";

                addKnifeRecipe(
                    new ItemStack(Blocks.WOOL),
                    new ItemStack(Items.STRING, 4),
                    knife
                );
            }

            addKnifeRecipe(
                new ItemStack(TinkerSurvivalWorld.rockStone),
                new ItemStack(TinkerSurvivalWorld.flintShard, i),
                knife
            );

            addKnifeRecipe(
                new ItemStack(Items.FLINT),
                new ItemStack(TinkerSurvivalWorld.flintShard, i * 2),
                knife
            );

            addKnifeRecipe(
                new ItemStack(Blocks.SAPLING),
                new ItemStack(Items.STICK, i),
                knife
            );
        };
    }

    private static void addKnifeRecipe(ItemStack input, ItemStack output, String tool) {
        registerShaped(output, "T", "I", 'I', input, 'T', tool);
    }

    private static void addStickPlankRecipe(ItemStack output, ItemStack input, String tool) {
        registerShaped(output, "T", "P", 'P', input, 'T', tool);
    }

    private static void addLogRecipe(ItemStack output, ItemStack input, String tool) {
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
	    Map<String, String> woodOreMap = new HashMap<>();
        int WILDCARD = OreDictionary.WILDCARD_VALUE;
        String[] woodItems = {
            "logWood",
            "plankWood",
            "stickWood",
            "stickTreatedWood",
            "plankTreatedWood"
        };

        for (String type: woodItems) {
            for (ItemStack stack : OreDictionary.getOres(type)) {
                if (!stack.isEmpty()) {
                    String itemName = stack.getItem().getRegistryName().toString();
                    int itemMeta = stack.getMetadata();
                    woodOreMap.put(itemName + ":" + itemMeta, type);
                }
            }
        }

        for (IRecipe recipe : CraftingManager.REGISTRY) {
            ItemStack output = recipe.getRecipeOutput();

            if (!output.isEmpty()) {
                String outputRegName = output.getItem().getRegistryName().toString();
                int outputMeta = output.getMetadata();
                String outputName = outputRegName + ":" + WILDCARD;

                if (woodOreMap.get(outputName) == null) {
                    outputName = outputRegName + ":" + outputMeta;
                }

                if (woodOreMap.get(outputName) != null) {
                    String outputType = woodOreMap.get(outputName);
                    String msg = "Replaced recipe for: " + outputName;

                    if (recipe.getIngredients().size() > 0
                            && recipe.getIngredients().get(0).getMatchingStacks().length > 0) {
                        ItemStack input = recipe.getIngredients().get(0).getMatchingStacks()[0];
                        String inputRegName = input.getItem().getRegistryName().toString();
                        int inputMeta = input.getMetadata();
                        String inputName = inputRegName + ":" + WILDCARD;

                        if (woodOreMap.get(inputName) == null) {
                            inputName = inputRegName + ":" + inputMeta;
                        }
                        if (woodOreMap.get(inputName) != null) {
                            String inputType = woodOreMap.get(inputName);

                            input.setCount(1);
                            output.setCount(2);

                            if (outputType.equals("plankWood")) {
                                addLogRecipe(output, input, "crudeSaw");

                                output.setCount(4);
                                addLogRecipe(output, input, "ticSaw");

                                RecipeHelper.addFakeRecipe(recipe);
                                TinkerSurvival.logger.info(msg);
                            }
                            else if (inputType.contains("plank")
                                    && outputType.contains("stick")) {

                                addStickPlankRecipe(output, input, "crudeSaw");
                                addStickPlankRecipe(output, input, "ticSaw");

                                RecipeHelper.addFakeRecipe(recipe);
                                TinkerSurvival.logger.info(msg);
                            }
                        }
                    }
                }
            }
        }

        initKnifeRecipes();
        initSmeltingRecipes();
        initBowlRecipes();
    }
}
