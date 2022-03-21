/*
 * Derived from the No Tree Punching mod by AlcatrazEscapee.
 * https://github.com/alcatrazEscapee/no-tree-punching/tree/1.18.x/src/main/java/com/alcatrazescapee/notreepunching/mixin
 * Work under copyright. See the project LICENSE.md for details.
 */

package tinkersurvival.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.BlockStateBase.class)
public interface AbstractBlockStateAccessor {

    /**
     * This value, despite being set on the properties, is copied to each block state. The property mutator is public, but we need this in order to mutate the same value on each block state.
     */
    @Mutable
    @Accessor("requiresCorrectToolForDrops")
    void setRequiresCorrectToolForDrops(boolean value);

    @Accessor("destroySpeed")
    float getDestroySpeed();

}
