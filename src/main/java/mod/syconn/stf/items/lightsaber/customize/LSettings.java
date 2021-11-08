package mod.syconn.stf.items.lightsaber.customize;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class LSettings {

    private LType type;
    private int a_damage;
    private float a_speed;
    private boolean activated;

    private LCrystal crystal;

    public LSettings(LType type, int a_damage, float a_speed, boolean activated, LCrystal crystal) {
        this.type = type;
        this.a_damage = a_damage;
        this.a_speed = a_speed;
        this.activated = activated;
        this.crystal = crystal;
    }

    public LType getType() {
        return type;
    }

    public int getA_damage() {
        return a_damage;
    }

    public float getA_speed() {
        return a_speed;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean hasCrystal(){
        return crystal != null;
    }

    public LCrystal getCrystal(){ return crystal; }

    public LColor getColor(){ return crystal.color; }

    public void setColor(LColor color) {
        this.crystal.color = color;
    }

    public void setA_damage(int a_damage) {
        this.a_damage = a_damage;
    }

    public void setA_speed(float a_speed) {
        this.a_speed = a_speed;
    }

    public boolean setActivated(boolean activated) {
        if (crystal != null) {
            this.activated = activated;
            //PLAY SOUND SUCCESS
            return true;
        }
        //PLAY SOUND FAILED
        return false;
    }

    public void setCrystal(LCrystal crystal) {
        this.crystal = crystal;
    }

    public enum Modify {
        color,
        a_damage,
        a_speed,
        activated,
        crystal
    }

    public static class Kyber_Material implements ToolMaterial {

        public static final Kyber_Material INSTANCE = new Kyber_Material();

        public Kyber_Material() {
        }

        @Override
        public int getDurability() {
            return 0;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 4;
        }

        @Override
        public float getAttackDamage() {
            return 9;
        }

        @Override
        public int getMiningLevel() {
            return 4;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }
    }
}
