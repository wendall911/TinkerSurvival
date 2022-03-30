package tinkersurvival.items.tool.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import vazkii.botania.api.mana.ManaItemHandler;

public class ManaItemModifier extends Modifier {

    private static final int MANA_PER_DAMAGE = 60;

    public int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide
                && holder instanceof Player player
                && tool.getDamage() > 0
                && ManaItemHandler.instance().requestManaExactForTool(stack, player, getManaPerDamage() * 2, true)) {
            tool.setDamage(tool.getDamage() - 1);
        }
    }

}
