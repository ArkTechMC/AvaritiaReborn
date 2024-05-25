package com.iafenvoy.avaritia.data.recipe;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.registry.ModItems;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExtremeCraftingShapelessRecipe {
    public static final HashMap<Identifier, ExtremeCraftingShapelessRecipe> RECIPES = new HashMap<>();
    public static final ExtremeCraftingShapelessRecipe INFINITY_CATALYST = new ExtremeCraftingShapelessRecipe(new Identifier(AvaritiaReborn.MOD_ID, "infinity_catalyst"), new ItemStack(ModItems.INFINITY_CATALYST));
    private final Identifier id;
    private final ItemStack output;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private boolean filled = false;

    public ExtremeCraftingShapelessRecipe(Identifier id, ItemStack output) {
        this.id = id;
        this.output = output;
    }

    public Identifier getId() {
        return this.id;
    }

    public ItemStack getOutput() {
        return this.output.copy();
    }

    public void reload() {
        this.ingredients.clear();
        this.filled = false;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addIngredients(List<Ingredient> ingredient) {
        this.ingredients.addAll(ingredient);
    }

    public void addItems(List<ItemConvertible> items) {
        this.addIngredients(items.stream().map(Ingredient::ofItems).toList());
    }

    public boolean match(List<ItemStack> stacks) {
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
        if (!this.filled) { // In order to be faster.
            this.filled = true;
            for (Singularity singularity : Singularity.MATERIALS.values())
                if (singularity.hasAvailable())
                    this.ingredients.add(Ingredient.ofStacks(SingularityHelper.buildStack(singularity)));
        }
        return List.copyOf(this.ingredients);
    }

    public static void reloadAll() {
        INFINITY_CATALYST.reload();
        INFINITY_CATALYST.addItems(List.of(
                ModItems.DIAMOND_LATTICE,
                ModItems.CRYSTAL_MATRIX_INGOT,
                ModItems.NEUTRON_PILE,
                ModItems.NEUTRON_NUGGET,
                ModItems.NEUTRONIUM_INGOT,
                ModItems.ULTIMATE_STEW,
                ModItems.COSMIC_MEATBALLS,
                ModItems.ENDEST_PEARL,
                ModItems.RECORD_FRAGMENT
        ));
    }

    static {
        RECIPES.put(INFINITY_CATALYST.getId(), INFINITY_CATALYST);
    }
}
