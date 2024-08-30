package com.cleardragonf.ourmod.entity.ai.JackOSurprisGoals;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class PlacePumpkinGoal extends Goal {
    private final PathfinderMob mob;
    private final double speedModifier;
    private final int radius;
    private final int placeFrequency;
    private int tickCounter;

    public PlacePumpkinGoal(PathfinderMob mob, double speedModifier, int radius, int placeFrequency) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.radius = radius;
        this.placeFrequency = placeFrequency;
        this.tickCounter = 0;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return mob.getRandom().nextInt(placeFrequency) == 0; // Randomly decides when to place a pumpkin
    }

    @Override
    public void start() {
        // Set the mob to wander around
        PathNavigation navigation = mob.getNavigation();
        navigation.setSpeedModifier(speedModifier);
        navigation.moveTo(mob.getRandom().nextInt(radius) + mob.getX(), mob.getY(), mob.getRandom().nextInt(radius) + mob.getZ(), speedModifier);
    }

    @Override
    public void tick() {
        if (tickCounter >= placeFrequency) {
            tickCounter = 0;
            BlockPos pos = mob.blockPosition().offset((int) mob.getLookAngle().x, 0, (int) mob.getLookAngle().z);
            BlockState blockState = mob.level().getBlockState(pos);
            if (blockState.isAir() || blockState.getBlock() == Blocks.AIR) {
                mob.level().setBlockAndUpdate(pos, Blocks.PUMPKIN.defaultBlockState());
            }
        } else {
            tickCounter++;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return true; // Keeps the goal active
    }
}
