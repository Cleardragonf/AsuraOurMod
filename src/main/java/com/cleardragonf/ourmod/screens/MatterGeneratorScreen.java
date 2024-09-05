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

    private static final int BACKGROUND_COLOR = 0xFF555555;
    private static final int FOREGROUND_COLOR = 0xFFCC2222;
    private static final int BAR_WIDTH = 17;
    private static final int BAR_HEIGHT = 27;

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
        renderBackground(guiGraphics, v, i, i1);
        renderEnergyBars(guiGraphics);
    }

    private void renderBackground(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderEnergyBars(GuiGraphics guiGraphics) {
        int[] energyScales = {
                this.menu.getFireManaScaled(),
                this.menu.getLightManaScaled(),
                this.menu.getEnergyStoredScaled(),
                this.menu.getVoidManaScaled(),
                this.menu.getWindManaScaled(),
                this.menu.getDarkManaScaled(),
                this.menu.getEarthManaScaled()
        };

        int[][] barPositions = {
                {25, 53}, {43, 53}, {61, 53},
                {79, 53}, {97, 53}, {115, 53}, {133, 53}
        };

        for (int i = 0; i < barPositions.length; i++) {
            int xPos = barPositions[i][0];
            int yPos = barPositions[i][1];

            renderEnergyBar(guiGraphics, xPos, yPos, energyScales[i]);
        }
    }

    private void renderEnergyBar(GuiGraphics guiGraphics, int xPos, int yPos, int energyScaled) {
        // Background
        guiGraphics.fill(leftPos + xPos, topPos + yPos, leftPos + xPos + BAR_WIDTH, topPos + yPos + BAR_HEIGHT, BACKGROUND_COLOR);

        // Foreground
        guiGraphics.fill(leftPos + xPos + 1, topPos + yPos + 1 + (25 - energyScaled), leftPos + xPos + BAR_WIDTH - 1, topPos + yPos + BAR_HEIGHT, FOREGROUND_COLOR);

        // Measurement
        guiGraphics.blit(TEXTURE, leftPos + xPos, topPos + yPos, 177, 31, BAR_WIDTH, BAR_HEIGHT);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 161, y + 30, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);

        int[] manaStored = {
                this.menu.getEnergy(), this.menu.getFireMana(), this.menu.getEarthMana(),
                this.menu.getWindMana(), this.menu.getDarkMana(), this.menu.getLightMana(), this.menu.getVoidMana()
        };

        int[] maxMana = {
                this.menu.getMaxEnergy(), this.menu.getMaxFireMana(), this.menu.getMaxEarthMana(),
                this.menu.getMaxWindMana(), this.menu.getMaxDarkMana(), this.menu.getMaxLightMana(), this.menu.getMaxVoidMana()
        };

        Component[] tooltips = {
                Component.literal("Water Mana: " + manaStored[0] + " / " + maxMana[0]),
                Component.literal("Fire Mana: " + manaStored[1] + " / " + maxMana[1]),
                Component.literal("Earth Mana: " + manaStored[2] + " / " + maxMana[2]),
                Component.literal("Wind Mana: " + manaStored[3] + " / " + maxMana[3]),
                Component.literal("Dark Mana: " + manaStored[4] + " / " + maxMana[4]),
                Component.literal("Light Mana: " + manaStored[5] + " / " + maxMana[5]),
                Component.literal("Void Mana: " + manaStored[6] + " / " + maxMana[6])
        };

        int[][] tooltipPositions = {
                {62, 54}, {26, 54}, {134, 54},
                {98, 54}, {116, 54}, {44, 54}, {80, 54}
        };

        for (int i = 0; i < tooltipPositions.length; i++) {
            if (isHovering(tooltipPositions[i][0], tooltipPositions[i][1], 16, 25, pMouseX, pMouseY)) {
                pGuiGraphics.renderTooltip(this.font, tooltips[i], pMouseX, pMouseY);
            }
        }
    }
}