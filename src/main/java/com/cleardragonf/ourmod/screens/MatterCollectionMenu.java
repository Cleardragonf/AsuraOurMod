package com.cleardragonf.ourmod.screens;

import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.block.entity.MatterCollectionEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class MatterCollectionMenu extends AbstractContainerMenu {
    public final MatterCollectionEntity blockEntity;
    public final Level level;
    private final ContainerData data;

    //Client Constructor
    public MatterCollectionMenu(int containerId, Inventory inv, FriendlyByteBuf extraData){
        this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(18));
    }

    //Server Constructor
    public MatterCollectionMenu(int containerId, Inventory inv, BlockEntity entity, ContainerData data){
        super(ModMenuTypes.MATTER_COLLECTION_MENU.get(), containerId);
        checkContainerSize(inv, 1);
        blockEntity = ((MatterCollectionEntity) entity);
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 30));
        });
        addDataSlots(data);
    }

    public boolean isCrafting(){
        return data.get(0) > 0;
    }

    public int getScaledProgress(){
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
    private static final int TE_INVENTORY_SLOT_COUNT = 1;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
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
                player, ModBlocks.MATTER_COLLECTION_BLOCK.get());
    }



    private void addPlayerInventory(Inventory playerInventory){
        for(int i = 0; i< 3; ++i){
            for(int l = 0; l< 9; ++l){
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(Inventory playerInventory){
        for (int i = 0; i< 9; ++i){
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public int getEnergy() {
        return this.data.get(2);
    }

    public int getFireMana(){
        return this.data.get(6);
    }


    public int getEarthMana(){
        return this.data.get(8);
    }


    public int getWindMana(){
        return this.data.get(10);
    }


    public int getDarkMana(){
        return this.data.get(12);
    }


    public int getLightMana(){
        return this.data.get(14);
    }


    public int getVoidMana(){
        return this.data.get(16);
    }

    public int getMaxEnergy() {
        return this.data.get(3);
    }

    public int getMaxFireMana() {
        return this.data.get(7);
    }


    public int getMaxEarthMana() {
        return this.data.get(9);
    }

    public int getMaxWindMana() {
        return this.data.get(11);
    }

    public int getMaxDarkMana() {
        return this.data.get(13);
    }

    public int getMaxLightMana() {
        return this.data.get(15);
    }

    public int getMaxVoidMana() {
        return this.data.get(17);
    }

    public int getBurnTime() {
        return this.data.get(4);
    }

    public int getMaxBurnTime() {
        return this.data.get(5);
    }

    public int getEnergyStoredScaled() {
        return (int) (((float) getEnergy() / (float) getMaxEnergy()) * 25);
    }

    public int getFireManaScaled(){
        return (int) (((float) getFireMana() / (float) getMaxFireMana()) * 25);
    }


    public int getEarthManaScaled(){
        return (int) (((float) getEarthMana() / (float) getMaxEarthMana()) * 25);
    }

    public int getWindManaScaled(){
        return (int) (((float) getWindMana() / (float) getMaxWindMana()) * 25);
    }

    public int getDarkManaScaled(){
        return (int) (((float) getDarkMana() / (float) getMaxDarkMana()) * 25);
    }

    public int getLightManaScaled(){
        return (int) (((float) getLightMana() / (float) getMaxLightMana()) * 25);
    }

    public int getVoidManaScaled(){
        return (int) (((float) getVoidMana() / (float) getMaxVoidMana()) * 25);
    }

}
