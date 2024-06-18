package com.iafenvoy.avaritia.registry;

import com.iafenvoy.avaritia.item.tool.InfinityCrossbowItem;
import com.iafenvoy.avaritia.item.tool.InfinityPickaxeItem;
import com.iafenvoy.avaritia.item.tool.InfinityShovelItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class AvaritiaPredicates {
    public static void register(){
        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_PICKAXE, new Identifier(InfinityPickaxeItem.HAMMER_NBT), (stack, world, entity, seed) -> stack.getOrCreateNbt().getBoolean(InfinityPickaxeItem.HAMMER_NBT) ? 1 : 0);
        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_SHOVEL, new Identifier(InfinityShovelItem.DESTROYER_NBT), (stack, world, entity, seed) -> stack.getOrCreateNbt().getBoolean(InfinityShovelItem.DESTROYER_NBT) ? 1 : 0);

        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_BOW, new Identifier("pull"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity == null ? 0 : livingEntity.getActiveItem() != itemStack ? 0 : (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20);
        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_BOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1 : 0);

        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_CROSSBOW, new Identifier("pull"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity == null ? 0 : InfinityCrossbowItem.isCharged(itemStack) ? 0 : (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / InfinityCrossbowItem.getPullTime(itemStack));
        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_CROSSBOW, new Identifier("pulling"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack && !InfinityCrossbowItem.isCharged(itemStack) ? 1 : 0);
        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_CROSSBOW, new Identifier("charged"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && InfinityCrossbowItem.isCharged(itemStack) ? 1 : 0);
        ModelPredicateProviderRegistry.register(AvaritiaItems.INFINITY_CROSSBOW, new Identifier("firework"), (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && InfinityCrossbowItem.isCharged(itemStack) && InfinityCrossbowItem.hasProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1 : 0);
    }
}
