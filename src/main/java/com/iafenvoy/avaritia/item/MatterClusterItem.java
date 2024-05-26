package com.iafenvoy.avaritia.item;

import com.iafenvoy.avaritia.registry.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MatterClusterItem extends Item {
    public static final String ITEMS_NBT = "Items";

    public MatterClusterItem() {
        super(new FabricItemSettings());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        NbtList items = stack.getOrCreateNbt().getList(ITEMS_NBT, NbtElement.COMPOUND_TYPE);
        tooltip.add(Text.literal("Remain Stack(s): " + items.size()));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        NbtList items = itemStack.getOrCreateNbt().getList(ITEMS_NBT, NbtElement.COMPOUND_TYPE);
        if (items.size() > 0) {
            NbtCompound first = items.getCompound(0);
            ItemStack f = ItemStack.fromNbt(first);
            user.getInventory().offerOrDrop(f);
            items.remove(0);
        }
        if (items.size() == 0) return TypedActionResult.success(ItemStack.EMPTY, true);
        else itemStack.getOrCreateNbt().put(ITEMS_NBT, items);
        return TypedActionResult.success(itemStack);
    }

    public static ItemStack create(List<ItemStack> items) {
        NbtList list = new NbtList();
        for (ItemStack x : items) {
            if (x.isEmpty()) continue;
            NbtCompound compound = new NbtCompound();
            x.writeNbt(compound);
            list.add(compound);
        }
        ItemStack stack = new ItemStack(ModItems.MATTER_CLUSTER);
        stack.getOrCreateNbt().put(ITEMS_NBT, list);
        return stack;
    }
}
