package tinkersurvival.data.loot;

import java.util.Objects;
import java.util.stream.Collectors;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import net.minecraftforge.registries.ForgeRegistries;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.add(TinkerSurvivalWorld.ANDESITE_LOOSE_ROCK, ModBlockLootTables::createLooseRockDrops);
        this.add(TinkerSurvivalWorld.DIORITE_LOOSE_ROCK, ModBlockLootTables::createLooseRockDrops);
        this.add(TinkerSurvivalWorld.GRANITE_LOOSE_ROCK, ModBlockLootTables::createLooseRockDrops);
        this.add(TinkerSurvivalWorld.STONE_LOOSE_ROCK, ModBlockLootTables::createLooseRockDrops);
        this.add(TinkerSurvivalWorld.SANDSTONE_LOOSE_ROCK, ModBlockLootTables::createLooseRockDrops);
        this.add(TinkerSurvivalWorld.RED_SANDSTONE_LOOSE_ROCK, ModBlockLootTables::createLooseRockDrops);
        this.add(TinkerSurvivalWorld.ROCK_STONE_BLOCK, ModBlockLootTables::createLooseRockDrops);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> TinkerSurvival.MODID.equals(Objects.requireNonNull(block.getRegistryName()).getNamespace()))
                .collect(Collectors.toSet());
    }

    private static LootTable.Builder createLooseRockDrops(Block block) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(TinkerSurvivalWorld.ROCK_STONE)));
        /*
         * Bonus Flint disabled
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.FLINT).when(
                        LootItemRandomChanceCondition.randomChance(ConfigHandler.Server.flintFromLooseRocksChance())
                    )));
        */
    }

}
