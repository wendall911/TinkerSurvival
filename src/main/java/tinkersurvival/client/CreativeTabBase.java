package tinkersurvival.client;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabBase extends CreativeTabs {

    protected Item tabItem;

    public CreativeTabBase(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon(){
        return new ItemStack(tabItem);
    }

    public void setTabItem(Item item){
        tabItem = item;
    }
}
