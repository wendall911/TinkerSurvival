package tinkersurvival.data.loot;

import java.util.stream.Collectors;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.registries.ForgeRegistries;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class ModBlockLootTables extends BlockLoot {
    
    @Override
    protected void addTables() {
        //dropSelf(ModBlocks.WHITE_BRICKS.get());
        dropOther(TinkerSurvivalWorld.andesiteLooseRock.get(), TinkerSurvivalWorld.rockStone.get());
        dropOther(TinkerSurvivalWorld.dioriteLooseRock.get(), TinkerSurvivalWorld.rockStone.get());
        dropOther(TinkerSurvivalWorld.graniteLooseRock.get(), TinkerSurvivalWorld.rockStone.get());
        dropOther(TinkerSurvivalWorld.stoneLooseRock.get(), TinkerSurvivalWorld.rockStone.get());
        dropOther(TinkerSurvivalWorld.sandstoneLooseRock.get(), TinkerSurvivalWorld.rockStone.get());
        dropOther(TinkerSurvivalWorld.redSandstoneLooseRock.get(), TinkerSurvivalWorld.rockStone.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> TinkerSurvival.MODID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
