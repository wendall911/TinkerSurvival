package tinkersurvival.tools.tool;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.AoeToolCore;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class Knife extends AoeToolCore {

    private static final Set<Block> EFFECTIVE_ON =
        Sets.newHashSet(
            Blocks.TALLGRASS,
            Blocks.DOUBLE_PLANT,
            Blocks.MELON_BLOCK,
            Blocks.PUMPKIN,
            Blocks.WOOL
        );

    public Knife(PartMaterialType... requiredComponents) {
        super(requiredComponents);

        addCategory(Category.HARVEST, Category.WEAPON);
    }

    public Knife(String name) {
        this(PartMaterialType.handle(TinkerTools.toolRod),
            PartMaterialType.head(TinkerTools.knifeBlade),
            PartMaterialType.extra(TinkerTools.binding));

        setTranslationKey(name);
        setRegistryName(name);
        this.name = name;
    }

    public String name;

    @Override
    public float damageCutoff() {
        return 7f;
    }

    @Override
    public float damagePotential() {
        return 0.30f;
    }

    @Override
    public double attackSpeed() {
        return 4;
    }

    @Override
    public boolean isEffective(IBlockState state) {
        return _isEffective(state.getBlock());
    }

    public boolean shouldBreakBlock(Block block) {
        return _isEffective(block);
    }

    private boolean _isEffective(Block block) {
        return EFFECTIVE_ON.contains(block);
    }

    public boolean shouldDamageItem(Block block){
        return (block instanceof BlockTallGrass || block instanceof BlockDoublePlant);
    }

    @Override
    public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
        boolean hit;
        if (player instanceof EntityPlayer) {
            hit = dealHybridDamage(DamageSource.causePlayerDamage((EntityPlayer) player), entity, damage);
        }
        else {
            hit = dealHybridDamage(DamageSource.causeMobDamage(player), entity, damage);
        }

        return hit;
    }

    public static boolean dealHybridDamage(DamageSource source, Entity target, float damage) {
        if(target instanceof EntityLivingBase) {
            damage /= 2f;
        }

        // half damage normal, half damage armor bypassing
        boolean hit = target.attackEntityFrom(source, damage);
        if (hit && target instanceof EntityLivingBase) {
            EntityLivingBase targetLiving = (EntityLivingBase) target;
            // reset things to deal damage again
            targetLiving.hurtResistantTime = 0;
            targetLiving.lastDamage = 0;
            targetLiving.attackEntityFrom(source.setDamageBypassesArmor(), damage);

            int count = Math.round(damage / 2f);
            if (count > 0) {
                TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, targetLiving, count);
            }
        }
        return hit;
    }

    @Override
    protected ToolNBT buildTagData(List<Material> materials) {
        return buildDefaultTag(materials);
    }

}
