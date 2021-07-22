package mod.syconn.stf.item;

import mod.syconn.stf.Main;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Lightsaber extends SwordItem {

    public Lightsaber() {
        super(Kyber_Material.INSTANCE, 9, -1.6f, new Settings().group(Main.TEST_GROUP).maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("tooltip.stf.lightsaber.unactive").formatted(Formatting.BOLD, Formatting.GREEN));
    }

    public static class Kyber_Material implements ToolMaterial {

        public static final Kyber_Material INSTANCE = new Kyber_Material();

        public Kyber_Material() {}

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