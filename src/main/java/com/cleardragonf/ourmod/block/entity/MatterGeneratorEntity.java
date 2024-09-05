package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.screens.MatterGeneratorMenu;
import com.cleardragonf.ourmod.util.*;
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
    private final LazyOptional<WaterManaStorage> waterManaOptional = LazyOptional.of(() ->this.waterMana);
    public LazyOptional<WaterManaStorage> getWaterManaOptional(){
        return this.waterManaOptional;
    }
    public WaterManaStorage getWaterMana(){
        return this.waterMana;
    }

    private final FireManaStorage fireMana = new FireManaStorage(10000, 0, 100, 0);
    private final LazyOptional<FireManaStorage> fireManaOptional = LazyOptional.of(() ->this.fireMana);
    public LazyOptional<FireManaStorage> getFireManaOptional(){
        return this.fireManaOptional;
    }
    public FireManaStorage getFireMana(){
        return this.fireMana;
    }

    private final AirManaStorage windMana = new AirManaStorage(10000, 0, 100, 0);
    private final LazyOptional<AirManaStorage> windManaOptional = LazyOptional.of(() ->this.windMana);
    public LazyOptional<AirManaStorage> getWindManaOptional(){
        return this.windManaOptional;
    }
    public AirManaStorage getWindMana(){
        return this.windMana;
    }

    private final EarthManaStorage earthMana = new EarthManaStorage(10000, 0, 100, 0);
    private final LazyOptional<EarthManaStorage> earthManaOptional = LazyOptional.of(() ->this.earthMana);
    public LazyOptional<EarthManaStorage> getEarthManaOptional(){
        return this.earthManaOptional;
    }
    public EarthManaStorage getEarthMana(){
        return this.earthMana;
    }

    private final DarknessManaStorage darkMana = new DarknessManaStorage(10000, 0, 100, 0);
    private final LazyOptional<DarknessManaStorage> darkManaOptional = LazyOptional.of(() ->this.darkMana);
    public LazyOptional<DarknessManaStorage> getDarkManaOptional(){
        return this.darkManaOptional;
    }
    public DarknessManaStorage getDarkMana(){
        return this.darkMana;
    }

    private final LightManaStorage lightMana = new LightManaStorage(10000, 0, 100, 0);
    private final LazyOptional<LightManaStorage> lightManaOptional = LazyOptional.of(() ->this.lightMana);
    public LazyOptional<LightManaStorage> getLightManaOptional(){
        return this.lightManaOptional;
    }
    public LightManaStorage getLightMana(){
        return this.lightMana;
    }

    private final VoidManaStorage voidMana = new VoidManaStorage(10000, 0, 100, 0);
    private final LazyOptional<VoidManaStorage> voidManaOptional = LazyOptional.of(() ->this.voidMana);
    public LazyOptional<VoidManaStorage> getVoidManaOptional(){
        return this.voidManaOptional;
    }
    public VoidManaStorage getVoidMana(){
        return this.voidMana;
    }


    private final ItemStackHandler itemHandler = new ItemStackHandler(7);

    private static final int FIRE_INPUT_SLOT = 0;
    private static final int WATER_INPUT_SLOT = 1;
    private static final int WIND_INPUT_SLOT = 2;
    private static final int EARTH_INPUT_SLOT = 3;
    private static final int LIGHT_INPUT_SLOT = 4;
    private static final int VOID_INPUT_SLOT = 5;
    private static final int DARKNESS_INPUT_SLOT = 6;


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
                    case 6 -> MatterGeneratorEntity.this.fireMana.getEnergyStored();
                    case 7 -> MatterGeneratorEntity.this.fireMana.getMaxEnergyStored();
                    case 8 -> MatterGeneratorEntity.this.earthMana.getEnergyStored();
                    case 9 -> MatterGeneratorEntity.this.earthMana.getMaxEnergyStored();
                    case 10 -> MatterGeneratorEntity.this.windMana.getEnergyStored();
                    case 11 -> MatterGeneratorEntity.this.windMana.getMaxEnergyStored();
                    case 12 -> MatterGeneratorEntity.this.darkMana.getEnergyStored();
                    case 13 -> MatterGeneratorEntity.this.darkMana.getMaxEnergyStored();
                    case 14 -> MatterGeneratorEntity.this.lightMana.getEnergyStored();
                    case 15 -> MatterGeneratorEntity.this.lightMana.getMaxEnergyStored();
                    case 16 -> MatterGeneratorEntity.this.voidMana.getEnergyStored();
                    case 17 -> MatterGeneratorEntity.this.voidMana.getMaxEnergyStored();
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
                    case 6 -> MatterGeneratorEntity.this.fireMana.setEnergy(value);
                    case 8 -> MatterGeneratorEntity.this.earthMana.setEnergy(value);
                    case 10 -> MatterGeneratorEntity.this.windMana.setEnergy(value);
                    case 12 -> MatterGeneratorEntity.this.darkMana.setEnergy(value);
                    case 14 -> MatterGeneratorEntity.this.lightMana.setEnergy(value);
                    case 16 -> MatterGeneratorEntity.this.voidMana.setEnergy(value);

                }
            }

            @Override
            public int getCount() {
                return 18;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }else if(cap == ForgeCapabilities.ENERGY){
            return this.waterManaOptional.cast();
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
        tag.put("FireEnergy", this.fireMana.serializeNBT());
        tag.put("WindEnergy", this.windMana.serializeNBT());
        tag.put("EarthEnergy", this.earthMana.serializeNBT());
        tag.put("DarkEnergy", this.darkMana.serializeNBT());
        tag.put("LightEnergy", this.lightMana.serializeNBT());
        tag.put("VoidEnergy", this.voidMana.serializeNBT());
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
        if(tag.contains("FireEnergy", CompoundTag.TAG_INT)){
            this.fireMana.deserializeNBT(tag.get("FireEnergy"));
        }
        if(tag.contains("EarthEnergy", CompoundTag.TAG_INT)){
            this.earthMana.deserializeNBT(tag.get("EarthEnergy"));
        }
        if(tag.contains("WindEnergy", CompoundTag.TAG_INT)){
            this.windMana.deserializeNBT(tag.get("WindEnergy"));
        }
        if(tag.contains("DarkEnergy", CompoundTag.TAG_INT)){
            this.darkMana.deserializeNBT(tag.get("DarkEnergy"));
        }
        if(tag.contains("LightEnergy", CompoundTag.TAG_INT)){
            this.lightMana.deserializeNBT(tag.get("LightEnergy"));
        }
        if(tag.contains("VoidEnergy", CompoundTag.TAG_INT)){
            this.voidMana.deserializeNBT(tag.get("VoidEnergy"));
        }
    }

    private int burnTime = 0, maxBurnTime = 0;

    public void tick(Level level1, BlockPos blockPos, BlockState blockState) {
        if(this.level == null || this.level.isClientSide())
            return;

        // Iterate through each input slot and process if it has a recipe
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            if (hasRecipe(slot)) {
                // Ensure the respective mana storage has enough capacity to store the generated energy
                if (canStoreEnergy(slot)) {
                    increaseGatheringProgress();
                    setChanged(level1, blockPos, blockState);

                    if (hasGatheringFinished()) {
                        craftItem(slot);
                        resetGathering();
                    }
                }
            }
        }
    }

    // Checks if the respective mana storage has enough capacity to store energy
    private boolean canStoreEnergy(int slot) {
        return switch (slot) {
            case WATER_INPUT_SLOT -> this.waterMana.getEnergyStored() < this.waterMana.getMaxEnergyStored();
            case FIRE_INPUT_SLOT -> this.fireMana.getEnergyStored() < this.fireMana.getMaxEnergyStored();
            case EARTH_INPUT_SLOT -> this.earthMana.getEnergyStored() < this.earthMana.getMaxEnergyStored();
            case WIND_INPUT_SLOT -> this.windMana.getEnergyStored() < this.windMana.getMaxEnergyStored();
            case DARKNESS_INPUT_SLOT -> this.darkMana.getEnergyStored() < this.darkMana.getMaxEnergyStored();
            case LIGHT_INPUT_SLOT -> this.lightMana.getEnergyStored() < this.lightMana.getMaxEnergyStored();
            case VOID_INPUT_SLOT -> this.voidMana.getEnergyStored() < this.voidMana.getMaxEnergyStored();
            // Add other slots (EARTH, WIND, DARKNESS, LIGHT, VOID) and their respective mana storages here
            default -> false;
        };
    }

