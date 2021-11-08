package mod.syconn.stf.items;

import mod.syconn.stf.StarMain;
import mod.syconn.stf.init.FItems;
import mod.syconn.stf.items.lightsaber.customize.LType;
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

public class Hilts extends Item {

    public Hilts() {
        super(new FabricItemSettings().maxCount(1).group(StarMain.TEST_GROUP).rarity(Rarity.RARE));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new LiteralText("Combine with a crystal in").formatted(Formatting.BOLD, Formatting.BLUE));
        tooltip.add(new LiteralText("a Workstation to make a lightsaber").formatted(Formatting.BOLD, Formatting.BLUE));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (group == StarMain.TEST_GROUP) {
            for (LType type : LType.values()){
                ItemStack stack = new ItemStack(FItems.HILTS);
                stack.getOrCreateNbt().putInt("handle", type.getId());
                stacks.add(stack);
            }
        }
    }
}
