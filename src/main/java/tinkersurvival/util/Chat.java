package tinkersurvival.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Chat {

    public static final String NOTICE = "message.notice";
    public static final String WARNING = "message.warning";
    public static final String TOOL_BROKE = "message.tool_broke";
    public static final String WRONG_TOOL = "message.wrong_tool";
    public static final String SARCASTIC_WRONG_TOOL = "message.wrong_tool2";

    public static void sendMessage(Player player, String title) {
        Component message = (new TranslatableComponent(title)).withStyle(ChatFormatting.RED);

        if (title.contains(NOTICE)) {
            message = (new TranslatableComponent(title)).withStyle(ChatFormatting.YELLOW);
        }

        sendMessage(player, message, false);
    }

    public static void sendMessage(Player player, String title, String replace, boolean delayed) {
        sendMessage(player, title, new TextComponent(replace), delayed);
    }

    public static void sendMessage(Player player, String title, ItemStack stack, boolean delayed) {
        sendMessage(player, title, stack.getItem(), delayed);
    }

    public static void sendMessage(Player player, String title, Item item, boolean delayed) {
        Component name = item.getDescription();

        sendMessage(player, title, name, delayed);
    }

    public static void sendMessage(Player player, String title, Component replace, boolean delayed) {
        Component message = new TranslatableComponent(title, replace, delayed);

        sendMessage(player, message, delayed);
    }

    private static void sendMessage(Player player, Component message, boolean delayed) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int delay = 0;

        if (delayed) {
            delay = 700;
        }

        executor.schedule(() -> player.displayClientMessage(message, true), delay, TimeUnit.MILLISECONDS);
    }

}
