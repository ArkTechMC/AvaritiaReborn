package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.registry.ModRecipes;
import net.fabricmc.api.ModInitializer;

public class AvaritiaReborn implements ModInitializer {
    public static final String MOD_ID = "avaritia";

    @Override
    public void onInitialize() {
        ModRecipes.register();
    }
}
