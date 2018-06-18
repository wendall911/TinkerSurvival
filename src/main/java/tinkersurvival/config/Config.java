package tinkersurvival.config;

import net.minecraftforge.common.config.Config.Comment;

import tinkersurvival.TinkerSurvival;

@net.minecraftforge.common.config.Config(modid=TinkerSurvival.MODID)
public class Config {

    public static Tools tools;
    public static class Tools {
        @Comment({"List of mods that tools will always work for. All other mod tools will become wet noodles."})
        public static String[] MOD_TOOL_WHITELIST = new String[] {
            "immersiveengineering",
            "opencomputers",
            "tconstruct",
            "tinkersurvival"
        };

        @Comment({"List of individual tools that will always work. Format tooltype-modid:item"})
        public static String[] TOOLS_WHITELIST = new String[] {
            "shears-minecraft:shears"
        };
    }

    public static Armor armor;
    public static class Armor {
        @Comment({"List of mods that armor will always work and be craftable for. All other mod armor will not be equipable or craftable."})
        public static String[] MOD_ARMOR_WHITELIST = new String[] {
            "immersiveengineering",
            "conarm"
        };

        @Comment({"List of individual armor items that will always work. Format modid:item"})
        public static String[] ARMOR_WHITELIST = new String[] {
            "tconstruct:piggybackpack"
        };

        @Comment({"List of all armor with temperature modifiers. Format modid:item=modifier"})
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

        @Comment({"Constructs' Armory wool insulation modifier amount."})
        public static int INSULATED_MODIFIER=1;

        @Comment({"Constructs' Armory jelled slime cooling modifier amount."})
        public static int CHILLING_MODIFIER=-1;
    }

    public static Balance balance;
    public static class Balance {
        @Comment({"Chance for a rocks to generate on surface. Default 100% - 1.0D"})
        public static double ROCKGEN_CHANCE = 1.0D;

        @Comment({"Chance for a sucessful flint knapping. Default 60% - 0.6D"})
        public static double FLINT_CHANCE = 0.6D;

        @Comment({"Chance for tall grass to drop plant fibers. Default 60% - 0.6D. Knives are 40% more effective."})
        public static double GRASS_FIBER_CHANCE = 0.5D;

        @Comment({"Heal rate for bandages. Crude bandages are 50% less effective."})
        public static double HEAL_RATE = 0.14D;
    }

    public static Features features;
    public static class Features {
        @Comment({"Stop enderman griefing, wtf Notch."})
        public static boolean NO_GRIEFING = true;

        @Comment({"Ok, I love endermen, make them pick up this stuff. If NO_GRIEFING=true"})
        public static String[] GRIEFING_WHITELIST = new String[] {
            "minecraft:red_flower",
            "minecraft:yellow_flower"
        };

        @Comment({"I cry myself to sleep at night..."})
        public static boolean NO_SLEEPING = true;
    }

}
