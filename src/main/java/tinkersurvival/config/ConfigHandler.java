package tinkersurvival.config;

import net.minecraftforge.common.config.Config;

import tinkersurvival.TinkerSurvival;

@Config(modid=TinkerSurvival.MODID)
public class ConfigHandler {

    public static Tools tools;
    public static class Tools {
        @Config.Comment({"List of mods that tools will always work for. All other mod tools will become wet noodles."})
        public static String[] MOD_TOOL_WHITELIST = new String[] {
            "opencomputers",
            "tconstruct",
            "tinkersurvival"
        };

        @Config.Comment({"List of individual tools that will always work. Format tooltype-modid:item"})
        public static String[] TOOLS_WHITELIST = new String[] {
            "shears-minecraft:shears",
            "hammer-immersiveengineering:tool",
            "wirecutter-immersiveengineering:tool",
            "sword-immersiveengineering:revolver",
            "sword-immersiveengineering:railgun",
            "pickaxe-immersiveengineering:drill"
        };
    }

    public static Armor armor;
    public static class Armor {
        @Config.Comment({"List of mods that armor will always work and be craftable for. All other mod armor will not be equipable or craftable."})
        public static String[] MOD_ARMOR_WHITELIST = new String[] {
            "immersiveengineering",
            "conarm"
        };

        @Config.Comment({"List of individual armor items that will always work. Format modid:item"})
        public static String[] ARMOR_WHITELIST = new String[] {
            "tconstruct:piggybackpack"
        };

        @Config.Comment({"List of all armor with temperature modifiers. Format modid:item=modifier"})
        public static String[] ARMOR_TEMP_MODIFIERS = new String[] {
            "tinkersurvival:reinforced_wool_helmet=1",
            "tinkersurvival:reinforced_wool_chestplate=1",
            "tinkersurvival:reinforced_wool_leggings=1",
            "tinkersurvival:reinforced_wool_boots=1",
            "tinkersurvival:reinforced_jelled_slime_helmet=-1",
            "tinkersurvival:reinforced_jelled_slime_chestplate=-1",
            "tinkersurvival:reinforced_jelled_slime_leggings=-1",
            "tinkersurvival:reinforced_jelled_slime_boots=-1"
        };

        @Config.Comment({"Construct's Armory wool insulation modifier amount."})
        public static int INSULATED_MODIFIER = 1;

        @Config.Comment({"Construct's Armory jelled slime cooling modifier amount."})
        public static int CHILLING_MODIFIER = -1;
    }

    public static Balance balance;
    public static class Balance {
        @Config.Comment({"Enables the generation of rock piles on the surface. Default true"})
        public static boolean ENABLE_ROCKGEN = true;

        @Config.Comment({"Chance for a rocks to generate on surface. Default 1.0D (= 100%)"})
        public static double ROCKGEN_CHANCE = 1.0D;

        @Config.Comment({"Enables rock drop from harvesting dirt. (default: false)"})
        public static boolean ENABLE_ROCK_FROM_DIRT = false;

        @Config.Comment({"Chance for a rocks to drop from harvesting dirt with bare hands. Default 0.4 (= 40%)"})
        public static double ROCK_FROM_DIRT_CHANCE = 0.4D;

        @Config.Comment({"Chance for a successful flint knapping. Default 60% - 0.6D"})
        public static double FLINT_CHANCE = 0.6D;

        @Config.Comment({"Chance for tall grass to drop plant fibers. Default 60% - 0.6D. Knives are 40% more effective."})
        public static double GRASS_FIBER_CHANCE = 0.5D;

        @Config.Comment({"Heal rate for bandages. Crude bandages are 50% less effective."})
        public static double HEAL_RATE = 0.14D;

        @Config.Comment({"Option to adjust slow down on wrong tool usage. Default 1.0)"})
        public static float SLOW_DOWN_MULTIPLIER = 1.0F;

        @Config.Comment({"Chance for stick drip from breaking leaves by hand. Default 20% - 0.2F)"})
        public static float STICK_DROP_CHANCE_HAND = 0.2F;

        @Config.Comment({"Chance for stick drip from breaking leaves with a knife Default 50% - 0.5F)"})
        public static float STICK_DROP_CHANCE_KNIFE = 0.5F;
    }

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

}
