package com.cleardragonf.ourmod.entity;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OurMod.MODID);

    public static final RegistryObject<EntityType<JackOSurpriseEntity>> JACKOSURPRISE =
            ENTITY_TYPES.register("jackosurprise", () -> EntityType.Builder.of(JackOSurpriseEntity::new, MobCategory.MONSTER)
                    .sized(2.5f,3.0f).build("jackosurprise"));

    public static void register(IEventBus bus){
        ENTITY_TYPES.register(bus);
    }
}
