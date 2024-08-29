package com.cleardragonf.ourmod.entity.client;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class JackOSurpriseRenderer extends MobRenderer<JackOSurpriseEntity, JackOSurpriseModel<JackOSurpriseEntity>> {
    public JackOSurpriseRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new JackOSurpriseModel<>(pContext.bakeLayer(ModModelLayers.JACKOSURPRISE_LAYER)), 0.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(JackOSurpriseEntity pEntity) {
        return new ResourceLocation(OurMod.MODID, "textures/entity/jackosurprise.png");
    }
}
