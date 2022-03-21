package tinkersurvival.world.item.tools;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import tinkersurvival.TinkerSurvival;

public class CrudeHatchet extends AxeItem {

    public CrudeHatchet(Tier tier, int damage, float speed, Item.Properties tabGroup) {
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

}
