package com.cleardragonf.ourmod.item;

import com.cleardragonf.ourmod.entity.custom.PumpkinProjectileEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class JackOLanternItem extends Item {
    public JackOLanternItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL, 0.5F, 0.4F /level.getRandom().nextFloat() * 0.4F + 0.8F);
        if(!level.isClientSide){
            PumpkinProjectileEntity pumpkinProjectileEntity = new PumpkinProjectileEntity(level, player);
            pumpkinProjectileEntity.setItem(itemstack);
            pumpkinProjectileEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5f, 1f);
            level.addFreshEntity(pumpkinProjectileEntity);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if(!player.getAbilities().instabuild){
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
