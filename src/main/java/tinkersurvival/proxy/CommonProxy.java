package tinkersurvival.proxy;

import net.minecraft.core.registries.Registries;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.items.TConItems;
import tinkersurvival.items.TinkerSurvivalItems;
import tinkersurvival.TinkerSurvival;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID)
public class CommonProxy {

    public CommonProxy() {}

    public void start() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        registerListeners(bus);
    }

    public void registerListeners(IEventBus bus) {
        bus.register(RegistryListener.class);
    }

    public static final class RegistryListener {

        private static boolean setupDone = false;

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerEvent(RegisterEvent event) {
            event.register(Registries.ITEM, TConItems::init);
            event.register(Registries.ITEM, TinkerSurvivalItems::init);
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void setupRegistries(FMLConstructModEvent event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            if (setupDone) {
                return;
            }
            setupDone = true;

            TinkerSurvivalModule.initRegistries(bus);
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(new ItemStack(TConItems.KNIFE.get()));
                event.accept(new ItemStack(TConItems.SAW.get()));
                event.accept(new ItemStack(TConItems.SAW_BLADE.get()));
                event.accept(new ItemStack(TConItems.SAW_BLADE_CAST.get()));
                event.accept(new ItemStack(TConItems.SAW_BLADE_CAST.get()));
                event.accept(new ItemStack(TinkerSurvivalItems.MODPACK_BOOK));
            }
        }

    }

}
