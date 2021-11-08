package mod.syconn.stf.client.screens.handler;

import mod.syconn.stf.client.screens.slots.SingleItemSlot;
import mod.syconn.stf.init.FItems;
import mod.syconn.stf.init.FScreens;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;

public class WorkstationScreenHandler extends ScreenHandler {

    private final PlayerEntity player;
    private final Inventory inventory;

    public WorkstationScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    public WorkstationScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(FScreens.WORKSTATION, syncId);
        checkSize(inventory, 3);
        inventory.onOpen(playerInventory.player);
        this.inventory = inventory;
        player = playerInventory.player;

        this.addSlot(new SingleItemSlot(inventory, 0, 27, 35, FItems.HILTS));
        this.addSlot(new SingleItemSlot(inventory, 1, 80, 35, FItems.LIGHTSABER));
        this.addSlot(new SingleItemSlot(inventory, 2, 133, 35, FItems.KYBER));

        //The player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
}