// Other methods remain the same


    private void resetGathering() {
        progress = 0;
    }

    private void craftItem(int slot) {
        switch (slot){
            case WATER_INPUT_SLOT -> {
                this.waterMana.addEnergy(getEnergyQuantity(this.itemHandler.getStackInSlot(slot).getItem()));
            }
            case FIRE_INPUT_SLOT -> {
                this.fireMana.addEnergy(getEnergyQuantity(this.itemHandler.getStackInSlot(slot).getItem()));
            }
            case EARTH_INPUT_SLOT -> {
                this.earthMana.addEnergy(getEnergyQuantity(this.itemHandler.getStackInSlot(slot).getItem()));
            }
            case WIND_INPUT_SLOT -> {
                this.windMana.addEnergy(getEnergyQuantity(this.itemHandler.getStackInSlot(slot).getItem()));
            }
            case DARKNESS_INPUT_SLOT -> {
                this.darkMana.addEnergy(getEnergyQuantity(this.itemHandler.getStackInSlot(slot).getItem()));
            }
            case LIGHT_INPUT_SLOT -> {
                this.lightMana.addEnergy(getEnergyQuantity(this.itemHandler.getStackInSlot(slot).getItem()));
            }
            case VOID_INPUT_SLOT -> {
                this.voidMana.addEnergy(getEnergyQuantity(this.itemHandler.getStackInSlot(slot).getItem()));
            }
        }
        this.itemHandler.extractItem(slot, 1, false);
    }

    private int getEnergyQuantity(Item inputItem) {

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
