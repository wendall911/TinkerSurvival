package tinkersurvival.world.item.tools;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class CrudeSaw extends AxeItem {

    public String name;

    public CrudeSaw(String name, Tier tier, int damage, float speed, Item.Properties tabGroup) {
        super(tier, damage, speed, tabGroup);

        this.name = name;
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        ItemStack container = stack.copy();
        
        if (this.name == "crude_saw_handle") {
            return ItemStack.EMPTY;
        }
        else if (!container.hurt(1, new Random(), null)) {
            return container;
        }
        else {
            return new ItemStack(TinkerSurvivalWorld.CRUDE_SAW_HANDLE.get());
        }
    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return true;
    }

}
