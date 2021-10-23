package mod.syconn.stf.item.lightsaber;

import mod.syconn.stf.StarMain;

import mod.syconn.stf.item.lightsaber.customize.LColor;
import mod.syconn.stf.item.lightsaber.customize.LSettings;
import mod.syconn.stf.item.lightsaber.customize.LType;
import mod.syconn.stf.util.LightsaberHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Lightsaber extends SwordItem {

    public Lightsaber(LSettings settings) {
        super(LSettings.Kyber_Material.INSTANCE, settings.getA_damage(), settings.getA_speed(), new Settings().group(StarMain.TEST_GROUP).maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (LightsaberHelper.getSettings(stack).isActivated()) tooltip.add(new TranslatableText("tooltip.stf.lightsaber.active").formatted(Formatting.BOLD, Formatting.GREEN));
        if (!LightsaberHelper.getSettings(stack).isActivated()) tooltip.add(new TranslatableText("tooltip.stf.lightsaber.unactive").formatted(Formatting.BOLD, Formatting.RED));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (player.isSneaking()) {
            LSettings settings = LightsaberHelper.swapMode(stack);
            stack = LightsaberHelper.save(settings);
            player.getStackInHand(hand).setNbt(stack.getNbt());
            return TypedActionResult.pass(stack);
        }

        player.setCurrentHand(hand);

        return new TypedActionResult<>(ActionResult.PASS, stack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        ItemStack stack = LightsaberHelper.save(new LSettings(LType.LUKE, LColor.BLUE, 9, -1.6f, true));
        stacks.add(stack);

        stack = LightsaberHelper.save(new LSettings(LType.VADER, LColor.RED, 9, -1.6f, true));
        stacks.add(stack);

        stack = LightsaberHelper.save(new LSettings(LType.YODA, LColor.GREEN, 9, -1.6f, true));
        stacks.add(stack);

        stack = LightsaberHelper.save(new LSettings(LType.WHITE, LColor.WHITE, 9, -1.6f, true));
        stacks.add(stack);

        stack = LightsaberHelper.save(new LSettings(LType.GUARD, LColor.YELLOW, 9, -1.6f, true));
        stacks.add(stack);

        stack = LightsaberHelper.save(new LSettings(LType.WINDU, LColor.PURPLE, 9, -1.6f, true));
        stacks.add(stack);
    }
}