package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.recipe.ExtremeCraftingShapedRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(AvaritiaReborn.MOD_ID, ExtremeCraftingShapedRecipe.Serializer.ID),
                ExtremeCraftingShapedRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(AvaritiaReborn.MOD_ID, ExtremeCraftingShapedRecipe.Serializer.ID),
                ExtremeCraftingShapedRecipe.Type.INSTANCE);
    }
}
