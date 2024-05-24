package com.iafenvoy.avaritia.gui;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NeutroniumCompressorScreen extends HandledScreen<NeutroniumCompressorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(AvaritiaReborn.MOD_ID, "textures/gui/compressor.png");

    public NeutroniumCompressorScreen(NeutroniumCompressorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        this.renderProgressArrow(context, x, y);
        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0);
        context.drawText(this.textRenderer, (int) this.handler.getCurrentProgress() + " / " + (int) this.handler.getMaxProgress(), 65, 60, 0, false);
        context.getMatrices().pop();

    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (this.handler.getCurrentProgress() > 0) {
            context.drawTexture(TEXTURE, x + 90, y + 35, 176, 16, 18, this.handler.getScaledProgressSingularity());
            context.drawTexture(TEXTURE, x + 62, y + 34, 176, 0, this.handler.getScaledProgressArrow(), 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }
}
