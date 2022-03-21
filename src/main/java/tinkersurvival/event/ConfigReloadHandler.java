package tinkersurvival.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.util.ItemUse;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigReloadHandler {

    @SubscribeEvent
    public static void onFileChange(final ModConfigEvent.Reloading event) {
        ItemUse.init();
    }

}
