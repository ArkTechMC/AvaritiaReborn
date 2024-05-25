package com.iafenvoy.avaritia.data.singularity;

import com.iafenvoy.avaritia.util.IdUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Singularity {
    public static final Singularity EMPTY = new Singularity("", 0);
    public static final HashMap<String, Singularity> MATERIALS = new HashMap<>();
    private final String id;
    private final int cost;
    private List<SingularityRecipe> recipes = new ArrayList<>();

    public Singularity(String id, int cost) {
        this.id = id;
        this.cost = cost;
    }

    public List<SingularityRecipe> getRecipes() {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        return this.recipes;
    }

    public String getId() {
        return this.id;
    }

    public int getCost() {
        return this.cost;
    }

    public void addRecipe(SingularityRecipe recipe) {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        this.recipes.add(recipe);
    }

    public SingularityIngredient test(ItemStack stack) {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        for (SingularityRecipe recipe : this.recipes.stream().filter(SingularityRecipe::canUse).toList())
            for (SingularityIngredient ingredient : recipe.ingredients)
                if (ingredient.ingredient.test(stack))
                    return ingredient;
        return null;
    }

    public boolean hasAvailable() {
        return this.recipes.stream().anyMatch(SingularityRecipe::canUse);
    }

    public record SingularityRecipe(List<String> dependency, String result, List<SingularityIngredient> ingredients) {
        public boolean canUse() {
            return this.dependency.isEmpty() || this.dependency.stream().anyMatch(FabricLoader.getInstance()::isModLoaded);
        }
    }

    public record SingularityIngredient(Ingredient ingredient, int amount) {
        public static final SingularityIngredient EMPTY = new SingularityIngredient(Ingredient.EMPTY, 0);
    }
}
