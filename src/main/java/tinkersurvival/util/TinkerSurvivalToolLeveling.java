package tinkersurvival.util;

/*
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ProjectileModifierTrait;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.TinkersItem;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;

import slimeknights.toolleveling.config.Config;

import tinkersurvival.util.ToolLevelNBT;

public class TinkerSurvivalToolLeveling extends ProjectileModifierTrait {

    public TinkerSurvivalToolLeveling() {
        super("crafty", 0xffffff);

        aspects.clear();
        addAspects(new ModifierAspect.DataAspect(this));

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    private ToolLevelNBT getLevelData(NBTTagCompound modifierNBT) {
        return new ToolLevelNBT(modifierNBT);
    }

    public ToolLevelNBT getLevelData(ItemStack itemStack) {
        return getLevelData(TinkerUtil.getModifierTag(itemStack, getModifierIdentifier()));
    }

    public void resetCxp(ItemStack tool) {
        applyCxp(tool, 0, false);
    }

    public void addCxp(ItemStack tool, int amount) {
        applyCxp(tool, amount, true);
    }

    private void applyCxp(ItemStack tool, int amount, boolean add) {
        NBTTagList tagList = TagUtil.getModifiersTagList(tool);
        int index = TinkerUtil.getIndexInCompoundList(tagList, identifier);
        NBTTagCompound modifierTag = tagList.getCompoundTagAt(index);

        ToolLevelNBT data = getLevelData(modifierTag);
        if (add) {
            data.cxp += amount;
        }
        else {
            data.cxp = amount;
        }

        data.write(modifierTag);
        TagUtil.setModifiersTagList(tool, tagList);

        this.apply(tool);
    }

}
*/
