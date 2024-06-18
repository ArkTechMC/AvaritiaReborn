package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.registry.*;
import com.iafenvoy.avaritia.render.InfinityArrowEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class AvaritiaRebornClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AvaritiaPredicates.register();
        AvaritiaResourceManagers.registerClient();
        AvaritiaScreenHandlers.registerAllScreenHandlers();

        EntityRendererRegistry.register(AvaritiaEntities.INFINITY_ARROW, InfinityArrowEntityRenderer::new);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> SingularityHelper.getColorFromStack(stack), AvaritiaItems.SINGULARITY);
    }
}
