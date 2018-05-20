package tinkersurvival.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public class ToolLevelNBT extends ModifierNBT {

    private static final String TAG_CXP = "cxp";

    public int cxp;

    public ToolLevelNBT(NBTTagCompound tag) {
        super(tag);
    }

    @Override
    public void read(NBTTagCompound tag) {
        super.read(tag);
        cxp = tag.getInteger(TAG_CXP);
    }

    @Override
    public void write(NBTTagCompound tag) {
        super.write(tag);
        tag.setInteger(TAG_CXP, cxp);
    }

    public static void get(ItemStack tool) {
    }

    public void save(ItemStack tool) {
    }

}
