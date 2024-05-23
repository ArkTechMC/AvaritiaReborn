package com.iafenvoy.avaritia.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public record ExtremeCraftingShapedRecipe(Identifier id, ItemStack output,
                                          ReadOnlyInventoryHolder<Ingredient> recipeItems) {
    public static final HashMap<Identifier, ExtremeCraftingShapedRecipe> recipes = new HashMap<>();

    public boolean matches(ReadOnlyInventoryHolder<ItemStack> inventory) {
        if (!this.recipeItems.sameSize(inventory)) return false;
        for (int i = 0; i < this.recipeItems.size(); i++)
            if (!this.recipeItems.get(i).test(inventory.get(i)))
                return false;
        return true;
    }
}
