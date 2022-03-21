package tinkersurvival.tools.tool;

import com.google.common.collect.Sets;

import java.util.Set;

import tinkersurvival.TinkerSurvival;

/*
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
*/

/*
public class CrudeKnife extends ItemTool {

    private static final Set<Block> EFFECTIVE_ON =
        Sets.newHashSet(
            Blocks.TALLGRASS,
            Blocks.DOUBLE_PLANT,
            Blocks.MELON_BLOCK,
            Blocks.PUMPKIN,
            Blocks.WOOL
        );

    public String name;

    public CrudeKnife(Item.ToolMaterial material, String name){
        super(1.0F, -2.0F, material, EFFECTIVE_ON);
        setTranslationKey(name);
        setRegistryName(name);
        this.name = name;
        setCreativeTab(TinkerSurvival.TS_Tab);
    }

    public boolean shouldBreakBlock(Block block) {
        return EFFECTIVE_ON.contains(block);
    }

    public boolean shouldDamageItem(Block block){
        return (block instanceof BlockTallGrass || block instanceof BlockDoublePlant);
    }

}
*/
