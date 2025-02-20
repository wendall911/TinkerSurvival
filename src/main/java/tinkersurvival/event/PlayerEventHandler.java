package tinkersurvival.event;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.tables.block.entity.inventory.TinkerStationContainerWrapper;
import tinkersurvival.items.tool.Knife;
import tinkersurvival.items.tool.Saw;
import tinkersurvival.TinkerSurvival;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        /*
         * Checking here to see if the saw or knife are the output of a crafting recipe.
         * If they are a potential output, tag the item for removal, since we leverage
         * getContainterItem as a built-in mechanic to use the saw and knife as crafting tools.
         *
         * Removing the tool here messes with the custom crafting mechanic in TCon, so just
         * tag it and remove later.
         *
         * This is specifically done to work around the non-standard way recipes for repair
         * kits are handled. Also need to work around bugs in the LazyResultContainer that just
         * does a lot of bad/incorrect/unimplemented things.
         *
         * Reviving this for 1.19.2
         *
         */

        if (!event.getEntity().level().isClientSide) {
            ItemLike thing = event.getCrafting().getItem();

            if (thing instanceof Saw || thing instanceof Knife) {
                Container craftMatrix = event.getInventory();

                /*
                 * TinkerStationContainerWrapper removes the item from the inventory, which is correct, just this
                 * needs to be ignored for the crafting station, but applied for other crafting inventories because
                 * of the bug with how repair kit recipes work.
                 */
                if (!(craftMatrix instanceof TinkerStationContainerWrapper)) {
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
    }

}
