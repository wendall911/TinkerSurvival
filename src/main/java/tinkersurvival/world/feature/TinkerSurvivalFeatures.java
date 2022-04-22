package tinkersurvival.world.feature;

import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.world.feature.LooseRocks;

public final class TinkerSurvivalFeatures extends TinkerSurvivalModule {

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> LOOSE_ROCKS_FEATURE = FEATURE_REGISTRY.register(
        "loose_rocks",
        LooseRocks::new
    );

}
