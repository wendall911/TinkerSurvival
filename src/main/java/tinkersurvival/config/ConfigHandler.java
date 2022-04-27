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
        private static BooleanValue ENABLE_HUNGER_PENALTY;
        private static IntValue HUNGER;
        private static IntValue SATURATION;

        private static final List<String> MODS_LIST = Arrays.asList("mods");
        private static final String[] modsStrings = new String[] {
            "tconstruct",
            "tinkersurvival"
        };
        private static final Predicate<Object> modidValidator = s -> s instanceof String
                && ((String) s).matches("[a-z]+");
        private final ConfigValue<List<? extends String>> MODS;

        private static final List<String> ITEMS_LIST = Arrays.asList("items");
        private static final String[] itemsStrings = new String[] {
            "hammer-immersiveengineering:hammer",
            "wirecutter-immersiveengineering:wirecutter",
        };
        private static Predicate<Object> itemidValidator = s -> s instanceof String
                && ((String) s).matches("[a-z]+[-]{1}[a-z]+[:]{1}[a-z_]+");
        private final ConfigValue<List<? extends String>> ITEMS;

        private static BooleanValue LOG_MODPACK_DATA;

        private static final List<String> BLOCK_MODS_LIST = Arrays.asList("blockmods");
        private static final String[] blockModsStrings = new String[] {
            "cfm",
            "furnish"
        };
        private final ConfigValue<List<? extends String>> BLOCK_MODS;

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
            LOG_MODPACK_DATA = builder
                .comment("Used to dump log info for Tinkers's Survival Modpack. Ignore.")
                .define("LOG_MODPACK_DATA", false);
            BLOCK_MODS = builder
                .comment("List of mods that have blocks that are generally decorative in nature and require no tool for harvesting blocks. Default: "
                        + "[\"" + String.join("\", \"", blockModsStrings) + "\"]")
                .defineListAllowEmpty(BLOCK_MODS_LIST, getFields(blockModsStrings), modidValidator);
            ENABLE_HUNGER_PENALTY = builder
                .comment("Hunger penalty feature. If after dying, player is rewarded with reduced hunger levels.")
                .define("ENABLE_HUNGER_PENALTY", false);
            HUNGER = builder
                .comment("Hunger value after death. (0 = Really? That's just cruel, 20 = No penalty.)")
                .defineInRange("HUNGER", 8, 0, 20);
            SATURATION = builder
                .comment("Saturation value after death. Range 0 to 20.")
                .defineInRange("SATURATION", 0, 0, 20);
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

        public static boolean logModpackData() {
            return CONFIG.LOG_MODPACK_DATA.get();
        }

        public static List<String> blockWhitelistMods() {
            List<String> mods = (List<String>) CONFIG.BLOCK_MODS.get();

            return mods;
        }

        public static boolean enableHungerPenalty() {
            return CONFIG.ENABLE_HUNGER_PENALTY.get();
        }

        public static int hunger() {
            return CONFIG.HUNGER.get();
        }

        public static int saturation() {
            return CONFIG.SATURATION.get();
        }

    }

}
