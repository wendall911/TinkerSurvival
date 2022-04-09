package tinkersurvival.proxy;

import net.minecraft.client.gui.Font;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import slimeknights.tconstruct.shared.CommonsClientEvents;

import tinkersurvival.client.TinkerSurvivalBook;
import tinkersurvival.TinkerSurvival;

@EventBusSubscriber(modid = TinkerSurvival.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public final class ClientProxy extends CommonProxy {

    public ClientProxy() {
        TinkerSurvivalBook.init();
    }

    @SubscribeEvent
    static void clientSetup(final FMLClientSetupEvent event) {
        Font unicode = CommonsClientEvents.unicodeFontRender();

        TinkerSurvivalBook.TINKERS_SURVIVAL.fontRenderer = unicode;
        TinkerSurvivalBook.TINKERS_SURVIVAL_MODPACK.fontRenderer = unicode;
    }

}
