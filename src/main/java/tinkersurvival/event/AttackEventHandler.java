package tinkersurvival.event;

/*
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.util.ItemUse;
*/

public class AttackEventHandler {

/*
    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {

        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) {
            return;
        }

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

        if (player instanceof FakePlayer || player.capabilities.isCreativeMode) {
            return;
        }

        if (!ItemUse.isWhitelistItem(player.getHeldItemMainhand())) {
            Sounds.play(player, Sounds.SWORD_FAIL, 0.4F, 1.0F);
            event.setAmount(0.0f);
            event.setCanceled(true);
        }
    }
*/

}
