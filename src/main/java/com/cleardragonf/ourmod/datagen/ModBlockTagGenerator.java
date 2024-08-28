package com.cleardragonf.ourmod.datagen;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,@Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OurMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Blocks.MATTER_DETECTOR_VALUABLES)
                .add(ModBlocks.MATTER_ORE.get(),
                        ModBlocks.NETHER_MATTER_ORE.get(),
                        ModBlocks.WATER_MATTER_ORE.get(),
                        ModBlocks.END_STONE_MATTER_ORE.get(),
                        ModBlocks.DEEPSLATE_MATTER_ORE.get()
                        );


        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.MATTER_ORE.get(),
                        ModBlocks.NETHER_MATTER_ORE.get(),
                        ModBlocks.WATER_MATTER_ORE.get(),
                        ModBlocks.END_STONE_MATTER_ORE.get(),
                        ModBlocks.DEEPSLATE_MATTER_ORE.get()
                );



        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.MATTER_ORE.get(),
                        ModBlocks.WATER_MATTER_ORE.get(),
                        ModBlocks.NETHER_MATTER_ORE.get(),
                        ModBlocks.END_STONE_MATTER_ORE.get(),
                        ModBlocks.DEEPSLATE_MATTER_ORE.get()
                );


    }
}
