package com.cleardragonf.ourmod.util;

import net.minecraftforge.energy.EnergyStorage;

public class LightManaStorage extends EnergyStorage {
    public LightManaStorage(int capacity) {
        super(capacity);
    }

    public LightManaStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public LightManaStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public LightManaStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }
    public void setEnergy(int energy){
        if(energy < 0)
            energy = 0;
        if(energy > this.capacity)
            energy = this.capacity;
        this.energy = energy;
    }

    public void addEnergy(int energy){
        setEnergy(this.energy + energy);
    }

    public void removeEnergy(int energy) {
        setEnergy(this.energy - energy);
    }
}
