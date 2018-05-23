package tinkersurvival.world.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion {

	public PotionBase(String name, boolean harmful, int color) {
		super(harmful, color);
        setPotionName("effect." + name);
        setRegistryName(new ResourceLocation("tinkersurvival:" + name));
	}

}
