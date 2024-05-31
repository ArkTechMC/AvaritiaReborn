package com.iafenvoy.avaritia.render;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.iafenvoy.avaritia.util.RandomHelper;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class HaloRenderHelper {
    private static final TagKey<Item> HALO_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(AvaritiaReborn.MOD_ID, "halo"));
    private static final Identifier HALO_BG = new Identifier(AvaritiaReborn.MOD_ID, "textures/item/halo.png");
    private static final Identifier HALO_NOISE = new Identifier(AvaritiaReborn.MOD_ID, "textures/item/halo_noise.png");

    public static void render(ItemStack stack, MatrixStack matrices, VertexConsumer consumer, int light, int overlay) {
        if (stack.isIn(HALO_TAG)) {
            float scale = (float) RandomHelper.nextDouble(0.9, 1.1);
            float offset = (1 - scale) / 2;
            matrices.scale(scale, scale, scale);
            matrices.translate(offset, offset, offset);
        }
    }
}
