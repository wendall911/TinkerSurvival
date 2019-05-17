package tinkersurvival.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import tinkersurvival.world.TinkerSurvivalWorld;

public class ItemWoodenCup extends ItemBase {

    public static String name;

    private static int duration;

    public ItemWoodenCup(String name, int duration) {
        super(name, 1);
        this.name = name;
        this.duration = duration;
        this.setMaxDamage(0);
        setMaxStackSize(1);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!player.isPotionActive(TinkerSurvivalWorld.zombieEssence)) {
            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (!world.isRemote) {
            entityLiving.addPotionEffect(new PotionEffect(TinkerSurvivalWorld.zombieEssence, this.duration,
                this.duration - 20, false, false));

            stack.shrink(1);

            if (entityLiving instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityLiving, stack);
            }
        }
        return stack;
    }

}
