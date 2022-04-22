package tinkersurvival.world.item;

import javax.annotation.Nonnull;

import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.sound.Sounds;
import tinkersurvival.TinkerSurvival;

import java.util.Objects;

public class RockStone extends BlockItem {

    public RockStone(Block block, Item.Properties tabGroup) {
        super(block, tabGroup);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockState state = context.getLevel().getBlockState(context.getClickedPos());
        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();

        if (state.getMaterial() == Material.STONE &&
                Objects.requireNonNull(context.getPlayer()).getMainHandItem().getItem() instanceof RockStone) {
            if (level.isClientSide()) {
                Objects.requireNonNull(player).swing(hand);
            }
            else {
                if (!level.isClientSide) {
                    if (level.random.nextFloat() < 0.5) {
                        if (level.random.nextFloat() < ConfigHandler.Server.flintChance()) {
                            NonNullList<ItemStack> dropStack =
                                NonNullList.withSize(1, new ItemStack(TinkerSurvivalItems.FLINT_SHARD, 2));

                            Containers.dropContents(level, Objects.requireNonNull(player).getOnPos(), dropStack);
                        }

                        Objects.requireNonNull(player).getItemInHand(hand).shrink(1);
                    }
                    level.playSound(null, Objects.requireNonNull(player).getOnPos(), Sounds.FLINT_KNAPPING.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }

            return InteractionResult.PASS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return new InteractionResultHolder<>(InteractionResult.FAIL, player.getItemInHand(hand));
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        return InteractionResult.FAIL;
    }

}
