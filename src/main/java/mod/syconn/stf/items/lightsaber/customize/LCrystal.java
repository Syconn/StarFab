package mod.syconn.stf.items.lightsaber.customize;

import mod.syconn.stf.init.FItems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class LCrystal {

    public int strength;
    public LColor color;
    public boolean dyeable;

    public LCrystal(LColor color, int strength, boolean dyeable) {
        this.strength = strength;
        this.color = color;
        this.dyeable = dyeable;
    }

    public static ItemStack create(LColor color, int strength, boolean dyeable){
        ItemStack stack = new ItemStack(FItems.KYBER);
        stack.getOrCreateNbt().putInt("color", color.getColor());
        stack.getOrCreateNbt().putInt("strength", strength);
        stack.getOrCreateNbt().putBoolean("dyeable", dyeable);
        return stack;
    }

    public static ItemStack copyOf(LCrystal crystal){
        return create(crystal.color, crystal.strength, crystal.dyeable);
    }

    public static LCrystal get(ItemStack stack){
        return new LCrystal(LColor.getColor(stack.getOrCreateNbt().getInt("color")), stack.getOrCreateNbt().getInt("strength"), stack.getOrCreateNbt().getBoolean("dyeable"));
    }

    public static NbtCompound save(LColor color, int strength, boolean dyeable){
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("color", color.getColor());
        nbt.putInt("strength", strength);
        nbt.putBoolean("dyeable", dyeable);
        return nbt;
    }

    public static LCrystal read(NbtCompound nbt){
        return new LCrystal(LColor.getColor(nbt.getInt("color")), nbt.getInt("strength"), nbt.getBoolean("dyeable"));
    }
}