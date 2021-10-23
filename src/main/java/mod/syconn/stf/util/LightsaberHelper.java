package mod.syconn.stf.util;

import mod.syconn.stf.init.FItems;
import mod.syconn.stf.item.lightsaber.customize.LColor;
import mod.syconn.stf.item.lightsaber.customize.LSettings;
import mod.syconn.stf.item.lightsaber.customize.LType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public class LightsaberHelper {

    public static LSettings getSettings(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        return new LSettings(LType.getType(nbt.getInt("type")), LColor.getColor(nbt.getInt("color")), nbt.getInt("damage"), nbt.getFloat("speed"), nbt.getBoolean("activated"));
    }

    public static ItemStack save(LSettings settings){
        ItemStack stack = new ItemStack(FItems.LIGHTSABER);
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("color", settings.getColor().getColor());
        nbt.putInt("damage", settings.getA_damage());
        nbt.putFloat("speed", settings.getA_speed());
        nbt.putBoolean("activated", settings.isActivated());
        nbt.putInt("type", settings.getType().getId());
        stack.setNbt(nbt);
        return stack;
    }

    public static LSettings swapMode(ItemStack stack){
        LSettings settings = getSettings(stack);
        settings.setActivated(!settings.isActivated());
        return settings;
    }

    public static LSettings modify(LSettings.Modify modify, float modifier, ItemStack stack, @Nullable LSettings setting){
        LSettings settings = getSettings(stack);
        if (setting != null) settings = setting;

        if (LSettings.Modify.color == modify){
            settings.setColor(LColor.getColor((int) modifier));
        }

        if (LSettings.Modify.a_damage == modify){
            settings.setA_damage((int) modifier);
        }

        if (LSettings.Modify.a_speed == modify){
            settings.setA_speed(modifier);
        }

        if (LSettings.Modify.activated == modify){
            settings.setActivated(modifier >= 1);
        }

        return settings;
    }
}
