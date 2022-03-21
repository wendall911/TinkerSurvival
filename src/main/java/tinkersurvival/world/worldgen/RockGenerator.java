package tinkersurvival.world.worldgen;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.WorldGenLevel;

import net.minecraftforge.common.util.Lazy;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

public class RockGenerator extends Feature<NoneFeatureConfiguration> {

    private static final Lazy<Map<Block, Supplier<? extends Block>>> looseRockSupplier = Lazy.of(() -> new ImmutableMap.Builder<Block, Supplier<? extends Block>>()
        .put(Blocks.STONE, TinkerSurvivalWorld.stoneLooseRock)
        .put(Blocks.ANDESITE, TinkerSurvivalWorld.andesiteLooseRock)
        .put(Blocks.DIORITE, TinkerSurvivalWorld.dioriteLooseRock)
        .put(Blocks.GRANITE, TinkerSurvivalWorld.graniteLooseRock)
        .put(Blocks.SANDSTONE, TinkerSurvivalWorld.sandstoneLooseRock)
        .put(Blocks.RED_SANDSTONE, TinkerSurvivalWorld.redSandstoneLooseRock)
        .put(Blocks.TERRACOTTA, TinkerSurvivalWorld.redSandstoneLooseRock)
        .put(Blocks.SAND, TinkerSurvivalWorld.sandstoneLooseRock)
        .put(Blocks.RED_SAND, TinkerSurvivalWorld.redSandstoneLooseRock)
        .build()
    );

    public RockGenerator() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();

        final BlockState stateAt = level.getBlockState(pos);
        final BlockState stateDown = level.getBlockState(pos.below());

        if (stateAt.isAir() && TagManager.Blocks.looseRockPlaceableOn.contains(stateDown.getBlock())) {
            for (int y = 1; y <= 8; y++) {
                final BlockPos stonePos = pos.below(y);
                final BlockState stoneState = level.getBlockState(stonePos);
                if (looseRockSupplier.get().containsKey(stoneState.getBlock())) {
                    final Block looseRockBlock = looseRockSupplier.get().get(stoneState.getBlock()).get();

                    level.setBlock(pos, looseRockBlock.defaultBlockState(), 3);
                    break;
                }
            }
        }

        return true;
    }

}
