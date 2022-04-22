package tinkersurvival.event;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.sound.Sounds;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class AttackEventHandler {

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player player) {
            if (!player.isCreative()) {
                final ItemStack handStack = player.getMainHandItem();
                final Level level = player.getLevel();

                if (!ItemUse.isWhitelistItem(handStack)) {
                    if (!level.isClientSide && ConfigHandler.Client.enableFailSound()) {
                        level.playSound(null, player.getOnPos(), Sounds.SWORD_FAIL.get(), SoundSource.BLOCKS, 0.4F, 1.0F);
                    }
                    event.setAmount(0.0f);
                    event.setCanceled(true);
                }
            }
        }
    }

}
