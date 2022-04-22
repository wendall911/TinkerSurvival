package tinkersurvival.proxy;

import net.minecraft.client.gui.Font;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import slimeknights.tconstruct.shared.CommonsClientEvents;

import tinkersurvival.client.TinkerSurvivalBook;

@OnlyIn(Dist.CLIENT)
public final class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    @Override
    public void start() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        registerListeners(bus);

        TinkerSurvivalBook.init();

        super.start();
    }

    @Override
    public void registerListeners(IEventBus bus) {
        super.registerListeners(bus);

        bus.addListener(this::clientSetup);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        Font unicode = CommonsClientEvents.unicodeFontRender();

        TinkerSurvivalBook.TINKERS_SURVIVAL.fontRenderer = unicode;
        TinkerSurvivalBook.TINKERS_SURVIVAL_MODPACK.fontRenderer = unicode;
    }

}
