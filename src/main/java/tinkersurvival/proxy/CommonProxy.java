package tinkersurvival.proxy;

import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import slimeknights.tconstruct.common.registration.CastItemObject;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerToolParts;
import slimeknights.tconstruct.tools.TinkerTools;

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
            Consumer<ItemStack> output = ((CreativeModeTab.Output) event)::accept;

            if (event.getTabKey() == TinkerTools.tabTools.getKey()) {
                ToolBuildHandler.addVariants(output, TConItems.KNIFE.get(), "");
                ToolBuildHandler.addVariants(output, TConItems.SAW.get(), "");
            }

            if (event.getTabKey() == TinkerToolParts.tabToolParts.getKey()) {
                TConItems.SAW_BLADE.get().addVariants(output, "");
            }

            if (event.getTabKey() == TinkerSmeltery.tabSmeltery.getKey()) {
                addCast(event, CastItemObject::get);
                addCast(event, CastItemObject::getSand);
                addCast(event, CastItemObject::getRedSand);
            }

            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.accept(new ItemStack(TinkerSurvivalItems.MODPACK_BOOK));
            }
        }

        private static void addCast(CreativeModeTab.Output output, Function<CastItemObject, ItemLike> getter) {
            output.accept(getter.apply(TConItems.SAW_BLADE_CAST));
        }

    }

}
