package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.registry.ModResourceManagers;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;

public class AvaritiaReborn implements ModInitializer {
    public static final String MOD_ID = "avaritia";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ModResourceManagers.register();
    }
}
