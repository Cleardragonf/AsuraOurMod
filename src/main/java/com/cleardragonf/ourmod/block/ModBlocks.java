package com.cleardragonf.ourmod.block;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.custom.ManaBatterBlock;
import com.cleardragonf.ourmod.block.custom.MatterCollectionBlock;
import com.cleardragonf.ourmod.block.custom.MatterConversionBlock;
import com.cleardragonf.ourmod.block.custom.MatterGeneratorBlock;
import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.sound.ModSounds;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, OurMod.MODID);

    public static final RegistryObject<Block> MATTER_BLOCK = registerBlock("matter_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST))) ;
    public static final RegistryObject<Block> RAW_MATTER_BLOCK = registerBlock("raw_matter_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> MATTER_CONVERSION_BLOCK = registerBlock("matter_conversion_block",
            () -> new MatterConversionBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST))) ;
    public static final RegistryObject<Block> MATTER_GENERATOR_BLOCK = registerBlock("matter_generator_block",
            () -> new MatterGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST))) ;
    public static final RegistryObject<Block> MATTER_COLLECTION_BLOCK = registerBlock("matter_collection_block",
            () -> new MatterCollectionBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST))) ;
    public static final RegistryObject<Block> MANA_BATTERY_BLOCK = registerBlock("mana_battery_block",
            () -> new ManaBatterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST))) ;


    public static final RegistryObject<Block> MATTER_ORE = registerBlock("matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> WATER_MATTER_ORE = registerBlock("water_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(15.0f, 100.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 9)));
    public static final RegistryObject<Block> WIND_MATTER_ORE = registerBlock("wind_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(15.0f, 100.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 9)));
    public static final RegistryObject<Block> FIRE_MATTER_ORE = registerBlock("fire_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(15.0f, 100.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 9)));
    public static final RegistryObject<Block> EARTH_MATTER_ORE = registerBlock("earth_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(15.0f, 100.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 9)));
    public static final RegistryObject<Block> DARKNESS_MATTER_ORE = registerBlock("darkness_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(15.0f, 100.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 9)));
    public static final RegistryObject<Block> LIGHT_MATTER_ORE = registerBlock("light_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(15.0f, 100.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 9)));

    public static final RegistryObject<Block> DEEPSLATE_MATTER_ORE = registerBlock("deepslate_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> NETHER_MATTER_ORE = registerBlock("nether_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHERRACK)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> END_STONE_MATTER_ORE = registerBlock("end_stone_matter_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.END_STONE)
                    .sound(ModSounds.BREAKING_MATTER_ORE_SOUNDS)
                    .strength(2f).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
         RegistryObject<T> toReturn = BLOCKS.register(name, block);
         registerBlockItem(name, toReturn);
         return toReturn;
    }


    public static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
