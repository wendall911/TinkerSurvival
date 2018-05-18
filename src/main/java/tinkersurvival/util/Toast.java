package tinkersurvival.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.text.TextComponentTranslation;

public class Toast {
	public static void hint(String title, String subtitle, String replace){
        Minecraft.getMinecraft().getToastGui().add(
            new SystemToast(
                SystemToast.Type.TUTORIAL_HINT,
                new TextComponentTranslation(title),
                replace == null ? new TextComponentTranslation(subtitle) : new TextComponentTranslation(subtitle, replace)
            )
        );
	}
}
