package tinkersurvival.config;

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

        public static BooleanValue ENABLE_FAIL_SOUND;

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

        Common(ForgeConfigSpec.Builder builder) {
        }

    }

    public static final class Server {

        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Server CONFIG;

        public static BooleanValue ENABLE_ROCK_GEN;
        public static IntValue ROCK_GEN_FREQUENCY;
        public static DoubleValue FLINT_CHANCE;
        public static DoubleValue GRASS_FIBER_BONUS_CHANCE;
        public static DoubleValue HEAL_RATE;
        public static DoubleValue SLOW_DOWN_MULTIPLIER;

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
            GRASS_FIBER_BONUS_CHANCE = builder
                .comment("Chance for grass to drop bonus plant fibers with a knife. (1.0 = 100%, 0.4 = 40%, etc.)")
                .defineInRange("GRASS_FIBER_BONUS_CHANCE", 0.5, 0.1, 1.0);
            HEAL_RATE = builder
                .comment("Heal rate for bandages. Crude bandages are 50% less effective. (1.0 = 100%, 0.4 = 40%, etc.)")
                .defineInRange("HEAL_RATE", 0.14, 0.1, 1.0);
            SLOW_DOWN_MULTIPLIER = builder
                .comment("Option to adjust slow down on wrong tool usage. (1.0 = 100%, 2.0 = 200%, etc.)")
                .defineInRange("SLOW_DOWN_MULTIPLIER", 1.0, 1.0, 5.0);
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
        
        public static double grassFiberBonusChance() {
            return CONFIG.GRASS_FIBER_BONUS_CHANCE.get();
        }
        
        public static double healRate() {
            return CONFIG.HEAL_RATE.get();
        }
        
        public static double slowDownMultiplier() {
            return CONFIG.SLOW_DOWN_MULTIPLIER.get();
        }
        
    }
    /*
    public static Features features;
    public static class Features {

        @Config.RequiresMcRestart
        @Config.Comment({"Stop enderman griefing, wtf Notch."})
        public static boolean NO_GRIEFING = true;

        @Config.Comment({"Ok, I love endermen, make them pick up this stuff. If NO_GRIEFING=true"})
        public static String[] GRIEFING_WHITELIST = new String[] {
            "minecraft:red_flower",
            "minecraft:yellow_flower"
        };

        @Config.Comment({"I cry myself to sleep at night..."})
        public static boolean NO_SLEEPING = true;

        @Config.Comment({"Includes dirt in the slowdown of mining speed with bare hands. Default true"})
        public static boolean SLOW_DOWN_DIRT_PUNCHING = true;

        @Config.RequiresMcRestart
        @Config.Comment({"Enables the saw item to craft planks. Default true"})
        public static boolean ENABLE_SAW = true;

        @Config.RequiresMcRestart
        @Config.Comment({"Removes plank and stick recipes from the game, only craftable with saw. Default true"})
        public static boolean FORCE_SAW_FOR_PLANKS = true;
    }

    */

}
