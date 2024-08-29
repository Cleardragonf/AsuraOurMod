package com.cleardragonf.ourmod.entity.ai.JackOSurprisGoals;

import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class spawnGoals extends Goal{

    private final JackOSurpriseEntity entity;
    private int spawnDelay = 0;
    private int ticksUntilNextSpawn = 50;
    private boolean shouldCountTillNextAttack = true;

    public spawnGoals(JackOSurpriseEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void start() {
        super.start();
        spawnDelay = 0;
        ticksUntilNextSpawn = 40;
    }


    @Override
    public void stop() {
        entity.setSpawning(false);
        super.stop();
    }
}
