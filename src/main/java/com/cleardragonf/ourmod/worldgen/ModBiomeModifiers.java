package com.cleardragonf.ourmod.worldgen;

import com.cleardragonf.ourmod.OurMod;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {

    public  static final ResourceKey<BiomeModifier> ADD_MATTER_ORE = registerKey("add_matter_ore");
    public  static final ResourceKey<BiomeModifier> ADD_WATER_MATTER_ORE = registerKey("add_water_matter_ore");
    public  static final ResourceKey<BiomeModifier> ADD_WIND_MATTER_ORE = registerKey("add_wind_matter_ore");
    public  static final ResourceKey<BiomeModifier> ADD_FIRE_MATTER_ORE = registerKey("add_fire_matter_ore");
    public  static final ResourceKey<BiomeModifier> ADD_EARTH_MATTER_ORE = registerKey("add_earth_matter_ore");
    public  static final ResourceKey<BiomeModifier> ADD_DARKNESS_MATTER_ORE = registerKey("add_darkness_matter_ore");
    public  static final ResourceKey<BiomeModifier> ADD_LIGHT_MATTER_ORE = registerKey("add_light_matter_ore");

    public  static final ResourceKey<BiomeModifier> ADD_NETHER_MATTER_ORE = registerKey("add_nether_matter_ore");
    public  static final ResourceKey<BiomeModifier> ADD_END_MATTER_ORE = registerKey("add_end_matter_ore");

    public static void bootstrap(BootstapContext<BiomeModifier> context){
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_WATER_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OCEAN),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WATER_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.RAW_GENERATION));
        context.register(ADD_WIND_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WIND_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.RAW_GENERATION));
        context.register(ADD_FIRE_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.FIRE_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_EARTH_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.EARTH_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_DARKNESS_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DARKNESS_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_LIGHT_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LIGHT_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_NETHER_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NETHER_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_END_MATTER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_MATTER_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name){
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(OurMod.MODID, name));
    }
}
