package com.mrbysco.cactusmod.entities;

import com.google.common.collect.Lists;
import com.mrbysco.cactusmod.Reference;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.event.EventHooks;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractSpikeEntity extends Projectile {
	private static final EntityDataAccessor<Byte> CRITICAL = SynchedEntityData.defineId(AbstractSpikeEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> PIERCE_LEVEL = SynchedEntityData.defineId(AbstractSpikeEntity.class, EntityDataSerializers.BYTE);
	@Nullable
	private BlockState inBlockState;
	protected boolean inGround;
	protected int timeInGround;
	public int spikeShake;
	private int ticksInGround;
	private double damage = 2.0D;
	private int knockbackStrength;
	private SoundEvent hitSound = this.getDefaultHitGroundSoundEvent();
	private IntOpenHashSet piercedEntities;
	private List<Entity> hitEntities;

	protected AbstractSpikeEntity(EntityType<? extends AbstractSpikeEntity> type, Level level) {
		super(type, level);
	}

	protected AbstractSpikeEntity(EntityType<? extends AbstractSpikeEntity> type, double x, double y, double z, Level level) {
		this(type, level);
		this.setPos(x, y, z);
	}

	protected AbstractSpikeEntity(EntityType<? extends AbstractSpikeEntity> type, LivingEntity shooter, Level level) {
		this(type, shooter.getX(), shooter.getEyeY() - (double) 0.1F, shooter.getZ(), level);
		this.setOwner(shooter);
	}

	public void setSoundEvent(SoundEvent soundIn) {
		this.hitSound = soundIn;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		double d0 = this.getBoundingBox().getSize() * 10.0D;
		if (Double.isNaN(d0)) {
			d0 = 1.0D;
		}

		d0 = d0 * 64.0D * getViewScale();
		return distance < d0 * d0;
	}

	protected void defineSynchedData() {
		this.entityData.define(CRITICAL, (byte) 0);
		this.entityData.define(PIERCE_LEVEL, (byte) 0);
	}

	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
		this.ticksInGround = 0;
	}

	@OnlyIn(Dist.CLIENT)
	public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPos(x, y, z);
		this.setRot(yaw, pitch);
	}

	@OnlyIn(Dist.CLIENT)
	public void lerpMotion(double x, double y, double z) {
		super.lerpMotion(x, y, z);
		this.ticksInGround = 0;
	}

	@Override
	public void tick() {
		super.tick();
		boolean flag = this.getNoClip();
		Vec3 vector3d = this.getDeltaMovement();
		if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
			double f = vector3d.horizontalDistance();
			this.setYRot((float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
			this.setXRot((float) (Mth.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI)));
			this.yRotO = this.getYRot();
			this.xRotO = this.getXRot();
		}

		BlockPos blockpos = this.blockPosition();
		BlockState blockstate = this.level().getBlockState(blockpos);
		if (!blockstate.isAir() && !flag) {
			VoxelShape voxelshape = blockstate.getBlockSupportShape(this.level(), blockpos);
			if (!voxelshape.isEmpty()) {
				Vec3 vector3d1 = this.position();

				for (AABB axisalignedbb : voxelshape.toAabbs()) {
					if (axisalignedbb.move(blockpos).contains(vector3d1)) {
						this.inGround = true;
						break;
					}
				}
			}
		}

		if (this.spikeShake > 0) {
			--this.spikeShake;
		}

		if (this.isInWaterOrRain() || blockstate.is(Blocks.POWDER_SNOW)) {
			this.clearFire();
		}

		if (this.inGround && !flag) {
			if (this.inBlockState != blockstate && this.shouldFall()) {
				this.startFalling();
			} else if (!this.level().isClientSide) {
				this.tickDespawn();
			}

			++this.timeInGround;
		} else {
			this.timeInGround = 0;
			Vec3 vector3d2 = this.position();
			Vec3 vector3d3 = vector3d2.add(vector3d);
			HitResult raytraceresult = this.level().clip(new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (raytraceresult.getType() != HitResult.Type.MISS) {
				vector3d3 = raytraceresult.getLocation();
			}

			while (!this.isRemoved()) {
				EntityHitResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}

				if (raytraceresult != null && raytraceresult.getType() == HitResult.Type.ENTITY) {
					Entity entity = ((EntityHitResult) raytraceresult).getEntity();
					Entity entity1 = this.getOwner();
					if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}

				if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS && !flag && !EventHooks.onProjectileImpact(this, raytraceresult)) {
					this.onHit(raytraceresult);
					this.hasImpulse = true;
				}

				if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
					break;
				}

				raytraceresult = null;
			}

			vector3d = this.getDeltaMovement();
			double d3 = vector3d.x;
			double d4 = vector3d.y;
			double d0 = vector3d.z;
			if (this.getIsCritical()) {
				for (int i = 0; i < 4; ++i) {
					this.level().addParticle(ParticleTypes.CRIT, this.getX() + d3 * (double) i / 4.0D, this.getY() + d4 * (double) i / 4.0D, this.getZ() + d0 * (double) i / 4.0D, -d3, -d4 + 0.2D, -d0);
				}
			}

			double d5 = this.getX() + d3;
			double d1 = this.getY() + d4;
			double d2 = this.getZ() + d0;
			double f1 = vector3d.horizontalDistance();
			if (flag) {
				this.setYRot((float) (Mth.atan2(-d3, -d0) * (double) (180F / (float) Math.PI)));
			} else {
				this.setYRot((float) (Mth.atan2(d3, d0) * (double) (180F / (float) Math.PI)));
			}

			this.setXRot((float) (Mth.atan2(d4, (double) f1) * (double) (180F / (float) Math.PI)));
			this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
			this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
			float f2 = 0.99F;
			float f3 = 0.05F;
			if (this.isInWater()) {
				for (int j = 0; j < 4; ++j) {
					float f4 = 0.25F;
					this.level().addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
				}

				f2 = this.getWaterDrag();
			}

			this.setDeltaMovement(vector3d.scale((double) f2));
			if (!this.isNoGravity() && !flag) {
				Vec3 vector3d4 = this.getDeltaMovement();
				this.setDeltaMovement(vector3d4.x, vector3d4.y - (double) 0.05F, vector3d4.z);
			}

			this.setPos(d5, d1, d2);
			this.checkInsideBlocks();
		}
	}

	public static DamageSource causeSpikeDamage(AbstractSpikeEntity spike, @Nullable Entity indirectEntityIn) {
		return Reference.spikeDamage(spike, indirectEntityIn);
	}

	private boolean shouldFall() {
		return this.inGround && this.level().noCollision((new AABB(this.position(), this.position())).inflate(0.06D));
	}

	private void startFalling() {
		this.inGround = false;
		Vec3 vector3d = this.getDeltaMovement();
		this.setDeltaMovement(vector3d.multiply((double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F)));
		this.ticksInGround = 0;
	}

	public void move(MoverType typeIn, Vec3 pos) {
		super.move(typeIn, pos);
		if (typeIn != MoverType.SELF && this.shouldFall()) {
			this.startFalling();
		}

	}

	protected void tickDespawn() {
		++this.ticksInGround;
		if (this.ticksInGround >= 1200) {
			this.discard();
		}

	}

	private void resetPiercedEntities() {
		if (this.hitEntities != null) {
			this.hitEntities.clear();
		}

		if (this.piercedEntities != null) {
			this.piercedEntities.clear();
		}

	}

	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity entity = hitResult.getEntity();
		float f = (float) this.getDeltaMovement().length();
		int i = Mth.ceil(Mth.clamp((double) f * this.damage, 0.0D, 2.147483647E9D));
		if (this.getPierceLevel() > 0) {
			if (this.piercedEntities == null) {
				this.piercedEntities = new IntOpenHashSet(5);
			}

			if (this.hitEntities == null) {
				this.hitEntities = Lists.newArrayListWithCapacity(5);
			}

			if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
				this.discard();
				return;
			}

			this.piercedEntities.add(entity.getId());
		}

		if (this.getIsCritical()) {
			long j = this.random.nextInt(i / 2 + 2);
			i = (int) Math.min(j + (long) i, 2147483647L);
		}

		Entity entity1 = this.getOwner();
		DamageSource damagesource;
		if (entity1 == null) {
			damagesource = causeSpikeDamage(this, this);
		} else {
			damagesource = causeSpikeDamage(this, entity1);
			if (entity1 instanceof LivingEntity) {
				((LivingEntity) entity1).setLastHurtMob(entity);
			}
		}

		boolean flag = entity.getType() == EntityType.ENDERMAN;
		int k = entity.getRemainingFireTicks();
		if (this.isOnFire() && !flag) {
			entity.setSecondsOnFire(5);
		}

		if (entity.hurt(damagesource, (float) i)) {
			if (flag) {
				return;
			}

			if (entity instanceof LivingEntity livingentity) {
				if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
					livingentity.setArrowCount(livingentity.getArrowCount() + 1);
				}

				if (this.knockbackStrength > 0) {
					Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knockbackStrength * 0.6D);
					if (vector3d.lengthSqr() > 0.0D) {
						livingentity.push(vector3d.x, 0.1D, vector3d.z);
					}
				}

				if (!this.level().isClientSide && entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
				}

				this.doPostHurtEffects(livingentity);
				if (livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
					((ServerPlayer) entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
				}

				if (!entity.isAlive() && this.hitEntities != null) {
					this.hitEntities.add(livingentity);
				}

				if (!this.level().isClientSide && entity1 instanceof ServerPlayer serverPlayer) {
					if (this.hitEntities != null && this.getShotFromCrossbow()) {
						CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverPlayer, this.hitEntities);
					} else if (!entity.isAlive() && this.getShotFromCrossbow()) {
						CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverPlayer, List.of(entity));
					}
				}
			}

			this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
			if (this.getPierceLevel() <= 0) {
				this.discard();
			}
		} else {
			entity.setRemainingFireTicks(k);
			this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
			this.setYRot(getYRot() + 180.0F);
			this.yRotO += 180.0F;
			if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
				this.discard();
			}
		}

	}

	protected void onHitBlock(BlockHitResult hitResult) {
		this.inBlockState = this.level().getBlockState(hitResult.getBlockPos());
		super.onHitBlock(hitResult);
		Vec3 vector3d = hitResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
		this.setDeltaMovement(vector3d);
		Vec3 vector3d1 = vector3d.normalize().scale(0.05F);
		this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
		this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
		this.inGround = true;
		this.spikeShake = 7;
		this.setIsCritical(false);
		this.setPierceLevel((byte) 0);
		this.setSoundEvent(SoundEvents.ARROW_HIT);
		this.setShotFromCrossbow(false);
		this.resetPiercedEntities();
	}

	/**
	 * The sound made when an entity is hit by this projectile
	 */
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return SoundEvents.ARROW_HIT;
	}

	protected final SoundEvent getHitGroundSoundEvent() {
		return this.hitSound;
	}

	protected void doPostHurtEffects(LivingEntity living) {
	}

	/**
	 * Gets the EntityRayTraceResult representing the entity hit
	 */
	@Nullable
	protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
		return ProjectileUtil.getEntityHitResult(this.level(), this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
	}

	protected boolean canHitEntity(Entity p_230298_1_) {
		return super.canHitEntity(p_230298_1_) && (this.piercedEntities == null || !this.piercedEntities.contains(p_230298_1_.getId()));
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("life", (short) this.ticksInGround);
		if (this.inBlockState != null) {
			compound.put("inBlockState", NbtUtils.writeBlockState(this.inBlockState));
		}

		compound.putByte("shake", (byte) this.spikeShake);
		compound.putBoolean("inGround", this.inGround);
		compound.putDouble("damage", this.damage);
		compound.putBoolean("crit", this.getIsCritical());
		compound.putByte("PierceLevel", this.getPierceLevel());
		compound.putString("SoundEvent", BuiltInRegistries.SOUND_EVENT.getKey(this.hitSound).toString());
		compound.putBoolean("ShotFromCrossbow", this.getShotFromCrossbow());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.ticksInGround = compound.getShort("life");
		if (compound.contains("inBlockState", 10)) {
			this.inBlockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compound.getCompound("inBlockState"));

		}

		this.spikeShake = compound.getByte("shake") & 255;
		this.inGround = compound.getBoolean("inGround");
		if (compound.contains("damage", 99)) {
			this.damage = compound.getDouble("damage");
		}

		this.setIsCritical(compound.getBoolean("crit"));
		this.setPierceLevel(compound.getByte("PierceLevel"));
		if (compound.contains("SoundEvent", 8)) {
			SoundEvent soundevent = BuiltInRegistries.SOUND_EVENT.get(new ResourceLocation(compound.getString("SoundEvent")));
			this.hitSound = soundevent != null ? soundevent : this.getDefaultHitGroundSoundEvent();
		}

		this.setShotFromCrossbow(compound.getBoolean("ShotFromCrossbow"));
	}

	public void setOwner(@Nullable Entity entityIn) {
		super.setOwner(entityIn);
	}

	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.NONE;
	}

	public void setDamage(double damageIn) {
		this.damage = damageIn;
	}

	public double getDamage() {
		return this.damage;
	}

	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}

	/**
	 * Returns true if it's possible to attack this entity with an item.
	 */
	public boolean isAttackable() {
		return false;
	}

	protected float getEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		return 0.13F;
	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public void setIsCritical(boolean critical) {
		this.setArrowFlag(1, critical);
	}

	public void setPierceLevel(byte level) {
		this.entityData.set(PIERCE_LEVEL, level);
	}

	private void setArrowFlag(int p_203049_1_, boolean p_203049_2_) {
		byte b0 = this.entityData.get(CRITICAL);
		if (p_203049_2_) {
			this.entityData.set(CRITICAL, (byte) (b0 | p_203049_1_));
		} else {
			this.entityData.set(CRITICAL, (byte) (b0 & ~p_203049_1_));
		}

	}

	/**
	 * Whether the arrow has a stream of critical hit particles flying behind it.
	 */
	public boolean getIsCritical() {
		byte b0 = this.entityData.get(CRITICAL);
		return (b0 & 1) != 0;
	}

	/**
	 * Whether the arrow was shot from a crossbow.
	 */
	public boolean getShotFromCrossbow() {
		byte b0 = this.entityData.get(CRITICAL);
		return (b0 & 4) != 0;
	}

	public byte getPierceLevel() {
		return this.entityData.get(PIERCE_LEVEL);
	}

	public void setEnchantmentEffectsFromEntity(LivingEntity p_190547_1_, float p_190547_2_) {
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, p_190547_1_);
		int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_190547_1_);
		this.setDamage((double) (p_190547_2_ * 2.0F) + this.random.nextGaussian() * 0.25D + (double) ((float) this.level().getDifficulty().getId() * 0.11F));
		if (i > 0) {
			this.setDamage(this.getDamage() + (double) i * 0.5D + 0.5D);
		}

		if (j > 0) {
			this.setKnockbackStrength(j);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_190547_1_) > 0) {
			this.setSecondsOnFire(100);
		}

	}

	protected float getWaterDrag() {
		return 0.6F;
	}

	/**
	 * Sets if this arrow can noClip
	 */
	public void setNoClip(boolean noClipIn) {
		this.noPhysics = noClipIn;
		this.setArrowFlag(2, noClipIn);
	}

	/**
	 * Whether the arrow can noClip
	 */
	public boolean getNoClip() {
		if (!this.level().isClientSide) {
			return this.noPhysics;
		} else {
			return (this.entityData.get(CRITICAL) & 2) != 0;
		}
	}

	/**
	 * Sets data about if this arrow entity was shot from a crossbow
	 */
	public void setShotFromCrossbow(boolean fromCrossbow) {
		this.setArrowFlag(4, fromCrossbow);
	}

	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		Entity entity = this.getOwner();
		return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
	}
}