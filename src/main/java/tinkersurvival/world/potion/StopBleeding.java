package tinkersurvival.world.potion;

/*
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.world.potion.PotionBase;

public class StopBleeding extends PotionBase {

    static final float HEAL_RATE = (float)ConfigHandler.balance.HEAL_RATE;

    public StopBleeding() {
        super("stopbleeding", false, 0xf7b7ad);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        if (entity.getHealth() >= entity.getMaxHealth()) {
            entity.removePotionEffect(this);
        }

        float healAmount = HEAL_RATE * amplifier;
        entity.setHealth(entity.getHealth() + healAmount);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }

}
*/
