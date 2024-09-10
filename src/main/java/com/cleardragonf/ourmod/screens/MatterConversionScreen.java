package com.cleardragonf.ourmod.screens;

import com.cleardragonf.ourmod.OurMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class MatterConversionScreen extends AbstractContainerScreen<MatterConversionMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OurMod.MODID, "textures/gui/matter_conversion_gui.png");
    private static final int SCROLLBAR_WIDTH = 10;
    private static final int SCROLLBAR_HEIGHT = 100; // Adjust as needed
    private static final int SCROLLBAR_X = 165; // X position relative to the screen
    private static final int SCROLLBAR_Y = 17;  // Y position relative to the screen
    private static final int SLOT_SIZE = 18;
    private static final int MAX_SCROLL = 41; // Total number of slots

    private int scrollOffset = 0;

    public MatterConversionScreen(MatterConversionMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    // Assuming each slot is 18 pixels in height (default slot size in Minecraft)
    final int SLOT_HEIGHT = 18;

    // Calculate visible slots based on the height of the GUI and any top/bottom margins
    int visibleSlots = (this.imageHeight - 17 * 2) / SLOT_HEIGHT; // 17*2 represents padding/margins


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        // Render slots with scroll offset
        this.menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            int totalSlots = iItemHandler.getSlots();
            int visibleSlots = (this.imageHeight - 17 * 2) / SLOT_SIZE; // Visible area for slots
            for (int j = 0; j < totalSlots; j++) {
                int slotY = y + 17 + (j * SLOT_SIZE) - (scrollOffset * SLOT_SIZE);
                if (slotY >= y + 17 && slotY < y + imageHeight - 17) {
                    drawSlot(guiGraphics, x + 80, slotY, j);
                }
            }
        });

        // Render scrollbar background and handle
        int scrollbarX = x + SCROLLBAR_X;
        int scrollbarY = y + SCROLLBAR_Y;
        guiGraphics.fill(scrollbarX, scrollbarY, scrollbarX + SCROLLBAR_WIDTH, scrollbarY + SCROLLBAR_HEIGHT, 0xFF000000); // Background

        // Calculate scroll handle position
        int totalSlots = this.menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).map(iItemHandler -> iItemHandler.getSlots()).orElse(0);
        int maxScrollOffset = Math.max(0, totalSlots - visibleSlots);
        int scrollHandleHeight = (int)((float)SCROLLBAR_HEIGHT * ((float)visibleSlots / totalSlots));
        int scrollHandleY = scrollbarY + (int)(((float)scrollOffset / maxScrollOffset) * (SCROLLBAR_HEIGHT - scrollHandleHeight));

        guiGraphics.fill(scrollbarX + 2, scrollHandleY, scrollbarX + SCROLLBAR_WIDTH - 2, scrollHandleY + scrollHandleHeight, 0xFF888888); // Scroll Handle
    }


    private void drawSlot(GuiGraphics guiGraphics, int x, int y, int slotIndex) {
        guiGraphics.blit(TEXTURE, x, y, 176, 0, SLOT_SIZE, SLOT_SIZE);  // Draw slot background
        this.menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            ItemStack stack = iItemHandler.getStackInSlot(slotIndex);
            if (!stack.isEmpty()) {
                guiGraphics.renderItem(stack, x + 1, y + 1);
            }
        });
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        int totalSlots = this.menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER)
                .map(iItemHandler -> iItemHandler.getSlots()).orElse(0);

        // Adjust maximum scroll based on the number of slots and visible area
        int visibleSlots = (this.imageHeight - 17 * 2) / SLOT_SIZE; // Adjust for top/bottom margins
        int maxScrollOffset = Math.max(0, totalSlots - visibleSlots);

        // Scroll up
        if (delta > 0 && scrollOffset > 0) {
            scrollOffset--;
        }
        // Scroll down
        else if (delta < 0 && scrollOffset < maxScrollOffset) {
            scrollOffset++;
        }

        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0 && isWithinScrollbar(mouseX, mouseY)) {
            int totalSlots = this.menu.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).map(iItemHandler -> iItemHandler.getSlots()).orElse(0);
            int visibleSlots = (this.imageHeight - 17 * 2) / SLOT_SIZE;
            int maxScrollOffset = Math.max(0, totalSlots - visibleSlots);

            int scrollbarY = this.topPos + SCROLLBAR_Y;
            float scrollRatio = (float)(mouseY - scrollbarY) / SCROLLBAR_HEIGHT;
            scrollOffset = Math.min(maxScrollOffset, Math.max(0, (int)(scrollRatio * maxScrollOffset)));
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private boolean isWithinScrollbar(double mouseX, double mouseY) {
        int scrollbarX = this.leftPos + SCROLLBAR_X;
        int scrollbarY = this.topPos + SCROLLBAR_Y;
        return mouseX >= scrollbarX && mouseX <= scrollbarX + SCROLLBAR_WIDTH && mouseY >= scrollbarY && mouseY <= scrollbarY + SCROLLBAR_HEIGHT;
    }



    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
