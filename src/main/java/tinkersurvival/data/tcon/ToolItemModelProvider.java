package tinkersurvival.data.tcon;

import java.io.IOException;

import com.google.gson.JsonObject;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.tconstruct.library.data.AbstractToolItemModelProvider;

import tinkersurvival.TinkerSurvival;
import tinkersurvival.items.TConItems;

public class ToolItemModelProvider extends AbstractToolItemModelProvider {

    public ToolItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, existingFileHelper, TinkerSurvival.MODID);
    }

    @Override
    public String getName() {
        return "TinkerSurvival Tool Item Model Provider";
    }

    @Override
    protected void addModels() throws IOException {
        JsonObject toolBlocking = readJson(new ResourceLocation( TinkerSurvival.MODID, "base/tool_blocking"));

        tool(TConItems.KNIFE, toolBlocking, "head");
        tool(TConItems.SAW, toolBlocking, "head");
    }

}
