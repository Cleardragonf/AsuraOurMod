package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.block.custom.MatterCollectionBlock;
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

import java.util.ArrayList;
import java.util.List;

public class ManaBatteryEntity extends BlockEntity implements MenuProvider {

    private final WaterManaStorage waterMana = new WaterManaStorage(10000, 100, 100, 0);
    private final LazyOptional<WaterManaStorage> waterManaOptional = LazyOptional.of(() ->this.waterMana);
    public LazyOptional<WaterManaStorage> getWaterManaOptional(){
        return this.waterManaOptional;
    }
    public WaterManaStorage getWaterMana(){
        return this.waterMana;
    }

    private final FireManaStorage fireMana = new FireManaStorage(10000, 100, 100, 0);
    private final LazyOptional<FireManaStorage> fireManaOptional = LazyOptional.of(() ->this.fireMana);
    public LazyOptional<FireManaStorage> getFireManaOptional(){
        return this.fireManaOptional;
    }
    public FireManaStorage getFireMana(){
        return this.fireMana;
    }

    private final AirManaStorage windMana = new AirManaStorage(10000, 100, 100, 0);
    private final LazyOptional<AirManaStorage> windManaOptional = LazyOptional.of(() ->this.windMana);
    public LazyOptional<AirManaStorage> getWindManaOptional(){
        return this.windManaOptional;
    }
    public AirManaStorage getWindMana(){
        return this.windMana;
    }

    private final EarthManaStorage earthMana = new EarthManaStorage(10000, 100, 100, 0);
    private final LazyOptional<EarthManaStorage> earthManaOptional = LazyOptional.of(() ->this.earthMana);
    public LazyOptional<EarthManaStorage> getEarthManaOptional(){
        return this.earthManaOptional;
    }
    public EarthManaStorage getEarthMana(){
        return this.earthMana;
    }

    private final DarknessManaStorage darkMana = new DarknessManaStorage(10000, 100, 100, 0);
    private final LazyOptional<DarknessManaStorage> darkManaOptional = LazyOptional.of(() ->this.darkMana);
    public LazyOptional<DarknessManaStorage> getDarkManaOptional(){
        return this.darkManaOptional;
    }
    public DarknessManaStorage getDarkMana(){
        return this.darkMana;
    }

    private final LightManaStorage lightMana = new LightManaStorage(10000, 100, 100, 0);
    private final LazyOptional<LightManaStorage> lightManaOptional = LazyOptional.of(() ->this.lightMana);
    public LazyOptional<LightManaStorage> getLightManaOptional(){
        return this.lightManaOptional;
    }
    public LightManaStorage getLightMana(){
        return this.lightMana;
    }

    private final VoidManaStorage voidMana = new VoidManaStorage(10000, 100, 100, 0);
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
    private List<BlockPos> inputBlocks = new ArrayList<>();

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (this.level == null || this.level.isClientSide()) {
            return;
        }


        // Reset the gathering progress and mana before checking the surrounding blocks
        resetGathering();

        // Iterate through surrounding blocks in a 3x3x3 area centered on the entity
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (int x = -10; x <= 10; x++) {
            for (int y = -10; y <= 10; y++) {
                for (int z = -10; z <= 10; z++) {
                    // Set the mutable position to the surrounding block
                    mutableBlockPos.set(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);

                    // Check if the block at the current position is dirt, stone, etc.
                    BlockState surroundingBlockState = level.getBlockState(mutableBlockPos);
                    FluidState surroundingFluidState = level.getFluidState(mutableBlockPos);
                    int lightLevel = level.getMaxLocalRawBrightness(mutableBlockPos);

                    if (surroundingBlockState.is(ModBlocks.MATTER_COLLECTION_BLOCK.get())) {
                        // Increase Earth Mana by 1 for each dirt or stone block
                        if (!inputBlocks.contains(mutableBlockPos.immutable())) {
                            inputBlocks.add(mutableBlockPos.immutable());
                        }
                    }
                }
            }
        }
        //TODO: Get mana from the surround blocks of inputBlocks
        for(BlockPos pos : inputBlocks){
            if(!(level.getBlockEntity(pos) instanceof MatterCollectionEntity)){
                inputBlocks.remove(pos);
                return;
            }else{
                MatterCollectionEntity blockentity = (MatterCollectionEntity) level.getBlockEntity(pos);
                transferMana(blockentity, this, 100);
            }
        }

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

    public void transferMana(MatterCollectionEntity source, ManaBatteryEntity target, int amount) {
        if(!(this.waterMana.getEnergyStored() == this.waterMana.getMaxEnergyStored())){
            int extractedWaterMana = source.extractWaterMana(amount);
            target.receiveWaterMana(extractedWaterMana);
            setChanged();
        }
        if(!(this.fireMana.getEnergyStored() == this.fireMana.getMaxEnergyStored())) {
            int extractedFireMana = source.extractFireMana(amount);
            target.receiveFireMana(extractedFireMana);
            setChanged();
        }
        if(!(this.windMana.getEnergyStored() == this.windMana.getMaxEnergyStored())) {
            int extractedAirMana = source.extractAirMana(amount);
            target.receiveAirMana(extractedAirMana);
            setChanged();
        }
        if(!(this.earthMana.getEnergyStored() == this.earthMana.getMaxEnergyStored())) {
            int extractedEarthMana = source.extractEarthMana(amount);
            target.receiveEarthMana(extractedEarthMana);
            setChanged();
        }
        if(!(this.lightMana.getEnergyStored() == this.lightMana.getMaxEnergyStored())) {
            int extractedLightMana = source.extractLightMana(amount);
            target.receiveLightMana(extractedLightMana);
            setChanged();
        }
        if(!(this.darkMana.getEnergyStored() == this.darkMana.getMaxEnergyStored())) {
            int extractedDarkMana = source.extractDarkMana(amount);
            target.receiveDarkMana(extractedDarkMana);
            setChanged();
        }
        if(!(this.voidMana.getEnergyStored() == this.voidMana.getMaxEnergyStored())) {
            int extractedVoidMana = source.extractVoidMana(amount);
            target.receiveVoidMana(extractedVoidMana);
            setChanged();
        }
        // Repeat this for other mana types (Dark, Light, Void)...
    }


    public void receiveWaterMana(int amount) {
        this.waterMana.receiveEnergy(amount, false);
    }

    public void receiveFireMana(int amount) {
        this.fireMana.receiveEnergy(amount, false);
    }

    public void receiveEarthMana(int amount) {
        this.earthMana.receiveEnergy(amount, false);
    }

    public void receiveAirMana(int amount) {
        this.windMana.receiveEnergy(amount, false);
    }
    public void receiveLightMana(int amount) {
        this.lightMana.receiveEnergy(amount, false);
    }
    public void receiveDarkMana(int amount) {
        this.darkMana.receiveEnergy(amount, false);
    }
    public void receiveVoidMana(int amount) {
        this.voidMana.receiveEnergy(amount, false);
    }

}
