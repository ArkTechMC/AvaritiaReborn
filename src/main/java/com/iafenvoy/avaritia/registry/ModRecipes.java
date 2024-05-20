package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.recipe.ExtremeCraftingTableRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(AvaritiaReborn.MOD_ID, ExtremeCraftingTableRecipe.Serializer.ID),
                ExtremeCraftingTableRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(AvaritiaReborn.MOD_ID, ExtremeCraftingTableRecipe.Type.ID),
                ExtremeCraftingTableRecipe.Type.INSTANCE);
    }
}
