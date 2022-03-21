package tinkersurvival.tools.tool;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.ForgeHooks;

import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class Saw extends ModifiableItem {

    public Saw(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        ItemStack container = stack.copy();
        ToolStack tool = ToolStack.from(container);
        Player player = ForgeHooks.getCraftingPlayer();

        if (tool.isBroken()) {
            // Don't do that!
            player.hurt(DamageSource.GENERIC, 0.5f);
        }
        else if (!ToolDamageUtil.damage(tool, 1, null, container)) {
            return container;
        }

        player.addItem(container);

        return ItemStack.EMPTY;
    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return true;
    }


}
