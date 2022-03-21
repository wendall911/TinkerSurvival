package tinkersurvival.event;

/*
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.util.ItemUse;
*/

public class LivingEquipmentChangeEventHandler {

    /*
    @SubscribeEvent
    public void onChange(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityEquipmentSlot slot = event.getSlot();
            if (slot.getSlotType() == EntityEquipmentSlot.Type.ARMOR
                    && ItemUse.isArmor(event.getTo())
                    && !ItemUse.isWhitelistArmor(event.getTo())) {
                EntityPlayer player = (EntityPlayer) event.getEntityLiving();
                ItemStack itemstack = player.getItemStackFromSlot(slot);

                Sounds.play(player, Sounds.ARMOR_FAIL, 0.4F, 1.0F);

                if (!player.inventory.addItemStackToInventory(itemstack)) {
                    player.entityDropItem(itemstack, 0.0F);
                }

                player.setItemStackToSlot(slot, ItemStack.EMPTY);
            }
        }
    }
    */
}
