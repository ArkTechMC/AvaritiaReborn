package com.iafenvoy.avaritia.recipe;

import com.google.gson.*;
import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.util.RecipeUtil;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ExtremeRecipeResourceManager implements SimpleSynchronousResourceReloadListener {
    private static final Gson GSON = new Gson();

    @Override
    public Identifier getFabricId() {
        return new Identifier(AvaritiaReborn.MOD_ID, "extreme_recipe");
    }

    @Override
    public void reload(ResourceManager manager) {
        ExtremeCraftingShapedRecipe.recipes.clear();
        for (Map.Entry<Identifier, Resource> entry : manager.findResources("extreme_recipes", p -> p.getPath().endsWith(".json")).entrySet()) {
            try (InputStream stream = entry.getValue().getInputStream()) {
                JsonElement element = JsonParser.parseReader(new InputStreamReader(stream));
                if (!element.isJsonObject())
                    throw new JsonSyntaxException("Extreme recipe should be a json object: " + entry.getKey());
                ExtremeCraftingShapedRecipe recipe = this.read(entry.getKey(), element.getAsJsonObject());
                ExtremeCraftingShapedRecipe.recipes.put(entry.getKey(), recipe);
            } catch (Exception e) {
                AvaritiaReborn.LOGGER.error("Error occurred while loading resource json " + entry.getKey().toString(), e);
            }
        }
        AvaritiaReborn.LOGGER.info(ExtremeCraftingShapedRecipe.recipes.size() + " extreme recipes loaded.");
    }

    private ExtremeCraftingShapedRecipe read(Identifier id, JsonObject json) {
        ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
        Map<String, Ingredient> map = RecipeUtil.readSymbols(JsonHelper.getObject(json, "key"));
        String[] strings = RecipeUtil.getPattern(JsonHelper.getArray(json, "pattern"), 9, 9);
        Ingredient[][] inputs = RecipeUtil.replacePattern(strings, map);
        return new ExtremeCraftingShapedRecipe(id, output, new ReadOnlyInventoryHolder<>(inputs));
    }
}
