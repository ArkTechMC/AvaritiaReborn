package com.iafenvoy.avaritia.data.recipe;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ExtremeCraftingShapelessRecipe {
    public static final HashMap<Identifier, ExtremeCraftingShapelessRecipe> RECIPES = new HashMap<>();
    public static final ExtremeCraftingShapelessRecipe INFINITY_CATALYST = new ExtremeCraftingShapelessRecipe(new Identifier(AvaritiaReborn.MOD_ID, "infinity_catalyst"), new ItemStack(ModItems.INFINITY_CATALYST), () -> {
        List<Ingredient> ingredients = new ArrayList<>(Stream.of(
                ModItems.DIAMOND_LATTICE,
                ModItems.CRYSTAL_MATRIX_INGOT,
                ModItems.NEUTRON_PILE,
                ModItems.NEUTRON_NUGGET,
                ModItems.NEUTRONIUM_INGOT,
                ModItems.ULTIMATE_STEW,
                ModItems.COSMIC_MEATBALLS,
                ModItems.ENDEST_PEARL,
                ModItems.RECORD_FRAGMENT
        ).map(Ingredient::ofItems).toList());
        for (Singularity singularity : Singularity.MATERIALS.values())
            if (singularity.hasAvailable())
                ingredients.add(Ingredient.ofStacks(SingularityHelper.buildStack(singularity)));
        return ingredients;
    });
    public static final ExtremeCraftingShapelessRecipe ULTIMATE_STEW = new ExtremeCraftingShapelessRecipe(new Identifier(AvaritiaReborn.MOD_ID, "ultimate_stew"), new ItemStack(ModItems.ULTIMATE_STEW), () -> Stream.of(
            ModItems.NEUTRON_PILE,
            Items.WHEAT,
            Items.CARROT,
            Items.BEETROOT,
            Items.POTATO,
            Blocks.MELON,
            Blocks.PUMPKIN,
            Items.CACTUS,
            Items.RED_MUSHROOM,
            Items.BROWN_MUSHROOM,
            Items.NETHER_WART,
            Items.SWEET_BERRIES,
            Items.GLOW_BERRIES,
            Items.CHORUS_FRUIT
    ).map(Ingredient::ofItems).toList());
    public static final ExtremeCraftingShapelessRecipe COSMIC_MEATBALLS = new ExtremeCraftingShapelessRecipe(new Identifier(AvaritiaReborn.MOD_ID, "cosmic_meatballs"), new ItemStack(ModItems.COSMIC_MEATBALLS), () -> Stream.of(
            ModItems.NEUTRON_PILE,
            Items.BEEF,
            Items.CHICKEN,
            Items.PORKCHOP,
            Items.MUTTON,
            Items.TROPICAL_FISH,
            Items.SALMON,
            Items.COD,
            Items.PUFFERFISH
    ).map(Ingredient::ofItems).toList());
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

    public static void reloadAll() {
        INFINITY_CATALYST.reload();
        ULTIMATE_STEW.reload();
        COSMIC_MEATBALLS.reload();
    }

    static {
        RECIPES.put(INFINITY_CATALYST.getId(), INFINITY_CATALYST);
        RECIPES.put(ULTIMATE_STEW.getId(), ULTIMATE_STEW);
        RECIPES.put(COSMIC_MEATBALLS.getId(), COSMIC_MEATBALLS);
    }
}
