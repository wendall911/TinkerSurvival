package tinkersurvival.items;

import javax.annotation.Nonnull;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class CrudeItemTier implements Tier {
    private int uses = 0;
    private float speed = 0.0F;
    private float attackDamageBonus = 0.0F;
    private int harvestLvl = 0;
    private int enchantability = 0;
    private Item repairIngredientItem = Items.BARRIER;
    private Tag.Named<Item> repairIngredientTag;

    public CrudeItemTier() {}

    public CrudeItemTier setMaxUses(int maxUses) {
        this.uses = maxUses;

        return this;
    }

    public CrudeItemTier setEfficiency(float eff) {
        this.speed = eff;

        return this;
    }

    public CrudeItemTier setAttackDamage(float dmg) {
        this.attackDamageBonus = dmg;

        return this;
    }

    public CrudeItemTier setHarvestLvl(int lvl) {
        this.harvestLvl = lvl;

        return this;
    }

    public CrudeItemTier setEnchantability(int ench) {
        this.enchantability = ench;

        return this;
    }

    public CrudeItemTier setRepairMat(Tag.Named<Item> tag) {
        this.repairIngredientTag = tag;

        return this;
    }

    public CrudeItemTier setRepairMat(Item item) {
        this.repairIngredientItem = item;

        return this;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.harvestLvl;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    @Nonnull
    public Ingredient getRepairIngredient() {
        if (this.repairIngredientTag != null) {
            return Ingredient.of(this.repairIngredientTag);
        }
        else {
            return Ingredient.of(this.repairIngredientItem);
        }
    }

}
