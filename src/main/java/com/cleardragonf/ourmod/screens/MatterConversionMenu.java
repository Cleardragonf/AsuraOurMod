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

    private static final int TE_INVENTORY_ROWS = 5;  // Number of rows in the scrollable inventory
    private static final int TE_INVENTORY_COLUMNS = 9; // Number of columns in the scrollable inventory
    private static final int SLOT_SIZE = 18; // Slot size in pixels
    private static final int INVENTORY_START_X = 8; // Starting X position of the scrollable inventory
    private static final int INVENTORY_START_Y = 17; // Starting Y position of the scrollable inventory

    public MatterConversionMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public MatterConversionMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MATTER_CONVERSION_MENU.get(), containerId);
        checkContainerSize(inv, 41);
        blockEntity = ((MatterConversionBlockEntity) entity);
        this.level = inv.player.level();
        this.data = data;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 8, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 26, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 44, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 62, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 4, 80, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 5, 98, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 6, 116, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 7, 134, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 8, 152, 5));
            for (int i = 0; i < 5; ++i) {
                for (int l = 0; l < 9; ++l) {
                    this.addSlot(new SlotItemHandler(iItemHandler, l + i * 9 + 9, 8 + l * 18, 36 + i * 18));
                }
            }




            //Attempting Slots for the Scrolling....

            // Add slots for the scrollable inventory
//            for (int row = 0; row < TE_INVENTORY_ROWS; row++) {
//                for (int col = 0; col < TE_INVENTORY_COLUMNS; col++) {
//                    int index = row * TE_INVENTORY_COLUMNS + col;
//                    int x = INVENTORY_START_X + col * SLOT_SIZE;
//                    int y = INVENTORY_START_Y + row * SLOT_SIZE;
//                    this.addSlot(new SlotItemHandler(iItemHandler, index + 9, x, y));
//                }
//            }
        });

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);
    }
    // Handle slot clicks
    public void handleSlotClick(int slotIndex, int button) {
        SlotItemHandler slot = (SlotItemHandler) this.getSlot(slotIndex);
        if (slot != null) {
            // Simulate handling item click
            // Example: Place or pick up an item
            ItemStack stack = slot.getItem();
            // Perform actions based on the button and slot state
            // For example, if button is left click, pick up item
        }
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



    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 41;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  // EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        int teInventoryStart = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
        int teInventoryEnd = teInventoryStart + TE_INVENTORY_ROWS * TE_INVENTORY_COLUMNS;

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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.MATTER_CONVERSION_BLOCK.get());
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
}
