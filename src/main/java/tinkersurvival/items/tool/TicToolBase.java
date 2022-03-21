package tinkersurvival.items.tool;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.ForgeHooks;

import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import tinkersurvival.util.Chat;

public class TicToolBase extends ModifiableItem {

    public TicToolBase(Properties properties, ToolDefinition toolDefinition) {
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

            Chat.sendMessage(player, Chat.WARNING);
        }

        return returnOrDropTool(tool, container, player);

    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return true;
    }

    public ItemStack returnOrDropTool(ToolStack tool, ItemStack container, Player player) {
        Inventory inventory = player.getInventory();
        ItemStack stack = container.copy();

        ToolDamageUtil.directDamage(tool, 1, null, container);

        if (!tool.isBroken()) {
            return container;
        }
        else if (!player.addItem(container)) {
            // Drop broken tool if no space
            NonNullList<ItemStack> dropStack = NonNullList.withSize(1, container);

            Containers.dropContents(player.getLevel(), new BlockPos(player.getEyePosition()), dropStack);
        }

        Chat.sendMessage(player, Chat.TOOL_BROKE, stack, true);

        return ItemStack.EMPTY;
    }

}
