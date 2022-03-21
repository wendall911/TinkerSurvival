/*
 * Derived from the No Tree Punching mod by AlcatrazEscapee.
 * https://github.com/alcatrazEscapee/no-tree-punching/tree/1.18.x/src/main/java/com/alcatrazescapee/notreepunching/mixin
 * Work under copyright. See the project LICENSE.md for details.
 */

package tinkersurvival.mixin;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.block.Block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DiggerItem.class)
public interface DiggerItemAccessor {

    @Accessor("blocks")
    Tag<Block> getBlocks();

}
