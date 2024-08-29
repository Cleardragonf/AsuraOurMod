package com.cleardragonf.ourmod.entity.custom;

import com.cleardragonf.ourmod.entity.ai.JackOSurprisGoals.spawnGoals;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;

public class JackOSurpriseEntity extends Monster implements RangedAttackMob {
    public JackOSurpriseEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    private static final EntityDataAccessor<Boolean> SPAWNING =
            SynchedEntityData.defineId(JackOSurpriseEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(JackOSurpriseEntity.class, EntityDataSerializers.BOOLEAN);

    // Animation States
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState spawnAnimationState = new AnimationState();  // Correct animation state for spawn
    public int spawnAnimationTimeout = 0;
    public int spawnFirsttime = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

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
        this.entityData.define(ATTACKING, false);
    }

    public void setAttacking(boolean attacking){
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking(){
        return this.entityData.get(ATTACKING);
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

        if(this.isAttacking() && attackAnimationTimeout <= 0){
            attackAnimationTimeout = 60;
            attackAnimationState.start(this.tickCount);
        }else{
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()){
            attackAnimationState.stop();
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
//        float f;
//        if (this.getPose() == Pose.STANDING) {
//            f = Math.min(pPartialTick * 6F, 1f);
//        } else {
//            f = 0;
//        }
//
//        this.walkAnimation.update(f, 0.2f);
        this.walkAnimation.update(1f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 2f));
        this.goalSelector.addGoal(0, new spawnGoals(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 60, 10.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.2D));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20D);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        this.setAttacking(true);
        PumpkinProjectileEntity projectileEntity = new PumpkinProjectileEntity(this.level(), this);
        double d0 = pTarget.getEyeY() - (double)1.1F;
        double d1 = pTarget.getX() - this.getX();
        double d2 = d0 - projectileEntity.getY();
        double d3 = pTarget.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double)0.2F;
        projectileEntity.shoot(d1, d2 + d4, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(projectileEntity);
    }
}
