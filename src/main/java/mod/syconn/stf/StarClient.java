package mod.syconn.stf;

import mod.syconn.stf.init.FItems;
import net.fabricmc.api.ClientModInitializer;

public class StarClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FItems.registerOverrides();
        FItems.registerColors();
    }
}
