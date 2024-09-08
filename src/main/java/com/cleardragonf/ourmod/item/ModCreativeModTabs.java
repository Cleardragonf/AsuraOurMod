package com.cleardragonf.ourmod.item;

import com.cleardragonf.ourmod.OurMod;
import com.cleardragonf.ourmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
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
                        pOutput.accept(ModItems.RAW_WATER_MATTER.get());
                        pOutput.accept(ModItems.PURIFIED_WATER_MATTER.get());
                        pOutput.accept(ModItems.RAW_WIND_MATTER.get());
                        pOutput.accept(ModItems.RAW_WIND_MATTER.get());
                        pOutput.accept(ModItems.RAW_FIRE_MATTER.get());
                        pOutput.accept(ModItems.PURIFIED_FIRE_MATTER.get());
                        pOutput.accept(ModItems.RAW_EARTH_MATTER.get());
                        pOutput.accept(ModItems.PURIFIED_EARTH_MATTER.get());
                        pOutput.accept(ModItems.RAW_DARKNESS_MATTER.get());
                        pOutput.accept(ModItems.PURIFIED_DARKNESS_MATTER.get());
                        pOutput.accept(ModItems.RAW_LIGHT_MATTER.get());
                        pOutput.accept(ModItems.PURIFIED_LIGHT_MATTER.get());



                        pOutput.accept(ModItems.MATTER_DETECTOR.get());


                        pOutput.accept(ModBlocks.MATTER_BLOCK.get());
                        pOutput.accept(ModBlocks.RAW_MATTER_BLOCK.get());


                        pOutput.accept(ModBlocks.TEMPLATE_BLOCK.get());
                        pOutput.accept(ModBlocks.MATTER_CONVERSION_BLOCK.get());
                        pOutput.accept(ModBlocks.MATTER_GENERATOR_BLOCK.get());
                        pOutput.accept(ModBlocks.MATTER_COLLECTION_BLOCK.get());
                        pOutput.accept(ModBlocks.MANA_BATTERY_BLOCK.get());


                        pOutput.accept(ModBlocks.MATTER_ORE.get());
                        pOutput.accept(ModBlocks.WATER_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.WIND_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.EARTH_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.FIRE_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.DARKNESS_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.LIGHT_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.DEEPSLATE_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_MATTER_ORE.get());
                        pOutput.accept(ModBlocks.END_STONE_MATTER_ORE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
