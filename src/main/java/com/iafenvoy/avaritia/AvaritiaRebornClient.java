package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.gui.ExtremeCraftingTableScreen;
import com.iafenvoy.avaritia.gui.NeutronCollectorScreen;
import com.iafenvoy.avaritia.gui.NeutroniumCompressorScreen;
import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.registry.ModScreenHandlers;
import com.iafenvoy.avaritia.singularity.SingularityHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class AvaritiaRebornClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreenHandlers.registerAllScreenHandlers();
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> SingularityHelper.getColorFromStack(stack), ModItems.SINGULARITY);
        HandledScreens.register(ModScreenHandlers.NEUTRON_COLLECTOR_SCREEN_HANDLER, NeutronCollectorScreen::new);
        HandledScreens.register(ModScreenHandlers.EXTREME_CRAFTING_TABLE_SCREEN_HANDLER, ExtremeCraftingTableScreen::new);
        HandledScreens.register(ModScreenHandlers.NEUTRONIUM_COMPRESSOR_SCREEN_HANDLER, NeutroniumCompressorScreen::new);
    }
}
