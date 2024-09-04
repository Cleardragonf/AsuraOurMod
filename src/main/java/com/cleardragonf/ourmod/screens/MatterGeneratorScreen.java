package com.cleardragonf.ourmod.screens;

import com.cleardragonf.ourmod.OurMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MatterGeneratorScreen extends AbstractContainerScreen<MatterGeneratorMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(OurMod.MODID, "textures/gui/matter_generator_gui.png");
    public MatterGeneratorScreen(MatterGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) /2;
        int y = (height - imageHeight) /2;
        guiGraphics.blit(TEXTURE, x, y, 0,0,imageWidth, imageHeight);
        renderProgressArrow(guiGraphics, x,y);

        int energyScaled = this.menu.getEnergyStoredScaled();
        int fireEnergyScaled = this.menu.getFireManaScaled();

        // AARRGGBB

        // background
        guiGraphics.fill(
                this.leftPos + 25,
                this.topPos + 53,
                this.leftPos + 42,
                this.topPos + 79,
                0xFF555555);

        // foreground
        guiGraphics.fill(
                this.leftPos + 26,
                this.topPos + 54 + (25 - energyScaled),
                this.leftPos + 41,
                this.topPos + 79,
                0xFFCC2222
        );

        // Fire's background
        guiGraphics.fill(
                this.leftPos + 43,
                this.topPos + 53,
                this.leftPos + 60,
                this.topPos + 79,
                0xFF555555);

        // Fire's foreground
        guiGraphics.fill(
                this.leftPos + 44,
                this.topPos + 54 + (25 - fireEnergyScaled),
                this.leftPos + 59,
                this.topPos + 79,
                0xFFCC2222
        );
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 161, y + 30, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX,  pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int energyStored = this.menu.getEnergy();
        int maxEnergy = this.menu.getMaxEnergy();

        int fireManaStored = this.menu.getFireMana();
        int maxFireMana = this.menu.getMaxFireMana();

        Component text = Component.literal("Water Mana: " + energyStored + " / " + maxEnergy);
        if(isHovering(26, 54, 16, 25, pMouseX, pMouseY)) {
            pGuiGraphics.renderTooltip(this.font, text, pMouseX, pMouseY);
        }
        Component fireText = Component.literal("Fire Mana: " + fireManaStored + " / " + maxFireMana);
        if(isHovering(44, 54, 16, 25, pMouseX, pMouseY)) {
            pGuiGraphics.renderTooltip(this.font, fireText, pMouseX, pMouseY);
        }
    }
}
