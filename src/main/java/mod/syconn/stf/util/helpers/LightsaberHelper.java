package mod.syconn.stf.util.helpers;

import mod.syconn.stf.init.FItems;
import mod.syconn.stf.items.lightsaber.customize.LColor;
import mod.syconn.stf.items.lightsaber.customize.LCrystal;
import mod.syconn.stf.items.lightsaber.customize.LSettings;
import mod.syconn.stf.items.lightsaber.customize.LType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public class LightsaberHelper {

    public static LSettings getSettings(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        return new LSettings(LType.getType(nbt.getInt("type")),nbt.getInt("damage"), nbt.getFloat("speed"), nbt.getBoolean("activated"), LCrystal.read(nbt.getCompound("crystal")));
    }

    public static ItemStack save(LSettings settings){
        ItemStack stack = new ItemStack(FItems.LIGHTSABER);
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("damage", settings.getA_damage());
        nbt.putFloat("speed", settings.getA_speed());
        nbt.putBoolean("activated", settings.isActivated());
        nbt.putInt("type", settings.getType().getId());
        nbt.put("crystal", LCrystal.save(settings.getColor(), settings.getCrystal().strength, settings.getCrystal().dyeable));
        stack.setNbt(nbt);
        return stack;
    }

    public static ItemStack create(ItemStack hilt, LCrystal crystal){
        return save(new LSettings(LType.getType(hilt.getOrCreateNbt().getInt("handle")), 9 + crystal.strength, -1.6f, true, crystal));
    }

    public static LSettings swapMode(ItemStack stack){
        LSettings settings = getSettings(stack);
        if (!settings.setActivated(!settings.isActivated()))
            System.out.println("Missing Crystal");
        return settings;
    }

    public static ItemStack createHilt(LSettings settings){
        ItemStack stack = new ItemStack(FItems.HILTS);
        stack.getOrCreateNbt().putInt("handle", settings.getType().getId());
        return stack;
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

        return settings;
    }
}
