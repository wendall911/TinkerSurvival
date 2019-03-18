package tinkersurvival.proxy;

import java.util.Random;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import slimeknights.tconstruct.library.client.ToolBuildGuiInfo;
import slimeknights.tconstruct.library.TinkerRegistryClient;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.tools.TinkerSurvivalTools;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        TinkerSurvivalTools.registerItemModels();
        TinkerSurvivalWorld.registerItemModels();
        TinkerSurvivalWorld.registerBlockModels();
    }

    @Override
    public void registerItemModel(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(TinkerSurvival.MODID + ":" + id, "inventory"));
    }
    @Override
    public void registerItemModel(Item item, int meta){
        registerItemModel(item, meta, item.getRegistryName().getPath());
    }
    @Override
    public void registerItemModel(Item item) {
        registerItemModel(item, 0, item.getRegistryName().getPath());
    }

    @Override
    public void registerItemModelWithVariant(Item item, int meta, String id, String variant){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(TinkerSurvival.MODID + ":" + id, variant));
    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.format(unlocalized, args);
    }

    @Override
    public void initGuis() {
        ToolBuildGuiInfo knifeInfo = new ToolBuildGuiInfo(TinkerSurvivalTools.ticKnife);
        knifeInfo.addSlotPosition(12, 62); // handle
        knifeInfo.addSlotPosition(48, 27); // blade
        knifeInfo.addSlotPosition(30, 44); // binding
        TinkerRegistryClient.addToolBuilding(knifeInfo);

        ToolBuildGuiInfo sawInfo = new ToolBuildGuiInfo(TinkerSurvivalTools.ticSaw);
        sawInfo.addSlotPosition(49, 61); // handle
        sawInfo.addSlotPosition(11, 39); // head
        sawInfo.addSlotPosition(22, 20); // head
        sawInfo.addSlotPosition(31, 44); // binding
        TinkerRegistryClient.addToolBuilding(sawInfo);

        TinkerSurvival.TS_Tab.setTabItem(TinkerSurvivalTools.crudeHatchet);
    }

}
