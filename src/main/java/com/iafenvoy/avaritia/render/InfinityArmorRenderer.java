package com.iafenvoy.avaritia.render;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.registry.ModItems;
import com.iafenvoy.avaritia.render.model.InfinityArmorModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class InfinityArmorRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier ARMOR_WING = new Identifier(AvaritiaReborn.MOD_ID, "textures/models/armor/infinity_armor_wing.png");
    private static final Identifier ARMOR_WING_GLOW = new Identifier(AvaritiaReborn.MOD_ID, "textures/models/armor/infinity_armor_wingglow.png");
    private static final Identifier ARMOR_MASK_WING = new Identifier(AvaritiaReborn.MOD_ID, "textures/models/armor/infinity_armor_mask_wings.png");

    public InfinityArmorRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity instanceof PlayerEntity player && entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.INFINITY_CHESTPLATE) && player.getAbilities().flying) {
            matrices.push();
            InfinityArmorModel<T> model = new InfinityArmorModel<>(InfinityArmorModel.createBodyLayer().createModel());
            VertexConsumer consumer1 = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getEntityTranslucent(ARMOR_WING), false, false);
            model.render(matrices, consumer1, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
            VertexConsumer consumer2 = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getEntityTranslucent(ARMOR_WING_GLOW), false, false);
            model.render(matrices, consumer2, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.5F);
            matrices.pop();
        }
    }
}
