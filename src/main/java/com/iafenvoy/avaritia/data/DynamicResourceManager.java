package com.iafenvoy.avaritia.data;

import com.google.gson.Gson;
import com.iafenvoy.avaritia.AvaritiaReborn;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class DynamicResourceManager implements SimpleSynchronousResourceReloadListener {
    private static final Gson GSON = new Gson();

    @Override
    public Identifier getFabricId() {
        return new Identifier(AvaritiaReborn.MOD_ID, "dynamic");
    }

    @Override
    public void reload(ResourceManager manager) {
        //These will be completed by RecipeManagerMixin
//        SingularityResourceManager.reload(manager);
//        ExtremeRecipeResourceManager.reload(manager);
        for (Map.Entry<Identifier, Resource> entry : manager.findResources(AvaritiaReborn.MOD_ID + "/dynamic", p -> p.getPath().endsWith(".json")).entrySet()) {
            try (InputStream stream = entry.getValue().getInputStream()) {
                DynamicManager.DynamicData data = GSON.fromJson(new InputStreamReader(stream), DynamicManager.DynamicData.class);
                DynamicManager.process(data);
            } catch (Exception e) {
                AvaritiaReborn.LOGGER.error("Error occurred while loading resource json " + entry.getKey().toString(), e);
            }
        }
    }
}
