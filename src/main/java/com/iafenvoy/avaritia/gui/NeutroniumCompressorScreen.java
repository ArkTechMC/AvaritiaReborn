package com.iafenvoy.avaritia.gui;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NeutroniumCompressorScreen extends HandledScreen<NeutroniumCompressorScreenHandler> {
    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier TEXTURE = new Identifier(AvaritiaReborn.MOD_ID, "textures/gui/compressor.png");


    public NeutroniumCompressorScreen(NeutroniumCompressorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

//        renderProgressArrow(context.getMatrices(), x, y);
        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0);
        //textRenderer.draw(matrices, "Progress: " + String.valueOf(handler.getScaledProgress()) + "%",50,60,0);
        context.drawText(textRenderer, (int) handler.getCurrentProgress() + " / " + (int) handler.getMaxProgress(), 65, 60, 0,false);
        context.getMatrices().pop();

    }

//    private void renderProgressArrow(MatrixStack matrices, int x, int y) {
//        if (handler.getCurrentProgress() > 0) {
//            drawTexture(matrices, x + 90, y + 35, 176, 16, 18, (int) handler.getScaledProgressSingularity());
//            drawTexture(matrices, x + 62, y + 34, 176, 0, (int) handler.getScaledProgressArrow(), 16);
//        }
//    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }


    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
