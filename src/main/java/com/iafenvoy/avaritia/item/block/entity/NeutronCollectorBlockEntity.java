package com.iafenvoy.avaritia.item.block.entity;

import com.iafenvoy.avaritia.gui.NeutronCollectorScreenHandler;
import com.iafenvoy.avaritia.registry.AvaritiaBlockEntities;
import com.iafenvoy.avaritia.registry.AvaritiaItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NeutronCollectorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    protected final PropertyDelegate propertyDelegate;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private int progress = 0;
    private int maxProgress = 7111;

    public NeutronCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(AvaritiaBlockEntities.NEUTRON_COLLECTOR, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> NeutronCollectorBlockEntity.this.progress;
                    case 1 -> NeutronCollectorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> NeutronCollectorBlockEntity.this.progress = value;
                    case 1 -> NeutronCollectorBlockEntity.this.maxProgress = value;
                }
            }

            public int size() {
                return 2;
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, NeutronCollectorBlockEntity entity) {
        if (world.isClient()) {
            return;
        }
        entity.propertyDelegate.set(0, entity.propertyDelegate.get(0) + 1);
        entity.progress++;
        if (entity.progress >= entity.maxProgress) {
            craftItem(entity);
            entity.resetProgress();
        }
    }

    private static void craftItem(NeutronCollectorBlockEntity entity) {
        entity.inventory.set(0, new ItemStack(AvaritiaItems.NEUTRON_PILE, 1 + entity.inventory.get(0).getCount()));
        entity.resetProgress();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new NeutronCollectorScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putInt("neutron_collector.progress", this.progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, this.inventory);
        super.readNbt(nbt);
        this.progress = nbt.getInt("neutron_collector.progress");
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
