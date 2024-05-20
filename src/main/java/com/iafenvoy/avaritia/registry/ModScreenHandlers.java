package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.gui.ExtremeCraftingTableScreenHandler;
import com.iafenvoy.avaritia.gui.NeutronCollectorScreenHandler;
import com.iafenvoy.avaritia.gui.NeutroniumCompressorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
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
    }
}
