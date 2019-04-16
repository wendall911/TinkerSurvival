package tinkersurvival.recipe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    private static void initSawRecipeFor(IRecipe recipe, RecipeInfo input, RecipeInfo output, List<String> plankOreRecipes) {

        final String replacedMsg = "Replaced recipe for: " + recipe.getRegistryName();
        final String addedMsg = "Added Saw recipes for: " + recipe.getRegistryName();

        ItemStack inputItem = getSafeItem(input, 1);
        ItemStack twoOutputItems = getSafeItem(output, 2);
        ItemStack fourOutputItems = getSafeItem(output, 4);

        if (output.getItemType().equals("plankWood")) {
            registerRecipe(twoOutputItems, inputItem, "crudeSaw");
            registerRecipe(fourOutputItems, inputItem, "ticSaw");
            TinkerSurvival.logger.info(addedMsg);

            if (Config.Features.FORCE_SAW_FOR_PLANKS) {
                RecipeHelper.addFakeRecipe(recipe);
                TinkerSurvival.logger.info(replacedMsg);
            }

            return;
        }

        if (input.getItemType().contains("plank") && output.getItemType().contains("stick")) {

            // Catch wildcard recipe for plankWood -> stickWood
            if (input.getRegName().equals("minecraft:planks") && output.getRegName().equals("minecraft:stick")) {

                for (String plank : plankOreRecipes) {
                    registerRecipe(
                            twoOutputItems,
                            getSafeItem(plank, 1, OreDictionary.WILDCARD_VALUE),
                            "crudeSaw"
                    );
                    registerRecipe(
                            twoOutputItems,
                            getSafeItem(plank, 1, OreDictionary.WILDCARD_VALUE),
                            "ticSaw"
                    );
                }

            } else {
                registerRecipe(twoOutputItems, inputItem, "crudeSaw");
                registerRecipe(twoOutputItems, inputItem, "ticSaw");
            }

            TinkerSurvival.logger.info(addedMsg);

            if (Config.Features.FORCE_SAW_FOR_PLANKS) {
                RecipeHelper.addFakeRecipe(recipe);
                TinkerSurvival.logger.info(replacedMsg);
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

    private static ItemStack getSafeItem(RecipeInfo info, int count) {
        return getSafeItem(info.getRegName(), count, info.getMetaData());
    }

    private static void registerRecipe(ItemStack output, ItemStack input, String tool) {
        registerShaped(output, "T", "P", 'P', input, 'T', tool);
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

        return StreamSupport.stream(iterable.spliterator(), false)
                .filter(s -> !s.isEmpty())
                .map(s -> {
                    String itemName = s.getItem().getRegistryName().toString();
                    int itemMeta = s.getMetadata();
                    return new Pair<>(itemName, itemMeta);
                });
    }

    private static void processRegisteredRecipes() {
        Map<String, String> woodOreMap = new HashMap<>();
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

            woodOreMap.put(n + ":" + m, "stickWood");
        });

        for (String type : new String[]{
                "logWood",
                "stickTreatedWood",
                "plankTreatedWood"
        }) {
            processRecipes(type, (n, m) -> woodOreMap.put(n + ":" + m, type));
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
            woodOreMap.put(n + ":" + m, "plankWood");
        });

        for (IRecipe recipe : CraftingManager.REGISTRY) {
            handleRecipe(recipe, woodOreMap, plankOreRecipes);
        }
    }


    private static void handleRecipe(IRecipe recipe, Map<String, String> woodOreMap,
                                     List<String> plankOreRecipes) {

        RecipeInfo output = new RecipeInfo(recipe.getRecipeOutput(), woodOreMap);

        if (output.getItemsStack().isEmpty()) {
            return;
        }

        // if output is not a material
        if (output.getItemType() == null) {

            // make other armor useless
            if (ItemUse.isArmor(output.getItemsStack()) && !ItemUse.isWhitelistArmor(output.getItemsStack())) {
                RecipeHelper.addFakeRecipe(recipe);
                TinkerSurvival.logger.info("Removed armor recipe for: " + recipe.getRegistryName());
            }
            return;
        }

        if (recipe.getIngredients().size() <= 0
                || recipe.getIngredients().get(0).getMatchingStacks().length <= 0) {
            return;
        }

        RecipeInfo input = new RecipeInfo(recipe.getIngredients().get(0).getMatchingStacks()[0], woodOreMap);

        if (input.getItemType() == null
                || !output.getItemType().equals("plankWood") && !output.getItemType().contains("stick")) {
            return;
        }

        if (Config.Features.ENABLE_SAW) {
            initSawRecipeFor(recipe, input, output, plankOreRecipes);
        }
    }


    public static void updateRecipes() {
        // also adds saw recipes
        processRegisteredRecipes();

        initKnifeRecipes();
        initSmeltingRecipes();
        initBowlRecipes();
    }
}
