package com.cleardragonf.ourmod;

import com.cleardragonf.ourmod.block.ModBlocks;
import com.cleardragonf.ourmod.block.entity.ModBlockEntities;
import com.cleardragonf.ourmod.entity.ModEntities;
import com.cleardragonf.ourmod.entity.client.JackOSurpriseRenderer;
import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import com.cleardragonf.ourmod.item.ModCreativeModTabs;
import com.cleardragonf.ourmod.item.ModItems;
import com.cleardragonf.ourmod.screens.MatterConversionScreen;
import com.cleardragonf.ourmod.screens.ModMenuTypes;
import com.cleardragonf.ourmod.sound.ModSounds;
import com.cleardragonf.ourmod.util.ConfigurationManager;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;

@Mod(OurMod.MODID)
public class OurMod {
    public static final String MODID = "ourmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Tick counter
    private int tickCounter = 0;

    public OurMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus);

//        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        ConfigurationManager.init();
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
        // Logic for server starting
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event){
        // Increment tick counter
        tickCounter++;

        // Check every 10 seconds (200 ticks)
        if (tickCounter >= 200) {
            tickCounter = 0; // Reset counter
            if(ConfigurationManager.getCurrentEvent().equals("Halloween")){
                ServerLevel serverLevel = (ServerLevel) event.getServer().getLevel(net.minecraft.world.level.Level.OVERWORLD);
                if (serverLevel != null) {
                    List<ServerPlayer> players = serverLevel.players();

                    // Iterate over all players in the server
                    for (ServerPlayer player : players) {
                        BlockPos playerPos = player.blockPosition();
                        int radius = 5; // Check blocks in a 5 block radius around the player

                        // Iterate over blocks in the specified radius
                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                for (int z = -radius; z <= radius; z++) {
                                    BlockPos checkPos = playerPos.offset(x, y, z);
                                    BlockState blockState = serverLevel.getBlockState(checkPos);
                                    Block block = blockState.getBlock();
                                    if(block instanceof PumpkinBlock){
                                        serverLevel.destroyBlock(checkPos, false);
                                        JackOSurpriseEntity jackOSurprise = new JackOSurpriseEntity(ModEntities.JACKOSURPRISE.get(), serverLevel);
                                        jackOSurprise.setPos(checkPos.getX() + 0.5, checkPos.getY(), checkPos.getZ() + 0.5);
                                        jackOSurprise.triggerSpawnAnimation();
                                        serverLevel.addFreshEntity(jackOSurprise);
                                        jackOSurprise.setSpawning(true);
                                    }

                                    // Perform your logic on the block, e.g., logging or triggering events
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            MenuScreens.register(ModMenuTypes.MATTER_CONVERSION_MENU.get(), MatterConversionScreen::new);
            EntityRenderers.register(ModEntities.JACKOSURPRISE.get(), JackOSurpriseRenderer::new);
            EntityRenderers.register(ModEntities.PUMPKIN_PROJECTILE.get(), ThrownItemRenderer::new);
        }
    }
}
