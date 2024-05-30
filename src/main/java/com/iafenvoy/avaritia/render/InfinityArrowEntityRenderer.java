package com.iafenvoy.avaritia.render;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.entity.InfinityArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class InfinityArrowEntityRenderer extends ProjectileEntityRenderer<InfinityArrowEntity> {

    public InfinityArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(InfinityArrowEntity arrowEntity) {
        return new Identifier(AvaritiaReborn.MOD_ID, "textures/entity/infinity_arrow.png");
    }
}
