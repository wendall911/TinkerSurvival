package tinkersurvival.items.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.mantle.item.LecternBookItem;

import tinkersurvival.client.TinkerSurvivalBook;

public class TinkerSurvivalBookItem extends LecternBookItem {

    private final BookType bookType;

    public TinkerSurvivalBookItem(Properties props, BookType bookType) {
        super(props);
        this.bookType = bookType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            TinkerSurvivalBook.getBook(bookType).openGui(hand, stack);
        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public void openLecternScreenClient(BlockPos pos, ItemStack stack) {
        TinkerSurvivalBook.getBook(bookType).openGui(pos, stack);
    }

    public enum BookType {
        TINKERS_SURVIVAL,
        TINKERS_SURVIVAL_MODPACK
    }

}
