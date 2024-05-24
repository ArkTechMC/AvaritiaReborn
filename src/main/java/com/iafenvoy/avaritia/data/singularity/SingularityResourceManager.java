package com.iafenvoy.avaritia.data.singularity;

import com.google.gson.Gson;
import com.iafenvoy.avaritia.AvaritiaReborn;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class SingularityResourceManager implements SimpleSynchronousResourceReloadListener {
    private static final Gson GSON = new Gson();

    @Override
    public Identifier getFabricId() {
        return new Identifier(AvaritiaReborn.MOD_ID, "singularity");
    }

    @Override
    public void reload(ResourceManager manager) {
        Singularity.MATERIALS.clear();
        for (Map.Entry<Identifier, Resource> entry : manager.findResources(AvaritiaReborn.MOD_ID + "/singularity", p -> p.getPath().endsWith(".json")).entrySet()) {
            try (InputStream stream = entry.getValue().getInputStream()) {
                Singularity singularity = GSON.fromJson(new InputStreamReader(stream), Singularity.class);
                Singularity.MATERIALS.put(singularity.getId(), singularity);
            } catch (Exception e) {
                AvaritiaReborn.LOGGER.error("Error occurred while loading resource json " + entry.getKey().toString(), e);
            }
        }
        for (Map.Entry<Identifier, Resource> entry : manager.findResources(AvaritiaReborn.MOD_ID + "/singularity_recipes", p -> p.getPath().endsWith(".json")).entrySet()) {
            try (InputStream stream = entry.getValue().getInputStream()) {
                Singularity.SingularityRecipe recipe = GSON.fromJson(new InputStreamReader(stream), Singularity.SingularityRecipe.class);
                if (Singularity.MATERIALS.containsKey(recipe.result()))
                    Singularity.MATERIALS.get(recipe.result()).addRecipe(recipe);
                else
                    AvaritiaReborn.LOGGER.warn("Unknown singularity: " + recipe.result());
            } catch (Exception e) {
                AvaritiaReborn.LOGGER.error("Error occurred while loading resource json " + entry.getKey().toString(), e);
            }
        }
        AvaritiaReborn.LOGGER.info(Singularity.MATERIALS.size() + " singularities data loaded.");
    }
}
