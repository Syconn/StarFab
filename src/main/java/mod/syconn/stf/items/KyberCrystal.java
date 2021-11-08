package mod.syconn.stf.items;

import mod.syconn.stf.StarMain;
import mod.syconn.stf.items.lightsaber.customize.LColor;
import mod.syconn.stf.items.lightsaber.customize.LCrystal;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class KyberCrystal extends Item {

    public KyberCrystal() {
        super(new FabricItemSettings().group(StarMain.TEST_GROUP).maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        LCrystal crystal = LCrystal.get(stack);

        if (context.isAdvanced()) {
            tooltip.add(new LiteralText("  Settings").formatted(Formatting.BOLD, Formatting.GRAY));
            tooltip.add(new LiteralText("   Color: " + crystal.color.getName()).formatted(Formatting.GRAY));
            tooltip.add(new LiteralText("   Strength: " + crystal.strength).formatted(Formatting.GRAY));
            tooltip.add(new LiteralText("   Dyeable: " + crystal.dyeable).formatted(Formatting.GRAY));
        }
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (group == StarMain.TEST_GROUP) {
            for (LColor color : LColor.values()){
                ItemStack stack = LCrystal.create(color, new Random().nextInt(4), new Random().nextBoolean());
                stacks.add(stack);
            }
        }
    }
}
