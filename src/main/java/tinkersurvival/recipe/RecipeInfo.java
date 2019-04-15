package tinkersurvival.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Map;

class RecipeInfo {
    private int MetaData;
    private String Name;
    private String RegName;
    private ItemStack ItemsStack;
    private String ItemType;

    RecipeInfo(ItemStack items, Map<String, String> woodOreMap) {
        this.ItemsStack = items;

        if (ItemsStack.isEmpty()) return;

        ResourceLocation registryName = this.ItemsStack.getItem().getRegistryName();
        if (registryName != null) {
            this.RegName = registryName.toString();
        }

        this.MetaData = this.ItemsStack.getMetadata();
        this.Name = this.RegName + ":" + OreDictionary.WILDCARD_VALUE;

        this.ItemType = woodOreMap.get(this.Name);

        if (this.ItemType == null) {
            this.ItemType = woodOreMap.get(this.RegName + ":" + this.MetaData);
        }
    }

    int getMetaData(){
        return this.MetaData;
    }

    String getName(){
        return this.Name;
    }

    String getRegName(){
        return this.RegName;
    }

    ItemStack getItemsStack(){
        return this.ItemsStack;
    }

    String getItemType(){
        return this.ItemType;
    }
}
