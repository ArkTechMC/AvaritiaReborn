package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.data.singularity.SingularityCommand;
import com.iafenvoy.avaritia.item.armor.InfinityArmorItem;
import com.iafenvoy.avaritia.registry.ModGameRules;
import com.iafenvoy.avaritia.registry.ModRecipes;
import com.iafenvoy.avaritia.registry.ModResourceManagers;
import com.iafenvoy.avaritia.registry.ModScreenHandlers;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;

public class AvaritiaReborn implements ModInitializer {
    public static final String MOD_ID = "avaritia";
    public static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        ModGameRules.init();
        ModResourceManagers.register();
        SingularityCommand.register();
        ModScreenHandlers.init();
        ModRecipes.register();

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> !(entity instanceof PlayerEntity player) || !InfinityArmorItem.fullyEquipped(player));//For inifinity armor
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) -> !(entity instanceof PlayerEntity player) || !InfinityArmorItem.fullyEquipped(player));
    }
}
