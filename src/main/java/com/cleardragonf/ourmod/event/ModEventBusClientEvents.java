package com.cleardragonf.ourmod.event;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.client.JackOSurpriseModel;
import com.cleardragonf.ourmod.entity.client.ModModelLayers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OurMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(ModModelLayers.JACKOSURPRISE_LAYER, JackOSurpriseModel::createBodyLayer);
    }
}
