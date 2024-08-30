package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.screens.MatterConversionMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
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

    private final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex){
                case 2 -> MatterGeneratorEntity.this.waterMana.getEnergyStored();
                case 3 -> MatterGeneratorEntity.this.waterMana.getMaxEnergyStored();
                case 4 -> MatterGeneratorEntity.this.burnTime;
                case 5 -> MatterGeneratorEntity.this.maxBurnTime;
                default -> throw new UnsupportedOperationException("unexpected");
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex){
                case 2 -> MatterGeneratorEntity.this.waterMana.setEnergy(pValue);
                case 4 -> MatterGeneratorEntity.this.burnTime = pValue;
                case 5 -> MatterGeneratorEntity.this.maxBurnTime = maxBurnTime;
            };
        }

        @Override
        public int getCount() {
            return 6;
        }
    };

    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public MatterGeneratorEntity( BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MATTER_GENERATOR_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i){
                    case 0 -> MatterGeneratorEntity.this.progress;
                    case 1 -> MatterGeneratorEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> MatterGeneratorEntity.this.progress = value;
                    case 1 -> MatterGeneratorEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private void sendUpdate(){
        setChanged();
        if(this.level != null){
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
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
        tag.put("Energy", this.waterMana.serializeNBT());

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("matter_conversion.progress");
        if(tag.contains("Energy", CompoundTag.TAG_INT)){
            this.waterMana.deserializeNBT(tag.get("Energy"));
        }
    }

    private int burnTime = 0, maxBurnTime = 0;

    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
        if(this.level == null || this.level.isClientSide())
            return;
        if(this.waterMana.getEnergyStored()< this.waterMana.getMaxEnergyStored()){
            if(this.burnTime <= 0){
                if(canBurn(this.itemHandler.getStackInSlot(INPUT_SLOT))){
                    this.burnTime = this.maxBurnTime = getBurnTime(this.itemHandler.getStackInSlot(INPUT_SLOT));
                    this.itemHandler.getStackInSlot(INPUT_SLOT).shrink(1);
                    sendUpdate();
                }else{
                    this.burnTime--;
                    this.waterMana.addEnergy(1);
                    sendUpdate();
                }
            }

        }


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

    public int getBurnTime(ItemStack stack){
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
    }

    public boolean canBurn(ItemStack stack) {
        return getBurnTime(stack) > 0;
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
