package com.cleardragonf.ourmod.datagen;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider  {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OurMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.MATTER_BLOCK);
        blockWithItem(ModBlocks.RAW_MATTER_BLOCK);
        blockWithItem(ModBlocks.DEEPSLATE_MATTER_ORE);
        blockWithItem(ModBlocks.NETHER_MATTER_ORE);
        blockWithItem(ModBlocks.END_STONE_MATTER_ORE);
        blockWithItem(ModBlocks.MATTER_ORE);
        blockWithItem(ModBlocks.WATER_MATTER_ORE);
        blockWithItem(ModBlocks.FIRE_MATTER_ORE);
        blockWithItem(ModBlocks.EARTH_MATTER_ORE);
        blockWithItem(ModBlocks.WIND_MATTER_ORE);
        blockWithItem(ModBlocks.DARKNESS_MATTER_ORE);
        blockWithItem(ModBlocks.LIGHT_MATTER_ORE);

        simpleBlockWithItem( ModBlocks.MATTER_CONVERSION_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/matter_conversion_block")));
        simpleBlockWithItem( ModBlocks.MATTER_GENERATOR_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/matter_generator_block")));
        simpleBlockWithItem( ModBlocks.MATTER_COLLECTION_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/matter_collection_block")));
        simpleBlockWithItem(ModBlocks.MANA_BATTERY_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/mana_battery_block")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
