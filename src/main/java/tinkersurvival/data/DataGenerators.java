package tinkersurvival.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import tinkersurvival.data.client.ModBlockStateProvider;
import tinkersurvival.data.client.ModItemModelProvider;
import tinkersurvival.data.loot.ModLootTables;
import tinkersurvival.data.loot.GlobalLootModifier;
import tinkersurvival.data.recipes.ModRecipesProvider;
import tinkersurvival.TinkerSurvival;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);

        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(new ModRecipesProvider(gen));
        gen.addProvider(new ModLootTables(gen));
        gen.addProvider(new GlobalLootModifier(gen));
    }

}
