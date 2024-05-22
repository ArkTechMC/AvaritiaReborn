package com.iafenvoy.avaritia.singularity;

import com.iafenvoy.avaritia.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class SingularityHelper {
    private static final String TYPE_KEY = "singularity_type";

    public static int getColorFromStack(ItemStack stack) {
        return getFromStack(stack).getColor();
    }

    public static boolean same(ItemStack stack, Singularity singularity) {
        String type = stack.getOrCreateNbt().getString(TYPE_KEY);
        return singularity.getId().equals(type);
    }

    public static Singularity getFromStack(ItemStack stack) {
        String type = stack.getOrCreateNbt().getString(TYPE_KEY);
        return Singularity.MATERIALS.getOrDefault(type, Singularity.EMPTY);
    }

    public static ItemStack buildStack(Singularity singularity) {
        ItemStack stack = new ItemStack(ModItems.SINGULARITY);
        stack.getOrCreateNbt().putString(TYPE_KEY, singularity.getId());
        return stack;
    }

    public static Singularity get(ItemConvertible itemConvertible) {
        for (Map.Entry<String, Singularity> material : Singularity.MATERIALS.entrySet()) {
            if (itemConvertible instanceof Block block)
                if (material.getValue().checkBlock(block) != null)
                    return material.getValue();
            if (itemConvertible instanceof BlockItem item)
                if (material.getValue().checkBlock(item.getBlock()) != null)
                    return material.getValue();
            if (itemConvertible instanceof Item item)
                if (material.getValue().checkItem(item) != null)
                    return material.getValue();
        }
        return Singularity.EMPTY;
    }

    public static Singularity.SingularityIngredient getIngredient(ItemConvertible itemConvertible, Singularity singularity) {
        if (itemConvertible instanceof Block block) {
            Singularity.SingularityIngredient ingredient = singularity.checkBlock(block);
            if (ingredient != null)
                return ingredient;
        }
        if (itemConvertible instanceof BlockItem item) {
            Singularity.SingularityIngredient ingredient = singularity.checkBlock(item.getBlock());
            if (ingredient != null)
                return ingredient;
        }
        if (itemConvertible instanceof Item item) {
            Singularity.SingularityIngredient ingredient = singularity.checkItem(item);
            if (ingredient != null)
                return ingredient;
        }
        return Singularity.SingularityIngredient.EMPTY;
    }
}
