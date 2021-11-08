package mod.syconn.stf.blocks;

import mod.syconn.stf.blockentity.WorkstationEntity;
import mod.syconn.stf.init.FBlockEntities;
import mod.syconn.stf.init.FBlocks;
import mod.syconn.stf.items.lightsaber.customize.LColor;
import mod.syconn.stf.util.BlockProperties;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class LightsaberWorkstation extends BlockWithEntity implements BlockEntityProvider {

    public static EnumProperty<LColor> COLOR = BlockProperties.COLOR;
    public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;;

    public LightsaberWorkstation() {
        super(FabricBlockSettings.of(Material.WOOD).strength(4.5f).breakByTool(FabricToolTags.PICKAXES, 1).requiresTool().sounds(BlockSoundGroup.METAL));
        setDefaultState(getStateManager().getDefaultState().with(COLOR, LColor.BLUE).with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(BlockProperties.COLOR, LColor.getColor(ctx.getPlayer().getMainHandStack().getOrCreateNbt().getInt("color"))).with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(COLOR);
        stateManager.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
          switch (state.get(FACING)){
              case EAST -> {
                  return shapeE;
              }
              case SOUTH -> {
                  return shapeS;
              }
              case WEST -> {
                  return shapeW;
              }
              default -> {
                  return shapeN;
              }
          }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative() && !world.isClient) {
            ItemStack stack = create(state.get(COLOR));
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack));
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {

            if (world.getBlockEntity(pos) instanceof WorkstationEntity) {
                WorkstationEntity be = (WorkstationEntity)world.getBlockEntity(pos);

                if (be.getItems() != null && be.actualSize() == 3) ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), be.getItems().get(1));
                else ItemScatterer.spawn(world, pos, be.getItems());
                System.out.println(be.actualSize() == 3);

                // update comparators
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        for (LColor color : LColor.values()){
            ItemStack stack = create(color);
            stacks.add(stack);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WorkstationEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, FBlockEntities.WORKSTATION, WorkstationEntity::tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;

        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

        if (screenHandlerFactory != null) {
            player.openHandledScreen(screenHandlerFactory);
        }

        return ActionResult.PASS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return false;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public ItemStack create(LColor color){
        ItemStack stack = new ItemStack(FBlocks.WORKSTATION);
        stack.getOrCreateNbt().putInt("color", color.getColor());
        return stack;
    }

    private final VoxelShape shapeN = Stream.of(
            Block.createCuboidShape(0, 0, 0, 3, 13, 3),
            Block.createCuboidShape(7, 10, 4, 13, 12, 6),
            Block.createCuboidShape(13, 13, 12.5, 13.8, 14, 13.5),
            Block.createCuboidShape(10.2, 13, 12.5, 11, 14, 13.5),
            Block.createCuboidShape(11, 13, 12, 13, 14, 14),
            Block.createCuboidShape(7, 10, 9, 9, 12, 14),
            Block.createCuboidShape(5, 10, 4, 6, 11, 10),
            Block.createCuboidShape(3, 13, 4, 4, 14, 10),
            Block.createCuboidShape(2, 13, 2, 5, 15, 4),
            Block.createCuboidShape(0, 9, 0, 16, 10, 16),
            Block.createCuboidShape(0, 12, 0, 16, 13, 16),
            Block.createCuboidShape(0, 0, 13, 3, 13, 16),
            Block.createCuboidShape(13, 0, 13, 16, 13, 16),
            Block.createCuboidShape(13, 0, 0, 16, 13, 3)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();

    private final VoxelShape shapeE = Stream.of(
            Block.createCuboidShape(2.535711428571431, 13, 12.928575714285714, 3.535711428571431, 14, 13.728575714285714),
            Block.createCuboidShape(2.535711428571431, 13, 10.128575714285713, 3.535711428571431, 14, 10.928575714285714),
            Block.createCuboidShape(2.035711428571431, 13, 10.928575714285714, 4.035711428571431, 14, 12.928575714285714),
            Block.createCuboidShape(10.035711428571428, 10, 6.928575714285714, 12.035711428571428, 12, 12.928575714285714),
            Block.createCuboidShape(2.035711428571431, 10, 6.928575714285714, 7.03571142857143, 12, 8.928575714285714),
            Block.createCuboidShape(0.03571142857143084, 9, -0.07142428571428816, 16.035711428571428, 10, 15.928575714285714),
            Block.createCuboidShape(0.03571142857143084, 12, -0.07142428571428816, 16.035711428571428, 13, 15.928575714285714),
            Block.createCuboidShape(12.035711428571428, 13, 1.9285757142857136, 14.035711428571428, 15, 4.928575714285714),
            Block.createCuboidShape(6.035711428571431, 13, 2.9285757142857136, 12.035711428571428, 14, 3.9285757142857136),
            Block.createCuboidShape(6.035711428571431, 10, 4.928575714285714, 12.035711428571428, 11, 5.928575714285714),
            Block.createCuboidShape(0.03571142857143084, 0, 12.928575714285714, 3.035711428571431, 13, 15.928575714285714),
            Block.createCuboidShape(13.035711428571428, 0, 12.928575714285714, 16.035711428571428, 13, 15.928575714285714),
            Block.createCuboidShape(13.035711428571428, 0, -0.07142428571428816, 16.035711428571428, 13, 2.9285757142857136),
            Block.createCuboidShape(0.03571142857143084, 0, -0.07142428571428816, 3.035711428571431, 13, 2.9285757142857136)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();

    private final VoxelShape shapeS = Stream.of(
            Block.createCuboidShape(2.15714, 13, 2.46429, 2.95714, 14, 3.46429),
            Block.createCuboidShape(4.95714, 13, 2.46429, 5.75714, 14, 3.46429),
            Block.createCuboidShape(2.95714, 13, 1.96429, 4.95714, 14, 3.9642899999999996),
            Block.createCuboidShape(2.95714, 10, 9.96429, 8.957139999999999, 12, 11.96429),
            Block.createCuboidShape(6.95714, 10, 1.96429, 8.957139999999999, 12, 6.96429),
            Block.createCuboidShape(-0.04285999999999998, 9, -0.03570999999999991, 15.95714, 10, 15.96429),
            Block.createCuboidShape(-0.04285999999999998, 12, -0.03570999999999991, 15.95714, 13, 15.96429),
            Block.createCuboidShape(10.957139999999999, 13, 11.96429, 13.957139999999999, 15, 13.96429),
            Block.createCuboidShape(11.957139999999999, 13, 5.96429, 12.957139999999999, 14, 11.96429),
            Block.createCuboidShape(9.957139999999999, 10, 5.96429, 10.957139999999999, 11, 11.96429),
            Block.createCuboidShape(-0.04285999999999998, 0, -0.03570999999999991, 2.95714, 13, 2.96429),
            Block.createCuboidShape(-0.04285999999999998, 0, 12.96429, 2.95714, 13, 15.96429),
            Block.createCuboidShape(12.957139999999999, 0, 12.96429, 15.95714, 13, 15.96429),
            Block.createCuboidShape(12.957139999999999, 0, -0.03570999999999991, 15.95714, 13, 2.96429)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();

    private final VoxelShape shapeW = Stream.of(
            Block.createCuboidShape(12.571425714285713, 13, 2.2357185714285706, 13.571425714285713, 14, 3.0357185714285713),
            Block.createCuboidShape(12.571425714285713, 13, 5.035718571428571, 13.571425714285713, 14, 5.835718571428572),
            Block.createCuboidShape(12.071425714285713, 13, 3.0357185714285713, 14.071425714285713, 14, 5.035718571428571),
            Block.createCuboidShape(4.071425714285716, 10, 3.0357185714285713, 6.071425714285716, 12, 9.035718571428571),
            Block.createCuboidShape(9.071425714285715, 10, 7.035718571428571, 14.071425714285713, 12, 9.035718571428571),
            Block.createCuboidShape(0.07142571428571642, 9, 0.035718571428571266, 16.071425714285713, 10, 16.035718571428575),
            Block.createCuboidShape(0.07142571428571642, 12, 0.035718571428571266, 16.071425714285713, 13, 16.035718571428575),
            Block.createCuboidShape(2.0714257142857164, 13, 11.035718571428571, 4.071425714285716, 15, 14.035718571428571),
            Block.createCuboidShape(4.071425714285716, 13, 12.035718571428571, 10.071425714285713, 14, 13.035718571428571),
            Block.createCuboidShape(4.071425714285716, 10, 10.035718571428571, 10.071425714285713, 11, 11.035718571428571),
            Block.createCuboidShape(13.071425714285713, 0, 0.035718571428571266, 16.071425714285713, 13, 3.0357185714285713),
            Block.createCuboidShape(0.07142571428571642, 0, 0.035718571428571266, 3.0714257142857164, 13, 3.0357185714285713),
            Block.createCuboidShape(0.07142571428571642, 0, 13.035718571428571, 3.0714257142857164, 13, 16.035718571428575),
            Block.createCuboidShape(13.071425714285713, 0, 13.035718571428571, 16.071425714285713, 13, 16.035718571428575)
    ).reduce((v1, v2) -> VoxelShapes.combine(v1, v2, BooleanBiFunction.OR)).get();
}
