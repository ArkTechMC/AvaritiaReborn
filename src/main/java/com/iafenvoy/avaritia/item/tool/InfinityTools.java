package com.iafenvoy.avaritia.item.tool;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;

public class InfinityTools {
    private static void onPostHit(LivingEntity entity, LivingEntity sourceentity) {
        if (Math.random() < 0.5D)
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 10, 0));
    }

    public static class Bow extends BowItem {
        public Bow() {
            super(new Settings());
        }

        @Override
        public boolean postHit(ItemStack itemtack, LivingEntity entity, LivingEntity sourceentity) {
            boolean retval = super.postHit(itemtack, entity, sourceentity);
            onPostHit(entity, sourceentity);
            return retval;
        }
    }
}
