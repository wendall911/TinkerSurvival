package tinkersurvival.temperature;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;

import tinkersurvival.config.Config;
import tinkersurvival.temperature.ConArmHelper;
import tinkersurvival.TinkerSurvival;

import toughasnails.api.temperature.IModifierMonitor;
import toughasnails.api.temperature.Temperature;
import static toughasnails.api.temperature.TemperatureHelper.registerTemperatureModifier;

import java.util.stream.IntStream;

public class ArmorModifier extends TemperatureModifier {

    private static Map<String, Integer> armorTempMap = new HashMap<>();
    private static int insulate = 1;
    private static int chill = -1;

    public ArmorModifier(String id) {
        super(id);
    }

    public static void init() {
        for (String item : Config.Armor.ARMOR_TEMP_MODIFIERS) {
            boolean valid = false;

            if (item.contains("=")) {
                String[] nameParts = item.split("=");

                if (nameParts.length == 2) {
                    String armorName = nameParts[0];
					int modifier = 0;

                    try {
						modifier = Integer.parseInt(nameParts[1]);
					} catch (NumberFormatException e) {}

                    if (modifier != 0) {
						Item armor = Item.getByNameOrId(armorName);
						if (armor != null) {
							valid = true;
							armorTempMap.put(armorName, modifier);
						}
                    }
                }
            }

            if (!valid) {
                TinkerSurvival.logger.warn("ARMOR_TEMP_MODIFIERS item "
                        + item + " has an incorrect format.");
            }
        }

        if (Config.Armor.INSULATED_MODIFIER <= 0) {
            TinkerSurvival.logger.warn("INSULATED_MODIFIER must be a positive integer. Using Default");
        }
        else {
            insulate = Config.Armor.INSULATED_MODIFIER;
        }

        if (Config.Armor.CHILLING_MODIFIER >= 0) {
            TinkerSurvival.logger.warn("CHILLING_MODIFIER must be a negative integer. Using Default");
        }
        else {
            chill = Config.Armor.CHILLING_MODIFIER;
        }

        registerTemperatureModifier(new ArmorModifier(TinkerSurvival.MODID + ":armor"));
    }

    @Override
    public Temperature applyPlayerModifiers(EntityPlayer player, Temperature initialTemperature, IModifierMonitor monitor) {
        int newTemperatureLevel = initialTemperature.getRawValue();

        int modifier = IntStream.range(0, 4)
                .map(i -> getArmorTemp(i, player.inventory)).sum();

        newTemperatureLevel += modifier;

        monitor.addEntry(new IModifierMonitor.Context(this.getId(), "Tinkers' Survival Armor", initialTemperature, new Temperature(newTemperatureLevel)));

        return new Temperature(newTemperatureLevel);
    }

    @Override
    public boolean isPlayerSpecific() {
        return true;
    }

    private int getArmorTemp(int i, InventoryPlayer inventory) {
        ItemStack itemstack = inventory.armorInventory.get(i);
        int temp = 0;

        if (itemstack != ItemStack.EMPTY) {
            if (Loader.isModLoaded("conarm")) {
                temp = ConArmHelper.getArmorTemp(itemstack, chill, insulate);
            }
            else if (armorTempMap.get(itemstack.getItem().getRegistryName().toString()) != null) {
                temp = armorTempMap.get(itemstack.getItem().getRegistryName().toString());
            }
        }

        return temp;
    }

}
