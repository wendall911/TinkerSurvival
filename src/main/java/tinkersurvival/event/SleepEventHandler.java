package tinkersurvival.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import tinkersurvival.TinkerSurvival;

public class SleepEventHandler {

	@SubscribeEvent
	public void onPlayerSleep(PlayerSleepInBedEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();

		event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);

		if (world.provider.canRespawnHere() && (world.provider.getBiomeForCoords(event.getPos()) != Biomes.HELL)) {
			player.setSpawnPoint(event.getPos(), false);
			player.setSpawnChunk(event.getPos(), false, player.dimension);
			TinkerSurvival.proxy.toastHint("message.notice", "message.spawn_set", null);
		}
	}

}
