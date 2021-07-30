package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CactusToolHandler {
	
	@SubscribeEvent
	public void CactusSwordEvent(LivingAttackEvent event) {
		boolean flag = event.getSource().getEntity() instanceof PlayerEntity;
		if(event.getSource().getMsgId() == "player" && flag) {
			PlayerEntity player = (PlayerEntity)event.getSource().getEntity();
			ItemStack stack = player.getMainHandItem();
			World world = player.level;
			
			if(stack.getItem() == CactusRegistry.CACTUS_SWORD.get() && !world.isClientSide) {
				if(world.random.nextInt(10) < 3)
					player.hurt(DamageSource.CACTUS, 1F);
			}
		}
	}
	
	@SubscribeEvent
	public void CactusShieldEvent(LivingHurtEvent event) {
		if(event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			ItemStack heldStack = player.getUseItem();
			World world = player.level;

			if(!heldStack.isEmpty() && heldStack.getItem() == CactusRegistry.CACTUS_SHIELD.get()) {
				if(canBlockDamageSource(event.getSource(), player)) {
					damageShield(player, event.getAmount());

					if(world.random.nextInt(10) <= 5) {
						Entity trueSource = event.getSource().getEntity();
						trueSource.hurt(DamageSource.GENERIC, 1F + MathHelper.floor(event.getAmount()));
					}
				}
			}
		}
	}
	
	private boolean canBlockDamageSource(DamageSource damageSourceIn, LivingEntity player)
    {
        if (!damageSourceIn.isBypassArmor() && player.isBlocking()) {
			Vector3d vec3d = damageSourceIn.getSourcePosition();

            if (vec3d != null) {
				Vector3d vec3d1 = player.getViewVector(1.0F);
				Vector3d vec3d2 = vec3d.vectorTo(new Vector3d(player.getX(), player.getY(), player.getZ())).normalize();
                vec3d2 = new Vector3d(vec3d2.x, 0.0D, vec3d2.z);

                if (vec3d2.dot(vec3d1) < 0.0D) {
                    return true;
                }
            }
        }

        return false;
    }
	
	public static void damageShield(PlayerEntity player, float damage)
	{
		ItemStack shield = player.getUseItem();
		Hand handIn = player.getUsedItemHand();
		shield.hurtAndBreak(1 + MathHelper.floor(damage), player,
				(p_213833_1_) -> {
					p_213833_1_.broadcastBreakEvent(handIn);
					net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, shield, handIn);
				});

		if (shield.getCount() <= 0)
        {
            ForgeEventFactory.onPlayerDestroyItem(player, shield, player.getUsedItemHand());
            player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
        }
	}
}
