package mod.syconn.stf.blockentity;

import mod.syconn.stf.util.interfaces.IInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class InventoryEntity extends BlockEntity implements IInventory, NamedScreenHandlerFactory {

    protected final int size;
    protected DefaultedList<ItemStack> items;
    protected boolean singleUse;
    protected boolean using;
    protected int usingNum;

    public InventoryEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size, boolean singleUse) {
        super(type, pos, state);
        this.size = size;
        items = DefaultedList.ofSize(size, ItemStack.EMPTY);
        this.singleUse = singleUse;
    }

    public static void tick(World world, BlockPos pos, BlockState state, WorkstationEntity be) {}

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (singleUse && using){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        System.out.println("Closed");
        usingNum--;
        if (usingNum == 0)
            using = false;
        markDirty();
    }

    @Override
    public void onOpen(PlayerEntity player) {
        usingNum++;
        using = true;
        markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, items);
        nbt.putBoolean("singleUse", singleUse);
        nbt.putBoolean("using", using);
        nbt.putInt("usingNum", usingNum);
        return super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        singleUse = nbt.getBoolean("singleUse");
        using = nbt.getBoolean("using");
        usingNum = nbt.getInt("usingNum");
    }
}
