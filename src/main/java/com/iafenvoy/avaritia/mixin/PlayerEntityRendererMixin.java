package com.iafenvoy.avaritia.mixin;

import com.iafenvoy.avaritia.item.tool.InfinityCrossbowItem;
import com.iafenvoy.avaritia.registry.AvaritiaItems;
import com.iafenvoy.avaritia.render.InfinityArmorRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addCustomRenderer(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.features.add(new InfinityArmorRenderer<>(this));
    }

    @Inject(at = @At("HEAD"), method = "getArmPose", cancellable = true)
    private static void getArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> info) {
        ItemStack itemStack2 = player.getStackInHand(hand);
        if (!player.handSwinging && itemStack2.isOf(AvaritiaItems.INFINITY_CROSSBOW) && InfinityCrossbowItem.isCharged(itemStack2))
            info.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
    }
}
