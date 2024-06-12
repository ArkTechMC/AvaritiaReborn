package com.iafenvoy.avaritia.data.recipe;

import com.google.gson.JsonObject;
import com.iafenvoy.avaritia.util.RecipeUtil;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record ExtremeCraftingShapedRecipe(Identifier id, ItemStack output,
                                          List<List<Ingredient>> recipeItems) implements Recipe<SimpleInventory> {
    private static <T, M> boolean differentSize(List<List<T>> first, List<List<M>> second) {
        return first.size() != second.size() || first.get(0).size() != second.get(0).size();
    }

    public boolean matches(List<List<ItemStack>> inventory) {
        if (differentSize(inventory, this.recipeItems)) return false;
        for (int i = 0; i < this.recipeItems.size(); i++) {
            List<Ingredient> ing = this.recipeItems.get(i);
            List<ItemStack> inv = inventory.get(i);
            for (int j = 0; j < ing.size(); j++)
                if (!ing.get(j).test(inv.get(j)))
                    return false;
        }
        return true;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        List<List<ItemStack>> stacks = RecipeUtil.toTable(inventory.stacks, 9, 9);
        if (differentSize(stacks, this.recipeItems)) return false;
        for (int i = 0; i < this.recipeItems.size(); i++) {
            List<Ingredient> ing = this.recipeItems.get(i);
            List<ItemStack> inv = stacks.get(i);
            for (int j = 0; j < ing.size(); j++)
                if (!ing.get(j).test(inv.get(j)))
                    return false;
        }
        return true;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 9 && height >= 9;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public enum Type implements RecipeType<ExtremeCraftingShapedRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<ExtremeCraftingShapedRecipe> {
        INSTANCE;

        @Override
        public ExtremeCraftingShapedRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            Map<String, Ingredient> map = RecipeUtil.readSymbols(JsonHelper.getObject(json, "key"));
            String[] strings = RecipeUtil.getPattern(JsonHelper.getArray(json, "pattern"), 9, 9);
            Ingredient[][] inputs = RecipeUtil.replacePattern(strings, map);
            return new ExtremeCraftingShapedRecipe(id, output, RecipeUtil.toTable(inputs));
        }

        @Override
        public ExtremeCraftingShapedRecipe read(Identifier id, PacketByteBuf buf) {
            int width = buf.readInt();
            int height = buf.readInt();
            List<List<Ingredient>> inputs = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                List<Ingredient> ingredients = new ArrayList<>();
                for (int j = 0; j < height; j++)
                    ingredients.add(Ingredient.fromPacket(buf));
                inputs.add(ingredients);
            }
            ItemStack output = ItemStack.fromNbt(buf.readNbt());
            return new ExtremeCraftingShapedRecipe(id, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, ExtremeCraftingShapedRecipe recipe) {
            buf.writeInt(recipe.recipeItems.size());
            buf.writeInt(recipe.recipeItems.get(0).size());
            for (List<Ingredient> recipeItem : recipe.recipeItems)
                for (Ingredient ingredient : recipeItem)
                    ingredient.write(buf);
            NbtCompound compound = new NbtCompound();
            recipe.output.writeNbt(compound);
            buf.writeNbt(compound);
        }
    }
}
