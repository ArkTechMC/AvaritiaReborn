package com.iafenvoy.avaritia.compat.emi;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.registry.ModBlocks;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompressorRecipePlugin implements EmiPlugin {
    private static final Identifier COMPRESSOR = new Identifier(AvaritiaReborn.MOD_ID, "compressor");
    private static final EmiTexture TEXTURE = new EmiTexture(new Identifier(AvaritiaReborn.MOD_ID, "textures/gui/compressor.png"), 14, 20, 136, 45);
    private static final EmiStack WORKSTATION = EmiStack.of(ModBlocks.COMPRESSOR);
    private static final EmiRecipeCategory COMPRESSOR_CATEGORY = new EmiRecipeCategory(COMPRESSOR, WORKSTATION);

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(COMPRESSOR_CATEGORY);
        registry.addWorkstation(COMPRESSOR_CATEGORY, WORKSTATION);
        for (Map.Entry<String, Singularity> singularity : Singularity.MATERIALS.entrySet())
            registry.addRecipe(new EmiCompressorRecipe(singularity.getKey(), singularity.getValue()));
    }

    public record EmiCompressorRecipe(String id, Singularity singularity) implements EmiRecipe {
        @Override
        public EmiRecipeCategory getCategory() {
            return COMPRESSOR_CATEGORY;
        }

        @Override
        public @NotNull Identifier getId() {
            return new Identifier(AvaritiaReborn.MOD_ID, "compressor_" + this.id);
        }

        @Override
        public List<EmiIngredient> getInputs() {
            List<EmiIngredient> ingredients = new ArrayList<>();
            for (Singularity.SingularityRecipe recipe : this.singularity.getRecipes())
                for (Singularity.SingularityIngredient ingredient : recipe.ingredients())
                    ingredients.add(EmiIngredient.of(ingredient.ingredient(), (int) Math.ceil((double) this.singularity.getCost() / ingredient.amount())));
            return List.of(EmiIngredient.of(ingredients, this.singularity.getCost()));
        }

        @Override
        public List<EmiStack> getOutputs() {
            return List.of(EmiStack.of(SingularityHelper.buildStack(this.singularity)));
        }

        @Override
        public int getDisplayWidth() {
            return 136;
        }

        @Override
        public int getDisplayHeight() {
            return 45;
        }

        @Override
        public void addWidgets(WidgetHolder widgets) {
            widgets.addTexture(TEXTURE, 0, 0);
            widgets.addSlot(this.getInputs().get(0), 24, 14);
            widgets.addSlot(this.getOutputs().get(0), 102, 14).recipeContext(this);
        }
    }
}
