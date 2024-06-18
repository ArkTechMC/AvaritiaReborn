package com.iafenvoy.avaritia.item.block.entity;

import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.gui.NeutroniumCompressorScreenHandler;
import com.iafenvoy.avaritia.item.block.NeutroniumCompressorBlock;
import com.iafenvoy.avaritia.registry.AvaritiaBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NeutroniumCompressorBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    protected final PropertyDelegate propertyDelegate;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private int progress = 0;
    private Singularity material = Singularity.EMPTY;

    public NeutroniumCompressorBlockEntity(BlockPos pos, BlockState state) {
        super(AvaritiaBlockEntities.NEUTRONIUM_COMPRESSOR, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> NeutroniumCompressorBlockEntity.this.progress;
                    case 1 -> NeutroniumCompressorBlockEntity.this.material.getCost();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> NeutroniumCompressorBlockEntity.this.progress = value;
                    case 1 -> {
                    }
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    private static void consumeItem(NeutroniumCompressorBlockEntity entity) {
        Singularity singularity = SingularityHelper.get(entity.inventory.get(0));
        if (entity.propertyDelegate.get(0) == 0) {
            if (singularity != Singularity.EMPTY) {
                entity.material = singularity;
                addToProgress(entity);
                entity.inventory.get(0).decrement(1);
            }
        } else {
            if (singularity == entity.material) {
                addToProgress(entity);
                entity.inventory.get(0).decrement(1);
            }
        }
    }

    private static void addToProgress(NeutroniumCompressorBlockEntity entity) {
        if (entity.inventory.get(0).getItem() == null)
            return;
        Singularity.SingularityIngredient ingredient = SingularityHelper.getIngredient(entity.inventory.get(0), entity.material);
        entity.propertyDelegate.set(0, entity.propertyDelegate.get(0) + ingredient.amount());
    }

    public static void tick(World world, BlockPos pos, BlockState state, NeutroniumCompressorBlockEntity entity) {
        if (entity.inventory.get(0).getItem() != null && entity.inventory.get(0).getItem() != Items.AIR) {
            world.setBlockState(pos, state.with(NeutroniumCompressorBlock.ACTIVE, true));
            consumeItem(entity);
        } else
            world.setBlockState(pos, state.with(NeutroniumCompressorBlock.ACTIVE, false));
        if (entity.material != Singularity.EMPTY && entity.progress >= entity.material.getCost())
            craftItem(entity);
    }

    private static void craftItem(NeutroniumCompressorBlockEntity entity) {
        if (entity.inventory.get(1).getItem() == Items.AIR) {
            entity.inventory.set(1, SingularityHelper.buildStack(entity.material));
            entity.resetProgress();
            entity.material = Singularity.EMPTY;
        } else if (SingularityHelper.same(entity.inventory.get(1), entity.material)) {
            entity.inventory.get(1).increment(1);
            entity.resetProgress();
            entity.material = Singularity.EMPTY;
        }
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
        return new NeutroniumCompressorScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("neutronium_compressor.progress", this.progress);
        nbt.putString("neutronium_compressor.material", this.material.getId());
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, this.inventory);
        this.progress = nbt.getInt("neutronium_compressor.progress");
        this.material = Singularity.MATERIALS.getOrDefault(nbt.getString("neutronium_compressor.material"), Singularity.EMPTY);
        super.readNbt(nbt);
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
