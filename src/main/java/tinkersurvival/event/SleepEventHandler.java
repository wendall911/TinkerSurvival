package tinkersurvival.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.world.World;

import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.util.Chat;

public class SleepEventHandler {

    @SubscribeEvent
    public void onPlayerSleep(PlayerSleepInBedEvent event) {
        if (ConfigHandler.features.NO_SLEEPING) {
            EntityPlayer player = event.getEntityPlayer();
            World world = player.getEntityWorld();

            event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);

            if (world.provider.canRespawnHere() && (world.provider.getBiomeForCoords(event.getPos()) != Biomes.HELL)) {
                player.setSpawnPoint(event.getPos(), false);
                player.setSpawnChunk(event.getPos(), false, player.dimension);
                Chat.sendMessage(player, "message.spawn_set", null);
            }
        }
    }

}
