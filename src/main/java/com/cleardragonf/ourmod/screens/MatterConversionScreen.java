package com.cleardragonf.ourmod.screens;

import com.cleardragonf.ourmod.OurMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class MatterConversionScreen extends AbstractContainerScreen<MatterConversionMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OurMod.MODID, "textures/gui/matter_conversion_gui.png");
    private static final int SCROLLBAR_WIDTH = 5;
    private static final int SCROLLBAR_HEIGHT = 63;
    private static final int SCROLLBAR_X = 169;
    private static final int SCROLLBAR_Y = 29;
    private static final int SLOT_SIZE = 18;
    private static final int MAX_SCROLL = 45; // Total number of slots
    private int scrollOffset = 0;

    public MatterConversionScreen(MatterConversionMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 256;
        this.imageHeight = 256;
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
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // Calculate visible slot range based on scroll offset
        int firstVisibleSlot = scrollOffset;
        int visibleSlotCount = (imageHeight - 34) / SLOT_SIZE;  // Adjust this if padding/margins differ
        int lastVisibleSlot = Math.min(firstVisibleSlot + visibleSlotCount, MAX_SCROLL);

        // Render only visible slots with scroll offset adjustment
        for (int j = firstVisibleSlot; j < lastVisibleSlot; j++) {
            int slotRenderY = y + 30 + (j - scrollOffset) * SLOT_SIZE;
            SlotItemHandler slot = (SlotItemHandler) this.menu.getSlot(j);

            // Render slot at the calculated position (same x but adjusted y)
            renderSlot(guiGraphics, slot, x + 80, slotRenderY);
        }

        // Render scrollbar background and handle
        int scrollbarX = x + SCROLLBAR_X;
        int scrollbarY = y + SCROLLBAR_Y;
        guiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + SCROLLBAR_HEIGHT, 0xFF000000); // Background
        guiGraphics.fill(scrollbarX + 2, scrollbarY + 2 + (scrollOffset * (SCROLLBAR_HEIGHT / MAX_SCROLL)), scrollbarX + SCROLLBAR_WIDTH - 2, scrollbarY + SCROLLBAR_HEIGHT / MAX_SCROLL + 2 + (scrollOffset * (SCROLLBAR_HEIGHT / MAX_SCROLL)), 0xFF888888); // Scroll Handle
    }

    private void renderSlot(GuiGraphics guiGraphics, SlotItemHandler slot, int renderX, int renderY) {
        // Draw the slot background
        guiGraphics.blit(TEXTURE, renderX, renderY, 176, 0, SLOT_SIZE, SLOT_SIZE);

        // Render the item in the slot, if present
        ItemStack stack = slot.getItem();
        if (!stack.isEmpty()) {
            guiGraphics.renderItem(stack, renderX + 1, renderY + 1);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (delta > 0 && scrollOffset > 0) {
            scrollOffset--;
        } else if (delta < 0 && scrollOffset < MAX_SCROLL - 1) {
            scrollOffset++;
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int firstVisibleSlot = scrollOffset;
        int visibleSlotCount = (imageHeight - 34) / SLOT_SIZE;  // Adjust this if padding/margins differ
        int lastVisibleSlot = Math.min(firstVisibleSlot + visibleSlotCount, MAX_SCROLL);

        // Calculate the mouse position relative to the screen
        int mouseXRel = (int) mouseX - x;
        int mouseYRel = (int) mouseY - y;

        // Check if the click is within the slot area
        for (int j = firstVisibleSlot; j < lastVisibleSlot; j++) {
            int slotRenderY = 30 + (j - scrollOffset) * SLOT_SIZE;

            if (mouseXRel >= 80 && mouseXRel < 80 + SLOT_SIZE && mouseYRel >= slotRenderY && mouseYRel < slotRenderY + SLOT_SIZE) {
                // Clicked within the slot area
                SlotItemHandler slot = (SlotItemHandler) this.menu.getSlot(j);
                if (slot != null) {
                    // Forward click event to the container
                    // Simulate interaction with the slot (e.g., pickup or place item)
                    this.menu.handleSlotClick(j, button);
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
