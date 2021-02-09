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
		boolean flag = event.getSource().getTrueSource() instanceof PlayerEntity;
		if(event.getSource().getDamageType() == "player" && flag) {
			PlayerEntity player = (PlayerEntity)event.getSource().getTrueSource();
			ItemStack stack = player.getHeldItemMainhand();
			World world = player.world;
			
			if(stack.getItem() == CactusRegistry.CACTUS_SWORD.get() && !world.isRemote) {
				if(world.rand.nextInt(10) < 3)
					player.attackEntityFrom(DamageSource.CACTUS, 1F);
			}
		}
	}
	
	@SubscribeEvent
	public void CactusShieldEvent(LivingHurtEvent event) {
		if(event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity)event.getEntityLiving();
			ItemStack heldStack = player.getActiveItemStack();
			World world = player.world;

			if(!heldStack.isEmpty() && heldStack.getItem() == CactusRegistry.CACTUS_SHIELD.get()) {
				if(canBlockDamageSource(event.getSource(), player)) {
					damageShield(player, event.getAmount());

					if(world.rand.nextInt(10) <= 5) {
						Entity trueSource = event.getSource().getTrueSource();
						trueSource.attackEntityFrom(DamageSource.GENERIC, 1F + MathHelper.floor(event.getAmount()));
					}
				}
			}
		}
	}
	
	private boolean canBlockDamageSource(DamageSource damageSourceIn, LivingEntity player)
    {
        if (!damageSourceIn.isUnblockable() && player.isActiveItemStackBlocking()) {
			Vector3d vec3d = damageSourceIn.getDamageLocation();

            if (vec3d != null) {
				Vector3d vec3d1 = player.getLook(1.0F);
				Vector3d vec3d2 = vec3d.subtractReverse(new Vector3d(player.getPosX(), player.getPosY(), player.getPosZ())).normalize();
                vec3d2 = new Vector3d(vec3d2.x, 0.0D, vec3d2.z);

                if (vec3d2.dotProduct(vec3d1) < 0.0D) {
                    return true;
                }
            }
        }

        return false;
    }
	
	public static void damageShield(PlayerEntity player, float damage)
	{
		ItemStack shield = player.getActiveItemStack();
		Hand handIn = player.getActiveHand();
		shield.damageItem(1 + MathHelper.floor(damage), player,
				(p_213833_1_) -> {
					p_213833_1_.sendBreakAnimation(handIn);
					net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, shield, handIn);
				});

		if (shield.getCount() <= 0)
        {
            ForgeEventFactory.onPlayerDestroyItem(player, shield, player.getActiveHand());
            player.setHeldItem(player.getActiveHand(), ItemStack.EMPTY);
        }
	}
}
