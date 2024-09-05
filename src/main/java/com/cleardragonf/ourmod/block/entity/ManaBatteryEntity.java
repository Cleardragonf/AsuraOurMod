package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.screens.ManaBatteryMenu;
import com.cleardragonf.ourmod.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManaBatteryEntity extends BlockEntity implements MenuProvider {

    private final WaterManaStorage waterMana = new WaterManaStorage(1000, 0, 100, 0);
    private final LazyOptional<WaterManaStorage> waterManaOptional = LazyOptional.of(() ->this.waterMana);
    public LazyOptional<WaterManaStorage> getWaterManaOptional(){
        return this.waterManaOptional;
    }
    public WaterManaStorage getWaterMana(){
        return this.waterMana;
    }

    private final FireManaStorage fireMana = new FireManaStorage(1000, 0, 100, 0);
    private final LazyOptional<FireManaStorage> fireManaOptional = LazyOptional.of(() ->this.fireMana);
    public LazyOptional<FireManaStorage> getFireManaOptional(){
        return this.fireManaOptional;
    }
    public FireManaStorage getFireMana(){
        return this.fireMana;
    }

    private final AirManaStorage windMana = new AirManaStorage(1000, 0, 100, 0);
    private final LazyOptional<AirManaStorage> windManaOptional = LazyOptional.of(() ->this.windMana);
    public LazyOptional<AirManaStorage> getWindManaOptional(){
        return this.windManaOptional;
    }
    public AirManaStorage getWindMana(){
        return this.windMana;
    }

    private final EarthManaStorage earthMana = new EarthManaStorage(1000, 0, 100, 0);
    private final LazyOptional<EarthManaStorage> earthManaOptional = LazyOptional.of(() ->this.earthMana);
    public LazyOptional<EarthManaStorage> getEarthManaOptional(){
        return this.earthManaOptional;
    }
    public EarthManaStorage getEarthMana(){
        return this.earthMana;
    }

    private final DarknessManaStorage darkMana = new DarknessManaStorage(1000, 0, 100, 0);
    private final LazyOptional<DarknessManaStorage> darkManaOptional = LazyOptional.of(() ->this.darkMana);
    public LazyOptional<DarknessManaStorage> getDarkManaOptional(){
        return this.darkManaOptional;
    }
    public DarknessManaStorage getDarkMana(){
        return this.darkMana;
    }

    private final LightManaStorage lightMana = new LightManaStorage(1000, 0, 100, 0);
    private final LazyOptional<LightManaStorage> lightManaOptional = LazyOptional.of(() ->this.lightMana);
    public LazyOptional<LightManaStorage> getLightManaOptional(){
        return this.lightManaOptional;
    }
    public LightManaStorage getLightMana(){
        return this.lightMana;
    }

    private final VoidManaStorage voidMana = new VoidManaStorage(1000, 0, 100, 0);
    private final LazyOptional<VoidManaStorage> voidManaOptional = LazyOptional.of(() ->this.voidMana);
    public LazyOptional<VoidManaStorage> getVoidManaOptional(){
        return this.voidManaOptional;
    }
    public VoidManaStorage getVoidMana(){
        return this.voidMana;
    }


    private final ItemStackHandler itemHandler = new ItemStackHandler(1);

    private static final int INPUT_SLOT = 0;


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78; //somehow this is generating for the maxENERGYH STORAGE lol

    public ManaBatteryEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MANA_BATTERY_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i){
                    case 0 -> ManaBatteryEntity.this.progress;
                    case 1 -> ManaBatteryEntity.this.maxProgress;
                    case 2 -> ManaBatteryEntity.this.waterMana.getEnergyStored();
                    case 3 -> ManaBatteryEntity.this.waterMana.getMaxEnergyStored();
                    case 4 -> ManaBatteryEntity.this.burnTime;
                    case 5 -> ManaBatteryEntity.this.maxBurnTime;
                    case 6 -> ManaBatteryEntity.this.fireMana.getEnergyStored();
                    case 7 -> ManaBatteryEntity.this.fireMana.getMaxEnergyStored();
                    case 8 -> ManaBatteryEntity.this.earthMana.getEnergyStored();
                    case 9 -> ManaBatteryEntity.this.earthMana.getMaxEnergyStored();
                    case 10 -> ManaBatteryEntity.this.windMana.getEnergyStored();
                    case 11 -> ManaBatteryEntity.this.windMana.getMaxEnergyStored();
                    case 12 -> ManaBatteryEntity.this.darkMana.getEnergyStored();
                    case 13 -> ManaBatteryEntity.this.darkMana.getMaxEnergyStored();
                    case 14 -> ManaBatteryEntity.this.lightMana.getEnergyStored();
                    case 15 -> ManaBatteryEntity.this.lightMana.getMaxEnergyStored();
                    case 16 -> ManaBatteryEntity.this.voidMana.getEnergyStored();
                    case 17 -> ManaBatteryEntity.this.voidMana.getMaxEnergyStored();
                    default -> throw new UnsupportedOperationException("unexpected");
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0 -> ManaBatteryEntity.this.progress = value;
                    case 1 -> ManaBatteryEntity.this.maxProgress = value;
                    case 2 -> ManaBatteryEntity.this.waterMana.setEnergy(value);
                    case 4 -> ManaBatteryEntity.this.burnTime = value;
                    case 5 -> ManaBatteryEntity.this.maxBurnTime = maxBurnTime;
                    case 6 -> ManaBatteryEntity.this.fireMana.setEnergy(value);
                    case 8 -> ManaBatteryEntity.this.earthMana.setEnergy(value);
                    case 10 -> ManaBatteryEntity.this.windMana.setEnergy(value);
                    case 12 -> ManaBatteryEntity.this.darkMana.setEnergy(value);
                    case 14 -> ManaBatteryEntity.this.lightMana.setEnergy(value);
                    case 16 -> ManaBatteryEntity.this.voidMana.setEnergy(value);

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
        return Component.translatable("block.ourmod.mana_battery_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ManaBatteryMenu(i, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("mana_battery.progress", progress);
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
        progress = tag.getInt("mana_battery.progress");
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

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (this.level == null || this.level.isClientSide()) {
            return;
        }

        // Reset the gathering progress and mana before checking the surrounding blocks
        resetGathering();

        // Iterate through surrounding blocks in a 3x3x3 area centered on the entity
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int surroundingEarthBlocks = 0;
        int surroundingFireBlocks = 0;
        int surroundingAirBlocks = 0;
        int surroundingWaterBlocks = 0;
        int surroundingDarkBlocks = 0;
        int surroundingLightBlocks = 0;
        int surroundingVoidBlocks = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    // Set the mutable position to the surrounding block
                    mutableBlockPos.set(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);

                    // Check if the block at the current position is dirt, stone, etc.
                    BlockState surroundingBlockState = level.getBlockState(mutableBlockPos);
                    FluidState surroundingFluidState = level.getFluidState(mutableBlockPos);
                    int lightLevel = level.getMaxLocalRawBrightness(mutableBlockPos);

                    if (surroundingBlockState.is(BlockTags.DIRT) || surroundingBlockState.is(Blocks.STONE)) {
                        // Increase Earth Mana by 1 for each dirt or stone block
                        surroundingEarthBlocks++;
                    } else if (surroundingBlockState.is(BlockTags.FIRE) || surroundingFluidState.is(FluidTags.LAVA)) {
                        surroundingFireBlocks++;
                    } else if (surroundingBlockState.is(Blocks.AIR)) {
                        surroundingAirBlocks++;
                    } else if (surroundingBlockState.is(Blocks.WATER) || surroundingFluidState.is(FluidTags.WATER)) {
                        surroundingWaterBlocks++;
                    } else if (surroundingBlockState.is(Blocks.END_STONE)) {
                        surroundingVoidBlocks++;
                    } else {
                        // Determine light or dark based on the light level
                        if (lightLevel >= 10) {
                            surroundingLightBlocks++;
                        } else {
                            surroundingDarkBlocks++;
                        }
                    }
                }
            }
        }

        // Add energy based on the number of blocks counted
        this.windMana.addEnergy(surroundingAirBlocks);
        this.fireMana.addEnergy(surroundingFireBlocks);
        this.earthMana.addEnergy(surroundingEarthBlocks);
        this.waterMana.addEnergy(surroundingWaterBlocks);
        this.darkMana.addEnergy(surroundingDarkBlocks);
        this.lightMana.addEnergy(surroundingLightBlocks);
        this.voidMana.addEnergy(surroundingVoidBlocks);
    }



// Other methods remain the same


    private void resetGathering() {
        progress = 0;
    }

    private boolean hasGatheringFinished() {
        return progress >= maxProgress;
    }

    private void increaseGatheringProgress() {
        progress++;
    }

}
