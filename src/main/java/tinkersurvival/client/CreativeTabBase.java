package tinkersurvival.client;

import java.util.function.Supplier;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.util.Lazy;

public class CreativeTabBase extends CreativeModeTab {
    
    private final Lazy<ItemStack> iconStack;

    public CreativeTabBase(String label, Supplier<ItemStack> iconStack) {
        super(label);
        this.iconStack = Lazy.of(iconStack);
    }

    @Override
    public ItemStack makeIcon() {
        return iconStack.get();
    }

}
