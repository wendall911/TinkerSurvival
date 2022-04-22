package tinkersurvival.items;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;

import tinkersurvival.items.CrudeItemTier;

public class CrudeItemTiers {

    public static final Tier FLINT_TIER = new CrudeItemTier().setMaxUses(20).setEfficiency(1.5F)
        .setAttackDamage(0.5F).setRepairMat(Items.FLINT);
    public static final Tier STONE_TIER = new CrudeItemTier().setMaxUses(5).setEfficiency(1.5F)
        .setAttackDamage(0.5F).setRepairMat(Items.COBBLESTONE);
    public static final Tier WOOD_TIER = new CrudeItemTier().setMaxUses(20).setEfficiency(0.0F)
        .setAttackDamage(0.0F).setRepairMat(ItemTags.PLANKS);

}
