package com.iafenvoy.avaritia.recipe;

import com.google.gson.JsonObject;
import com.iafenvoy.avaritia.util.RecipeUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtremeCraftingShapedRecipe implements Recipe<SimpleInventory> {
    public static final List<ExtremeCraftingShapedRecipe> recipes = new ArrayList<>();
    private final Identifier id;
    private final ItemStack output;
    private final Ingredient[][] recipeItems;

    public ExtremeCraftingShapedRecipe(Identifier id, ItemStack output, Ingredient[][] recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        recipes.add(this);
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        return this.matches(inventory);
    }

    public boolean matches(Inventory inventory) {
        for (int i = 0; i < 9 - this.recipeItems.length; i++)
            for (int j = 0; j < 9 - this.recipeItems[i].length; j++)
                if (this.matches(inventory, i, j))
                    return true;
        return false;
    }

    private boolean matches(Inventory inventory, int offsetX, int offsetY) {
        for (int i = 0; i < this.recipeItems.length; i++)
            for (int j = 0; j < this.recipeItems[i].length; j++)
                if (!this.recipeItems[i][j].test(inventory.getStack(i + offsetX + (j + offsetY) * 9)))
                    return false;
        return true;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output.copy();
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public Ingredient[][] getRecipeItems() {
        return this.recipeItems;
    }

    public static class Type implements RecipeType<ExtremeCraftingShapedRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<ExtremeCraftingShapedRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "extreme_shaped";
        // this is the name given in the json file

        @Override
        public ExtremeCraftingShapedRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            Map<String, Ingredient> map = RecipeUtil.readSymbols(JsonHelper.getObject(json, "key"));
            String[] strings = RecipeUtil.getPattern(JsonHelper.getArray(json, "pattern"), 9, 9);
            Ingredient[][] inputs = RecipeUtil.replacePattern(strings, map);
//            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
//            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(2, Ingredient.EMPTY);
//
//            for (int i = 0; i < inputs.size(); i++) {
//                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
//            }

            return new ExtremeCraftingShapedRecipe(id, output, inputs);
        }

        @Override
        public ExtremeCraftingShapedRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient[][] inputs = new Ingredient[buf.readInt()][buf.readInt()];
            for (int i = 0; i < inputs.length; i++)
                for (int j = 0; j < inputs[i].length; j++)
                    inputs[i][j] = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return new ExtremeCraftingShapedRecipe(id, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, ExtremeCraftingShapedRecipe recipe) {
            Ingredient[][] ingredients = recipe.getRecipeItems();
            buf.writeInt(ingredients.length);
            buf.writeInt(ingredients[0].length);
            for (Ingredient[] ingredient : ingredients)
                for (Ingredient value : ingredient)
                    value.write(buf);
            buf.writeItemStack(recipe.getOutput(null));
        }
    }
}
