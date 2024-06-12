package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.gui.*;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<NeutronCollectorScreenHandler> NEUTRON_COLLECTOR_SCREEN_HANDLER = register(new Identifier(AvaritiaReborn.MOD_ID, "neutron_collector"), NeutronCollectorScreenHandler::new);
    public static final ScreenHandlerType<ExtremeCraftingTableScreenHandler> EXTREME_CRAFTING_TABLE_SCREEN_HANDLER = register(new Identifier(AvaritiaReborn.MOD_ID, "extreme_crafting_table"), ExtremeCraftingTableScreenHandler::new);
    public static final ScreenHandlerType<NeutroniumCompressorScreenHandler> NEUTRONIUM_COMPRESSOR_SCREEN_HANDLER = register(new Identifier(AvaritiaReborn.MOD_ID, "neutronium_compressor"), NeutroniumCompressorScreenHandler::new);

    public static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void init(){//For server init
    }

    public static void registerAllScreenHandlers() {
        HandledScreens.register(ModScreenHandlers.NEUTRON_COLLECTOR_SCREEN_HANDLER, NeutronCollectorScreen::new);
        HandledScreens.register(ModScreenHandlers.EXTREME_CRAFTING_TABLE_SCREEN_HANDLER, ExtremeCraftingTableScreen::new);
        HandledScreens.register(ModScreenHandlers.NEUTRONIUM_COMPRESSOR_SCREEN_HANDLER, NeutroniumCompressorScreen::new);
    }
}
