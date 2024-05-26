package com.iafenvoy.avaritia.compat.emi;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.recipe.ExtremeCraftingShapedRecipe;
import com.iafenvoy.avaritia.data.recipe.ExtremeCraftingShapelessRecipe;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.registry.ModBlocks;
import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.registry.ModScreenHandlers;
import com.iafenvoy.avaritia.util.RecipeUtil;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExtremeCraftingRecipePlugin implements EmiPlugin {
    private static final Identifier EXTREME_CRAFTING = new Identifier(AvaritiaReborn.MOD_ID, "extreme_crafting");
    private static final EmiTexture TEXTURE = new EmiTexture(new Identifier(AvaritiaReborn.MOD_ID, "textures/gui/extreme_crafting_jei.png"), 0, 0, 188, 162);
    private static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.EXTREME_CRAFTING_TABLE);
    private static final EmiRecipeCategory EXTREME_CRAFTING_CATEGORY = new EmiRecipeCategory(EXTREME_CRAFTING, WORKSTATION);

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(EXTREME_CRAFTING_CATEGORY);
        registry.addWorkstation(EXTREME_CRAFTING_CATEGORY, WORKSTATION);
        for (ExtremeCraftingShapedRecipe recipe : ExtremeCraftingShapedRecipe.RECIPES.values())
            registry.addRecipe(new EmiExtremeCraftingRecipe(recipe));
        for (ExtremeCraftingShapelessRecipe recipe : ExtremeCraftingShapelessRecipe.RECIPES.values())
            registry.addRecipe(new EmiExtremeCraftingRecipe(recipe));
        registry.addRecipeHandler(ModScreenHandlers.EXTREME_CRAFTING_TABLE_SCREEN_HANDLER, new ExtremeCraftingHandler());
        registry.setDefaultComparison(ModItems.SINGULARITY, Comparison.of((a, b) -> SingularityHelper.compare(a.getItemStack(), b.getItemStack())));
    }

    public record EmiExtremeCraftingRecipe(Identifier id, boolean shapeless, List<List<Ingredient>> inputs,
                                           ItemStack output) implements EmiRecipe {
        public EmiExtremeCraftingRecipe(ExtremeCraftingShapedRecipe recipe) {
            this(recipe.id(), false, recipe.recipeItems(), recipe.output().copy());
        }

        public EmiExtremeCraftingRecipe(ExtremeCraftingShapelessRecipe recipe) {
            this(recipe.getId(), true, RecipeUtil.toTable(recipe.getAllIngredients(), 9, 9), recipe.getOutput().copy());
        }

        @Override
        public EmiRecipeCategory getCategory() {
            return EXTREME_CRAFTING_CATEGORY;
        }

        @Override
        public @Nullable Identifier getId() {
            return this.id;
        }

        @Override
        public List<EmiIngredient> getInputs() {
            List<EmiIngredient> ingredients = new ArrayList<>();
            for (List<Ingredient> ingredient : this.inputs)
                for (Ingredient i : ingredient)
                    ingredients.add(EmiIngredient.of(i));
            return ingredients;
        }

        @Override
        public List<EmiStack> getOutputs() {
            return List.of(EmiStack.of(this.output));
        }

        @Override
        public int getDisplayWidth() {
            return 190;
        }

        @Override
        public int getDisplayHeight() {
            return 165;
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            widgets.addTexture(TEXTURE, 0, 0);
            if (this.shapeless)
                widgets.addTexture(EmiTexture.SHAPELESS, 167, 2);
            for (int i = 0; i < this.inputs.size(); i++) {
                List<Ingredient> ingredients = this.inputs.get(i);
                for (int j = 0; j < ingredients.size(); j++)
                    widgets.addSlot(EmiIngredient.of(ingredients.get(j)), j * 18 + 1, i * 18 + 1);
            }
            widgets.addSlot(this.getOutputs().get(0), 163, 69).large(true).recipeContext(this);
        }
    }
}
