package tinkersurvival.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.text.TextComponentTranslation;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SomethingNeedsToastHandler {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
	public void onHint(SomethingNeedsToastEvent event) {
        String title = event.getTitle();
        String subtitle = event.getSubtitle();
        String replace = event.getReplace();

        Minecraft.getMinecraft().getToastGui().add(
            new SystemToast(
                SystemToast.Type.TUTORIAL_HINT,
                new TextComponentTranslation(title),
                replace == null ? new TextComponentTranslation(subtitle) : new TextComponentTranslation(subtitle, replace)
            )
        );
	}

}
