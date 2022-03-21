package tinkersurvival.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class DynamicItemTier implements Tier {
    private int uses;
    private float speed;
    private float attackDamageBonus;
    private int harvestLvl;
    private int enchantability;
    private Ingredient repairIngredient;

    public DynamicItemTier(int maxUses, float eff, float dmg, int harv, int ench,
            Ingredient repairMat) {
        this.uses = maxUses;
        this.speed = eff;
        this.attackDamageBonus = dmg;
        this.harvestLvl = harv;
        this.enchantability = ench;
        this.repairIngredient = repairMat;
    }

    public DynamicItemTier(int maxUses, float eff, float dmg, int harv, int ench,
            Tag<Item> repairMatTag) {
        this.uses = maxUses;
        this.speed = eff;
        this.attackDamageBonus = dmg;
        this.harvestLvl = harv;
        this.enchantability = ench;
        this.repairIngredient = Ingredient.of(repairMatTag);
    }

    public DynamicItemTier() {
        this.uses = 0;
        this.speed = 0;
        this.attackDamageBonus = 0;
        this.harvestLvl = 0;
        this.enchantability = 0;
        this.repairIngredient = Ingredient.of(Items.BARRIER);
    }

    public DynamicItemTier setMaxUses(int maxUses) {
        this.uses = maxUses;
        return this;
    }

    public DynamicItemTier setEfficiency(float eff) {
        this.speed = eff;
        return this;
    }

    public DynamicItemTier setAttackDamage(float dmg) {
        this.attackDamageBonus = dmg;
        return this;
    }

    public DynamicItemTier setHarvestLvl(int lvl) {
        this.harvestLvl = lvl;
        return this;
    }

    public DynamicItemTier setEnchantability(int ench) {
        this.enchantability = ench;
        return this;
    }

    public DynamicItemTier setRepairMat(Ingredient mat) {
        this.repairIngredient = mat;
        return this;
    }

    public DynamicItemTier setRepairMat(@Nullable Tag<Item> tag) {
        if (tag == null || tag.getValues().isEmpty()) {
            this.repairIngredient = Ingredient.of(Items.BARRIER);
        } else {
            this.repairIngredient = Ingredient.of(tag);
        }
        return this;
    }

    public DynamicItemTier setRepairMats(Item... items) {
        this.repairIngredient = Ingredient.of(items);
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
        return this.repairIngredient;
    }

}
