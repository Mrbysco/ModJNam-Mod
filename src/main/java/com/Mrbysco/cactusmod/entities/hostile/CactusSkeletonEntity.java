package com.mrbysco.cactusmod.entities.hostile;

import com.mrbysco.cactusmod.entities.AI.SpikeBowRangedAttackGoal;
import com.mrbysco.cactusmod.entities.AbstractSpikeEntity;
import com.mrbysco.cactusmod.entities.SpikeEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

public class CactusSkeletonEntity extends AbstractSkeleton {
	private final SpikeBowRangedAttackGoal<AbstractSkeleton> spikeAttackGoal = new SpikeBowRangedAttackGoal<>(this, 1.0D, 20, 15.0F);

	public CactusSkeletonEntity(EntityType<? extends CactusSkeletonEntity> entityType, Level worldIn) {
		super(entityType, worldIn);
	}

	@Override
	public void reassessWeaponGoal() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.meleeGoal);
			this.goalSelector.removeGoal(this.spikeAttackGoal);
			ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, CactusRegistry.CACTUS_BOW.get()));
			if (itemstack.getItem() instanceof net.minecraft.world.item.BowItem) {
				int i = 20;
				if (this.level.getDifficulty() != Difficulty.HARD) {
					i = 40;
				}
				this.spikeAttackGoal.setMinAttackInterval(i);
				this.goalSelector.addGoal(4, this.spikeAttackGoal);
			} else {
				this.goalSelector.addGoal(4, this.meleeGoal);
			}
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(CactusRegistry.CACTUS_BOW.get()));
	}

	@Override
	public boolean canFireProjectileWeapon(ProjectileWeaponItem p_230280_1_) {
		return p_230280_1_ == CactusRegistry.CACTUS_BOW.get();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SKELETON_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		AbstractSpikeEntity spike = this.getSpike(distanceFactor);
		double d0 = target.getX() - this.getX();
		double d1 = target.getY(0.3333333333333333D) - spike.getY();
		double d2 = target.getZ() - this.getZ();
		double d3 = (double) Mth.sqrt((float) (d0 * d0 + d2 * d2));
		spike.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, (float) (14 - this.level.getDifficulty().getId() * 4));
		this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level.addFreshEntity(spike);
	}

	protected AbstractSpikeEntity getSpike(float p_190726_1_) {
		SpikeEntity spike = new SpikeEntity(this.level, this);
		return spike;
	}
}
