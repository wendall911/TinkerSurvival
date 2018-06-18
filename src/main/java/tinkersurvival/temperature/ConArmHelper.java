package tinkersurvival.temperature;

import c4.conarm.common.armor.utils.ArmorHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import slimeknights.tconstruct.library.utils.TagUtil;

import tinkersurvival.integrations.ArmorMaterials;

public class ConArmHelper {

    public static int getArmorTemp(ItemStack stack, int chill, int insulate) {
        int temp = 0;

        if (ArmorHelper.isUnbrokenTinkersArmor(stack)) {
            NBTTagList list = TagUtil.getModifiersTagList(stack);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound compound = list.getCompoundTagAt(i);
                String identifier = compound.getString("identifier");
                if (identifier.equals(ArmorMaterials.insulated.getIdentifier())) {
                    temp += insulate;
                }
                if (identifier.equals(ArmorMaterials.chilling.getIdentifier())) {
                    temp += chill;
                }
            }
        }

        return temp;
    }

}
