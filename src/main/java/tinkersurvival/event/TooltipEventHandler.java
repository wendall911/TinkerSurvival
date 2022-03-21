package tinkersurvival.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class TooltipEventHandler {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Component message;

        if (!ItemUse.isWhitelistItem(stack)) {
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

                message = (new TranslatableComponent(tooltip)).withStyle(ChatFormatting.DARK_RED);

                event.getToolTip().add(message);
            }
        }
    }

}
