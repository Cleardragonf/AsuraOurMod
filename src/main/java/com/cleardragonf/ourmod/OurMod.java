package com.cleardragonf.ourmod;

import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.block.entity.ModBlockEntities;
import com.cleardragonf.ourmod.item.ModCreativeModTabs;
import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.screens.*;
import com.cleardragonf.ourmod.sound.ModSounds;
import com.cleardragonf.ourmod.util.matterValue.ItemValueRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(OurMod.MODID)
public class OurMod {
    public static final String MODID = "ourmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Map<Item, Integer> ITEM_VALUES = new HashMap<>();

    public OurMod() {
       IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModSounds.register(modEventBus);

       modEventBus.addListener(this::commonSetup);

        ItemValueRegistry.setupItemValues();

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }





    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            MenuScreens.register(ModMenuTypes.TEMPLATE_MENU.get(), TemplateScreen::new);
            MenuScreens.register(ModMenuTypes.MATTER_CONVERSION_MENU.get(), MatterConversionScreen::new);
            MenuScreens.register(ModMenuTypes.MATTER_GENERATOR_MENU.get(), MatterGeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.MATTER_COLLECTION_MENU.get(), MatterCollectionScreen::new);
            MenuScreens.register(ModMenuTypes.MANA_BATTERY_MENU.get(), ManaBatteryScreen::new);
        }
    }

}
