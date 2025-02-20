package tinkersurvival.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import tinkersurvival.TinkerSurvival;

public final class TagManager {

    public static final class Items {
        public static final TagKey<Item> SAW_BLADE_CAST = getItemTag(TinkerSurvival.MODID, "casts/multi_use/saw_blade");
        public static final TagKey<Item> SAW_BLADE_CAST_SINGLE = getItemTag(TinkerSurvival.MODID, "casts/single_use/saw_blade");

        private static TagKey<Item> getItemTag(String modid, String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(modid, name));
        }
    }

}
