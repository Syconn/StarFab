package mod.syconn.stf.items.lightsaber;

import mod.syconn.stf.StarMain;

import mod.syconn.stf.items.lightsaber.customize.LColor;
import mod.syconn.stf.items.lightsaber.customize.LCrystal;
import mod.syconn.stf.items.lightsaber.customize.LSettings;
import mod.syconn.stf.items.lightsaber.customize.LType;
import mod.syconn.stf.util.helpers.LightsaberHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class Lightsaber extends SwordItem {

    public Lightsaber(LSettings settings) {
        super(LSettings.Kyber_Material.INSTANCE, settings.getA_damage(), settings.getA_speed(), new Settings().group(StarMain.TEST_GROUP).maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (LightsaberHelper.getSettings(stack).isActivated()) tooltip.add(new TranslatableText("tooltip.stf.lightsaber.active").formatted(Formatting.BOLD, Formatting.GREEN));
        if (!LightsaberHelper.getSettings(stack).isActivated()) tooltip.add(new TranslatableText("tooltip.stf.lightsaber.unactive").formatted(Formatting.BOLD, Formatting.RED));

        tooltip.add(new LiteralText(""));

        LSettings settings = LightsaberHelper.getSettings(stack);

        if (context.isAdvanced()){
            tooltip.add(new LiteralText("  Settings").formatted(Formatting.BOLD, Formatting.GRAY));
            if (settings.hasCrystal()) {
                tooltip.add(new LiteralText("   Color: " + settings.getColor().getName()).formatted(Formatting.GRAY));
                tooltip.add(new LiteralText("   Strength: " + settings.getCrystal().strength).formatted(Formatting.GRAY));
                tooltip.add(new LiteralText("   Dyeable: " + settings.getCrystal().dyeable).formatted(Formatting.GRAY));
            }

            tooltip.add(new LiteralText("   Type: " + settings.getType().getName()).formatted(Formatting.GRAY));
        }
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
        if (group == StarMain.TEST_GROUP){
            ItemStack stack = LightsaberHelper.save(new LSettings(LType.LUKE, 9, -1.6f, true, new LCrystal(LColor.BLUE, new Random().nextInt(4), new Random().nextBoolean())));
            stacks.add(stack);

            stack = LightsaberHelper.save(new LSettings(LType.VADER, 9, -1.6f, true, new LCrystal(LColor.RED, new Random().nextInt(4), new Random().nextBoolean())));
            stacks.add(stack);

            stack = LightsaberHelper.save(new LSettings(LType.YODA, 9, -1.6f, true, new LCrystal(LColor.GREEN, new Random().nextInt(4), new Random().nextBoolean())));
            stacks.add(stack);

            stack = LightsaberHelper.save(new LSettings(LType.WHITE, 9, -1.6f, true, new LCrystal(LColor.WHITE, new Random().nextInt(4), new Random().nextBoolean())));
            stacks.add(stack);

            stack = LightsaberHelper.save(new LSettings(LType.GUARD, 9, -1.6f, true, new LCrystal(LColor.YELLOW, new Random().nextInt(4), new Random().nextBoolean())));
            stacks.add(stack);

            stack = LightsaberHelper.save(new LSettings(LType.WINDU, 9, -1.6f, true, new LCrystal(LColor.PURPLE, new Random().nextInt(4), new Random().nextBoolean())));
            stacks.add(stack);
        }
    }
}