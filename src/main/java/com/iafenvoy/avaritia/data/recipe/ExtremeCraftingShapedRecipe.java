package com.iafenvoy.avaritia.data.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;

public record ExtremeCraftingShapedRecipe(Identifier id, ItemStack output, List<List<Ingredient>> recipeItems) {
    public static final HashMap<Identifier, ExtremeCraftingShapedRecipe> RECIPES = new HashMap<>();

    private static <T, M> boolean sameSize(List<List<T>> first, List<List<M>> second) {
        return first.size() == second.size() && first.get(0).size() == second.get(0).size();
    }

    public boolean matches(List<List<ItemStack>> inventory) {
        if (!sameSize(inventory, this.recipeItems)) return false;
        for (int i = 0; i < this.recipeItems.size(); i++) {
            List<Ingredient> ing = this.recipeItems.get(i);
            List<ItemStack> inv = inventory.get(i);
            for (int j = 0; j < ing.size(); j++)
                if (!ing.get(j).test(inv.get(j)))
                    return false;
        }
        return true;
    }
}
