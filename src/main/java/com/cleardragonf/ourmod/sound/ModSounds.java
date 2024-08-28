package com.cleardragonf.ourmod.sound;

import com.cleardragonf.ourmod.OurMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OurMod.MODID);

public static final RegistryObject<SoundEvent> ORE_BREAKING = registerSoundEvents("ore_breaking");

    public static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(OurMod.MODID, name)));
    }

    public static final ForgeSoundType BREAKING_MATTER_ORE_SOUNDS =  new ForgeSoundType(1f, 1f,
            ModSounds.ORE_BREAKING, ModSounds.ORE_BREAKING, ModSounds.ORE_BREAKING, ModSounds.ORE_BREAKING, ModSounds.ORE_BREAKING);

    public static void register(IEventBus bus){
        SOUND_EVENTS.register(bus);
    }
}
