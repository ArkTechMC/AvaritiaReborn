package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.data.recipe.ExtremeRecipeResourceManager;
import com.iafenvoy.avaritia.data.singularity.SingularityResourceManager;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class ModResourceManagers {
    public static void register(){
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ExtremeRecipeResourceManager());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SingularityResourceManager());
    }
}
