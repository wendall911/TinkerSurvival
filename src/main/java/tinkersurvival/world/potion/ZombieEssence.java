package tinkersurvival.world.potion;

/*
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import tinkersurvival.world.potion.PotionBase;

public class ZombieEssence extends PotionBase {

    private int since = 0;

    public ZombieEssence() {
        super("zombieessence", false, 0x8db7c4);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
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
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

}
*/
