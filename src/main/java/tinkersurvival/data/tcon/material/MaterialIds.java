package tinkersurvival.data.tcon.material;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import slimeknights.tconstruct.library.materials.definition.MaterialId;

import tinkersurvival.TinkerSurvival;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MaterialIds {

    public static final MaterialId manaSteel = id("manasteel");

    private static MaterialId id(String name) {
        return new MaterialId(TinkerSurvival.MODID, name);
    }

}
