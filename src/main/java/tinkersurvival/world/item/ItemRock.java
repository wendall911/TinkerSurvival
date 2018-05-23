package tinkersurvival.world.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import tinkersurvival.TinkerSurvival;

public class ItemRock extends ItemBase {

    public static String name;

    public static enum Type {
        STONE("stone"), ANDESITE("andesite"), DIORITE("diorite"), GRANITE("granite");

        public final String name;

        Type(String name) {
            this.name = name;
        }
    }

    public ItemRock(String name) {
        super(name, Type.values().length);
        this.name = name;
        this.setMaxDamage(0);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "_" + getStoneName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (getCreativeTab() != tab) {
            return;
        }

        for (Type type : Type.values()) {
            items.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    public String getStoneName(ItemStack stack) {
        return Type.values()[stack.getMetadata()].name;
    }

}
