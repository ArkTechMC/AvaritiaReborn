package com.iafenvoy.avaritia.mixin;

import com.iafenvoy.avaritia.registry.AvaritiaItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends Entity {
    public MobEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "dropLoot", at = @At("HEAD"))
    public void dropHandler(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
        if (causedByPlayer && damageSource.getSource() instanceof PlayerEntity player)
            if (isSkeleton(this) && player.getInventory().getMainHandStack().isOf(AvaritiaItems.SKULLFIRE_SWORD))
                this.dropStack(new ItemStack(Items.WITHER_SKELETON_SKULL));
    }

    @Unique
    private static boolean isSkeleton(Entity entity) {
        return AbstractSkeletonEntity.class.isAssignableFrom(entity.getClass());
    }
}
