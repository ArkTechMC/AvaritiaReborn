package com.iafenvoy.avaritia.compat.emi;

import com.iafenvoy.avaritia.gui.ExtremeCraftingTableScreenHandler;
import dev.emi.emi.api.recipe.EmiPlayerInventory;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.EmiCraftContext;
import dev.emi.emi.api.recipe.handler.EmiRecipeHandler;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class ExtremeCraftingHandler implements EmiRecipeHandler<ExtremeCraftingTableScreenHandler> {
    @Override
    public EmiPlayerInventory getInventory(HandledScreen screen) {
        return new EmiPlayerInventory(screen.getScreenHandler().getStacks().stream().map(EmiStack::of).toList());
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe instanceof ExtremeCraftingRecipePlugin.EmiExtremeCraftingRecipe;
    }

    @Override
    public boolean canCraft(EmiRecipe recipe, EmiCraftContext context) {
        return recipe instanceof ExtremeCraftingRecipePlugin.EmiExtremeCraftingRecipe;
    }

    @Override
    public boolean craft(EmiRecipe recipe, EmiCraftContext context) {
        return recipe instanceof ExtremeCraftingRecipePlugin.EmiExtremeCraftingRecipe;
    }
}
