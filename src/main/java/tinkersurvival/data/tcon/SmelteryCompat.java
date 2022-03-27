package tinkersurvival.data.tcon;

import java.util.Locale;

import lombok.Getter;
import lombok.experimental.Accessors;

import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import slimeknights.mantle.registration.object.FluidObject;

import slimeknights.tconstruct.smeltery.data.Byproduct;

import tinkersurvival.items.TinkerSurvivalItems;

/** Enum holding all relevant smeltery compat */
public enum SmelteryCompat {

    MANASTEEL (TinkerSurvivalItems.MANASTEEL, Byproduct.IRON, Byproduct.GOLD);

    @Getter
    private final String name = this.name().toLowerCase(Locale.US);
    private final FluidObject<? extends ForgeFlowingFluid> fluid;
    @Getter
    private final boolean isOre;
    @Accessors(fluent = true)
    @Getter
    private final boolean hasDust;
    @Getter
    private final Byproduct[] byproducts;

    SmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, boolean hasDust) {
        this.fluid = fluid;
        this.isOre = false;
        this.byproducts = new Byproduct[0];
        this.hasDust = hasDust;
    }

    /** Byproducts means its an ore, no byproucts are alloys */
    SmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, Byproduct... byproducts) {
        this.fluid = fluid;
        this.isOre = byproducts.length > 0;
        this.byproducts = byproducts;
        this.hasDust = true;
    }

    /** Gets teh fluid for this compat */
    public FluidObject<?> getFluid() {
        return fluid;
    }

    /** Gets teh bucket for this compat */
    public Item getBucket() {
        return fluid.asItem();
    }
}
