package tinkersurvival.world.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import tinkersurvival.TinkerSurvival;

public class ItemRock extends ItemBase {

    public ItemRock(String name){
        super(name);

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "_" + getStoneName(stack);
    }
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (getCreativeTab() != tab) {
            return;
        }
        for (int i = 0; i < 4; ++i) {
            items.add(new ItemStack(this, 1, i));
        }
    }

    public String getStoneName(ItemStack stack){
        switch(stack.getMetadata()){
            case 0:
                return "stone"; // Vanilla Stone
            case 1:
                return "andesite"; // Vanilla Stone Variants
            case 2:
                return "diorite";
            case 3:
                return "granite";
            default:
                return "";
        }
    }
}
