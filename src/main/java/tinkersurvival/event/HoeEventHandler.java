package tinkersurvival.event;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class HoeEventHandler {

    @SubscribeEvent
    public static void onHoeBlock(UseHoeEvent event) {
        final Player player = event.getPlayer();

        if (!player.isCreative()) {
            final ItemStack handStack = player.getMainHandItem();
            final Level level = player.getLevel();

            if (!ItemUse.isWhitelistItem(handStack)) {
                if (level.isClientSide && ConfigHandler.Client.enableFailSound()) {
                    level.playSound(null, player.getOnPos(), Sounds.HOE_FAIL.get(), SoundSource.BLOCKS, 0.2F, 1.0F);
                }
                event.setCanceled(true);
            }
        }
    }

}
