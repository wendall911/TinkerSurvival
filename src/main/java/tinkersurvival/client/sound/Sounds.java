package tinkersurvival.client.sound;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import tinkersurvival.TinkerSurvival;

import java.util.ArrayList;
import java.util.List;

public class Sounds {
    private static final List<SoundEvent> sounds = new ArrayList<>();

    public static final SoundEvent ARMOR_FAIL = registerSound("armor_fail");
    public static final SoundEvent BOW_FAIL = registerSound("bow_fail");
    public static final SoundEvent FLINT_KNAPPING = registerSound("knapping");
    public static final SoundEvent HOE_FAIL = registerSound("hoe_fail");
    public static final SoundEvent SWORD_FAIL = registerSound("sword_fail");
    public static final SoundEvent TOOL_FAIL = registerSound("tool_fail");

    private static SoundEvent registerSound(String name){
        ResourceLocation location = new ResourceLocation(TinkerSurvival.MODID,name);
        SoundEvent sound = new SoundEvent(location).setRegistryName(location);
		sounds.add(sound);
        return sound;
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
            IForgeRegistry<SoundEvent> registry = event.getRegistry();
            sounds.forEach(registry::register);
        }
    }

	public static void play(Entity entity, SoundEvent sound, float volume, float pitch) {
		if (entity instanceof EntityPlayerMP) {
            entity.getEntityWorld().playSound(null, entity.getPosition(), sound, entity.getSoundCategory(), volume, pitch);
        }
	}
}
