package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;

public class CactusToolHandler {

	@SubscribeEvent
	public void CactusSwordEvent(LivingAttackEvent event) {
		DamageSource source = event.getSource();
		if (source != null && source.getEntity() instanceof Player player) {
			ItemStack stack = player.getMainHandItem();
			Level level = player.level();

			if (stack.getItem() == CactusRegistry.CACTUS_SWORD.get() && !level.isClientSide) {
				if (level.random.nextInt(10) < 3)
					player.hurt(player.damageSources().cactus(), 1F);
			}
		}
	}

	@SubscribeEvent
	public void CactusShieldEvent(LivingHurtEvent event) {
		if (event.getEntity() instanceof Player player) {
			ItemStack heldStack = player.getUseItem();
			Level level = player.level();

			if (!heldStack.isEmpty() && heldStack.getItem() == CactusRegistry.CACTUS_SHIELD.get()) {
				if (canBlockDamageSource(event.getSource(), player)) {
					damageShield(player, event.getAmount());

					if (level.random.nextInt(10) <= 5) {
						Entity trueSource = event.getSource().getEntity();
						trueSource.hurt(player.damageSources().generic(), 1F + Mth.floor(event.getAmount()));
					}
				}
			}
		}
	}

	private boolean canBlockDamageSource(DamageSource damageSourceIn, LivingEntity player) {
		if (!damageSourceIn.is(DamageTypeTags.BYPASSES_ARMOR) && player.isBlocking()) {
			Vec3 vec3d = damageSourceIn.getSourcePosition();

			if (vec3d != null) {
				Vec3 vec3d1 = player.getViewVector(1.0F);
				Vec3 vec3d2 = vec3d.vectorTo(new Vec3(player.getX(), player.getY(), player.getZ())).normalize();
				vec3d2 = new Vec3(vec3d2.x, 0.0D, vec3d2.z);

				return vec3d2.dot(vec3d1) < 0.0D;
			}
		}

		return false;
	}

	public static void damageShield(Player player, float damage) {
		ItemStack shield = player.getUseItem();
		InteractionHand handIn = player.getUsedItemHand();
		shield.hurtAndBreak(1 + Mth.floor(damage), player,
				(p_213833_1_) -> {
					p_213833_1_.broadcastBreakEvent(handIn);
					EventHooks.onPlayerDestroyItem(player, shield, handIn);
				});

		if (shield.getCount() <= 0) {
			EventHooks.onPlayerDestroyItem(player, shield, player.getUsedItemHand());
			player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
		}
	}
}
