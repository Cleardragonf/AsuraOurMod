package com.cleardragonf.ourmod.block.entity;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, OurMod.MODID);

    public static final RegistryObject<BlockEntityType<MatterConversionBlockEntity>> MATTER_CONVERSion_BE =
            BLOCK_ENTITIES.register("matter_conversion_be", () ->
                    BlockEntityType.Builder.of(MatterConversionBlockEntity::new,
                            ModBlocks.MATTER_CONVERSION_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
         BLOCK_ENTITIES.register(eventBus);
    }

}
