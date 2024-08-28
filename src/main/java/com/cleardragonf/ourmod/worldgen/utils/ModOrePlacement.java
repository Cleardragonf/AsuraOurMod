package com.cleardragonf.ourmod.worldgen.utils;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModOrePlacement {

    public static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2){
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    public  static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange){
        return orePlacement(CountPlacement.of(count), heightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int chance, PlacementModifier heightRange){
        return orePlacement(RarityFilter.onAverageOnceEvery(chance), heightRange);
    }

}
