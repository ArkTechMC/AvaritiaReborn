package com.iafenvoy.avaritia.data.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iafenvoy.avaritia.data.singularity.Singularity;
import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class ExtremeCraftingShapelessRecipe implements Recipe<SimpleInventory> {
    public static final HashMap<Identifier, List<Ingredient>> EXTRA_ITEM = new HashMap<>();
    private final Identifier id;
    private final ItemStack output;
    private final List<Ingredient> ingredients;
    private final List<String> specials;

    public ExtremeCraftingShapelessRecipe(Identifier id, ItemStack output, List<Ingredient> defaultItems, List<String> specials) {
        this.id = id;
        this.output = output;
        this.ingredients = defaultItems;
        this.specials = specials;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        List<ItemStack> stacks = inventory.stacks;
        stacks = stacks.stream().filter(x -> !x.isEmpty()).toList();
        List<Ingredient> ingredients = new ArrayList<>(this.getAllIngredients());
        if (stacks.size() != ingredients.size()) return false;
        for (ItemStack stack : stacks) {
            int index = 0;
            while (index < ingredients.size() && !ingredients.get(index).test(stack)) index++;
            if (index == ingredients.size()) return false;
            ingredients.remove(index);
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

    public List<Ingredient> getAllIngredients() {
        if (!EXTRA_ITEM.containsKey(this.id)) EXTRA_ITEM.put(this.id, new ArrayList<>());
        List<Ingredient> ingredientList = new ArrayList<>(Stream.of(this.ingredients, EXTRA_ITEM.get(this.id)).flatMap(Collection::stream).toList());
        for (String s : this.specials)
            if (s.equals("singularity"))
                for (Singularity singularity : Singularity.MATERIALS.values())
                    if (singularity.hasAvailable())
                        ingredientList.add(Ingredient.ofStacks(SingularityHelper.buildStack(singularity)));
        return ingredientList;
    }

    public static void findAndAdd(Identifier id, List<Ingredient> ingredients) {
        if (!EXTRA_ITEM.containsKey(id)) EXTRA_ITEM.put(id, new ArrayList<>());
        EXTRA_ITEM.get(id).addAll(ingredients);
    }

    public enum Type implements RecipeType<ExtremeCraftingShapelessRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<ExtremeCraftingShapelessRecipe> {
        INSTANCE;

        @Override
        public ExtremeCraftingShapelessRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            final List<Ingredient> ingredients = new ArrayList<>(json.getAsJsonArray("ingredients").asList().stream().map(Ingredient::fromJson).toList());
            List<String> specials = new ArrayList<>();
            if (json.has("special"))
                specials = JsonHelper.getArray(json, "special").asList().stream().map(JsonElement::getAsString).toList();
            return new ExtremeCraftingShapelessRecipe(id, output, ingredients, specials);
        }

        @Override
        public ExtremeCraftingShapelessRecipe read(Identifier id, PacketByteBuf buf) {
            int length = buf.readInt();
            List<Ingredient> inputs = new ArrayList<>();
            for (int i = 0; i < length; i++)
                if (!buf.readBoolean())
                    inputs.add(Ingredient.fromPacket(buf));
            ItemStack output = ItemStack.fromNbt(buf.readNbt());
            length = buf.readInt();
            List<String> specials = new ArrayList<>();
            for (int i = 0; i < length; i++)
                specials.add(buf.readString());
            return new ExtremeCraftingShapelessRecipe(id, output, inputs, specials);
        }

        @Override
        public void write(PacketByteBuf buf, ExtremeCraftingShapelessRecipe recipe) {
            List<Ingredient> ingredientList = recipe.getAllIngredients();// Fill this.ingredients
            buf.writeInt(ingredientList.size());
            for (Ingredient ingredient : ingredientList) {
                buf.writeBoolean(ingredient.isEmpty());
                if (!ingredient.isEmpty())
                    ingredient.write(buf);
            }
            NbtCompound compound = new NbtCompound();
            recipe.output.writeNbt(compound);
            buf.writeNbt(compound);
            buf.writeInt(recipe.specials.size());
            for (String special : recipe.specials)
                buf.writeString(special);
        }
    }
}
