package com.cleardragonf.ourmod.event;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.ModEntities;
import com.cleardragonf.ourmod.entity.client.JackOSurpriseModel;
import com.cleardragonf.ourmod.entity.client.ModModelLayers;
import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OurMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.JACKOSURPRISE.get(), JackOSurpriseEntity.createAttributes().build());
    }
}
