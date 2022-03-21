package tinkersurvival.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;

import tinkersurvival.data.client.ModBlockStateProvider;
import tinkersurvival.data.client.ModItemModelProvider;
import tinkersurvival.data.loot.ModLootTables;
import tinkersurvival.data.loot.GlobalLootModifier;
import tinkersurvival.data.overrides.BlockTagsOverrideProvider;
import tinkersurvival.data.overrides.MaterialOverrideProvider;
import tinkersurvival.data.recipes.ModRecipesProvider;
import tinkersurvival.data.tcon.sprite.TinkerPartSpriteProvider;
import tinkersurvival.data.tcon.StationSlotLayoutProvider;
import tinkersurvival.data.tcon.ToolDefinitionDataProvider;
import tinkersurvival.data.tcon.ToolsRecipeProvider;
import tinkersurvival.TinkerSurvival;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        TinkerPartSpriteProvider partSprites = new TinkerPartSpriteProvider();
        String modpackOverrides = System.getenv("MOD_OVERRIDES");

        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(new ModRecipesProvider(gen));
        gen.addProvider(new ModLootTables(gen));
        gen.addProvider(new GlobalLootModifier(gen));
        gen.addProvider(new ToolsRecipeProvider(gen));
        gen.addProvider(new StationSlotLayoutProvider(gen));
        gen.addProvider(new GeneratorPartTextureJsonGenerator(gen, TinkerSurvival.MODID, partSprites));
        gen.addProvider(new ToolDefinitionDataProvider(gen));

        if (modpackOverrides != null && modpackOverrides.contains("all")) {
            gen.addProvider(new MaterialOverrideProvider(gen));
            gen.addProvider(new BlockTagsOverrideProvider(gen, event.getExistingFileHelper()));
        }
    }

}
