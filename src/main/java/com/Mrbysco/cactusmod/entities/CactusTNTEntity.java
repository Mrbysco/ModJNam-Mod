package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import com.mrbysco.cactusmod.util.ExplosionHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

import javax.annotation.Nullable;

public class CactusTNTEntity extends Entity {
	private static final EntityDataAccessor<Integer> FUSE = SynchedEntityData.defineId(CactusTNTEntity.class, EntityDataSerializers.INT);
	@Nullable
	private LivingEntity tntPlacedBy;
	private int fuse = 80;

	public CactusTNTEntity(EntityType<? extends CactusTNTEntity> type, Level level) {
		super(type, level);
		this.blocksBuilding = true;
	}

	public CactusTNTEntity(SpawnEntity spawnEntity, Level level) {
		this(CactusRegistry.CACTUS_TNT_ENTITY.get(), level);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public CactusTNTEntity(Level level, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(CactusRegistry.CACTUS_TNT_ENTITY.get(), level);
		this.setPos(x, y, z);
		double d0 = level.random.nextDouble() * (double) ((float) Math.PI * 2F);
		this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double) 0.2F, -Math.cos(d0) * 0.02D);
		this.setFuse(80);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.tntPlacedBy = igniter;
	}

	@Override
	protected void defineSynchedData() {
		this.entityData.define(FUSE, Integer.valueOf(80));
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
	}

	@Override
	public boolean isPickable() {
		return this.getRemovalReason() != null;
	}

	@Override
	public void tick() {
		if (!this.isNoGravity()) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
		}

		this.move(MoverType.SELF, this.getDeltaMovement());
		this.setDeltaMovement(this.getDeltaMovement().scale(0.98D));
		if (this.onGround) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.7D, -0.5D, 0.7D));
		}

		--this.fuse;
		if (this.fuse <= 0) {
			this.discard();
			if (!this.level.isClientSide) {
				this.explode();
			}
		} else {
			this.updateInWaterStateAndDoFluidPushing();
			if (this.level.isClientSide) {
				this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	private void explode() {
		float f = 4.0F;
		ExplosionHelper.arrowExplosion(this, this.getX(), this.getY() + (double) (this.getBbHeight() / 16.0F), this.getZ(), f, true);
	}

	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putShort("Fuse", (short) this.getFuse());
	}

	protected void readAdditionalSaveData(CompoundTag compound) {
		this.setFuse(compound.getShort("Fuse"));
	}

	@Nullable
	public LivingEntity getTntPlacedBy() {
		return this.tntPlacedBy;
	}

	protected float getEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 0.15F;
	}

	public void setFuse(int fuseIn) {
		this.entityData.set(FUSE, fuseIn);
		this.fuse = fuseIn;
	}

	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		if (FUSE.equals(key)) {
			this.fuse = this.getFuseDataManager();
		}
	}

	public int getFuseDataManager() {
		return this.entityData.get(FUSE);
	}

	public int getFuse() {
		return this.fuse;
	}
}