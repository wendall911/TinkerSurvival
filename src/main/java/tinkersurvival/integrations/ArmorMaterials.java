package tinkersurvival.integrations;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import c4.conarm.common.ConstructsRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.TinkerRegistry;

import tinkersurvival.integrations.ArmorTraitInsulated;
import tinkersurvival.integrations.ArmorTraitChilling;

public class ArmorMaterials {

    public static Material wool;
    public static Material jelledSlime;
    public static ITrait insulated = new ArmorTraitInsulated();
    public static ITrait chilling = new ArmorTraitChilling();

    public static void preInit() {
        Item jelledSlimeBrick = Item.getByNameOrId("toughasnails:jelled_slime");

        wool = new Material("wool", 0xf0eae1, false);
        wool.addItem(new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE), 1, Material.VALUE_Ingot);
        wool.setRepresentativeItem(new ItemStack(Blocks.WOOL));
        MaterialIntegration woolIntegration = new MaterialIntegration(wool);
        TinkerRegistry.integrate(woolIntegration);
        woolIntegration.preInit();

        jelledSlime = new Material("jelledslime", 0x93c54b, false);
        jelledSlime.addItem(jelledSlimeBrick, 1, Material.VALUE_Ingot);
        jelledSlime.setRepresentativeItem(jelledSlimeBrick);
        MaterialIntegration jelledSlimeIntegration = new MaterialIntegration(jelledSlime);
        TinkerRegistry.integrate(jelledSlimeIntegration);
        jelledSlimeIntegration.preInit();

        TinkerRegistry.addMaterialStats(wool,
            new CoreMaterialStats(2.5F, 3),
            new PlatesMaterialStats(1, 1, 0),
            new TrimMaterialStats(0.5F));

        TinkerRegistry.addMaterialStats(jelledSlime,
            new CoreMaterialStats(2.5F, 3),
            new PlatesMaterialStats(1, 1, 0),
            new TrimMaterialStats(0.5F));
    }

    public static void init() {
        addArmorTrait(wool, insulated);
        addArmorTrait(jelledSlime, chilling);
    }

    private static void addArmorTrait(Material material, ITrait trait) {
        material.addTrait(trait, ArmorMaterialType.CORE);
        material.addTrait(trait, ArmorMaterialType.PLATES);
        material.addTrait(trait, ArmorMaterialType.TRIM);
    }

}