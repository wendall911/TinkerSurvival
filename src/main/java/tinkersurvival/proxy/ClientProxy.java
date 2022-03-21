package tinkersurvival.proxy;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;

public final class ClientProxy extends CommonProxy {

    public ClientProxy() {
        Sounds.init(TinkerSurvival.BUS);
    }

    @Nullable
	@Override
	public Player getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	@Nullable
	@Override
	public Level getClientLevel() {
		Minecraft mc = Minecraft.getInstance();

		return mc != null ? mc.level : null;
	}

}
