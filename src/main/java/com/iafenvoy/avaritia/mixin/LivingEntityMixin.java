package com.iafenvoy.avaritia.mixin;

import com.iafenvoy.avaritia.item.virtual.InfinityTotemItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void onTryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (!source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
            if (InfinityTotemItem.tryUse((LivingEntity) (Object) this))
                cir.setReturnValue(true);
    }
}
