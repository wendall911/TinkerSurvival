package tinkersurvival.tools.tool;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class CrudeSaw extends ItemAxe {

    public String name;

    public CrudeSaw(ToolMaterial material, String name) {
        super(material,1.0F,-3.0F);

        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);

        setNoRepair();
        setMaxStackSize(1);
        setContainerItem(this);
    }

}
