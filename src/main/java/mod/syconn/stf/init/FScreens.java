package mod.syconn.stf.init;

import mod.syconn.stf.StarMain;
import mod.syconn.stf.client.screens.WorkstationScreen;
import mod.syconn.stf.client.handler.WorkstationScreenHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class FScreens {

    public static ScreenHandlerType<WorkstationScreenHandler> WORKSTATION;

    public static void registerScreens(){
        ScreenRegistry.register(WORKSTATION, WorkstationScreen::new);
    }

    public static void registerHandlers() {
       WORKSTATION = ScreenHandlerRegistry.registerSimple(new Identifier(StarMain.ID, "workstation"), WorkstationScreenHandler::new);
    }
}
