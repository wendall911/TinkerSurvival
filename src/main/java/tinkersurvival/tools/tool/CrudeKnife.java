package tinkersurvival.tools.tool;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.TagManager;

public class CrudeKnife extends SwordItem {

    public CrudeKnife(Tier tier, int damage, float speed, Item.Properties tabGroup) {
        super(tier, damage, speed, tabGroup);
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        ItemStack container = stack.copy();
        
        if (!container.hurt(1, new Random(), null)) {
            return container;
        }
        else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack knife, Level level, BlockState state, BlockPos pos, LivingEntity player) {
        float destroySpeed = state.getDestroySpeed(level, pos);

        if (destroySpeed != 0.0F) {
            doDamage(knife, player);
        }
        else if (TagManager.Blocks.FIBER_PLANTS.contains(state.getBlock())) {
            if (level.random.nextFloat() < 0.2) {
                doDamage(knife, player);
            }
        }

        return true;
    }

    private void doDamage(ItemStack knife, LivingEntity player) {
        knife.hurtAndBreak(1, player, (item) -> {
            item.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
    }

}

