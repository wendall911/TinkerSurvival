package tinkersurvival.recipe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javafx.util.Pair;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import net.minecraftforge.oredict.OreDictionary;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.config.Config;
import tinkersurvival.util.ItemUse;
import tinkersurvival.world.TinkerSurvivalWorld;
import tinkersurvival.util.functionalInterfaces.*;

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
        String[] knives = {
                "crudeKnife",
                "ticKnife"
        };
        int count = 1;

        for (String knife : knives) {
            if (!knife.equals("crudeKnife")) {
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

            List<ItemStack> saplingRecipes = getRecipes("treeSapling", (n, m) -> getSafeItem(n, 1, m));
            for (ItemStack sapling : saplingRecipes) {
                addKnifeRecipe(
                        sapling,
                        new ItemStack(Items.STICK, count),
                        knife
                );
            }
        }
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
        HashMap<String, Object> config = new HashMap<>();

        config.put("output", output);
        config.put("input", input);
        config.put("tool", tool);

        return config;
    }

    private static void processRecipes(String type, Acceptor2<String, Integer> acceptor) {
        getRecipeItems(type).forEach(p -> acceptor.accept(p.getKey(), p.getValue()));
    }


    private static List<ItemStack> getRecipes(String type, Function2<String, Integer, ItemStack> transformer) {
        return getRecipeItems(type).
                map(p -> transformer.invoke(p.getKey(), p.getValue()))
                .collect(Collectors.toList());
    }


    private static Stream<Pair<String, Integer>> getRecipeItems(String type) {
        Iterable<ItemStack> iterable = () -> OreDictionary.getOres(type).iterator();

        return StreamSupport.stream(iterable.spliterator(), true)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String itemName = s.getItem().getRegistryName().toString();
                    int itemMeta = s.getMetadata();
                    return new Pair<>(itemName, itemMeta);
                });
    }


    private static void replaceWoodRecipes() {
        Map<String, String> woodOreMap = new HashMap<>();
        int WILDCARD = OreDictionary.WILDCARD_VALUE;
        List<HashMap> woodOreRecipes = new ArrayList<>();
        Map<String, Boolean> hasStickWood = new HashMap<>();
        List<String> plankOreRecipes = new ArrayList<>();

        /*
         * This probably has some mods it could conceivably not work for.
         * Problem is that some mods register stuff as stickWood
         * that doesn't require planks as the base. So we hope that
         * whatever mod decided to have "stick" in the name of their sticks.
         */
        processRecipes("stickWood", (n, m) -> {
            if (n.contains("stick")) {
                hasStickWood.put(n.split(":")[0], true);
            }
        });

        for (String type : new String[]{
                "logWood",
                "stickTreatedWood",
                "plankTreatedWood"
        }) {
            processRecipes("logWood", (n, m) -> woodOreMap.put(n + ":" + m, type));
        }

        /*
         * If a mod provides stick recipes, then we won't deal with it in the
         * wildcard recipe replacement.
         */
        processRecipes("plankWood", (n, m) -> {
            String mod = n.split(":")[0];
            if (mod.equals("minecraft") || hasStickWood.get(mod) == null) {
                plankOreRecipes.add(n);
            }
        });

//TODO: split and refactor to keep armor removal and add saw recipes with better output
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
                            } else if (inputType.contains("plank")
                                    && outputType.contains("stick")) {
                                // Catch wildcard recipe for plankWood -> stickWood
                                if (outputRegName.equals("minecraft:stick")
                                        && inputRegName.equals("minecraft:planks")) {
                                    for (String plank : plankOreRecipes) {
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
                                } else {
                                    woodOreRecipes.add(getRecipeConfig(twoOutputItems, inputItem, "crudeSaw"));
                                    woodOreRecipes.add(getRecipeConfig(twoOutputItems, inputItem, "ticSaw"));
                                }
                                RecipeHelper.addFakeRecipe(recipe);
                                TinkerSurvival.logger.info(msg);
                            }
                        }
                    }
                } else if (ItemUse.isArmor(output) && !ItemUse.isWhitelistArmor(output)) {
                    RecipeHelper.addFakeRecipe(recipe);
                    TinkerSurvival.logger.info("Removed armor recipe for: " + recipe.getRegistryName());
                }
            }
        }

        woodOreRecipes.forEach(config -> {
            registerShaped(
                    (ItemStack) config.get("output"),
                    "T",
                    "P",
                    'P',
                    config.get("input"),
                    'T',
                    config.get("tool")
            );
        });
    }

    public static void updateRecipes() {
        if (Config.Features.FORCE_SAW_USAGE) {
            replaceWoodRecipes();
        }

        initKnifeRecipes();
        initSmeltingRecipes();
        initBowlRecipes();
    }
}
