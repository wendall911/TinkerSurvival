package tinkersurvival.client;

import net.minecraft.resources.ResourceLocation;

import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.mantle.client.book.transformer.BookTransformer;

import slimeknights.tconstruct.library.client.book.sectiontransformer.ModifierSectionTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.ToolSectionTransformer;

import tinkersurvival.items.item.TinkerSurvivalBookItem.BookType;

import static tinkersurvival.common.TinkerSurvivalBookIDs.TINKERS_SURVIVAL_ID;
import static tinkersurvival.common.TinkerSurvivalBookIDs.TINKERS_SURVIVAL_MODPACK_ID;

public class TinkerSurvivalBook extends BookData {

    public static final BookData TINKERS_SURVIVAL = BookLoader.registerBook(TINKERS_SURVIVAL_ID, false, false);
    public static final BookData TINKERS_SURVIVAL_MODPACK = BookLoader.registerBook(TINKERS_SURVIVAL_MODPACK_ID, false, false);

    /**
     * Initializes the books
     */
    public static void init() {
        // tool transformers
        TINKERS_SURVIVAL.addTransformer(ToolSectionTransformer.INSTANCE);

        // modifier transformers
        ModifierSectionTransformer start = new ModifierSectionTransformer("start");
        ModifierSectionTransformer tools = new ModifierSectionTransformer("tools");
        ModifierSectionTransformer health = new ModifierSectionTransformer("health");
        TINKERS_SURVIVAL.addTransformer(start);
        TINKERS_SURVIVAL.addTransformer(tools);
        TINKERS_SURVIVAL.addTransformer(health);

        addStandardData(TINKERS_SURVIVAL, TINKERS_SURVIVAL_ID);
        addStandardData(TINKERS_SURVIVAL_MODPACK, TINKERS_SURVIVAL_MODPACK_ID);
    }

    /**
     * Adds the repository and the relevant transformers to the books
     *
     * @param book Book instance
     * @param id   Book ID
     */
    private static void addStandardData(BookData book, ResourceLocation id) {
        book.addRepository(
            new FileRepository(new ResourceLocation(id.getNamespace(), "book/" + id.getPath())));
        book.addTransformer(BookTransformer.indexTranformer());

        // padding needs to be last to ensure page counts are right
        book.addTransformer(BookTransformer.paddingTransformer());
    }

    /**
     * Gets the book for the enum value
     *
     * @param bookType Book type
     * @return Book
     */
    public static BookData getBook(BookType bookType) {
        return switch (bookType) {
            case TINKERS_SURVIVAL -> TINKERS_SURVIVAL;
            case TINKERS_SURVIVAL_MODPACK -> TINKERS_SURVIVAL_MODPACK;
        };
    }

}
