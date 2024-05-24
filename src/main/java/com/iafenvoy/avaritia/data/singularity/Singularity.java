package com.iafenvoy.avaritia.data.singularity;

import com.iafenvoy.avaritia.util.IdUtil;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Singularity {
    public static final Singularity EMPTY = new Singularity("", 0);
    public static final HashMap<String, Singularity> MATERIALS = new HashMap<>();
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
        return this.cost;
    }

    public void addRecipe(SingularityRecipe recipe) {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        this.recipes.add(recipe);
    }

    public SingularityIngredient checkBlock(Block block) {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        for (SingularityRecipe recipe : this.recipes.stream().filter(SingularityRecipe::canUse).toList()) {
            for (SingularityIngredient ingredient : recipe.ingredients) {
                if (ingredient.type.equals("block")) {
                    if (IdUtil.getId(block).equals(new Identifier(ingredient.value)))
                        return ingredient;
                } else if (ingredient.type.equals("block_tag")) {
                    TagKey<Block> tagKey = TagKey.of(RegistryKeys.BLOCK, new Identifier(ingredient.value));
                    if (block.getDefaultState().isIn(tagKey))
                        return ingredient;
                }
            }
        }
        return null;
    }

    public SingularityIngredient checkItem(Item item) {
        if (this.recipes == null) this.recipes = new ArrayList<>();
        for (SingularityRecipe recipe : this.recipes.stream().filter(SingularityRecipe::canUse).toList()) {
            for (SingularityIngredient ingredient : recipe.ingredients) {
                if (ingredient.type.equals("item")) {
                    if (IdUtil.getId(item).equals(new Identifier(ingredient.value)))
                        return ingredient;
                } else if (ingredient.type.equals("item_tag")) {
                    TagKey<Item> tagKey = TagKey.of(RegistryKeys.ITEM, new Identifier(ingredient.value));
                    if (item.getDefaultStack().isIn(tagKey))
                        return ingredient;
                }
            }
        }
        return null;
    }

    public boolean hasAvailable() {
        return this.recipes.stream().anyMatch(SingularityRecipe::canUse);
    }

    public record SingularityRecipe(List<String> dependency, String result, List<SingularityIngredient> ingredients) {
        public boolean canUse() {
            return this.dependency.isEmpty() || this.dependency.stream().anyMatch(FabricLoader.getInstance()::isModLoaded);
        }
    }

    public record SingularityIngredient(String type, String value, int amount) {
        public static final SingularityIngredient EMPTY = new SingularityIngredient("", "", 0);
    }
}
