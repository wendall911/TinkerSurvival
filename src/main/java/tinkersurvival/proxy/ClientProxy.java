package tinkersurvival.proxy;

import tinkersurvival.client.sound.Sounds;
import tinkersurvival.util.ItemUse;
import tinkersurvival.TinkerSurvival;

public final class ClientProxy extends CommonProxy {

    public ClientProxy() {
        Sounds.init(TinkerSurvival.BUS);
        ItemUse.init();
    }

}
