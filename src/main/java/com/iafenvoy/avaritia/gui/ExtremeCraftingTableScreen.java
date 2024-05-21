package com.iafenvoy.avaritia.gui;

import com.iafenvoy.avaritia.AvaritiaReborn;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ExtremeCraftingTableScreen extends HandledScreen<ExtremeCraftingTableScreenHandler> {
    //A path to the gui texture. In this example we use the texture from the dispenser
    public static final Identifier TEXTURE = new Identifier(AvaritiaReborn.MOD_ID, "textures/gui/extreme_crafting.png");


    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double) left || mouseY < (double) top || mouseX >= (double) (left + this.backgroundWidth) || mouseY >= (double) (top + this.backgroundHeight);
    }

    public ExtremeCraftingTableScreen(ExtremeCraftingTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, Text.of(""));
        this.backgroundWidth = 239;
        this.backgroundHeight = 256;
    }


    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 256) / 2;
        int y = (height - 256) / 2;

        context.drawTexture(TEXTURE,x,y,0,0,256,256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        //super.drawForeground(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
