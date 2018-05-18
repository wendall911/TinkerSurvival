package tinkersurvival.world.item;

import net.minecraft.item.Item;

public class ItemBase extends Item {

    public String name;

    public ItemBase(String name){
        super();

        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
    }
}
