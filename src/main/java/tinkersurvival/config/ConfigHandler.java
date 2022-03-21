package tinkersurvival.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import org.apache.commons.lang3.tuple.Pair;

import tinkersurvival.TinkerSurvival;

public class ConfigHandler {

    public static final ForgeConfigSpec CONFIG_SPEC;
    private static final ConfigHandler CONFIG;

    public static BooleanValue enableRockGen;
    public static IntValue rockGenFrequency;
    public static BooleanValue enableRockFromDirt;
    public static DoubleValue rockFromDirtChance;
    public static DoubleValue flintChance;
    public static DoubleValue grassFiberChance;
    public static DoubleValue healRate;
    public static DoubleValue slowDownMultiplier;
    public static DoubleValue stickDropChanceHand;
    public static DoubleValue stickDropChanceKnife;

    static {
        Pair<ConfigHandler,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigHandler::new);

        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    ConfigHandler(ForgeConfigSpec.Builder builder) {
        enableRockGen = builder
            .comment("Enables the generation of rock piles on the surface.")
            .define("enableRockGen", true);
        rockGenFrequency = builder
            .comment("RockGeneration frequency. (1 = low, 5 = all over)")
            .defineInRange("rockGenFrequency", 2, 1, 5);
        enableRockFromDirt = builder
            .comment("Enables rock drop from harvesting dirt.")
            .define("enableRockFromDirt", false);
        rockFromDirtChance = builder
            .comment("Chance for a rocks to drop from harvesting dirt with bare hands. (1.0 = 100%, 0.4 = 40%, etc.)")
            .defineInRange("rockFromDirtChance ", 0.4, 0.1, 1.0);
        flintChance = builder
            .comment("Chance for a successful flint knapping. (1.0 = 100%, 0.4 = 40%, etc.)")
            .defineInRange("flintChance", 0.6, 0.1, 1.0);
        grassFiberChance = builder
            .comment("Chance for tall grass to drop plant fibers. Knives are 40% more effective.  (1.0 = 100%, 0.4 = 40%, etc.)")
            .defineInRange("grassFiberChance", 0.5, 0.1, 1.0);
        healRate = builder
            .comment("Heal rate for bandages. Crude bandages are 50% less effective. (1.0 = 100%, 0.4 = 40%, etc.)")
            .defineInRange("healRate", 0.14, 0.1, 1.0);
        slowDownMultiplier = builder
            .comment("Option to adjust slow down on wrong tool usage. (1.0 = 100%, 2.0 = 200%, etc.)")
            .defineInRange("slowDownMultiplier", 1.0, 1.0, 5.0);
        stickDropChanceHand = builder
            .comment("Chance for stick drip from breaking leaves by hand. (1.0 = 100%, 0.4 = 40%, etc.)")
            .defineInRange("stickDropChanceHand", 0.2, 0.1, 1.0);
        stickDropChanceKnife = builder
            .comment("Chance for stick drip from breaking leaves with a knife (1.0 = 100%, 0.4 = 40%, etc.)")
            .defineInRange("stickDropChanceKnife", 0.5, 0.1, 1.0);
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

    public static Client client;
    public static class Client {
        @Config.Comment({"Enables the fail sound if using the wrong tool."})
        public static boolean ENABLE_FAIL_SOUND = true;
    }
    */

    public static boolean enableRockGen() {
        return CONFIG.enableRockGen.get();
    }
    
    public static int rockGenFrequency() {
        return CONFIG.rockGenFrequency.get();
    }
    
    public static boolean enableRockFromDirt() {
        return CONFIG.enableRockFromDirt.get();
    }
    
    public static double rockFromDirtChance() {
        return CONFIG.rockFromDirtChance .get();
    }
    
    public static double flintChance() {
        return CONFIG.flintChance.get();
    }
    
    public static double grassFiberChance() {
        return CONFIG.grassFiberChance.get();
    }
    
    public static double healRate() {
        return CONFIG.healRate.get();
    }
    
    public static double slowDownMultiplier() {
        return CONFIG.slowDownMultiplier.get();
    }
    
    public static double stickDropChanceHand() {
        return CONFIG.stickDropChanceHand.get();
    }
    
    public static double stickDropChanceKnife() {
        return CONFIG.stickDropChanceKnife.get();
    }

}
