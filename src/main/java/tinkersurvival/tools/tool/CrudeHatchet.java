package tinkersurvival.tools.tool;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class CrudeHatchet extends ItemAxe {

    public String name;

    public CrudeHatchet(ToolMaterial material, String name) {
        super(material, 4.0F, -3.0F);

        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);

        setNoRepair();
        setMaxStackSize(1);
        setContainerItem(this);
    }

}
