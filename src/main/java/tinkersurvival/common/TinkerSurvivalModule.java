package tinkersurvival.common;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

import tinkersurvival.config.ConfigHandler;
import tinkersurvival.TinkerSurvival;

public abstract class TinkerSurvivalModule {

    protected static final DeferredRegister<Feature<?>> FEATURE_REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, TinkerSurvival.MODID);
    protected static final DeferredRegister<MobEffect> MOBEFFECT_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TinkerSurvival.MODID);
    protected static final ItemDeferredRegisterExtension ITEM_TCON_REGISTRY = new ItemDeferredRegisterExtension(TinkerSurvival.MODID);
    protected static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, TinkerSurvival.MODID);

    protected TinkerSurvivalModule() {}

    public static void initRegistries(IEventBus bus) {
        MOBEFFECT_REGISTRY.register(bus);
        ITEM_TCON_REGISTRY.register(bus);
        LOOT_MODIFIER_REGISTRY.register(bus);

        if (ConfigHandler.Server.enableRockGen()) {
            FEATURE_REGISTRY.register(bus);
        }
    }

}
