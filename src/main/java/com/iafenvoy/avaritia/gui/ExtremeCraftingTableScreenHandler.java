package com.iafenvoy.avaritia.gui;

import com.iafenvoy.avaritia.registry.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;


public class ExtremeCraftingTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    //This constructor gets called on the client when the server wants it to open the screenHandler,
    //The client will call the other constructor with an empty Inventory and the screenHandler will automatically
    //sync this empty inventory with the inventory on the server.
    public ExtremeCraftingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(82));
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    public ExtremeCraftingTableScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.EXTREME_CRAFTING_TABLE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        this.inventory.onOpen(playerInventory.player);
        this.addCraftingSlots(inventory);
        this.addSlot(new OutputSlot(inventory, 81, 201, 80));
        this.addPlayerInventory(playerInventory);
        this.addPlayerHotbar(playerInventory);
        this.inventory.markDirty();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        player.sendMessage(Text.of(String.valueOf(invSlot)), false);

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (80 < invSlot && invSlot < 117) {
                //player.sendMessage(Text.of("shift no inventario"), false);
                if (!this.insertItem(originalStack, 0, 80, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (invSlot == 117) {
                //player.sendMessage(Text.of("shift no output slot"), false);

                if (!this.insertItem(originalStack, 81, 116, false)) {
                    return ItemStack.EMPTY;
                } else {
                    for (int i = 0; i < 81; ++i) {
                        if (this.getSlot(i).getStack().getCount() != 0) {
                            this.getSlot(i).getStack().setCount(this.getSlot(i).getStack().getCount() - 1);
                        }
                    }
                }

            } else {
                //player.sendMessage(Text.of("shift na crafting"), false);
                if (!this.insertItem(originalStack, 81, 116, false)) {
                    return ItemStack.EMPTY;
                }
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
    public boolean canUse(PlayerEntity player) {
        return true;
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i)
            for (int l = 0; l < 9; ++l)
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 31 + l * 18, 174 + i * 18));
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }


    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i)
            this.addSlot(new Slot(playerInventory, i, 31 + i * 18, 232));
    }

    private void addCraftingSlots(Inventory inventory) {
        for (int i = 0; i < 9; ++i)
            for (int l = 0; l < 9; ++l)
                this.addSlot(new CraftingSlot(inventory, l + i * 9, 3 + l * 18, 8 + i * 18));
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        if (slotIndex >= 0)
            this.slots.get(slotIndex).markDirty();
    }
}