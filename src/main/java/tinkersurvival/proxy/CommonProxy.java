package tinkersurvival.proxy;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Loader;

import tinkersurvival.config.Config;
import tinkersurvival.event.AttackEventHandler;
import tinkersurvival.event.BowEventHandler;
import tinkersurvival.event.HarvestEventHandler;
import tinkersurvival.event.HoeEventHandler;
import tinkersurvival.event.PlayerContainerEventHandler;
import tinkersurvival.event.PlayerEventHandler;
import tinkersurvival.event.SleepEventHandler;
import tinkersurvival.event.SomethingNeedsToastEvent;
import tinkersurvival.event.SomethingNeedsToastHandler;
import tinkersurvival.event.TooltipEventHandler;
import tinkersurvival.recipe.TinkerSurvivalRecipes;
import tinkersurvival.TinkerSurvival;
import tinkersurvival.tools.TinkerSurvivalTools;
import tinkersurvival.world.TinkerSurvivalWorld;
import tinkersurvival.world.worldgen.RockGenerator;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AttackEventHandler());
        MinecraftForge.EVENT_BUS.register(new BowEventHandler());
        MinecraftForge.EVENT_BUS.register(new HoeEventHandler());
        MinecraftForge.EVENT_BUS.register(new HarvestEventHandler());
        if (Loader.isModLoaded("tinkertoolleveling")) {
            MinecraftForge.EVENT_BUS.register(new PlayerContainerEventHandler());
        }
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        if (Config.Features.NO_SLEEPING) {
            MinecraftForge.EVENT_BUS.register(new SleepEventHandler());
        }
        MinecraftForge.EVENT_BUS.register(new SomethingNeedsToastHandler());
        MinecraftForge.EVENT_BUS.register(new TooltipEventHandler());
        MinecraftForge.EVENT_BUS.register(new RockGenerator());

    }

    public void init(FMLInitializationEvent event) {
        initGuis();
    }

    public void postInit(FMLPostInitializationEvent event) {
        TinkerSurvivalRecipes.updateRecipes();

        // Disable Enderman Griefing!!!
        if (Config.Features.NO_GRIEFING) {
            final Set<String> griefBlockIds = Sets.newHashSet(Config.Features.GRIEFING_WHITELIST);
            Set<Block> griefBlocks = new HashSet<Block>();

            for (String id: griefBlockIds) {
                Block block = Block.getBlockFromName(id);
                if (block != null) {
                    griefBlocks.add(block);
                }
            }

            RegistryNamespacedDefaultedByKey<ResourceLocation, Block> griefBlock = Block.REGISTRY;
            for (Block block : griefBlock) {
                if (!griefBlocks.contains(block)) {
                    EntityEnderman.setCarriable(block, false);
                }
                else {
                    //Griefing!!!!
                    TinkerSurvival.logger.info("Allowing griefing for block: " + block.getUnlocalizedName());
                }
            }
        }
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
        TinkerSurvivalWorld.initItemRepairMaterials();
    }

	public static void toastHint(String title, String subtitle, String replace) {
		SomethingNeedsToastEvent.fireEvent(title, subtitle, replace);
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
