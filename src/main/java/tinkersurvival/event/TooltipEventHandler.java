package tinkersurvival.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tinkersurvival.util.Event;

public class TooltipEventHandler {

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player == null
            || player instanceof FakePlayer
            || player.capabilities.isCreativeMode
                ) {
            return;
        }

        if (Event.isUselessBow(event.getItemStack())) {
            event.getToolTip().add(TextFormatting.DARK_RED + I18n.translateToLocalFormatted("tooltip.uselessBow1"));
        }

        if (Event.isUselessHoe(event.getItemStack())) {
            event.getToolTip().add(TextFormatting.DARK_RED + I18n.translateToLocalFormatted("tooltip.uselessHoe1"));
        }

        if (Event.isUselessSword(event.getItemStack())) {
            event.getToolTip().add(TextFormatting.DARK_RED + I18n.translateToLocalFormatted("tooltip.uselessWeapon1"));
        }
        if (Event.isUselessTool(event.getItemStack())) {
            event.getToolTip().add(TextFormatting.DARK_RED + I18n.translateToLocalFormatted("tooltip.uselessTool2"));
        }
    }

}
