package com.iafenvoy.avaritia.data.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ExtremeCraftingShapelessRecipe {
    public static final HashMap<Identifier, ExtremeCraftingShapelessRecipe> RECIPES = new HashMap<>();
    private final Identifier id;
    private final ItemStack output;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private boolean filled = false;
    private final Supplier<List<Ingredient>> defaultItems;

    public ExtremeCraftingShapelessRecipe(Identifier id, ItemStack output, Supplier<List<Ingredient>> defaultItems) {
        this.id = id;
        this.output = output;
        this.defaultItems = defaultItems;
    }

    public Identifier getId() {
        return this.id;
    }

    public ItemStack getOutput() {
        return this.output.copy();
    }

    public void addIngredients(List<Ingredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    public boolean match(List<ItemStack> stacks) {
        if (!this.filled) this.fill();
        stacks = stacks.stream().filter(x -> !x.isEmpty()).toList();
        if (stacks.size() != this.ingredients.size()) return false;
        List<Ingredient> ingredients = new ArrayList<>(this.ingredients);
        for (ItemStack stack : stacks) {
            int index = 0;
            while (index < ingredients.size() && !ingredients.get(index).test(stack)) index++;
            if (index == ingredients.size()) return false;
            ingredients.remove(index);
        }
        return true;
    }

    public List<Ingredient> getAllIngredients() {
        if (!this.filled) this.fill();
        return List.copyOf(this.ingredients);
    }

    private void fill() { // In order to be faster.
        this.filled = true;
        this.ingredients.addAll(0, this.defaultItems.get());
    }

    public static void findAndAdd(Identifier id, List<Ingredient> ingredients) {
        ExtremeCraftingShapelessRecipe recipe = RECIPES.get(id);
        if (recipe != null)
            recipe.addIngredients(ingredients);
    }
}
