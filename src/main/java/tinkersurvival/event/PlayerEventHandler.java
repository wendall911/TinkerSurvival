package tinkersurvival.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class PlayerEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getPlayer();

        if ((player != null)
                && !player.isCreative()
                && !player.isSpectator()
                && !player.level.isClientSide
                && event.isWasDeath()) {
            ServerPlayer sp = (ServerPlayer) player;

            if (ConfigHandler.Server.enableHungerPenalty()) {
                sp.getFoodData().setFoodLevel(ConfigHandler.Server.hunger());
                sp.getFoodData().setSaturation(ConfigHandler.Server.saturation());
            }
            if (ConfigHandler.Server.enableHealthPenalty()) {
                sp.setHealth(ConfigHandler.Server.health());
            }
        }
    }

}
