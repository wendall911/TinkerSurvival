package tinkersurvival.event;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.common.HarvestBlock;
import tinkersurvival.common.TagManager;
import tinkersurvival.config.ConfigHandler;
import tinkersurvival.mixin.AbstractBlockStateAccessor;
import tinkersurvival.sound.Sounds;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.Chat;
import tinkersurvival.util.ItemUse;
import tinkersurvival.util.ToolType;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class HarvestEventHandler {

    public static final Map<Player, BlockPos> harvestAttempts = new HashMap<>();

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        final LevelAccessor level = event.getWorld();
        final BlockPos pos = event.getPos();
        final BlockState state = level.getBlockState(pos);
        final Block block = state.getBlock();
        final Player player = event.getPlayer();
        final ToolType expectedToolType = HarvestBlock.BLOCK_TOOL_TYPES.getOrDefault(state.getBlock(), ToolType.NONE);
        boolean cancel = false;
        boolean alwaysBreakable = TagManager.Blocks.ALWAYS_BREAKABLE.contains(block) ||
                ((AbstractBlockStateAccessor) state).getDestroySpeed() == 0;

        if (!alwaysBreakable && !player.isCreative()) {
            if (expectedToolType != ToolType.NONE) {
                final ItemStack handStack = player.getMainHandItem();
                boolean correctTool = ItemUse.isCorrectTool(state, player, handStack);
                boolean isWhitelisted = ItemUse.isWhitelistItem(handStack);

                if (!isWhitelisted) {
                    cancel = true;
                    if (!player.getLevel().isClientSide && ConfigHandler.Client.enableFailSound()) {
                        level.playSound(null, player.getOnPos(), Sounds.TOOL_FAIL.get(), SoundSource.BLOCKS, 0.6F, 1.0F);
                    }
                }

                if (isWhitelisted && !correctTool) {
                    cancel = true;

                    if (harvestAttempts.containsKey(player)
                            || harvestAttempts.get(player) == null
                            || !harvestAttempts.get(player).equals(pos)) {

                        harvestAttempts.put(player, pos);

                        Chat.sendMessage(player, Chat.WRONG_TOOL, expectedToolType.toString().toLowerCase(), false);
                    }
                    else {
                        Chat.sendMessage(player, Chat.WARNING);
                        Chat.sendMessage(player, Chat.SARCASTIC_WRONG_TOOL, expectedToolType.toString().toLowerCase(), true);
                        player.hurt(DamageSource.GENERIC, 0.1f);
                    }
                }

            }
        }

        event.setCanceled(cancel);
    }

	@SubscribeEvent
    public static void harvestCheckEvent(PlayerEvent.HarvestCheck event) {
        final Player player = event.getPlayer();
        final BlockState state = event.getTargetBlock();
        final Block block = state.getBlock();

        if (!player.isCreative()) {
            final ItemStack handStack = player.getMainHandItem();
            final boolean correctTool = ItemUse.isCorrectTool(state, player, handStack);
            final ToolType expectedToolType = HarvestBlock.BLOCK_TOOL_TYPES.getOrDefault(block, ToolType.NONE);
            boolean canHarvest = event.canHarvest()
                    || ItemUse.alwaysDrops(state)
                    || expectedToolType == ToolType.NONE;

            if (!canHarvest) {
                final boolean isOre = Tags.Blocks.ORES.contains(block);

                if (isOre && expectedToolType == ToolType.PICKAXE) {
                    canHarvest = (correctTool && handStack.isCorrectToolForDrops(state));
                }
                else {
                    canHarvest = correctTool || handStack.isCorrectToolForDrops(state);
                }
            }

            event.setCanHarvest(canHarvest);
        }
    }

    // Controls the slow mining speed of blocks that aren't the right tool
    @SubscribeEvent
    public static void slowMining(PlayerEvent.BreakSpeed event) {
        final Player player = event.getPlayer();
        final Level level = player.getLevel();
        final BlockPos pos = event.getPos();
        final BlockState state = level.getBlockState(pos);
        final Block block = state.getBlock();
        final float destroySpeed = ((AbstractBlockStateAccessor) state).getDestroySpeed();
        float slowdown = destroySpeed;
        final ToolType expectedToolType = HarvestBlock.BLOCK_TOOL_TYPES.getOrDefault(block, ToolType.NONE);

        if (!player.isCreative() && expectedToolType != ToolType.NONE) {
            ItemStack handStack = player.getMainHandItem();
            boolean correctTool = ItemUse.isCorrectTool(state, player, handStack);
            boolean alwaysBreakable = TagManager.Blocks.ALWAYS_BREAKABLE.contains(block);
            boolean isWhitelisted = ItemUse.isWhitelistItem(handStack);

            if (!alwaysBreakable) {
                if (!isWhitelisted) {
                    slowdown = 0.2F;
                }
                else if (!correctTool) {
                    slowdown = 0.33F;
                }
            }
            else {
                if (!correctTool) {
                    slowdown = 0.33F;
                }
                else if (!isWhitelisted) {
                    slowdown = 0.2F;
                }
            }
        }

        if (slowdown != destroySpeed) {
            event.setNewSpeed(slowdown);
        }
    }

}
