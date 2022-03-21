package tinkersurvival.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.TagManager;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class PlayerEventHandler {

	@SubscribeEvent
    public static void playerInteractEvent(PlayerInteractEvent event) {
        final ItemStack stack = event.getItemStack();
        final Level level = event.getWorld();
        final BlockPos pos = event.getPos();
        final BlockState state = level.getBlockState(pos);
        final InteractionHand hand = event.getHand();
        final Player player = event.getPlayer();

        if (player instanceof ServerPlayer &&
                hand.equals(InteractionHand.MAIN_HAND)) {

            if (TagManager.Items.KNIFE_TOOLS.contains(stack.getItem())
                    && TagManager.Blocks.GRASS.contains(state.getBlock())) {
                if (!level.isClientSide) {
                    if (level.random.nextFloat() < 0.3) {
                        if (level.random.nextFloat() < ConfigHandler.Server.grassFiberBonusChance()) {
                            NonNullList<ItemStack> dropStack =
                                NonNullList.withSize(1, new ItemStack(TinkerSurvivalWorld.GRASS_FIBER.get(), 1));

                            Containers.dropContents(level, pos, dropStack);

                            stack.hurtAndBreak(1, player, (item) -> {
                                item.broadcastBreakEvent(event.getHand());
                            });
                        }
                    }
                }
            }
        }

    }

}
