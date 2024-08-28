package com.cleardragonf.ourmod.worldgen;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.worldgen.utils.ModOrePlacement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MATTER_ORE_PLACED_KEY = registerKey("matter_ore_placed");
    public static final ResourceKey<PlacedFeature> WATER_MATTER_ORE_PLACED_KEY = registerKey("water_matter_ore_placed");
    public static final ResourceKey<PlacedFeature> WIND_MATTER_ORE_PLACED_KEY = registerKey("wind_matter_ore_placed");
    public static final ResourceKey<PlacedFeature> FIRE_MATTER_ORE_PLACED_KEY = registerKey("fire_matter_ore_placed");
    public static final ResourceKey<PlacedFeature> EARTH_MATTER_ORE_PLACED_KEY = registerKey("earth_matter_ore_placed");
    public static final ResourceKey<PlacedFeature> DARKNESS_MATTER_ORE_PLACED_KEY = registerKey("darkness_matter_ore_placed");
    public static final ResourceKey<PlacedFeature> LIGHT_MATTER_ORE_PLACED_KEY = registerKey("light_matter_ore_placed");

    public static final ResourceKey<PlacedFeature> NETHER_MATTER_ORE_PLACED_KEY = registerKey("nether_matter_ore_placed");
    public static final ResourceKey<PlacedFeature> END_MATTER_ORE_PLACED_KEY = registerKey("end_matter_ore_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context){
        HolderGetter<ConfiguredFeature<?,?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, WATER_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_WATER_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, WIND_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_WIND_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(90), VerticalAnchor.absolute(256))));

        register(context, FIRE_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_FIRE_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, EARTH_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_EARTH_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, DARKNESS_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_DARKNESS_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, LIGHT_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_LIGHT_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(250), VerticalAnchor.absolute(256))));

        register(context, NETHER_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, END_MATTER_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_MATTER_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));


    }

    private static ResourceKey<PlacedFeature> registerKey(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(OurMod.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers){
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
