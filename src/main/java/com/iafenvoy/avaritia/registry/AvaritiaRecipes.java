package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.recipe.ExtremeCraftingShapedRecipe;
import com.iafenvoy.avaritia.data.recipe.ExtremeCraftingShapelessRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class AvaritiaRecipes {
    public static void init() {
        register("extreme_shaped", ExtremeCraftingShapedRecipe.Type.INSTANCE);
        register("extreme_shaped", ExtremeCraftingShapedRecipe.Serializer.INSTANCE);
        register("extreme_shapeless", ExtremeCraftingShapelessRecipe.Type.INSTANCE);
        register("extreme_shapeless", ExtremeCraftingShapelessRecipe.Serializer.INSTANCE);
    }

    public static RecipeSerializer<?> register(String id, RecipeSerializer<?> recipeType) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(AvaritiaReborn.MOD_ID, id), recipeType);
    }

    public static RecipeType<?> register(String id, RecipeType<?> recipeType) {
        return Registry.register(Registries.RECIPE_TYPE, new Identifier(AvaritiaReborn.MOD_ID, id), recipeType);
    }
}
