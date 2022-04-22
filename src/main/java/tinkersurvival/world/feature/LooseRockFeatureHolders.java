package tinkersurvival.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;

import tinkersurvival.config.ConfigHandler;

public class LooseRockFeatureHolders {

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> LOOSE_ROCKS_CONFIGURED = FeatureUtils.register(
        "loose_rocks",
        TinkerSurvivalFeatures.LOOSE_ROCKS_FEATURE.get()
    );

    public static final Holder<PlacedFeature> LOOSE_ROCKS_PLACEMENT = PlacementUtils.register("loose_rocks",
        LOOSE_ROCKS_CONFIGURED,
        CountPlacement.of(ConfigHandler.Server.rockGenFrequency()),
        InSquarePlacement.spread(),
        PlacementUtils.HEIGHTMAP_WORLD_SURFACE
    );

}
