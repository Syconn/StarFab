package mod.syconn.stf.blockentity;

import mod.syconn.stf.client.handler.WorkstationScreenHandler;
import mod.syconn.stf.init.FBlockEntities;
import mod.syconn.stf.util.helpers.WorkstationHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WorkstationEntity extends InventoryEntity {

    //Whenever your BlockEntity data changes and needs to be saved, call markDirty(). This will force the writeNbt method to be called when the world is next saved by marking the chunk which your block is in as dirty. As a general rule of thumb, simply call markDirty() whenever you change any custom variable in your BlockEntity class.
    public boolean[] added = new boolean[3];

    public WorkstationEntity(BlockPos pos, BlockState state) {
        super(FBlockEntities.WORKSTATION, pos, state, 3, true);
    }

    public static void tick(World world, BlockPos pos, BlockState state, WorkstationEntity be){
        if (!world.isClient()) {
            WorkstationHelper.Lightsaber(be);
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new WorkstationScreenHandler(syncId, inv, this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        added[0] = nbt.getBoolean("added0");
        added[1] = nbt.getBoolean("added1");
        added[2] = nbt.getBoolean("added2");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("added0", added[0]);
        nbt.putBoolean("added1", added[1]);
        nbt.putBoolean("added1", added[2]);
        return super.writeNbt(nbt);
    }
}
