package tinkersurvival.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import tinkersurvival.event.AttackEventHandler;
import tinkersurvival.event.BowEventHandler;
import tinkersurvival.event.HarvestEventHandler;
import tinkersurvival.event.HoeEventHandler;
import tinkersurvival.event.PlayerContainerEventHandler;
import tinkersurvival.event.PlayerEventHandler;
import tinkersurvival.event.SleepEventHandler;
import tinkersurvival.event.TooltipEventHandler;
import tinkersurvival.recipe.TinkerSurvivalRecipes;
import tinkersurvival.tools.TinkerSurvivalTools;
import tinkersurvival.world.TinkerSurvivalWorld;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AttackEventHandler());
        MinecraftForge.EVENT_BUS.register(new BowEventHandler());
        MinecraftForge.EVENT_BUS.register(new HoeEventHandler());
        MinecraftForge.EVENT_BUS.register(new HarvestEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerContainerEventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        MinecraftForge.EVENT_BUS.register(new SleepEventHandler());
        MinecraftForge.EVENT_BUS.register(new TooltipEventHandler());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        TinkerSurvivalTools.init();
        TinkerSurvivalTools.registerTools(event);
        TinkerSurvivalWorld.registerItems(event);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        TinkerSurvivalWorld.init();
        TinkerSurvivalWorld.registerBlocks(event);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        TinkerSurvivalRecipes.updateRecipes();
        TinkerSurvivalWorld.initItemRepairMaterials();
    }

    public void registerItemModel(Item item, int meta, String id) {
    }
    public void registerItemModel(Item item, int meta) {
    }
    public void registerItemModel(Item item){
    }

    public void registerItemModelWithVariant(Item item, int meta, String id, String variant) {
    }

    public String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }

    public void generateParticle(World world, BlockPos pos, EnumParticleTypes particle){
    }

    public void initGuis() {
    }

}
