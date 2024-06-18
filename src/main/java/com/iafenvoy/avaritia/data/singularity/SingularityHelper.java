package com.iafenvoy.avaritia.data.singularity;

import com.iafenvoy.avaritia.registry.AvaritiaItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;

public class SingularityHelper {
    private static final String TYPE_KEY = "singularity_type";

    @Environment(EnvType.CLIENT)
    public static int getColorFromStack(ItemStack stack) {
        String type = stack.getOrCreateNbt().getString(TYPE_KEY);
        return SingularityColor.COLOR_MAP.getOrDefault(type, SingularityColor.EMPTY).color().get();
    }

    public static boolean compare(ItemStack a, ItemStack b) {
        return getFromStack(a) == getFromStack(b);
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
        ItemStack stack = new ItemStack(AvaritiaItems.SINGULARITY);
        stack.getOrCreateNbt().putString(TYPE_KEY, singularity.getId());
        return stack;
    }

    public static Singularity get(String id) {
        for (Singularity material : Singularity.MATERIALS.values())
            if (material.getId().equals(id))
                return material;
        return Singularity.EMPTY;
    }

    public static Singularity get(ItemStack stack) {
        for (Singularity material : Singularity.MATERIALS.values())
            if (material.test(stack) != null)
                return material;
        return Singularity.EMPTY;
    }

    public static Singularity.SingularityIngredient getIngredient(ItemStack stack, Singularity singularity) {
        Singularity.SingularityIngredient ingredient = singularity.test(stack);
        if (ingredient != null)
            return ingredient;
        return Singularity.SingularityIngredient.EMPTY;
    }
}
