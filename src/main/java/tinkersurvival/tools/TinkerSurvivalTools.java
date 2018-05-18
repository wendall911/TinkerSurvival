package tinkersurvival.tools;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.tools.tool.CrudeHatchet;
import tinkersurvival.tools.tool.CrudeSaw;
import tinkersurvival.tools.tool.CrudeKnife;
import tinkersurvival.tools.tool.Knife;
import tinkersurvival.tools.tool.Saw;
import tinkersurvival.world.item.ItemBase;

import slimeknights.tconstruct.common.ModelRegisterUtil;
import slimeknights.tconstruct.library.TinkerRegistry;

public class TinkerSurvivalTools {

    private static final List<Item> all = new ArrayList<>();
    private static final List<ItemStack> knives = new ArrayList<>();
    private static final List<ItemStack> saws = new ArrayList<>();

    public static Item.ToolMaterial toolMaterialStone;
    public static Item.ToolMaterial toolMaterialFlint;

    public static CrudeKnife crudeKnife;
    public static CrudeHatchet crudeHatchet;
    public static CrudeSaw crudeSaw;
    public static ItemBase crudeSawBlade;
    public static Knife ticKnife;
    public static Saw ticSaw;

    public static void init() {
        toolMaterialStone = EnumHelper.addToolMaterial("TS_STONE", 0, 5, 1.5F, 0.5F, 0);
        toolMaterialFlint = EnumHelper.addToolMaterial("TS_FLINT", 0, 20, 1.5F, 0.5F, 0);
        crudeKnife = getCrudeKnife(crudeKnife, toolMaterialFlint, "crude_knife");
        crudeHatchet = getCrudeHatchet(crudeHatchet, toolMaterialStone, "crude_hatchet");
        crudeSaw = getCrudeSaw(crudeSaw, toolMaterialStone, "crude_saw");
        crudeSawBlade = getItem(crudeSawBlade, "crude_saw_blade");
        ticKnife = getKnife(ticKnife, "knife");
        ticSaw = getSaw(ticSaw, "saw");
    }

    private static CrudeKnife getCrudeKnife(CrudeKnife knife, Item.ToolMaterial material, String name) {
        knife = new CrudeKnife(material, name);
        all.add(knife);
        knives.add(new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE));
        return knife;
    }

    private static ItemBase getItem(ItemBase item, String name) {
        item = new ItemBase(name);
        all.add(item);
        return item;
    }

    private static CrudeHatchet getCrudeHatchet(CrudeHatchet hatchet, Item.ToolMaterial material, String name) {
        hatchet = new CrudeHatchet(material, name);
        all.add(hatchet);
        return hatchet;
    }

    private static CrudeSaw getCrudeSaw(CrudeSaw saw, Item.ToolMaterial material, String name) {
        saw = new CrudeSaw(material, name);
        all.add(saw);
        saws.add(new ItemStack(saw, 1, OreDictionary.WILDCARD_VALUE));
        return saw;
    }

    private static Saw getSaw(Saw saw, String name) {
        saw = new Saw(name);
        all.add(saw);
        saws.add(new ItemStack(saw, 1, OreDictionary.WILDCARD_VALUE));
        return saw;
    }

    private static Knife getKnife(Knife knife, String name) {
        knife = new Knife(name);
        all.add(knife);
        knives.add(new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE));
        return knife;
    }

    public static void registerTools(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        all.forEach(registry::register);
        TinkerRegistry.registerToolCrafting(ticKnife);
        ModelRegisterUtil.registerToolModel(ticKnife);
        TinkerRegistry.registerToolCrafting(ticSaw);
        ModelRegisterUtil.registerToolModel(ticSaw);
    }

    public static void registerItemModels() {
        all.forEach(item -> {
            if (item == ticKnife || item == ticSaw) {
                return;
            }
            else {
                TinkerSurvival.proxy.registerItemModel(item);
            }
        });
    }

    public static List<ItemStack> listAllKnives() {
        return knives;
    }

    public static List<ItemStack> listAllSaws() {
        return saws;
    }
}
