package tinkersurvival.proxy;

import javax.annotation.Nullable;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IProxy {

    @Nullable
    Player getClientPlayer();

    @Nullable
    Level getClientLevel();

    @Nullable
    MinecraftServer getServer();

}
