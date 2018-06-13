package tinkersurvival.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import slimeknights.tconstruct.library.utils.TinkerUtil;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;
import tinkersurvival.util.ToolLevelNBT;

public class TooltipEventHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onItemToolTip(ItemTooltipEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player == null
            || player instanceof FakePlayer
            || player.capabilities.isCreativeMode
                ) {
            return;
        }

        ItemStack stack = event.getItemStack();

        if (ItemUse.isWhitelistItem(stack)) {
            NBTTagCompound tag = TinkerUtil.getModifierTag(stack, TinkerSurvival.modToolLeveling.getModifierIdentifier());
            if (!tag.hasNoTags()) {
                ToolLevelNBT data = new ToolLevelNBT(tag);
                if (data.cxp >= 0) {
                    event.getToolTip().add(1, TextFormatting.GOLD + I18n.translateToLocalFormatted("tooltip.cxp"));
                }
            }
        }
        else {
            String type = ItemUse.getToolClass(stack);
            String tooltip = "tooltip.uselessTool2";

            if (type != null) {
                switch (type) {
                    case "bow":
                        tooltip = "tooltip.uselessBow1";
                        break;
                    case "hoe":
                        tooltip = "tooltip.uselessHoe1";
                        break;
                    case "pickaxe":
                        tooltip = "tooltip.uselessTool1";
                        break;
                    case "sword":
                        tooltip = "tooltip.uselessWeapon1";
                        break;
                    default:
                        break;

                }
                event.getToolTip().add(TextFormatting.DARK_RED + I18n.translateToLocalFormatted(tooltip));
            }
        }

    }

}
