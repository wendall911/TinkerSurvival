package tinkersurvival.data.tcon.sprite;

import slimeknights.tconstruct.library.client.data.material.AbstractPartSpriteProvider;

import tinkersurvival.TinkerSurvival;

public class SawPartSpriteProvider extends AbstractPartSpriteProvider {

    public SawPartSpriteProvider() {
        super(TinkerSurvival.MODID);
    }

    @Override
    public String getName() {
        return "TinkerSurvival - Construct Parts";
    }

    @Override
    protected void addAllSpites() {
        buildTool("saw").addBreakableHead("head").addBinding("binding").addHandle("handle");
        buildTool("knife").addBreakableHead("head").addBinding("binding").addHandle("handle");
    }

}
