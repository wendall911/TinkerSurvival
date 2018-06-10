package tinkersurvival.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static void initKnifeRecipes(List<ItemStack> saplings) {
        String[] knives = {
            "crudeKnife",
            "ticKnife"
        };
        int count = 1;

        for (String knife: knives) {
            if (knife != "crudeKnife") {
                addKnifeRecipe(
                    new ItemStack(Blocks.WOOL),
                    new ItemStack(Items.STRING, 4),
                    knife
                );
                count = 2;
            }

            addKnifeRecipe(
                new ItemStack(TinkerSurvivalWorld.rockStone),
                new ItemStack(TinkerSurvivalWorld.flintShard, count),
                knife
            );

            addKnifeRecipe(
                new ItemStack(Items.FLINT),
                new ItemStack(TinkerSurvivalWorld.flintShard, count * 2),
                knife
            );

            for (ItemStack sapling: saplings) {
                addKnifeRecipe(
                    sapling,
                    new ItemStack(Items.STICK, count),
                    knife
                );
            }
        };
    }

    private static void addKnifeRecipe(ItemStack input, ItemStack output, String tool) {
        registerShaped(output, "T", "I", 'I', input, 'T', tool);
    }

    private static void registerShaped(ItemStack output, Object... inputs) {
        RecipeHelper.addShapedOreRecipe(output, inputs);
    }

    private static ItemStack getSafeItem(String name, int count, int meta) {
        Item item = Item.getByNameOrId(name);
        return item == null ? ItemStack.EMPTY : new ItemStack(item, count, meta);
    }

    private static HashMap getRecipeConfig(ItemStack output, ItemStack input, String tool) {
        HashMap<String, Object> config = new HashMap<String, Object>();

        config.put("output", output);
        config.put("input", input);
        config.put("tool", tool);

        return config;
    }

    public static void updateRecipes() {
	    Map<String, String> woodOreMap = new HashMap<>();
        int WILDCARD = OreDictionary.WILDCARD_VALUE;
        List<HashMap> woodOreRecipes = new ArrayList<HashMap>();
        Map<String, Integer> hasPlankWood = new HashMap<>();
        Map<String, Boolean> hasStickWood = new HashMap<>();
        List<String> plankOreRecipes = new ArrayList<String>();
        List<ItemStack> saplings = new ArrayList<ItemStack>();

        String[] woodItems = {
            "treeSapling",
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

                    if (type.equals("plankWood")) {
                        hasPlankWood.put(itemName, itemMeta);
                    }
                    /*
                     * This probably has some mods it could conceivably not work for.
                     * Problem is that some mods register stuff as stickWood
                     * that doesn't require planks as the base. So we hope that
                     * whatever mod decided to have "stick" in the name of their sticks.
                     */
                    if (type.equals("stickWood") && itemName.contains("stick")) {
                        hasStickWood.put(itemName.split(":")[0], true);
                    }
                    if (type.equals("treeSapling")) {
                        saplings.add(getSafeItem(itemName, 1, itemMeta));
                    }
                    else {
                        woodOreMap.put(itemName + ":" + itemMeta, type);
                    }
                }
            }
        }

        /*
         * If a mod provides stick recipes, then we won't deal with it in the
         * wildcard recipe replacement.
         */
        for (String plank: hasPlankWood.keySet()) {
            String mod = plank.split(":")[0];
            if (mod.equals("minecraft") || hasStickWood.get(mod) == null) {
                plankOreRecipes.add(plank);
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

                    if (recipe.getIngredients().size() > 0
                            && recipe.getIngredients().get(0).getMatchingStacks().length > 0) {
                        ItemStack input = recipe.getIngredients().get(0).getMatchingStacks()[0];
                        String inputRegName = input.getItem().getRegistryName().toString();
                        int inputMeta = input.getMetadata();
                        String inputName = inputRegName + ":" + WILDCARD;

                        if (woodOreMap.get(inputName) == null) {
                            inputName = inputRegName + ":" + inputMeta;
                        }

                        if (woodOreMap.get(inputName) != null
                                && (outputType.equals("plankWood") || outputType.contains("stick"))) {
                            String inputType = woodOreMap.get(inputName);
                            String msg = "Replaced recipe for: " + recipe.getRegistryName();
                            ItemStack inputItem = getSafeItem(inputRegName, 1, inputMeta);
                            ItemStack twoOutputItems = getSafeItem(outputRegName, 2, outputMeta);
                            ItemStack fourOutputItems = getSafeItem(outputRegName, 4, outputMeta);

                            if (outputType.equals("plankWood")) {
                                woodOreRecipes.add(getRecipeConfig(twoOutputItems, inputItem, "crudeSaw"));
                                woodOreRecipes.add(getRecipeConfig(fourOutputItems, inputItem, "ticSaw"));
                                RecipeHelper.addFakeRecipe(recipe);
                                TinkerSurvival.logger.info(msg);
                            }
                            else if (inputType.contains("plank")
                                    && outputType.contains("stick")) {
                                // Catch wildcard recipe for plankWood -> stickWood
                                if (outputRegName.equals("minecraft:stick")
                                        && inputRegName.equals("minecraft:planks")) {
                                    for (String plank: plankOreRecipes) {
                                        woodOreRecipes.add(getRecipeConfig(
                                            twoOutputItems,
                                            getSafeItem(plank, 1, WILDCARD),
                                            "crudeSaw"
                                        ));
                                        woodOreRecipes.add(getRecipeConfig(
                                            twoOutputItems,
                                            getSafeItem(plank, 1, WILDCARD),
                                            "ticSaw"
                                        ));
                                    }
                                }
                                else {
                                    woodOreRecipes.add(getRecipeConfig(twoOutputItems, inputItem, "crudeSaw"));
                                    woodOreRecipes.add(getRecipeConfig(twoOutputItems, inputItem, "ticSaw"));
                                }
                                RecipeHelper.addFakeRecipe(recipe);
                                TinkerSurvival.logger.info(msg);
                            }
                        }
                    }
                }
            }
        }

        woodOreRecipes.forEach(config -> {
            registerShaped(
                (ItemStack) config.get("output"),
                "T",
                "P",
                'P',
                (ItemStack) config.get("input"),
                'T',
                (String) config.get("tool")
            );
        });

        initKnifeRecipes(saplings);
        initSmeltingRecipes();
        initBowlRecipes();
    }
}
