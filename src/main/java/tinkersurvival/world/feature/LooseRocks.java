package tinkersurvival.world.feature;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
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

import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

public class LooseRocks extends Feature<NoneFeatureConfiguration> {

    private static final Lazy<Map<Block, Supplier<? extends Block>>> LOOSE_ROCK_SUPPLIER = Lazy.of(() -> new ImmutableMap.Builder<Block, Supplier<? extends Block>>()
        .put(Blocks.STONE, TinkerSurvivalWorld.STONE_LOOSE_ROCK)
        .put(Blocks.ANDESITE, TinkerSurvivalWorld.ANDESITE_LOOSE_ROCK)
        .put(Blocks.DIORITE, TinkerSurvivalWorld.DIORITE_LOOSE_ROCK)
        .put(Blocks.GRANITE, TinkerSurvivalWorld.GRANITE_LOOSE_ROCK)
        .put(Blocks.SANDSTONE, TinkerSurvivalWorld.SANDSTONE_LOOSE_ROCK)
        .put(Blocks.RED_SANDSTONE, TinkerSurvivalWorld.RED_SANDSTONE_LOOSE_ROCK)
        .put(Blocks.TERRACOTTA, TinkerSurvivalWorld.RED_SANDSTONE_LOOSE_ROCK)
        .put(Blocks.SAND, TinkerSurvivalWorld.SANDSTONE_LOOSE_ROCK)
        .put(Blocks.RED_SAND, TinkerSurvivalWorld.RED_SANDSTONE_LOOSE_ROCK)
        .build()
    );

    public LooseRocks() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        final WorldGenLevel level = context.level();
        final BlockPos pos = context.origin();

        final BlockState stateAt = level.getBlockState(pos);
        final BlockState stateDown = level.getBlockState(pos.below());

        if (stateAt.isAir() && TagManager.Blocks.LOOSE_ROCK_PLACEABLE_ON.contains(stateDown.getBlock())) {
            for (int y = 1; y <= 8; y++) {
                final BlockPos stonePos = pos.below(y);
                final BlockState stoneState = level.getBlockState(stonePos);
                if (LOOSE_ROCK_SUPPLIER.get().containsKey(stoneState.getBlock())) {
                    final Block looseRockBlock = LOOSE_ROCK_SUPPLIER.get().get(stoneState.getBlock()).get();

                    level.setBlock(pos, looseRockBlock.defaultBlockState(), 3);
                    break;
                }
            }
        }

        return true;
    }

}
