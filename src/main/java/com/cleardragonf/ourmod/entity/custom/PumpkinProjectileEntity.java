package com.cleardragonf.ourmod.entity.custom;

import com.cleardragonf.ourmod.entity.ModEntities;
import com.cleardragonf.ourmod.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class PumpkinProjectileEntity extends ThrowableItemProjectile {
    public PumpkinProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public PumpkinProjectileEntity(Level pLevel) {
        super(ModEntities.PUMPKIN_PROJECTILE.get(), pLevel);
    }
    public PumpkinProjectileEntity(Level pLevel, LivingEntity entity) {
        super(ModEntities.PUMPKIN_PROJECTILE.get(), entity, pLevel);
    }


    @Override
    protected Item getDefaultItem() {
        return ModItems.JACKOSURPRISE_HEAD.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(!this.level().isClientSide()){
            if(pResult.getEntity() instanceof Player){
                Player player = (Player) pResult.getEntity();
                ItemStack jackOLantern = new ItemStack(Items.JACK_O_LANTERN);
                player.setItemSlot(EquipmentSlot.HEAD, jackOLantern);
            }
            if(pResult.getEntity() instanceof Mob){
                Mob mob = (Mob) pResult.getEntity();
                if(mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty()){
                    ItemStack jackOLatern = new ItemStack(Items.JACK_O_LANTERN);
                    mob.setItemSlot(EquipmentSlot.HEAD, jackOLatern);
                }
            }

        }
        super.onHitEntity(pResult);
    }
}
