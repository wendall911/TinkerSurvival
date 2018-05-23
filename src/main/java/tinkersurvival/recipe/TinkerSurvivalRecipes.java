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
        ItemStack sticks = new ItemStack(Items.STICK, 2);
        addStickPlankRecipe(sticks, "crudeSaw");
        addStickPlankRecipe(sticks, "ticSaw");
        if (ie) {
            addTreatedStickPlankRecipe("crudeSaw");
            addTreatedStickPlankRecipe("ticSaw");
        }
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
                "stoneRock",
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

    private static void addKnifeRecipe(String input, ItemStack output, String tool) {
        registerShaped(output, "T", "I", 'I', input, 'T', tool);
    }

    private static void addStickPlankRecipe(ItemStack output, String tool) {
        registerShaped(output, "T", "P", 'P', "plankWood", 'T', tool);
    }

    private static void addTreatedStickPlankRecipe(String tool) {
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
                String msg = "Replaced recipe for: " + outputName;

                if (outputName.contains("planks")
                        && recipe.getIngredients().size() > 0
                        && recipe.getIngredients().size() < 2) {
                    ItemStack input = recipe.getIngredients().get(0).getMatchingStacks()[0];
                    input.setCount(1);

                    output.setCount(2);
                    addLogRecipe(output, input, "crudeSaw");

                    output.setCount(4);
                    addLogRecipe(output, input, "ticSaw");

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
        initBowlRecipes();
    }
}
