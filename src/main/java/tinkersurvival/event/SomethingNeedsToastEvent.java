package tinkersurvival.event;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SomethingNeedsToastEvent extends Event {

    private String title;
    private String subtitle;
    private String replace;

    public SomethingNeedsToastEvent(String title, String subtitle, String replace) {
        this.title = title;
        this.subtitle = subtitle;
        this.replace = replace;
    }

	public static SomethingNeedsToastEvent fireEvent(String title, String subtitle, String replace) {
		SomethingNeedsToastEvent event = new SomethingNeedsToastEvent(title, subtitle, replace);
		MinecraftForge.EVENT_BUS.post(event);
		return event;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getReplace() {
        return replace;
    }

}
