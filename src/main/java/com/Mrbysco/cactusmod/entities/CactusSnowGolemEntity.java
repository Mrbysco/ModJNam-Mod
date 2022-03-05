package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CactusSnowGolemEntity extends AbstractGolem implements RangedAttackMob, IForgeShearable, ICactusMob {
	private static final EntityDataAccessor<Byte> CACTUS_EQUIPPED = SynchedEntityData.<Byte>defineId(CactusSnowGolemEntity.class, EntityDataSerializers.BYTE);

	public CactusSnowGolemEntity(EntityType<? extends AbstractGolem> type, Level world) {
		super(type, world);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25D, 20, 10.0F));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D, 1.0000001E-5F));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, (p_213621_0_) -> {
			return p_213621_0_ instanceof Enemy;
		}));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, (double) 0.20000000298023224D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(CACTUS_EQUIPPED, (byte) 16);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Cactus", this.isCactusEquipped());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("Pumpkin")) {
			this.setCactusEquipped(compound.getBoolean("Cactus"));
		}
	}

	public boolean isSensitiveToWater() {
		return true;
	}

	public void aiStep() {
		super.aiStep();
		if (!this.level.isClientSide) {
			int i = Mth.floor(this.getX());
			int j = Mth.floor(this.getY());
			int k = Mth.floor(this.getZ());
			if (this.level.getBiome(new BlockPos(i, 0, k)).value().getTemperature(new BlockPos(i, j, k)) > 1.0F) {
				this.hurt(DamageSource.ON_FIRE, 1.0F);
			}

			if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) {
				return;
			}

			BlockState blockstate = Blocks.SNOW.defaultBlockState();

			for (int l = 0; l < 4; ++l) {
				i = Mth.floor(this.getX() + (double) ((float) (l % 2 * 2 - 1) * 0.25F));
				j = Mth.floor(this.getY());
				k = Mth.floor(this.getZ() + (double) ((float) (l / 2 % 2 * 2 - 1) * 0.25F));
				BlockPos blockpos = new BlockPos(i, j, k);
				if (this.level.isEmptyBlock(blockpos) && this.level.getBiome(blockpos).value().getTemperature(blockpos) < 0.8F && blockstate.canSurvive(this.level, blockpos)) {
					this.level.setBlockAndUpdate(blockpos, blockstate);
				}
			}
		}

	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		AbstractSpikeEntity spike = CactusRegistry.CACTUS_SPIKE.get().create(level);
		if (spike != null) {
			spike.setPos(getX(), getEyeY() - (double) 0.1F, getZ());
			double d0 = target.getX() - this.getX();
			double d1 = target.getY(0.3333333333333333D) - spike.getY();
			double d2 = target.getZ() - this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			spike.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, 12.0F);
			this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			this.level.addFreshEntity(spike);
		}
	}

	public boolean isShearable() {
		return this.isAlive() && this.isCactusEquipped();
	}

	public boolean isCactusEquipped() {
		return (this.entityData.get(CACTUS_EQUIPPED) & 16) != 0;
	}

	public void setCactusEquipped(boolean cactusEquipped) {
		byte b0 = this.entityData.get(CACTUS_EQUIPPED);
		if (cactusEquipped) {
			this.entityData.set(CACTUS_EQUIPPED, (byte) (b0 | 16));
		} else {
			this.entityData.set(CACTUS_EQUIPPED, (byte) (b0 & -17));
		}
	}

	@Override
	public boolean isShearable(@Nonnull ItemStack item, Level world, BlockPos pos) {
		return this.isShearable();
	}

	@Nonnull
	@Override
	public List<ItemStack> onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune) {
		world.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
		if (!world.isClientSide()) {
			setCactusEquipped(false);
			return java.util.Collections.singletonList(new ItemStack(CactusRegistry.CARVED_CACTUS.get()));
		}
		return java.util.Collections.emptyList();
	}

	@OnlyIn(Dist.CLIENT)
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, (double) (0.75F * this.getEyeHeight()), (double) (this.getBbWidth() * 0.4F));
	}

	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 1.7F;
	}

	@Nullable
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SNOW_GOLEM_AMBIENT;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.SNOW_GOLEM_HURT;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.SNOW_GOLEM_DEATH;
	}
}
