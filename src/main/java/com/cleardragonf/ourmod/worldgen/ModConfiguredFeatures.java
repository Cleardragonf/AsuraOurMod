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
    public static final ResourceKey<ConfiguredFeature<?,?>> NETHER_MATTER_ORE_KEY = registerKey("nether_matter_ore");
    public static final ResourceKey<ConfiguredFeature<?,?>> END_MATTER_ORE_KEY = registerKey("end_matter_ore");

    public static void boostrap(BootstapContext<ConfiguredFeature<?,?>> context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest airReplaceables = new BlockMatchTest(Blocks.AIR);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackREplaceables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);
        RuleTest waterReplaceables = new BlockMatchTest(Blocks.WATER);

        List<OreConfiguration.TargetBlockState> netherrackReplacementOres = List.of(
                OreConfiguration.target(netherrackREplaceables, ModBlocks.NETHER_MATTER_ORE.get().defaultBlockState()),
                OreConfiguration.target(netherrackREplaceables, ModBlocks.FIRE_NETHER_MATTER_ORE.get().defaultBlockState())
        );

        List<OreConfiguration.TargetBlockState> endReplacementOres = List.of(
                OreConfiguration.target(endReplaceables, ModBlocks.DARKNESS_END_STONE_MATTER_ORE.get().defaultBlockState())
        );

        List<OreConfiguration.TargetBlockState> overworldMatterOres = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.MATTER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_MATTER_ORE.get().defaultBlockState()),
                OreConfiguration.target(airReplaceables, ModBlocks.AIR_MATTER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.EARTH_DEEPSLATE_MATTER_ORE.get().defaultBlockState()),
                OreConfiguration.target(waterReplaceables, ModBlocks.WATER_MATTER_ORE.get().defaultBlockState())
        );

        register(context, OVERWORLD_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(overworldMatterOres, 9));
        register(context, NETHER_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplacementOres, 9));
        register(context, END_MATTER_ORE_KEY, Feature.ORE, new OreConfiguration(endReplacementOres,9));



    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(OurMod.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
