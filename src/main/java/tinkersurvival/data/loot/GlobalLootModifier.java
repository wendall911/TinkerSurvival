package tinkersurvival.data.loot;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.loot.TinkerSurvivalLootTables;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.common.TagManager;

public class GlobalLootModifier extends GlobalLootModifierProvider {

    public GlobalLootModifier(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn, TinkerSurvival.MODID);
    }

    @Override
    public String getName() {
        return "Tinker Survival - Global Loot Modifier";
    }

    @Override
    protected void start() {
        addPlantFiberDrops(Blocks.JUNGLE_LEAVES);
        addPlantFiberDrops(Blocks.OAK_LEAVES);
        addPlantFiberDrops(Blocks.SPRUCE_LEAVES);
        addPlantFiberDrops(Blocks.DARK_OAK_LEAVES);
        addPlantFiberDrops(Blocks.ACACIA_LEAVES);
        addPlantFiberDrops(Blocks.BIRCH_LEAVES);
        addPlantFiberDrops(Blocks.AZALEA_LEAVES);
        addPlantFiberDrops(Blocks.FLOWERING_AZALEA_LEAVES);
        addPlantFiberDrops(Blocks.VINE);
        addPlantFiberDrops(Blocks.FERN);
        addPlantFiberDrops(Blocks.LARGE_FERN);
        addPlantFiberDrops(Blocks.GRASS);
        addPlantFiberDrops(Blocks.TALL_GRASS);

        addStickDrops(Blocks.JUNGLE_LEAVES);
        addStickDrops(Blocks.OAK_LEAVES);
        addStickDrops(Blocks.SPRUCE_LEAVES);
        addStickDrops(Blocks.DARK_OAK_LEAVES);
        addStickDrops(Blocks.ACACIA_LEAVES);
        addStickDrops(Blocks.BIRCH_LEAVES);
        addStickDrops(Blocks.AZALEA_LEAVES);
        addStickDrops(Blocks.FLOWERING_AZALEA_LEAVES);

        addToolLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_toolsmith_crude_knife",
            TinkerSurvivalItems.CRUDE_KNIFE.get()
        );
        addToolLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_toolsmith_crude_hatchet",
            TinkerSurvivalItems.CRUDE_HATCHET.get()
        );
        addToolLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_toolsmith_crude_saw",
            TinkerSurvivalItems.CRUDE_SAW.get()
        );
        addRareLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_fisher_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP.get()
        );
        addRareLoot(
            BuiltInLootTables.BURIED_TREASURE,
            "buried_treasure_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP.get()
        );
        addRareLoot(
            BuiltInLootTables.SHIPWRECK_TREASURE,
            "shipwreck_treasure_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP.get()
        );
        addRareLoot(
            BuiltInLootTables.FISHING_TREASURE,
            "fishing_treasure_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP.get()
        );

    }

    public void addPlantFiberDrops(Block block) {
        String name = block.getRegistryName().getPath().toString();

        this.add(
            "plant_fiber_from_" + name,
            TinkerSurvivalLootTables.PLANT_FIBER_DROPS.get(),
            new TinkerSurvivalLootTables.LootTableModifier(
                createKnifeChanceCondition(0.16F, block),
                new ItemStack(TinkerSurvivalItems.PLANT_FIBER.get())
            )
        );
    }

    public void addStickDrops(Block block) {
        String name = block.getRegistryName().getPath().toString();

        this.add(
            "stick_drops_from_" + name,
            TinkerSurvivalLootTables.STICK_DROPS.get(),
            new TinkerSurvivalLootTables.LootTableModifier(
                createKnifeChanceCondition(0.16F, block),
                new ItemStack(Items.STICK)
            )
        );
    }

    public void addToolLoot(ResourceLocation loc, String name, Item item) {
        this.add(
            "tool_loot_" + name,
            TinkerSurvivalLootTables.TOOL_LOOT.get(),
            new TinkerSurvivalLootTables.LootTableModifier(
                createResourceChanceCondition(0.05F, loc),
                new ItemStack(item)
            )
        );
    }

    public void addRareLoot(ResourceLocation loc, String name, Item item) {
        this.add(
            "rare_loot_" + name,
            TinkerSurvivalLootTables.RARE_LOOT.get(),
            new TinkerSurvivalLootTables.LootTableModifier(
                createResourceChanceCondition(0.01F, loc),
                new ItemStack(item)
            )
        );
    }

    public static LootItemCondition[] createKnifeChanceCondition(float chance, Block block) {
        return new LootItemCondition[] {
            LootItemRandomChanceCondition.randomChance(chance).build(),
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(TagManager.Items.KNIFE_TOOLS)).build(),
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).build()
        };
    }

    public static LootItemCondition[] createResourceChanceCondition(float chance, ResourceLocation loc) {
        return new LootItemCondition[] {
            LootItemRandomChanceCondition.randomChance(chance).build(),
            LootTableIdCondition.builder(loc).build()
        };
    }

}
