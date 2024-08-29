package com.cleardragonf.ourmod.event;

import com.cleardragonf.ourmod.entity.ModEntities;
import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "ourmod")
public class PumpkinBreakEventHandler {

    @SubscribeEvent
    public static void onPlayerBreakPumpkin(BlockEvent.BreakEvent event) {
        // Get the block that was broken
        BlockState blockState = event.getState();

        // Check if the broken block is a pumpkin
        if (blockState.is(Blocks.PUMPKIN)) {
            // Get the world and position where the pumpkin was broken
            ServerLevel world = (ServerLevel) event.getLevel();
            BlockPos pos = event.getPos();

            // Get the player who broke the pumpkin
            Player player = event.getPlayer();

            // Spawn the JackOSurpriseEntity at the pumpkin's location
            JackOSurpriseEntity jackOSurprise = new JackOSurpriseEntity(ModEntities.JACKOSURPRISE.get(), world);
            jackOSurprise.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            jackOSurprise.triggerSpawnAnimation();
            world.addFreshEntity(jackOSurprise);
            jackOSurprise.setSpawning(true);

            // Play a sound or trigger a particle effect (optional)
            world.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }
}
