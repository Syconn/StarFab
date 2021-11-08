package mod.syconn.stf.util.helpers;

import mod.syconn.stf.blockentity.WorkstationEntity;
import mod.syconn.stf.items.Hilts;
import mod.syconn.stf.items.KyberCrystal;
import mod.syconn.stf.items.lightsaber.Lightsaber;
import mod.syconn.stf.items.lightsaber.customize.LCrystal;
import mod.syconn.stf.items.lightsaber.customize.LSettings;
import net.minecraft.item.ItemStack;

public class WorkstationHelper {

    public static void Lightsaber(WorkstationEntity be){
        if (be.getStack(1).getItem() instanceof Lightsaber){
            LSettings settings = LightsaberHelper.getSettings(be.getStack(1));

            if (be.getStack(0) == ItemStack.EMPTY && !be.added[0] && !be.added[1]){
                be.setStack(0, LightsaberHelper.createHilt(settings));
                be.added[0] = true;
            }

            if (be.getStack(2) == ItemStack.EMPTY && !be.added[2] && !be.added[1]){
                be.setStack(2, LCrystal.copyOf(settings.getCrystal()));
                be.added[2] = true;
            }

            if (be.getStack(0).getItem() instanceof Hilts && be.added[0] && (be.getStack(2).getItem() instanceof KyberCrystal && be.added[2])){
                be.added[1] = true;
            }

            if ((be.getStack(0) == ItemStack.EMPTY || be.getStack(2) == ItemStack.EMPTY) && be.added[0] && be.added[2]){
                be.removeStack(1);
                be.added[0] = false;
                be.added[1] = false;
                be.added[2] = false;
            }
        }

        if (be.getStack(1) == ItemStack.EMPTY) {
            if (be.getStack(0) == ItemStack.EMPTY && be.getStack(2) == ItemStack.EMPTY){
                be.added[0] = false;
                be.added[1] = false;
                be.added[2] = false;
            }

            if (be.getStack(0).getItem() instanceof Hilts && be.getStack(2).getItem() instanceof KyberCrystal){
                if (be.added[1]) {
                    be.added[0] = false;
                    be.added[2] = false;
                    be.removeStack(0);
                    be.removeStack(2);
                } else {
                    be.setStack(1, LightsaberHelper.create(be.getStack(0), LCrystal.get(be.getStack(2))));
                    be.added[0] = true;
                    be.added[1] = true;
                    be.added[2] = true;
                }
            }
        }
        be.markDirty();
    }
}