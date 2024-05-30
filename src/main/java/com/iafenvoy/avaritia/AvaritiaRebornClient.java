package com.iafenvoy.avaritia;

import com.iafenvoy.avaritia.data.singularity.SingularityHelper;
import com.iafenvoy.avaritia.item.tool.InfinityPickaxeItem;
import com.iafenvoy.avaritia.item.tool.InfinityShovelItem;
import com.iafenvoy.avaritia.registry.ModEntities;
import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.registry.ModResourceManagers;
import com.iafenvoy.avaritia.registry.ModScreenHandlers;
import com.iafenvoy.avaritia.render.InfinityArrowEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class AvaritiaRebornClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModResourceManagers.registerClient();
        ModScreenHandlers.registerAllScreenHandlers();
        EntityRendererRegistry.register(ModEntities.INFINITY_ARROW, InfinityArrowEntityRenderer::new);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> SingularityHelper.getColorFromStack(stack), ModItems.SINGULARITY);
        ModelPredicateProviderRegistry.register(ModItems.INFINITY_PICKAXE, new Identifier(InfinityPickaxeItem.HAMMER_NBT), (stack, world, entity, seed) -> stack.getOrCreateNbt().getBoolean(InfinityPickaxeItem.HAMMER_NBT) ? 1 : 0);
        ModelPredicateProviderRegistry.register(ModItems.INFINITY_SHOVEL, new Identifier(InfinityShovelItem.DESTROYER_NBT), (stack, world, entity, seed) -> stack.getOrCreateNbt().getBoolean(InfinityShovelItem.DESTROYER_NBT) ? 1 : 0);
    }
}
