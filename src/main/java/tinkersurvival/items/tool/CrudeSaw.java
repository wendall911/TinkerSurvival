package tinkersurvival.items.tool;

import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import tinkersurvival.common.TagManager;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;

public class CrudeSaw extends TieredItem {

    public String name;
    private final float speed;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public CrudeSaw(String name, Tier tier, float speed, Item.Properties tabGroup) {
        super(tier, tabGroup);

        this.speed = speed;
        this.name = name;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Speed modifier", speed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        ItemStack container = stack.copy();
        
        if (this.name == "crude_saw_handle") {
            return ItemStack.EMPTY;
        }
        else if (!container.hurt(1, new Random(), null)) {
            return container;
        }
        else {
            return new ItemStack(TinkerSurvivalItems.CRUDE_SAW_HANDLE);
        }
    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack pStack, BlockState pState) {
        return !pState.is(TagManager.Blocks.ALWAYS_BREAKABLE) ? this.speed : 1.0F;
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
          if (!pLevel.isClientSide && pState.getDestroySpeed(pLevel, pPos) != 0.0F) {
             pStack.hurtAndBreak(1, pEntityLiving, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
             });
          }

          return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.FAIL;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return false;
    }

}
