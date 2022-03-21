package tinkersurvival.event;

/*
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.util.ItemUse;
*/

public class HoeEventHandler {

    /*
    @SubscribeEvent
    public void onHoeBlock(UseHoeEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player == null
            || player instanceof FakePlayer
            || player.capabilities.isCreativeMode
                ) {
            return;
        }

        if (!ItemUse.isWhitelistItem(player.getHeldItemMainhand())) {
            Sounds.play(player, Sounds.HOE_FAIL, 0.2F, 1.0F);
            event.setCanceled(true);
        }
    }
    */

}
