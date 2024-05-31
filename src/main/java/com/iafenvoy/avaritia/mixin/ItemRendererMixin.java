package com.iafenvoy.avaritia.mixin;

import com.iafenvoy.avaritia.render.HaloRenderHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Inject(method = "renderBakedItemQuads", at = @At("HEAD"))
    private void startRenderItem(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay, CallbackInfo ci) {
        matrices.push();
        HaloRenderHelper.render(stack, matrices, vertices, light, overlay);
    }

    @Inject(method = "renderBakedItemQuads", at = @At("RETURN"))
    private void endRenderItem(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay, CallbackInfo ci) {
        matrices.pop();
    }
}
