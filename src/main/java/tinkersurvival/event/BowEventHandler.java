package tinkersurvival.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tinkersurvival.client.sound.Sounds;
import tinkersurvival.util.Event;

public class BowEventHandler {
    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        EntityPlayer player = event.getEntityPlayer();

        if (player == null
            || player instanceof FakePlayer
            || player.capabilities.isCreativeMode
                ) {
            return;
        }

        ItemStack heldItemStack = player.getHeldItemMainhand();

        if (Event.isUselessBow(heldItemStack)) {
            Sounds.play(player, Sounds.BOW_FAIL, 0.6F, 1.0F);
            event.setCanceled(true);
        }
    }
}
