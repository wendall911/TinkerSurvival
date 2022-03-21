package tinkersurvival.data.tcon.sprite;

import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;

import tinkersurvival.TinkerSurvival;

public class TinkerPartSpriteProvider extends AbstractPartSpriteProvider {

    public TinkerPartSpriteProvider() {
        super(TinkerSurvival.MODID);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - Construct Parts";
    }

    @Override
    protected void addAllSpites() {
        buildTool("saw").addBreakableHead("head").addBinding("binding"); // handle provided by pickaxe
    }

}
