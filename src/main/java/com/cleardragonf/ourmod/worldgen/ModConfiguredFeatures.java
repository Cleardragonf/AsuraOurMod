package com.cleardragonf.ourmod.worldgen;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_MATTER_ORE_KEY = registerKey("matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_WATER_MATTER_ORE_KEY = registerKey("water_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_WIND_MATTER_ORE_KEY = registerKey("wind_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_FIRE_MATTER_ORE_KEY = registerKey("fire_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_EARTH_MATTER_ORE_KEY = registerKey("earth_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_DARKNESS_MATTER_ORE_KEY = registerKey("darkness_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> OVERWORLD_LIGHT_MATTER_ORE_KEY = registerKey("light_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> NETHER_MATTER_ORE_KEY = registerKey("nether_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> END_MATTER_ORE_KEY = registerKey("end_matter_ore");

    public static void boostrap(BootstapContext<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackREplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldMatterOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.MATTER_ORE.get().defaultBlockState()),
                OreConfiguration.target(stoneReplaceable, ModBlocks.WATER_MATTER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_MATTER_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldWaterOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.WATER_MATTER_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldWindOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.WIND_MATTER_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldFireOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.FIRE_MATTER_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldEarthOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.EARTH_MATTER_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldDarknessOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.DARKNESS_MATTER_ORE.get().defaultBlockState())
        );
        List<OreConfiguration.TargetBlockState> overworldLightOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.LIGHT_MATTER_ORE.get().defaultBlockState())
        );


        register(context, OVERWORLD_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldMatterOres, 9));
        register(context, OVERWORLD_WATER_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldWaterOres, 9));
        register(context, OVERWORLD_WIND_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldWindOres, 9));
        register(context, OVERWORLD_FIRE_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldFireOres, 9));
        register(context, OVERWORLD_EARTH_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldEarthOres, 9));
        register(context, OVERWORLD_DARKNESS_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldDarknessOres, 9));
        register(context, OVERWORLD_LIGHT_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldLightOres, 9));
        register(context, NETHER_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackREplaceables, ModBlocks.NETHER_MATTER_ORE.get().defaultBlockState(), 9));
        register(context, END_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables, ModBlocks.END_STONE_MATTER_ORE.get().defaultBlockState(), 9));



    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(OurMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
