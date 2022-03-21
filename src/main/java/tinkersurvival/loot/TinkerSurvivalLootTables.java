package tinkersurvival.loot;

import com.google.gson.JsonObject;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import tinkersurvival.TinkerSurvival;

public class TinkerSurvivalLootTables {

    public static DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIER_REGISTRY;
    public static RegistryObject<LootTableSerializer> PLANT_FIBER_DROPS;

    public static void init(IEventBus bus) {
        LOOT_MODIFIER_REGISTRY = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, TinkerSurvival.MODID);

        LOOT_MODIFIER_REGISTRY.register(bus);

        PLANT_FIBER_DROPS = LOOT_MODIFIER_REGISTRY.register("plant_fiber_drops", LootTableSerializer::new);
	}

	public static class LootTableSerializer extends GlobalLootModifierSerializer<LootTableModifier> {

		@Override
		public LootTableModifier read(ResourceLocation location, JsonObject json, LootItemCondition[] lootCondition) {
			return new LootTableModifier(
                lootCondition,
                new ItemStack(GsonHelper.getAsItem(json, "item"))
            );
		}

		@Override
		public JsonObject write(LootTableModifier instance) {
            JsonObject jsonObject = makeConditions(instance.getConditions());

            jsonObject.addProperty("item", instance.getStack().getItem().getRegistryName().toString());

            return jsonObject;
		}

	}

	public static class LootTableModifier extends LootModifier {

        private final ItemStack stack;

        public LootTableModifier(LootItemCondition[] conditionsIn, ItemStack itemStack) {
            super(conditionsIn);

            this.stack = itemStack;
        }

        public LootItemCondition[] getConditions() {
            return this.conditions;
        }

        public ItemStack getStack() {
            return stack;
        }

        @Nonnull
        @Override
        protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            generatedLoot.add(stack.copy());
            
            return generatedLoot;
        }

	}

    /*
    public static ResourceLocation TREASURE;

    public static void init() {
        TREASURE = register("gameplay/fishing/treasure");
    }

    private static ResourceLocation register(String path) {
        return LootTableList.register(new ResourceLocation(TinkerSurvival.MODID + ":" + path));
    }
    */

}
