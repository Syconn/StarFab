package mod.syconn.stf;

import mod.syconn.stf.init.FBlocks;
import mod.syconn.stf.init.FItems;
import mod.syconn.stf.init.FScreens;
import net.fabricmc.api.ClientModInitializer;

public class StarClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FItems.registerOverrides();
        FItems.registerColors();
        FBlocks.registerColors();
        FScreens.registerScreens();
    }
}
