package com.cleardragonf.ourmod.screens;

import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.block.entity.MatterConversionBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class MatterConversionMenu extends AbstractContainerMenu {
    public final MatterConversionBlockEntity blockEntity;
    public final Level level;
    private final ContainerData data;

    // Slot indices and counts
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 41;  // Number of slots for the custom inventory

    private float scrollOffset = 0.0F; // Current scroll offset

    public MatterConversionMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public MatterConversionMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MATTER_CONVERSION_MENU.get(), containerId);
        checkContainerSize(inv, TE_INVENTORY_SLOT_COUNT);
        this.blockEntity = (MatterConversionBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            for (int i = 0; i < TE_INVENTORY_SLOT_COUNT; i++) {
                this.addSlot(new SlotItemHandler(iItemHandler, i, 8 + (i % 9) * 18, 5 + (i / 9) * 18));
            }
        });

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  // EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        int teInventoryStart = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
        int teInventoryEnd = teInventoryStart + TE_INVENTORY_SLOT_COUNT;

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, teInventoryStart, teInventoryEnd, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < teInventoryEnd) {
            // This is a TE slot so merge the stack into the player's inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.MATTER_CONVERSION_BLOCK.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 95 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 153));
        }
    }

    // Scroll-related methods
    public void scrollTo(float scrollPosition) {
        this.scrollOffset = scrollPosition;
        int rowIndex = this.getRowIndexForScroll(scrollPosition);

        for (int j = 0; j < 5; ++j) {
            for (int k = 0; k < 9; ++k) {
                int index = k + (j + rowIndex) * 9;
                if (index >= 0 && index < this.blockEntity.getItems().getSlots()) {
                    this.blockEntity.getItems().setStackInSlot(k + j * 9, this.blockEntity.getItems().getStackInSlot(index));
                } else {
                    this.blockEntity.getItems().setStackInSlot(k + j * 9, ItemStack.EMPTY);
                }
            }
        }
    }

    private int getRowIndexForScroll(float scrollPosition) {
        return Math.max((int) ((double) (scrollPosition * this.calculateRowCount()) + 0.5D), 0);
    }

    private int calculateRowCount() {
        return Math.max(0, this.blockEntity.getItems().getSlots() / 9 - 5);
    }

    public boolean canScroll() {
        return this.blockEntity.getItems().getSlots() > 45;
    }
}
