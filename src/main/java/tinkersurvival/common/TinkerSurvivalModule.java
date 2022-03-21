package tinkersurvival.common;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

import tinkersurvival.TinkerSurvival;

public abstract class TinkerSurvivalModule {

    protected static DeferredRegister<Block> BLOCK_REGISTRY;
    protected static DeferredRegister<Item> ITEM_REGISTRY;
    protected static DeferredRegister<Feature<?>> FEATURE_REGISTRY;
    protected static DeferredRegister<MobEffect> MOBEFFECT_REGISTRY;
    protected static ItemDeferredRegisterExtension TOOL_REGISTRY;
    protected static DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_REGISTRY;

    public static void initRegistries(IEventBus bus) {
        MOBEFFECT_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TinkerSurvival.MODID);
        BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TinkerSurvival.MODID);
        ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TinkerSurvival.MODID);
        TOOL_REGISTRY = new ItemDeferredRegisterExtension(TinkerSurvival.MODID);
        LOOT_MODIFIER_REGISTRY = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, TinkerSurvival.MODID);

        MOBEFFECT_REGISTRY.register(bus);
        BLOCK_REGISTRY.register(bus);
        ITEM_REGISTRY.register(bus);
        TOOL_REGISTRY.register(bus);
        LOOT_MODIFIER_REGISTRY.register(bus);
    }

}
