package tinkersurvival.config;

import tinkersurvival.TinkerSurvival;
import net.minecraftforge.common.config.Config.Comment;

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
    }

    public static Balance balance;
    public static class Balance {
        @Comment({"Chance for a sucessful flint knapping"})
        public static double FLINT_CHANCE = 0.6D;

        @Comment({"Chance for tall grass to drop plant fibers"})
        public static double GRASS_FIBER_CHANCE = 0.5D;
    }

    public static Features features;
    public static class Features {
        @Comment({"Stop enderman griefing"})
        public static boolean NO_GRIEFING = true;
    }
}
