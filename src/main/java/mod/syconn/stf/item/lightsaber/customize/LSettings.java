package mod.syconn.stf.item.lightsaber.customize;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class LSettings {
    private LType type;
    private LColor color;
    private int a_damage;
    private float a_speed;
    private boolean activated;

    public LSettings(LType type, LColor color, int a_damage, float a_speed, boolean activated) {
        this.type = type;
        this.color = color;
        this.a_damage = a_damage;
        this.a_speed = a_speed;
        this.activated = activated;
    }

    public LColor getColor() {
        return color;
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

    public void setColor(LColor color) {
        this.color = color;
    }

    public void setType(LType type) {
        this.type = type;
    }

    public void setA_damage(int a_damage) {
        this.a_damage = a_damage;
    }

    public void setA_speed(float a_speed) {
        this.a_speed = a_speed;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public enum Modify {
        color,
        a_damage,
        a_speed,
        activated
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
