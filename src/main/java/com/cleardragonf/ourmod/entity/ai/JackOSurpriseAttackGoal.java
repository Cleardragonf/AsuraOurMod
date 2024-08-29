package com.cleardragonf.ourmod.entity.ai;

import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class JackOSurpriseAttackGoal extends MeleeAttackGoal {
    private final JackOSurpriseEntity entity;
    private int attackDelay = 40;
    private int ticksUntilNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;

    public JackOSurpriseAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((JackOSurpriseEntity) pMob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 40;
        ticksUntilNextAttack = 20;
    }

    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack){
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity enemey, double distToEnmeySquared){
        return distToEnmeySquared <= this.getAttackReachSqr(enemey);
    }

    protected void resetAttackCooldown(){this.ticksUntilNextAttack = this.adjustedTickDelay(attackDelay * 2);}

    protected boolean isTimeToAttack() {return this.ticksUntilNextAttack <= 0;}

    protected boolean isTimeToStartAttackAnimation(){return this.ticksUntilNextAttack <= attackDelay;}

    protected int getTicksUntilNextAttack(){return this.ticksUntilNextAttack;}

    protected void performAttack(LivingEntity enemy){
        this.resetAttackCooldown();
        this.mob.swing(InteractionHand.MAIN_HAND);
        this.mob.doHurtTarget(enemy);
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
        if(isEnemyWithinAttackDistance(pEnemy, pDistToEnemySqr)){
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()){
                entity.setAttacking(true);
            }

            if(isTimeToAttack()){
                this.mob.getLookControl().setLookAt(pEnemy.getX(), pEnemy.getEyeY(), pEnemy.getZ());
                performAttack(pEnemy);
            }
        }else{
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimout = 0;
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();

    }
}
