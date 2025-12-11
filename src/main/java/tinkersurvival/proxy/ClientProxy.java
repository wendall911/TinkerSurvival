package tinkersurvival.proxy;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import tinkersurvival.integrations.patchouli.ChargedCharmsCustomCrafting;

public final class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    @Override
    public void start() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        registerListeners(bus);

        super.start();
    }

    @Override
    public void registerListeners(IEventBus bus) {
        super.registerListeners(bus);

        bus.addListener(this::clientSetup);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("chargedcharms")) {
            ChargedCharmsCustomCrafting.init();
        }
    }

}
