package com.cleardragonf.ourmod.datagen.loot;

import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
     public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.RAW_MATTER_BLOCK.get());
        this.dropSelf(ModBlocks.MATTER_BLOCK.get());

        this.dropSelf(ModBlocks.MATTER_CONVERSION_BLOCK.get());

        this.add(ModBlocks.MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.MATTER_ORE.get(), ModItems.RAW_MATTER.get()));
        this.add(ModBlocks.WATER_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.WATER_MATTER_ORE.get(), ModItems.RAW_WATER_MATTER.get()));
        this.add(ModBlocks.WIND_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.WIND_MATTER_ORE.get(), ModItems.RAW_WIND_MATTER.get()));
        this.add(ModBlocks.FIRE_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.FIRE_MATTER_ORE.get(), ModItems.RAW_FIRE_MATTER.get()));
        this.add(ModBlocks.EARTH_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.EARTH_MATTER_ORE.get(), ModItems.RAW_EARTH_MATTER.get()));
        this.add(ModBlocks.DARKNESS_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.DARKNESS_MATTER_ORE.get(), ModItems.RAW_DARKNESS_MATTER.get()));
        this.add(ModBlocks.LIGHT_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.LIGHT_MATTER_ORE.get(), ModItems.RAW_LIGHT_MATTER.get()));

        this.add(ModBlocks.DEEPSLATE_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.DEEPSLATE_MATTER_ORE.get(), ModItems.RAW_MATTER.get()));

        this.add(ModBlocks.NETHER_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.NETHER_MATTER_ORE.get(), ModItems.RAW_MATTER.get()));

        this.add(ModBlocks.END_STONE_MATTER_ORE.get(),
                block -> createMatterOreDrops(ModBlocks.END_STONE_MATTER_ORE.get(), ModItems.RAW_MATTER.get()));

    }

    protected LootTable.Builder createMatterOreDrops(Block pBlock, Item item){
         return createSilkTouchDispatchTable(pBlock,
                 this.applyExplosionDecay(pBlock,
                         LootItem.lootTableItem(item)
                                 .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                 .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
