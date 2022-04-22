package tinkersurvival.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LooseRockBlock extends Block {

    // West, ??, North, fromWest, height, fromNorth
    public static final VoxelShape rockHitbox = box(4, 0, 3, 12, 2, 11);

    public LooseRockBlock() {
        super(Properties.of(Material.CLAY).sound(SoundType.STONE).strength(0.0F).noCollission().noOcclusion());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!state.canSurvive(worldIn, pos) && !worldIn.isClientSide) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState stateUnder = worldIn.getBlockState(pos.below());
        return stateUnder.isFaceSturdy(worldIn, pos.below(), Direction.UP);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return rockHitbox;
    }

    @Override
    public boolean isPossibleToRespawnInThis() {
        return true;
    }

}
