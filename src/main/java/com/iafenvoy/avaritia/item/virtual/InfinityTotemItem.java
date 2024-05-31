package com.iafenvoy.avaritia.item.virtual;

import com.iafenvoy.avaritia.registry.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class InfinityTotemItem {
    public static boolean tryUse(LivingEntity entity) {
        boolean canUse = check(entity);
        if (canUse) {
            entity.setHealth(10.0F);
            entity.clearStatusEffects();
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 50 * 20, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 130 * 20, 4));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 20, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 35 * 20, 2));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 55 * 20, 0));
            entity.getWorld().sendEntityStatus(entity, (byte) 35);
        }
        return canUse;
    }

    public static boolean check(LivingEntity entity) {
        if (entity instanceof PlayerEntity player)
            return player.getInventory().containsAny(x -> x.isOf(ModItems.INFINITY_TOTEM));
        return entity.getMainHandStack().isOf(ModItems.INFINITY_TOTEM) || entity.getOffHandStack().isOf(ModItems.INFINITY_TOTEM);
    }
}
