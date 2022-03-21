package tinkersurvival.loot;

import com.google.gson.JsonObject;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.world.TinkerSurvivalWorld;

@EventBusSubscriber(modid = TinkerSurvival.MODID, bus = Bus.MOD)
public class TinkerSurvivalLootTables {

    /*
    public static ResourceLocation TREASURE;

    public static void init() {
        TREASURE = register("gameplay/fishing/treasure");
    }

    private static ResourceLocation register(String path) {
        return LootTableList.register(new ResourceLocation(TinkerSurvival.MODID + ":" + path));
    }
    */

	@SubscribeEvent
	public static void registerModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry().register(
            new LootTableSerializer().setRegistryName(TinkerSurvival.MODID, "grass_fiber_drops")
		);
	}

	public static class LootTableSerializer extends GlobalLootModifierSerializer<LootTableModifier> {

		@Override
		public LootTableModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
			return new LootTableModifier(ailootcondition);
		}

		@Override
		public JsonObject write(LootTableModifier instance) {
			return new JsonObject();
		}

	}

	private static class LootTableModifier extends LootModifier {

		protected LootTableModifier(LootItemCondition[] conditionsIn) {
			super(conditionsIn);
		}

		@Nonnull
		@Override
		protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
			generatedLoot.add(new ItemStack(TinkerSurvivalWorld.GRASS_FIBER.get()));
			return generatedLoot;
		}

	}

}
