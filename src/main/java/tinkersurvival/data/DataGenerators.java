package tinkersurvival.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.library.client.data.material.GeneratorPartTextureJsonGenerator;
import slimeknights.tconstruct.tools.data.sprite.TinkerMaterialSpriteProvider;

import tinkersurvival.data.client.ModItemModelProvider;
import tinkersurvival.data.overrides.BlockTagsOverrideProvider;
import tinkersurvival.data.recipes.ModRecipesProvider;
import tinkersurvival.data.tcon.MaterialPartTextureGenerator;
import tinkersurvival.data.tcon.sprite.SawPartSpriteProvider;
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
        SawPartSpriteProvider sawPartSprites = new SawPartSpriteProvider();
        String modpackOverrides = System.getenv("MOD_OVERRIDES");
        TinkerMaterialSpriteProvider materialSprites = new TinkerMaterialSpriteProvider();
        boolean client = event.includeClient();
        boolean server = event.includeServer();

        gen.addProvider(server, new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(server, blockTags);
        gen.addProvider(server, new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(server, new ModRecipesProvider(gen));
        gen.addProvider(server, new ToolsRecipeProvider(gen));
        gen.addProvider(server, new StationSlotLayoutProvider(gen));
        gen.addProvider(client, new GeneratorPartTextureJsonGenerator(gen, TinkerSurvival.MODID, sawPartSprites));
        gen.addProvider(server, new ToolDefinitionDataProvider(gen));
        gen.addProvider(client, new MaterialPartTextureGenerator(gen, existingFileHelper, sawPartSprites, materialSprites));

        if (modpackOverrides != null && modpackOverrides.contains("all")) {
            gen.addProvider(server, new BlockTagsOverrideProvider(gen, event.getExistingFileHelper()));
        }
    }

}
