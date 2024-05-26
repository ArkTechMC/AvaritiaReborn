package com.iafenvoy.avaritia.data.singularity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.iafenvoy.avaritia.AvaritiaReborn;
import net.minecraft.recipe.Ingredient;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SingularityResourceManager {
    public static void reload(ResourceManager manager) {
        Singularity.MATERIALS.clear();
        Singularity.reload();
        for (Map.Entry<Identifier, Resource> entry : manager.findResources(AvaritiaReborn.MOD_ID + "/singularity", p -> p.getPath().endsWith(".json")).entrySet()) {
            try (InputStream stream = entry.getValue().getInputStream()) {
                JsonObject object = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
                Singularity singularity = new Singularity(object.get("id").getAsString(), object.get("cost").getAsInt());
                Singularity.MATERIALS.put(singularity.getId(), singularity);
            } catch (Exception e) {
                AvaritiaReborn.LOGGER.error("Error occurred while loading resource json " + entry.getKey().toString(), e);
            }
        }
        for (Map.Entry<Identifier, Resource> entry : manager.findResources(AvaritiaReborn.MOD_ID + "/singularity_recipes", p -> p.getPath().endsWith(".json")).entrySet()) {
            try (InputStream stream = entry.getValue().getInputStream()) {
                JsonElement element = JsonParser.parseReader(new InputStreamReader(stream));
                if (!element.isJsonObject()) throw new JsonSyntaxException(entry.getKey() + " should be a json object");
                JsonObject root = element.getAsJsonObject();
                String result = root.get("result").getAsString();
                if (Singularity.MATERIALS.containsKey(result)) {
                    List<String> dependency = root.get("dependency").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
                    Singularity.SingularityRecipe recipe = new Singularity.SingularityRecipe(dependency, result, new ArrayList<>());
                    if (recipe.canUse())
                        for (JsonElement ingredient : root.get("ingredients").getAsJsonArray()) {
                            JsonObject object = ingredient.getAsJsonObject();
                            Singularity.SingularityIngredient i = new Singularity.SingularityIngredient(Ingredient.fromJson(object.get("ingredient")), object.get("amount").getAsInt());
                            recipe.ingredients().add(i);
                        }
                    else
                        AvaritiaReborn.LOGGER.warn("Cannot load " + entry.getKey() + " since dependency mod not found.");
                    Singularity.MATERIALS.get(result).addRecipe(recipe);
                } else {
                    AvaritiaReborn.LOGGER.warn("Unknown singularity: " + result);
                }
            } catch (Exception e) {
                AvaritiaReborn.LOGGER.error("Error occurred while loading resource json " + entry.getKey().toString(), e);
            }
        }
        AvaritiaReborn.LOGGER.info(Singularity.MATERIALS.size() + " singularities data loaded.");
    }
}
