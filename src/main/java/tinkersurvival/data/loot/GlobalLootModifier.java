package tinkersurvival.data.loot;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;

import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import slimeknights.tconstruct.tools.TinkerTools;

import tinkersurvival.common.loot.LootItemBlockIsTagCondition;
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
        addPlantFiberDrops(TagManager.Blocks.FIBER_PLANTS, "fiber_plants");
        addStickDrops(BlockTags.LEAVES, "leaves");

        addToolLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_toolsmith_crude_knife",
            TinkerSurvivalItems.CRUDE_KNIFE
        );
        addToolLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_toolsmith_crude_hatchet",
            TinkerSurvivalItems.CRUDE_HATCHET
        );
        addToolLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_toolsmith_crude_saw",
            TinkerSurvivalItems.CRUDE_SAW
        );
        addRareLoot(
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            "village_fisher_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP
        );
        addRareLoot(
            BuiltInLootTables.BURIED_TREASURE,
            "buried_treasure_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP
        );
        addRareLoot(
            BuiltInLootTables.SHIPWRECK_TREASURE,
            "shipwreck_treasure_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP
        );
        addRareLoot(
            BuiltInLootTables.FISHING_TREASURE,
            "fishing_treasure_wooden_cup",
            TinkerSurvivalItems.WOODEN_CUP
        );

    }

    public void addPlantFiberDrops(TagKey<Block> tag, String name) {
        this.add(
            "plant_fiber_from_" + name,
            TinkerSurvivalLootTables.PLANT_FIBER_DROPS.get(),
            new TinkerSurvivalLootTables.LootTableModifier(
                createKnifeChanceCondition(0.16F, tag),
                new ItemStack(TinkerSurvivalItems.PLANT_FIBER)
            )
        );
    }

    public void addStickDrops(TagKey<Block> tag, String name) {
        this.add(
            "stick_drops_from_" + name,
            TinkerSurvivalLootTables.STICK_DROPS.get(),
            new TinkerSurvivalLootTables.LootTableModifier(
                createKnifeChanceCondition(0.16F, tag),
                new ItemStack(Items.STICK)
            )
        );

        this.add(
                "extra_stick_drops_from_" + name,
                TinkerSurvivalLootTables.STICK_DROPS.get(),
                new TinkerSurvivalLootTables.LootTableModifier(
                    createExtraStickDropConditions(0.16F, tag),
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

    public static LootItemCondition[] createKnifeChanceCondition(float chance, TagKey<Block> tag) {
        return new LootItemCondition[] {
            LootItemRandomChanceCondition.randomChance(chance).build(),
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(TagManager.Items.KNIFE_TOOLS)).build(),
            LootItemBlockIsTagCondition.isTag(tag)
        };
    }

    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();

    /**
     * Returns a list of Conditions where a player must have broken the block without silk touch and/or shears like items, with the specified chance
     * Provided by Insane96 <delvillano.alberto@gmail.com>
     */
    public static LootItemCondition[] createExtraStickDropConditions(float chance, TagKey<Block> tag) {
        return new LootItemCondition[] {
            LootItemRandomChanceCondition.randomChance(chance).build(),
            LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().of(EntityType.PLAYER)).build(),
            LootItemBlockIsTagCondition.isTag(tag),
            HAS_NO_SILK_TOUCH.build(),
            MatchTool.toolMatches(ItemPredicate.Builder.item().of(TagManager.Items.SHEAR_TOOLS).of(TinkerTools.scythe.get())).invert().build()
        };
    }

    public static LootItemCondition[] createResourceChanceCondition(float chance, ResourceLocation loc) {
        return new LootItemCondition[] {
            LootItemRandomChanceCondition.randomChance(chance).build(),
            LootTableIdCondition.builder(loc).build()
        };
    }

}
