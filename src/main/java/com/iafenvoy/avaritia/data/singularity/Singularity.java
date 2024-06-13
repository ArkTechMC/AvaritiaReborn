package com.iafenvoy.avaritia.data.singularity;

import com.iafenvoy.avaritia.data.DependencyHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Singularity {
    public static final Singularity EMPTY = new Singularity("", 0);
    public static final HashMap<String, Singularity> MATERIALS = new HashMap<>();
    private static int add = 0, mul = 1;
    private final String id;
    private final int cost;
    private List<SingularityRecipe> recipes = new ArrayList<>();

    public Singularity(String id, int cost) {
        this.id = id;
        this.cost = cost;
    }

    public List<SingularityRecipe> getRecipes() {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        return this.recipes;
    }

    public String getId() {
        return this.id;
    }

    public int getCost() {
        return (this.cost + add) * mul;
    }

    public void addRecipe(SingularityRecipe recipe) {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        this.recipes.add(recipe);
    }

    public SingularityIngredient test(ItemStack stack) {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        for (SingularityRecipe recipe : this.recipes.stream().filter(x -> x.dependency.anyLoaded()).toList())
            for (SingularityIngredient ingredient : recipe.ingredients)
                if (ingredient.ingredient.test(stack))
                    return ingredient;
        return null;
    }

    public boolean hasAvailable() {
        return this.recipes.stream().anyMatch(x -> x.dependency.anyLoaded());
    }

    public void encode(PacketByteBuf buf) {
        buf.writeString(this.id);
        buf.writeInt(this.cost);
        buf.writeInt(this.recipes.size());
        for (SingularityRecipe recipe : this.recipes)
            recipe.encode(buf);
    }

    public static Singularity decode(PacketByteBuf buf) {
        String id = buf.readString();
        int cost = buf.readInt();
        int length = buf.readInt();
        Singularity singularity = new Singularity(id, cost);
        for (int i = 0; i < length; i++)
            singularity.addRecipe(SingularityRecipe.decode(buf));
        return singularity;
    }

    public static void encodeAll(PacketByteBuf buf) {
        buf.writeInt(add);
        buf.writeInt(mul);
        buf.writeInt(MATERIALS.size());
        for (Singularity singularity : MATERIALS.values())
            singularity.encode(buf);
    }

    public static void decodeAll(PacketByteBuf buf) {
        MATERIALS.clear();
        add = buf.readInt();
        mul = buf.readInt();
        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            Singularity singularity = decode(buf);
            MATERIALS.put(singularity.id, singularity);
        }
    }

    public static void reload() {
        add = 0;
        mul = 1;
    }

    public static void pushData(int add_, int mul_) {
        add += add_;
        mul += mul_;
    }

    public record SingularityRecipe(DependencyHolder dependency, String result,
                                    List<SingularityIngredient> ingredients) {
        public SingularityRecipe(List<String> dependency, String result, List<SingularityIngredient> ingredients) {
            this(new DependencyHolder(dependency), result, ingredients);
        }

        public void encode(PacketByteBuf buf) {
            buf.writeString(this.result);
            buf.writeInt(this.ingredients.size());
            for (SingularityIngredient ingredient : this.ingredients)
                ingredient.encode(buf);
        }

        public static SingularityRecipe decode(PacketByteBuf buf) {
            String result = buf.readString();
            int length = buf.readInt();
            List<SingularityIngredient> ingredients = new ArrayList<>();
            for (int i = 0; i < length; i++)
                ingredients.add(SingularityIngredient.decode(buf));
            return new SingularityRecipe(new DependencyHolder(), result, ingredients);
        }
    }

    public record SingularityIngredient(Ingredient ingredient, int amount) {
        public static final SingularityIngredient EMPTY = new SingularityIngredient(Ingredient.EMPTY, 0);

        public void encode(PacketByteBuf buf) {
            this.ingredient.write(buf);
            buf.writeInt(this.amount);
        }

        public static SingularityIngredient decode(PacketByteBuf buf) {
            return new SingularityIngredient(Ingredient.fromPacket(buf), buf.readInt());
        }
    }
}
