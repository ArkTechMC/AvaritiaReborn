package com.iafenvoy.avaritia.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class InfinityArmorModel<T extends LivingEntity> extends EntityModel<T> {
    public final ModelPart leftWing;
    public final ModelPart rightWing;

    public InfinityArmorModel(ModelPart root) {
        this.leftWing = root.getChild("leftWing");
        this.rightWing = root.getChild("rightWing");
    }

    public static TexturedModelData createBodyLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();
        partdefinition.addChild("leftWing",
                ModelPartBuilder.create().uv(0, 0).cuboid(-3.5f, -12, -3, 0, 32, 32),
                ModelTransform.of(0, 0, 0, 0, 1.57f, 0));
        partdefinition.addChild("rightWing",
                ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(3.5f, -12, -3, 0, 32, 32),
                ModelTransform.of(0, 0, 0, 0, -1.57f, 0));
        return TexturedModelData.of(meshdefinition, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.leftWing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        this.rightWing.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
