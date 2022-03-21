package tinkersurvival.proxy;

import javax.annotation.Nullable;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

public class CommonProxy implements IProxy {

    @Nullable private static MinecraftServer server;

    CommonProxy() {
        ConfigHandler.init();
        TinkerSurvivalWorld.init(TinkerSurvival.BUS);
    }

    @Nullable
    @Override
    public Player getClientPlayer() {
        return null;
    }

    @Nullable
    @Override
    public Level getClientLevel() {
        return null;
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return server;
    }

}
