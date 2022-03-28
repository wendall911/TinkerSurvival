package tinkersurvival.common;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.mantle.registration.deferred.FluidDeferredRegister;

import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.common.registration.ItemDeferredRegisterExtension;

import tinkersurvival.data.integration.ModIntegration;
import tinkersurvival.TinkerSurvival;

public abstract class TinkerSurvivalModule {

    protected static DeferredRegister<Block> BLOCK_REGISTRY;
    protected static DeferredRegister<Item> ITEM_REGISTRY;
    protected static DeferredRegister<Feature<?>> FEATURE_REGISTRY;
    protected static DeferredRegister<MobEffect> MOBEFFECT_REGISTRY;
    protected static BlockDeferredRegisterExtension METAL_BLOCK_REGISTRY;
    protected static ItemDeferredRegisterExtension TOOL_REGISTRY;
    protected static FluidDeferredRegister FLUID_REGISTRY;
    protected static DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_REGISTRY;
    protected static DeferredRegister<Item> FRUITTREES_ITEM_REGISTRY;
    protected static DeferredRegister<Item> BMO_ITEM_REGISTRY;
    protected static DeferredRegister<Item> BOP_ITEM_REGISTRY;
    protected static DeferredRegister<Item> QUARK_ITEM_REGISTRY;
    protected static DeferredRegister<Item> AYCE_ITEM_REGISTRY;
    protected static DeferredRegister<Item> TCON_ITEM_REGISTRY;

    public static void initRegistries(IEventBus bus) {
        MOBEFFECT_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TinkerSurvival.MODID);
        BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, TinkerSurvival.MODID);
        ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TinkerSurvival.MODID);
        METAL_BLOCK_REGISTRY = new BlockDeferredRegisterExtension(TinkerSurvival.MODID);
        TOOL_REGISTRY = new ItemDeferredRegisterExtension(TinkerSurvival.MODID);
        FLUID_REGISTRY = new FluidDeferredRegister(TinkerSurvival.MODID);
        LOOT_MODIFIER_REGISTRY = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, TinkerSurvival.MODID);
        FRUITTREES_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModIntegration.FT_MODID);
        BMO_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModIntegration.BMO_MODID);
        BOP_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModIntegration.BOP_MODID);
        QUARK_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModIntegration.QUARK_MODID);
        AYCE_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModIntegration.AYCE_MODID);
        TCON_ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, ModIntegration.TCON_MODID);

        MOBEFFECT_REGISTRY.register(bus);
        BLOCK_REGISTRY.register(bus);
        ITEM_REGISTRY.register(bus);
        METAL_BLOCK_REGISTRY.register(bus);
        TOOL_REGISTRY.register(bus);
        FLUID_REGISTRY.register(bus);
        LOOT_MODIFIER_REGISTRY.register(bus);
        FRUITTREES_ITEM_REGISTRY.register(bus);
        BMO_ITEM_REGISTRY.register(bus);
        BOP_ITEM_REGISTRY.register(bus);
        QUARK_ITEM_REGISTRY.register(bus);
        AYCE_ITEM_REGISTRY.register(bus);
        TCON_ITEM_REGISTRY.register(bus);
    }

}
