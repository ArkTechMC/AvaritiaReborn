package com.iafenvoy.avaritia.item.block.entity;

import com.iafenvoy.avaritia.gui.ExtremeCraftingTableScreenHandler;
import com.iafenvoy.avaritia.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ExtremeCraftingTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(82, ItemStack.EMPTY);


    public ExtremeCraftingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTREME_CRAFTING_TABLE, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
        //return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }
    // For versions 1.18.2 and below, please use return new TranslatableText(getCachedState().getBlock().getTranslationKey());

    @Override//adicionar isto para quando sair do mundo progresso estar guardado
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }




    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ExtremeCraftingTableScreenHandler(syncId, inv, this);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ExtremeCraftingTableBlockEntity entity) {

    }
}
