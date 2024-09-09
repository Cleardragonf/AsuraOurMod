package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.screens.MatterConversionMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.windows.INPUT;

import java.util.ArrayList;
import java.util.List;

public class MatterConversionBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(41);

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int INPUT_SLOT_4 = 3;
    private static final int INPUT_SLOT_5 = 4;
    private static final int INPUT_SLOT_6 = 5;
    private static final int INPUT_SLOT_7 = 6;
    private static final int INPUT_SLOT_8 = 7;
    private static final int INPUT_SLOT_9 = 8;

    //his.itemHandler.getStackInSlot(slot).getItem())

    private List<Integer> INPUTS = new ArrayList<>(){{
        add(INPUT_SLOT_1);
        add(INPUT_SLOT_2);
        add(INPUT_SLOT_3);
        add(INPUT_SLOT_4);
        add(INPUT_SLOT_5);
        add(INPUT_SLOT_6);
        add(INPUT_SLOT_7);
        add(INPUT_SLOT_8);
        add(INPUT_SLOT_9);
    }};


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public MatterConversionBlockEntity( BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MATTER_CONVERSion_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i){
                    case 0 -> MatterConversionBlockEntity.this.progress;
                    case 1 -> MatterConversionBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> MatterConversionBlockEntity.this.progress = value;
                    case 1 -> MatterConversionBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(() -> itemHandler).cast();
        }
        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);

    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i=0; i< itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.ourmod.matter_conversion_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MatterConversionMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("matter_conversion.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("matter_conversion.progress");
    }

    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {

//        for(Integer slot : INPUTS){
//            if(hasRecipe(slot)){  //TODO:  Make sure hasRecipe() also checks to make sure the slot is active...
//                increaseGatheringProgress();
//                setChanged(level1, blockPos, blockState);
//
//                if(hasGatheringFinished()){
//                    craftItem(slot);
//                    resetGathering();
//                }
//            }else{
//                resetGathering();
//            }
//        }

        //TODO:  Make a for loop that cycles through EACH of the INPUTS and pretty much does the same as the below.
//        if(hasRecipe()){
//            increaseGatheringProgress();
//            setChanged(level1, blockPos, blockState);
//
//            if(hasGatheringFinished()){
//                craftItem();
//                resetGathering();
//            }
//        }else{
//            resetGathering();
//        }
    }

    private void resetGathering() {
        progress = 0;
    }

    private void craftItem(int slot) {
        ItemStack result = new ItemStack(ModItems.CONDENSED_MATTER.get(), 1);
        this.itemHandler.extractItem(slot, 1, false);

        this.itemHandler.setStackInSlot(slot, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(slot).getCount() + result.getCount()));
    }

    private boolean hasGatheringFinished() {
        return progress >= maxProgress;
    }


    private void increaseGatheringProgress() {
        progress++;
    }

    private boolean hasRecipe(Integer slot) {
        Item inputItem = this.itemHandler.getStackInSlot(slot).getItem();

        if(inputItem == ModItems.RAW_MATTER.get()){
            ItemStack result = new ItemStack(ModItems.CONDENSED_MATTER.get());
            return true && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());

        }
        else if(inputItem == ModItems.RAW_EARTH_MATTER.get()){
            ItemStack result = new ItemStack(ModItems.PURIFIED_EARTH_MATTER.get());
            return true && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
        }
        else if(inputItem == ModItems.RAW_FIRE_MATTER.get()){
            ItemStack result = new ItemStack(ModItems.PURIFIED_FIRE_MATTER.get());
            return true && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
        }
        else if(inputItem == ModItems.RAW_WATER_MATTER.get()){
            ItemStack result = new ItemStack(ModItems.PURIFIED_WATER_MATTER.get());
            return true && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
        }
        else if(inputItem == ModItems.RAW_WIND_MATTER.get()){
            ItemStack result = new ItemStack(ModItems.PURIFIED_WIND_MATTER.get());
            return true && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
        }
        else if(inputItem == ModItems.RAW_DARKNESS_MATTER.get()){
            ItemStack result = new ItemStack(ModItems.PURIFIED_DARKNESS_MATTER.get());
            return true && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
        }
        else if(inputItem == ModItems.RAW_LIGHT_MATTER.get()){
            ItemStack result = new ItemStack(ModItems.PURIFIED_LIGHT_MATTER.get());
            return true && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
        }else{
            return false;
        }
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
//        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
        return true;
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
//        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        return true;
    }
}
