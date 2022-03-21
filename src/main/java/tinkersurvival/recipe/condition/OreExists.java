package tinkersurvival.recipe.condition;

import com.google.gson.JsonObject;

import java.util.function.BooleanSupplier;

/*
import net.minecraft.util.JsonUtils;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;
*/

/*
public class OreExists implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {   
        String key = JsonUtils.getString(json , "ore");
        boolean value = JsonUtils.getBoolean(json , "value", true);
        return () -> isExistingOreName(key) == value;
    }
    
    public static boolean isExistingOreName(String name) {    
        if (!OreDictionary.doesOreNameExist(name)) {
            return false;
        }
        else {
            return !OreDictionary.getOres(name).isEmpty();
        }
    }

}
*/
