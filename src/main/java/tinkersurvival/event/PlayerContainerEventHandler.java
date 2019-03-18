package tinkersurvival.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent.Close;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import slimeknights.tconstruct.library.utils.TinkerUtil;

import slimeknights.toolleveling.TinkerToolLeveling;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.tools.tool.Saw;

public class PlayerContainerEventHandler {

    @SubscribeEvent
    public void onCloseContainer(Close event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player == null
            || player instanceof FakePlayer
            || player.capabilities.isCreativeMode
                ) {
            return;
        }

        NonNullList<ItemStack> inventory = player.inventory.mainInventory;

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.get(i);
            NBTTagCompound tag = TinkerUtil.getModifierTag(stack, TinkerSurvival.modToolLeveling.getModifierIdentifier());
            if (!tag.isEmpty()) {
                int cxp = TinkerSurvival.modToolLeveling.getLevelData(stack).cxp;

                if (cxp > 0) {
                    TinkerSurvival.modToolLeveling.resetCxp(stack);
                    TinkerToolLeveling.modToolLeveling.addXp(stack, cxp * 3, player);
                }
            }
        }

    }

}
