package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.screens.MatterGeneratorMenu;
import com.cleardragonf.ourmod.util.WaterManaStorage;
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

public class MatterGeneratorEntity extends BlockEntity implements MenuProvider {

    private final WaterManaStorage waterMana = new WaterManaStorage(10000, 0, 100, 0);
    private final LazyOptional<WaterManaStorage> energyOptional = LazyOptional.of(() ->this.waterMana);

    public LazyOptional<WaterManaStorage> getEnergyOptional(){
        return this.energyOptional;
    }
    public WaterManaStorage getEnergy(){
        return this.waterMana;
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(7);

    private static final int FIRE_INPUT_SLOT = 0;
    private static final int WATER_INPUT_SLOT = 1;
    private static final int EARTH_INPUT_SLOT = 2;
    private static final int WIND_INPUT_SLOT = 3;
    private static final int DARKNESS_INPUT_SLOT = 4;
    private static final int LIGHT_INPUT_SLOT = 5;
    private static final int VOID_INPUT_SLOT = 6;


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78; //somehow this is generating for the maxENERGYH STORAGE lol

    public MatterGeneratorEntity( BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MATTER_GENERATOR_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i){
                    case 0 -> MatterGeneratorEntity.this.progress;
                    case 1 -> MatterGeneratorEntity.this.maxProgress;
                    case 2 -> MatterGeneratorEntity.this.waterMana.getEnergyStored();
                    case 3 -> MatterGeneratorEntity.this.waterMana.getMaxEnergyStored();
                    case 4 -> MatterGeneratorEntity.this.burnTime;
                    case 5 -> MatterGeneratorEntity.this.maxBurnTime;
                    default -> throw new UnsupportedOperationException("unexpected");
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> MatterGeneratorEntity.this.progress = value;
                    case 1 -> MatterGeneratorEntity.this.maxProgress = value;
                    case 2 -> MatterGeneratorEntity.this.waterMana.setEnergy(value);
                    case 4 -> MatterGeneratorEntity.this.burnTime = value;
                    case 5 -> MatterGeneratorEntity.this.maxBurnTime = maxBurnTime;
                }
            }

            @Override
            public int getCount() {
                return 6;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }else if(cap == ForgeCapabilities.ENERGY){
            return this.energyOptional.cast();
        }else{
            return super.getCapability(cap, side);
        }
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
        return Component.translatable("block.ourmod.matter_generator_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MatterGeneratorMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("matter_generator.progress", progress);
        tag.put("WaterEnergy", this.waterMana.serializeNBT());

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("matter_generator.progress");
        if(tag.contains("WaterEnergy", CompoundTag.TAG_INT)){
            this.waterMana.deserializeNBT(tag.get("WaterEnergy"));
        }
    }

    private int burnTime = 0, maxBurnTime = 0;

    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
        if(this.level == null || this.level.isClientSide())
            return;
        if(this.waterMana.getEnergyStored()< this.waterMana.getMaxEnergyStored()){
            if(hasRecipe(WATER_INPUT_SLOT)){
                increaseGatheringProgress();
                setChanged(level1, blockPos, blockState);

                if(hasGatheringFinished()){
                    craftItem(WATER_INPUT_SLOT);
                    resetGathering();
                }
            }else{
                resetGathering();
            }

        }
    }

    private void resetGathering() {
        progress = 0;
    }

    private void craftItem(int slot) {
        this.itemHandler.extractItem(slot, 1, false);

        switch (slot){
            case WATER_INPUT_SLOT -> {
                this.waterMana.addEnergy(getEnergyQuantity(slot));
            }
        }
    }

    private int getEnergyQuantity(int slot) {
        Item inputItem = this.itemHandler.getStackInSlot(slot).getItem();

        if(inputItem == ModItems.RAW_MATTER.get()){
            return 100;
        }
        else if(inputItem == ModItems.RAW_EARTH_MATTER.get()){
            return 100;
        }
        else if(inputItem == ModItems.RAW_FIRE_MATTER.get()){
            return 100;
        }
        else if(inputItem == ModItems.RAW_WATER_MATTER.get()){
            return 100;
        }
        else if(inputItem == ModItems.RAW_WIND_MATTER.get()){
            return 100;
        }
        else if(inputItem == ModItems.RAW_DARKNESS_MATTER.get()){
            return 100;
        }
        else if(inputItem == ModItems.RAW_LIGHT_MATTER.get()){
            return 100;
        }else{
            return 0;
        }
    }

    private boolean hasGatheringFinished() {
        return progress >= maxProgress;
    }


    private void increaseGatheringProgress() {
        progress++;
    }

    private boolean hasRecipe(int slot) {
        Item inputItem = this.itemHandler.getStackInSlot(slot).getItem();

        switch (slot){
            case WATER_INPUT_SLOT -> {
                if(inputItem == ModItems.RAW_WATER_MATTER.get() || inputItem == ModItems.PURIFIED_WATER_MATTER.get()){
                    return true;
                }
                else{
                    return false;
                }
            }
            case FIRE_INPUT_SLOT -> {
                if(inputItem == ModItems.RAW_FIRE_MATTER.get() || inputItem == ModItems.PURIFIED_FIRE_MATTER.get()){
                    return true;
                }else{
                    return false;
                }
            }
            case WIND_INPUT_SLOT -> {
                if(inputItem == ModItems.RAW_WIND_MATTER.get() || inputItem == ModItems.PURIFIED_WIND_MATTER.get()){
                    return true;
                }else{
                    return false;
                }
            }
            case EARTH_INPUT_SLOT -> {
                if(inputItem == ModItems.RAW_EARTH_MATTER.get() || inputItem == ModItems.PURIFIED_EARTH_MATTER.get()){
                    return true;
                }else{
                    return false;
                }
            }
            case DARKNESS_INPUT_SLOT -> {
                if(inputItem == ModItems.RAW_DARKNESS_MATTER.get() || inputItem == ModItems.PURIFIED_DARKNESS_MATTER.get()){
                    return true;
                }else{
                    return false;
                }
            }
            case LIGHT_INPUT_SLOT -> {
                if(inputItem == ModItems.RAW_LIGHT_MATTER.get() || inputItem == ModItems.PURIFIED_LIGHT_MATTER.get()){
                    return true;
                }else{
                    return false;
                }
            }
            case VOID_INPUT_SLOT -> {
                if(inputItem == ModItems.RAW_MATTER.get() || inputItem == ModItems.CONDENSED_MATTER.get()){
                    return true;
                }else{
                    return false;
                }
            }
            default -> {
                return false;
            }
        }
    }
}
