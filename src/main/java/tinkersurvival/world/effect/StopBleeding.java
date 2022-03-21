package tinkersurvival.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import tinkersurvival.config.ConfigHandler;

public class StopBleeding extends MobEffect {

    static final float HEAL_RATE = (float)ConfigHandler.Server.healRate();

    public StopBleeding() {
        super(MobEffectCategory.BENEFICIAL, 0xf7b7ad);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getHealth() >= entity.getMaxHealth()) {
            entity.removeEffect(this);
        }

        entity.heal(HEAL_RATE * (float)(amplifier + 1));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 50 >> amplifier;

        if (j > 0) {
            return duration % j == 0;
        }
        else {
            return true;
        }
    }

}
