package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CactiCartEntity extends AbstractMinecart implements ICactusMob {
	private int timeInCart;

	public CactiCartEntity(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Override
	protected Item getDropItem() {
		return CactusRegistry.CACTUS_CART.get();
	}

	public CactiCartEntity(Level level, double x, double y, double z) {
		super(CactusRegistry.CACTUS_CART_ENTITY.get(), level, x, y, z);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, (double) 0.2F);
	}

	@Override
	public ItemStack getPickResult() {
		return new ItemStack(CactusRegistry.CACTUS_CART.get());
	}

	@Override
	public boolean hasCustomDisplay() {
		return false;
	}

	@Override
	public boolean canBeRidden() {
		return true;
	}

	@Override
	public boolean isPoweredCart() {
		return false;
	}

	@Override
	public Type getMinecartType() {
		return Type.RIDEABLE;
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		InteractionResult ret = super.interact(player, hand);
		if (ret.consumesAction()) return ret;
		if (player.isSecondaryUseActive()) {
			return InteractionResult.PASS;
		} else if (this.isVehicle()) {
			return InteractionResult.PASS;
		} else if (!this.level().isClientSide) {
			return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else {
			return InteractionResult.SUCCESS;
		}
	}

	@Override
	public void activateMinecart(int x, int y, int z, boolean receivingPower) {
		if (receivingPower) {
			if (this.isVehicle()) {
				this.ejectPassengers();
			}

			if (this.getHurtTime() == 0) {
				this.setHurtDir(-this.getHurtDir());
				this.setHurtTime(10);
				this.setDamage(50.0F);
				this.markHurt();
			}
		}

	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			if (this.isVehicle()) {
				Entity entity = this.getPassengers().get(0);
				if (entity instanceof LivingEntity && !(entity instanceof ICactusMob)) {
					++this.timeInCart;
					if (this.timeInCart >= 40) {
						entity.hurt(damageSources().cactus(), 1.0F);
						this.timeInCart = 0;
					}
				}
			} else {
				if (this.timeInCart > 0)
					this.timeInCart = 0;
			}
		}
	}
}