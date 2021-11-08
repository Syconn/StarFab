package mod.syconn.stf.blocks;

import mod.syconn.stf.init.FBlocks;
import mod.syconn.stf.items.lightsaber.customize.LColor;
import mod.syconn.stf.items.lightsaber.customize.LCrystal;
import mod.syconn.stf.util.BlockProperties;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.stream.Stream;

public class CrystalBlock extends Block {

    public static EnumProperty<LColor> COLOR = BlockProperties.COLOR;

    public CrystalBlock() {
        super(FabricBlockSettings.of(Material.GLASS).strength(4.0f).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());
        setDefaultState(getStateManager().getDefaultState().with(COLOR, LColor.BLUE));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(BlockProperties.COLOR, LColor.getColor(ctx.getPlayer().getMainHandStack().getOrCreateNbt().getInt("color")));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(COLOR);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && !player.isCreative()) {
            if (EnchantmentHelper.getEquipmentLevel(Enchantments.SILK_TOUCH, player) == 1){
                ItemStack stack = create(state.get(COLOR));
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
            }

            else {
                ItemStack stack = LCrystal.create(state.get(COLOR), new Random().nextInt(4) + 1, !(new Random().nextBoolean()));
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
            }
        }
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        for (LColor color : LColor.values()){
            ItemStack stack = create(color);
            stacks.add(stack);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Stream.of(
                Block.createCuboidShape(6, 0, 6, 10, 1, 10),
                Block.createCuboidShape(6, 1.5, 3.5, 8, 5.5, 5.5),
                Block.createCuboidShape(6.5, 5.5, 4, 7.5, 6.5, 5),
                Block.createCuboidShape(6, 1.5, 11, 8, 5.5, 13),
                Block.createCuboidShape(6.5, 5.5, 11.5, 7.5, 6.5, 12.5),
                Block.createCuboidShape(10, 2.5, 7, 12, 6.5, 9),
                Block.createCuboidShape(10.5, 6.5, 7.5, 11.5, 7.5, 8.5)
        ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();
    }

    public ItemStack create(LColor color){
        ItemStack stack = new ItemStack(FBlocks.CRYSTAL);
        stack.getOrCreateNbt().putInt("color", color.getColor());
        return stack;
    }
}
