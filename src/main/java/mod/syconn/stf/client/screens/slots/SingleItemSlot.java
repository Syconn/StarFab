package mod.syconn.stf.client.screens.slots;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SingleItemSlot extends Slot {
    private final Item item;

    public SingleItemSlot(Inventory inventory, int index, int x, int y, Item item) {
        super(inventory, index, x, y);
        this.item = item;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() == this.item;
    }
}
