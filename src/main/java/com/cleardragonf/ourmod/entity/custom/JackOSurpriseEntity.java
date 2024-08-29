package com.cleardragonf.ourmod.entity.custom;

import com.cleardragonf.ourmod.entity.ai.JackOSurprisGoals.spawnGoals;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class JackOSurpriseEntity extends Monster {
    public JackOSurpriseEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    private static final EntityDataAccessor<Boolean> SPAWNING =
            SynchedEntityData.defineId(JackOSurpriseEntity.class, EntityDataSerializers.BOOLEAN);

    // Animation States
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState spawnAnimationState = new AnimationState();  // Correct animation state for spawn
    public int spawnAnimationTimeout = 0;
    public int spawnFirsttime = 0;

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPAWNING, false);
    }

    public void setSpawning(boolean spawning){
        this.entityData.set(SPAWNING, spawning);
    }

    public boolean isSpawning(){
       return this.entityData.get(SPAWNING);
    }

    // Method to setup animation states
    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isSpawning() && spawnAnimationTimeout <= 0){
            if(spawnFirsttime == 0){
                spawnAnimationTimeout = 80;
                spawnAnimationState.start(this.tickCount);
                spawnFirsttime = 1;
            }else{

            }

        }else{
            --this.spawnAnimationTimeout;
        }

        if(!this.isSpawning()){
            spawnAnimationState.stop();
        }

    }

    // Trigger spawn animation
    public void triggerSpawnAnimation() {
        if (this.level().isClientSide()) {
            this.spawnAnimationState.start(this.tickCount);
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 2f));
        this.goalSelector.addGoal(2, new spawnGoals(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20D);
    }
}
