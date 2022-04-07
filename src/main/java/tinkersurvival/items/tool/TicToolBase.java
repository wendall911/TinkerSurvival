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

import tinkersurvival.TinkerSurvival;
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
        Inventory inventory = player.getInventory();

        // Don't allow autocrafters, as we have no way to invalidate recipe for broken tool
        if (player == null) {
            return ItemStack.EMPTY;
        }

        if (container.getTag().getBoolean("remove")) {
            return ItemStack.EMPTY;
        }

        /*
         * This actually works correctly if not shift-clicking with repair kits.
         * The TCon implementation of Container is badly broken, as they override setItem and
         * do the wrong thing for removeItem. I'm not sure what the logic is of having these
         * be radically different from vanilla, but it causes some serious issues.
         *
         * This creates a bug where if the player has another saw/knife, depending on the
         * recipe, it will destroy the item in the crafting grid. Until TCon fixes their
         * crafting station recipe, this is going to have to be the case :(
         *
         * I will try and report this, but I'm not hopeful, as the attitude is
         * pretty dismissive in the issue tracker from other modders.
         *
         * I would just submit a PR, but then again, I'm not l337, so whatever ...
         *
         * I'm specifically talking about being dismissive when people report
         * bugs about duping items with any mod that deals with inventory. This has been
         * going on for years, and it is because specifically: getItem, setItem
         * and removeItem are just flat broken or disabled. These should 100% work like
         * vanilla, and the fact they don't is just perplexing, as it will
         * always result in duplication bugs for mods that deal with vanilla
         * crafting inventory correctly.
         *
         */

        if (inventory.contains(container)) {
            boolean removeItem = false;

            for (int i = 0; i < inventory.getContainerSize(); ++i) {
                if (inventory.getItem(i).sameItem(container)) {
                    if (container.getOrCreateTag().getFloat("crafty") == inventory.getItem(i).getOrCreateTag().getFloat("crafty")) {
                        if (inventory.getItem(i).getTag().getFloat("crafty") > 0) {
                            removeItem = true;
                        }
                    }
                }
            }

            if (removeItem) {
                return ItemStack.EMPTY;
            }
        }

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
        ItemStack stack = container.copy();

        ToolDamageUtil.directDamage(tool, 1, null, container);

        /*
         * Setting a random nbt property every time the tool is used to craft.
         * This is a workaround for the broken behavior of the TCon crafting station.
         * 
         */
        container.getOrCreateTag().putFloat("crafty", TinkerSurvival.RANDOM.nextFloat());

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
