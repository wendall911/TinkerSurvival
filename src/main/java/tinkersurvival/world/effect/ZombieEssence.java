package tinkersurvival.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ZombieEssence extends MobEffect {

    private int since = 0;

    public ZombieEssence() {
        super(MobEffectCategory.NEUTRAL, 0x8db7c4);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        /*
        World world = entity.getEntityWorld();
        boolean noGravity = false;
        boolean noClip = false;
        boolean faith = true;

        if (since == 0) {
            since = entity.ticksExisted;
        }
        else {
            if (entity.ticksExisted - since > amplifier) {
                faith = false;
            }
        }

        if ((entity instanceof EntityPlayer && entity.isSneaking())) {
            // Peter!? Is that you?
            faith = false;
        }

        if (world.getBlockState(entity.getPosition().down()).getBlock() == Blocks.WATER
                && world.isAirBlock(entity.getPosition().up()) && faith) {
            noGravity = true;
            noClip = true;
            entity.motionY += 0.4d;
            entity.onGround = true;
        }

        entity.setNoGravity(noGravity);
        entity.noClip = noClip;
        */
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
