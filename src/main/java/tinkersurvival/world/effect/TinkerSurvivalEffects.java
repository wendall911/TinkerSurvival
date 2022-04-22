package tinkersurvival.world.effect;

import net.minecraft.world.effect.MobEffect;

import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.common.TinkerSurvivalModule;
import tinkersurvival.world.effect.StopBleeding;
import tinkersurvival.world.effect.ZombieEssence;

public final class TinkerSurvivalEffects extends TinkerSurvivalModule {

    public static final RegistryObject<MobEffect> STOP_BLEEDING = MOBEFFECT_REGISTRY.register(
        "stop_bleeding", () -> new StopBleeding()
    );

    public static final RegistryObject<MobEffect> ZOMBIE_ESSENCE = MOBEFFECT_REGISTRY.register(
        "zombie_essence", () -> new ZombieEssence()
    );

}
