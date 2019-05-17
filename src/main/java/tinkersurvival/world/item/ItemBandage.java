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

public class ItemBandage extends ItemBase {

    public static String name;

    public static enum Type {
        CRUDE_BANDAGE("crude_bandages", 1, 600), BANDAGE("bandages", 2, 600);

        public final String name;
        public final int amplifier;
        public final int duration;

        Type(String name, int amplifier, int duration) {
            this.name = name;
            this.amplifier = amplifier;
            this.duration = duration;
        }
    }

    public ItemBandage(String name) {
        super(name, Type.values().length);
        this.name = name;
        this.setMaxDamage(0);
        setMaxStackSize(16);
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
    public String getTranslationKey(ItemStack stack) {
        return "item." + getBandageName(stack);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (player.getHealth() < player.getMaxHealth() && !player.isPotionActive(TinkerSurvivalWorld.stopBleeding)) {
            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }

        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        if (!world.isRemote) {
            Type healingType = Type.values()[MathHelper.clamp(stack.getItemDamage(), 0,
                Type.values().length - 1)];

            entityLiving.addPotionEffect(new PotionEffect(TinkerSurvivalWorld.stopBleeding, healingType.duration,
                healingType.amplifier, false, true));

            stack.shrink(1);

            if (entityLiving instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityLiving, stack);
            }
        }
        return stack;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (getCreativeTab() != tab) {
            return;
        }

        for (Type type : Type.values()) {
            items.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    public String getBandageName(ItemStack stack) {
        return Type.values()[stack.getMetadata()].name;
    }

}
