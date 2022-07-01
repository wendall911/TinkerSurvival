package tinkersurvival.event;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.world.BlockEvent.BlockToolModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.sound.Sounds;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class HoeEventHandler {

    @SubscribeEvent
    public static void onHoeBlock(BlockToolModificationEvent event) {
        final Player player = event.getPlayer() != null ? event.getPlayer() : null;

        if (player != null && !player.isCreative() && event.getToolAction() == ToolActions.HOE_TILL) {
            final ItemStack handStack = player.getMainHandItem();
            final Level level = player.getLevel();

            if (!ItemUse.isWhitelistItem(handStack)) {
                if (!level.isClientSide && ConfigHandler.Client.enableFailSound()) {
                    level.playSound(null, player.getOnPos(), Sounds.HOE_FAIL.get(), SoundSource.BLOCKS, 0.2F, 1.0F);
                }
                event.setCanceled(true);
            }
        }
    }

}
