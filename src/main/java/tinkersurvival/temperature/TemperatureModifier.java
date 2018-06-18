package tinkersurvival.temperature;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import toughasnails.api.temperature.IModifierMonitor;
import toughasnails.api.temperature.ITemperatureModifier;
import toughasnails.api.temperature.Temperature;

public abstract class TemperatureModifier implements ITemperatureModifier {

    private final String id;

    public TemperatureModifier(String id) {
        this.id = id;
    }

    @Override
    public Temperature applyEnvironmentModifiers(World world, BlockPos pos, Temperature initialTemperature, IModifierMonitor monitor) {
        return initialTemperature;
    }

    @Override
    public Temperature applyPlayerModifiers(EntityPlayer player, Temperature initialTemperature, IModifierMonitor monitor) {
        return applyEnvironmentModifiers(player.world, player.getPosition(), initialTemperature, monitor);
    }

    @Override
    public abstract boolean isPlayerSpecific();

    @Override
    public String getId() {
        return this.id;
    }

}
