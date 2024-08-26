package com.cleardragonf.ourmod.item;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OurMod.MODID);


    public static final RegistryObject<CreativeModeTab> OURMOD_TAB = CREATIVE_MODE_TABS.register("ourmod_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CONDENSED_MATTER.get()))
                    .title(Component.translatable("creativetab.ourmod_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.CONDENSED_MATTER.get());
                        pOutput.accept(ModItems.RAW_MATTER.get());


                        pOutput.accept(ModItems.MATTER_DETECTOR.get());


                        pOutput.accept(ModBlocks.MATTER_BLOCK.get());
                        pOutput.accept(ModBlocks.RAW_MATTER_BLOCK.get());

                        pOutput.accept(ModBlocks.MATTER_CONVERSION_BLOCK.get());


                        pOutput.accept(ModBlocks.MATTER_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.AIR_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.EARTH_DEEPSLATE_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.FIRE_NETHER_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.WATER_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.DARKNESS_END_STONE_MATTER_ORE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
