package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.screens.TemplateMenu;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TemplateBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public TemplateBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TEMPLATE_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i){
                    case 0 -> TemplateBlockEntity.this.progress;
                    case 1 -> TemplateBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> TemplateBlockEntity.this.progress = value;
                    case 1 -> TemplateBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
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
        return Component.translatable("block.ourmod.template_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new TemplateMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("template.progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("template.progress");
    }

    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
        if(hasRecipe()){
            increaseGatheringProgress();
            setChanged(level1, blockPos, blockState);

            if(hasGatheringFinished()){
                craftItem();
                resetGathering();
            }
        }else{
            resetGathering();
        }
    }

    private void resetGathering() {
        progress = 0;
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.CONDENSED_MATTER.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasGatheringFinished() {
        return progress >= maxProgress;
    }


    private void increaseGatheringProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Item inputItem = this.itemHandler.getStackInSlot(INPUT_SLOT).getItem();

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
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}
