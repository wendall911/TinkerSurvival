package tinkersurvival.recipe.condition;

import com.google.gson.JsonObject;

import java.util.function.BooleanSupplier;

import net.minecraft.item.Item;
import net.minecraft.util.JsonUtils;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ItemExists implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {   
        String key = JsonUtils.getString(json , "item");
        boolean value = JsonUtils.getBoolean(json , "value", true);
        return () -> isExistingItem(key) == value;
    }
    
    public static boolean isExistingItem(String name) {    
        return Item.getByNameOrId(name) != null;
    }

}
