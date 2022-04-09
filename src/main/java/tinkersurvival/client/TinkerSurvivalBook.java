package tinkersurvival.client;

import net.minecraft.resources.ResourceLocation;

import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.repository.FileRepository;
import slimeknights.mantle.client.book.transformer.BookTransformer;

import slimeknights.tconstruct.library.client.book.content.ContentMaterial;
import slimeknights.tconstruct.library.client.book.content.ContentModifier;
import slimeknights.tconstruct.library.client.book.content.ContentTool;
import slimeknights.tconstruct.library.client.book.sectiontransformer.ModifierSectionTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.ToolSectionTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.materials.SkullMaterialSectionTransformer;
import slimeknights.tconstruct.library.client.book.sectiontransformer.materials.TieredMaterialSectionTransformer;

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
        // register page types
        /*
        BookLoader.registerPageType(ContentMaterial.ID, ContentMaterial.class);
        BookLoader.registerPageType(ContentTool.ID, ContentTool.class);
        BookLoader.registerPageType(ContentModifier.ID, ContentModifier.class);
        */

        // tool transformers
        TINKERS_SURVIVAL.addTransformer(ToolSectionTransformer.INSTANCE);
        /*
        MIGHTY_SMELTING.addTransformer(ToolSectionTransformer.INSTANCE);
        ENCYCLOPEDIA.addTransformer(new ToolSectionTransformer("small_tools"));
        ENCYCLOPEDIA.addTransformer(new ToolSectionTransformer("large_tools"));
        */

        // material tier transformers
        /*
        MATERIALS_AND_YOU.addTransformer(new TieredMaterialSectionTransformer("tier_one_materials", 1, false));
        PUNY_SMELTING.addTransformer(new TieredMaterialSectionTransformer("tier_two_materials", 2, false));
        MIGHTY_SMELTING.addTransformer(new TieredMaterialSectionTransformer("tier_three_materials", 3, false));
        FANTASTIC_FOUNDRY.addTransformer(new TieredMaterialSectionTransformer("tier_four_materials", 4, false));
        TINKERS_GADGETRY.addTransformer(new SkullMaterialSectionTransformer("skull_materials", false));
        */

        // detailed transformers
        /*
        ENCYCLOPEDIA.addTransformer(new TieredMaterialSectionTransformer("tier_one_materials", 1, true));
        ENCYCLOPEDIA.addTransformer(new TieredMaterialSectionTransformer("tier_two_materials", 2, true));
        ENCYCLOPEDIA.addTransformer(new TieredMaterialSectionTransformer("tier_three_materials", 3, true));
        ENCYCLOPEDIA.addTransformer(new TieredMaterialSectionTransformer("tier_four_materials", 4, true));
        ENCYCLOPEDIA.addTransformer(new SkullMaterialSectionTransformer("skull_materials", true));
        */

        // modifier transformers
        /*
        ModifierSectionTransformer upgrades = new ModifierSectionTransformer("upgrades");
        ModifierSectionTransformer defense = new ModifierSectionTransformer("defense");
        ModifierSectionTransformer slotless = new ModifierSectionTransformer("slotless");
        ModifierSectionTransformer abilities = new ModifierSectionTransformer("abilities");
        PUNY_SMELTING.addTransformer(upgrades);
        PUNY_SMELTING.addTransformer(slotless);
        MIGHTY_SMELTING.addTransformer(defense);
        MIGHTY_SMELTING.addTransformer(abilities);
        ENCYCLOPEDIA.addTransformer(upgrades);
        ENCYCLOPEDIA.addTransformer(defense);
        ENCYCLOPEDIA.addTransformer(slotless);
        ENCYCLOPEDIA.addTransformer(abilities);
        */

        ModifierSectionTransformer start = new ModifierSectionTransformer("start");
        ModifierSectionTransformer tools = new ModifierSectionTransformer("tools");
        TINKERS_SURVIVAL.addTransformer(start);
        TINKERS_SURVIVAL.addTransformer(tools);

        // TODO: do we want to fire an event to add transformers to our books? Since we need the next two to be last
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
