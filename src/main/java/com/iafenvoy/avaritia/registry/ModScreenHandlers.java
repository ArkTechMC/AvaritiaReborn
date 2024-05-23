package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.gui.*;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<NeutronCollectorScreenHandler> NEUTRON_COLLECTOR_SCREEN_HANDLER;
    public static ScreenHandlerType<ExtremeCraftingTableScreenHandler> EXTREME_CRAFTING_TABLE_SCREEN_HANDLER;
    public static ScreenHandlerType<NeutroniumCompressorScreenHandler> NEUTRONIUM_COMPRESSOR_SCREEN_HANDLER;


    public static void registerAllScreenHandlers() {
        NEUTRON_COLLECTOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(AvaritiaReborn.MOD_ID, "neutron_collector"), NeutronCollectorScreenHandler::new);
        EXTREME_CRAFTING_TABLE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(AvaritiaReborn.MOD_ID, "extreme_crafting_table"), ExtremeCraftingTableScreenHandler::new);
        NEUTRONIUM_COMPRESSOR_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(AvaritiaReborn.MOD_ID, "neutronium_compressor"), NeutroniumCompressorScreenHandler::new);

        HandledScreens.register(ModScreenHandlers.NEUTRON_COLLECTOR_SCREEN_HANDLER, NeutronCollectorScreen::new);
        HandledScreens.register(ModScreenHandlers.EXTREME_CRAFTING_TABLE_SCREEN_HANDLER, ExtremeCraftingTableScreen::new);
        HandledScreens.register(ModScreenHandlers.NEUTRONIUM_COMPRESSOR_SCREEN_HANDLER, NeutroniumCompressorScreen::new);
    }
}
