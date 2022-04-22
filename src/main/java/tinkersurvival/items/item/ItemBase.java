package tinkersurvival.items.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.effect.TinkerSurvivalEffects;

import java.util.Objects;

public class ItemBase extends Item {

    private static UseAnim animation;

    public ItemBase(Item.Properties props, UseAnim animation) {
        super(props);

        this.animation = animation;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Player player = entity instanceof Player ? (Player)entity : null;

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (!level.isClientSide) {
            String name = Objects.requireNonNull(stack.getItem().getRegistryName()).getPath();

            if (name.contains("bandage")) {
                int amplifier = 0;

                if (!name.contains("crude")) {
                    amplifier = 1;
                }

                MobEffect effect = TinkerSurvivalEffects.STOP_BLEEDING.get();
                entity.addEffect(new MobEffectInstance(effect, 600, amplifier));
            }
            else if (name.contains("cup")) {
                MobEffect effect = TinkerSurvivalEffects.ZOMBIE_ESSENCE.get();
                entity.addEffect(new MobEffectInstance(effect, 3600, 1));
            }

        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (animation == UseAnim.DRINK) {
            level.gameEvent(entity, GameEvent.DRINKING_FINISH, entity.eyeBlockPosition());
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        String name = Objects.requireNonNull(stack.getItem().getRegistryName()).getPath();
        boolean stopBleeding = player.hasEffect(TinkerSurvivalEffects.STOP_BLEEDING.get());
        boolean zombieEssence = player.hasEffect(TinkerSurvivalEffects.ZOMBIE_ESSENCE.get());

        if (name.contains("bandage")) {
            if (stopBleeding || player.getHealth() >= player.getMaxHealth()) {
                return InteractionResultHolder.fail(stack);
            }
        }
        else if (name.contains("cup") && zombieEssence) {
            return InteractionResultHolder.fail(stack);
        }

        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return animation;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 1;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.PLAYER_ATTACK_WEAK;
    }

}
