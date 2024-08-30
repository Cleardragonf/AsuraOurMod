package com.cleardragonf.ourmod.entity.ai.JackOSurprisGoals;

import com.cleardragonf.ourmod.entity.custom.JackOSurpriseEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class attackGoals extends RangedAttackGoal {
    private final JackOSurpriseEntity entity;
    private int attackDelay = 60;
    private int ticksUntilNextAttack = 60;
    private boolean shouldCountTillNextAttack = false;
    private final double attackRadius;  // Define the attack radius

    public attackGoals(RangedAttackMob pMob, double pSpeedModifier, int attackInterval, float attackRadius) {
        super(pMob, pSpeedModifier, attackInterval, attackRadius);
        this.entity = (JackOSurpriseEntity) pMob;
        this.attackRadius = attackRadius; // Initialize the attack radius
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 60;
        ticksUntilNextAttack = 60;
    }

    @Override
    public void tick() {
        LivingEntity target = this.entity.getTarget();
        if (target != null && this.isInAttackRange(target)) {
            // Make the entity turn to face the target
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
            shouldCountTillNextAttack = true;
            if (isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if (isTimeToAttack()) {
                // Ensure the entity is facing the target before attacking
                this.entity.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
                this.entity.performRangedAttack(target, 1.0F); // Perform ranged attack
                this.resetAttackCooldown(); // Reset cooldown after the attack
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }

        if (shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }

    private boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    private boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    private void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.attackDelay;
    }

    // Check if the target is within attack range
    private boolean isInAttackRange(LivingEntity target) {
        double distanceToTargetSqr = this.entity.distanceToSqr(target.getX(), target.getY(), target.getZ());
        double attackRadiusSqr = this.attackRadius * this.attackRadius;  // Calculate the squared attack radius
        return distanceToTargetSqr <= attackRadiusSqr;
    }
}
