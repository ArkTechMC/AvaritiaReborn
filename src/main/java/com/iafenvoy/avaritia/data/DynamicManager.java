package com.iafenvoy.avaritia.data;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.iafenvoy.avaritia.data.recipe.ExtremeCraftingShapelessRecipe;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;

public class DynamicManager {

    public static void process(DynamicData data) {
        if (!data.available()) return;
        if (data.recipe() != null)
            data.recipe().addToRecipes();
        if (data.singularity() != null)
            data.singularity().pushData();
    }

    public record DynamicData(List<String> dependency, DynamicRecipe recipe, SingularityPlus singularity) {
        public boolean available() {
            return this.dependency.isEmpty() || this.dependency.stream().anyMatch(FabricLoader.getInstance()::isModLoaded);
        }
    }

    public record DynamicRecipe(List<DynamicIngredient> catalyst, List<DynamicIngredient> stew,
                                List<DynamicIngredient> meatballs) {
        public void addToRecipes() {
            if (this.catalyst != null)
                ExtremeCraftingShapelessRecipe.INFINITY_CATALYST.addIngredients(toIngredientList(this.catalyst));
            if (this.stew != null)
                ExtremeCraftingShapelessRecipe.ULTIMATE_STEW.addIngredients(toIngredientList(this.stew));
            if (this.meatballs != null)
                ExtremeCraftingShapelessRecipe.COSMIC_MEATBALLS.addIngredients(toIngredientList(this.meatballs));
        }
    }

    public record DynamicIngredient(String item, String tag) {
        public Ingredient toIngredient() {
            if (this.item != null && this.tag != null) {
                throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
            } else if (this.item != null) {
                Item item = Registries.ITEM.getOrEmpty(Identifier.tryParse(this.item)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + this.item + "'"));
                return Ingredient.ofItems(item);
            } else if (this.tag != null) {
                Identifier identifier = new Identifier(this.tag);
                TagKey<Item> tagKey = TagKey.of(RegistryKeys.ITEM, identifier);
                return Ingredient.fromTag(tagKey);
            } else {
                throw new JsonParseException("An ingredient entry needs either a tag or an item");
            }
        }
    }

    public record SingularityPlus(int add, int mul) {
        public void pushData() {
            Singularity.pushData(this.add, this.mul);
        }
    }

    private static List<Ingredient> toIngredientList(List<DynamicIngredient> items) {
        return items.stream().map(DynamicIngredient::toIngredient).toList();
    }
}
