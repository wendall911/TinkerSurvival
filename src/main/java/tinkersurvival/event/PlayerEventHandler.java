package tinkersurvival.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.items.tool.Knife;
import tinkersurvival.items.tool.Saw;
import tinkersurvival.TinkerSurvival;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        /*
         * Checking here to see if the saw or knife are the output of a crafting recipe.
         * If they are a potential output, tag the itme for removal, since we leverage
         * getContainterItem as a built-in mechanic to use the saw and knife as crafting tools.
         *
         * Removing the tool here messes with the custom crafting mechanic in TCon, so just 
         * tag it and remove later.
         *
         * This is sepcifically done to work around the non-standard way recipes for repair
         * kits are handled. Also need to work around bugs in the LazyResultContainer that just
         * does a lot of bad/incorrect/unimplemented things.
         *
         */

        if (!event.getPlayer().level.isClientSide) {
            ItemLike thing = event.getCrafting().getItem();

            if (thing instanceof Saw || thing instanceof Knife) {
                Container craftMatrix = event.getInventory();

                for (int i = 0; i < craftMatrix.getContainerSize(); ++i) {
                    ItemStack stack = craftMatrix.getItem(i);

                    if (stack.getItem() instanceof Saw || stack.getItem() instanceof Knife) {
                        ItemStack tool = craftMatrix.removeItemNoUpdate(i);

                        tool.getOrCreateTag().putBoolean("remove", true);

                        craftMatrix.setItem(i, tool);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getPlayer() instanceof Player ? (Player) event.getPlayer() : null;

        if ((player != null)
                && !player.isCreative()
                && !player.isSpectator()
                && !player.level.isClientSide
                && event.isWasDeath()) {
            ServerPlayer sp = (ServerPlayer) player;

            if (ConfigHandler.Server.enableHungerPenalty()) {
                sp.getFoodData().setFoodLevel(ConfigHandler.Server.hunger());
                sp.getFoodData().setSaturation(ConfigHandler.Server.saturation());
            }
            if (ConfigHandler.Server.enableHealthPenalty()) {
                sp.setHealth(ConfigHandler.Server.health());
            }
        }
    }

}
