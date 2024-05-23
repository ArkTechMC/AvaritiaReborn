package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.registry.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

public class AvaritiaRebornClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreenHandlers.registerAllScreenHandlers();
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> SingularityHelper.getColorFromStack(stack), ModItems.SINGULARITY);
    }
}
