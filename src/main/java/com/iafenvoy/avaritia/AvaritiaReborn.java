package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.registry.ModRecipes;
import com.iafenvoy.avaritia.singularity.SingularityResourceManager;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;

public class AvaritiaReborn implements ModInitializer {
    public static final String MOD_ID = "avaritia";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ModRecipes.register();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SingularityResourceManager());
    }
}
