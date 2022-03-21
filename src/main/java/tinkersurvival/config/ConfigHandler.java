package tinkersurvival.config;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import org.apache.commons.lang3.tuple.Pair;

import tinkersurvival.TinkerSurvival;

@Mod.EventBusSubscriber(modid = TinkerSurvival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ConfigHandler {

    private ConfigHandler() {}

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Client.CONFIG_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Common.CONFIG_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Server.CONFIG_SPEC);
    }

    public static final class Client {

        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Client CONFIG;

        private static BooleanValue ENABLE_FAIL_SOUND;

        static {
            Pair<Client,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Client(ForgeConfigSpec.Builder builder) {
            ENABLE_FAIL_SOUND = builder
                .comment("Enables the fail sound if using the wrong tool.")
                .define("ENABLE_FAIL_SOUND", true);
        }

        public static boolean enableFailSound() {
            return CONFIG.ENABLE_FAIL_SOUND.get();
        }

    }

    public static final class Common {

        public static final ForgeConfigSpec CONFIG_SPEC;

        private static final Common CONFIG;

        static {
            Pair<Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Common(ForgeConfigSpec.Builder builder) {}

    }

    public static final class Server {

        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Server CONFIG;

        private static BooleanValue ENABLE_ROCK_GEN;
        private static IntValue ROCK_GEN_FREQUENCY;
        private static DoubleValue FLINT_CHANCE;
        private static DoubleValue HEAL_RATE;
        private static DoubleValue SLOW_DOWN_MULTIPLIER;

        private static final List<String> MODS_LIST = Arrays.asList("mods");
        private static String[] modsStrings = new String[] {
            "tconstruct",
            "tinkersurvival"
        };
        private static Predicate<Object> modidValidator = s -> s instanceof String
                && ((String) s).matches("[a-z]+");
        private final ConfigValue<List<? extends String>> MODS;

        private static final List<String> ITEMS_LIST = Arrays.asList("items");
        private static String[] itemsStrings = new String[] {
            "shovel-immersiveengineering:drill",
            "pickaxe-immersiveengineering:drill",
            "axe-immersiveengineering:buzzsaw",
            "pickaxe-immersiveengineering:buzzsaw",
            "weapon-immersiveengineering:revolver",
            "weapon-immersiveengineering:revolver",
            "hammer-immersiveengineering:hammer",
            "wirecutter-immersiveengineering:wirecutter",
        };
        private static Predicate<Object> itemidValidator = s -> s instanceof String
                && ((String) s).matches("[a-z]+[-]{1}[a-z]+[:]{1}[a-z_]+");
        private final ConfigValue<List<? extends String>> ITEMS;

        static {
            Pair<Server,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Server(ForgeConfigSpec.Builder builder) {
            ENABLE_ROCK_GEN = builder
                .comment("Enables the generation of rock piles on the surface.")
                .define("ENABLE_ROCK_GEN", true);
            ROCK_GEN_FREQUENCY = builder
                .comment("RockGeneration frequency. (1 = low, 5 = all over)")
                .defineInRange("ROCK_GEN_FREQUENCY", 2, 1, 5);
            FLINT_CHANCE = builder
                .comment("Chance for a successful flint knapping. (1.0 = 100%, 0.4 = 40%, etc.)")
                .defineInRange("FLINT_CHANCE", 0.6, 0.1, 1.0);
            HEAL_RATE = builder
                .comment("Heal rate for bandages. Crude bandages are 50% less effective. (1.0 = 100%, 0.4 = 40%, etc.)")
                .defineInRange("HEAL_RATE", 0.14, 0.1, 1.0);
            SLOW_DOWN_MULTIPLIER = builder
                .comment("Option to adjust slow down on wrong tool usage. (1.0 = 100%, 2.0 = 200%, etc.)")
                .defineInRange("SLOW_DOWN_MULTIPLIER", 1.0, 1.0, 5.0);
            MODS = builder
                .comment("List of mods that tools will always work for. All other mod tools will become wet noodles. Default: "
                        + "[\"" + String.join("\", \"", modsStrings) + "\"]")
                .defineListAllowEmpty(MODS_LIST, getFields(modsStrings), modidValidator);
            ITEMS = builder
                .comment("List of individual tools that will always work. Format tooltype-modid:item Default: "
                        + "[\"" + String.join("\", \"", itemsStrings) + "\"]")
                .defineListAllowEmpty(ITEMS_LIST, getFields(itemsStrings), itemidValidator);
        }

        public static boolean enableRockGen() {
            return CONFIG.ENABLE_ROCK_GEN.get();
        }

        public static int rockGenFrequency() {
            return CONFIG.ROCK_GEN_FREQUENCY.get();
        }

        public static double flintChance() {
            return CONFIG.FLINT_CHANCE.get();
        }

        public static double healRate() {
            return CONFIG.HEAL_RATE.get();
        }

        public static double slowDownMultiplier() {
            return CONFIG.SLOW_DOWN_MULTIPLIER.get();
        }

        private static Supplier<List<? extends String>> getFields(String[] strings) {
            return () -> Arrays.asList(strings);
        }

        public static List<String> whitelistMods() {
            List<String> mods = (List<String>) CONFIG.MODS.get();

            return mods;
        }

        public static List<String> whitelistItems() {
            List<String> items = (List<String>) CONFIG.ITEMS.get();

            return items;
        }

    }

}
