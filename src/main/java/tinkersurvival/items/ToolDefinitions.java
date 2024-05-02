package tinkersurvival.items;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ToolDefinitions {

    public static final ToolDefinition KNIFE_DEFINITION = ToolDefinition.create(TConItems.KNIFE);
    public static final ToolDefinition SAW_DEFINITION = ToolDefinition.create(TConItems.SAW);

}
