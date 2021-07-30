package com.mrbysco.cactusmod.entities;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSpikeEntity extends ThrowableEntity {
    private static final DataParameter<Byte> CRITICAL = EntityDataManager.defineId(AbstractSpikeEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> PIERCE_LEVEL = EntityDataManager.defineId(AbstractSpikeEntity.class, DataSerializers.BYTE);
    @Nullable
    private BlockState inBlockState;
    protected boolean inGround;
    protected int timeInGround;
    public int spikeShake;
    private int ticksInGround;
    private double damage = 2.0D;
    private int knockbackStrength;
    private SoundEvent hitSound = this.getHitEntitySound();
    private IntOpenHashSet piercedEntities;
    private List<Entity> hitEntities;

    protected AbstractSpikeEntity(EntityType<? extends AbstractSpikeEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected AbstractSpikeEntity(EntityType<? extends AbstractSpikeEntity> type, double x, double y, double z, World worldIn) {
        this(type, worldIn);
        this.setPos(x, y, z);
    }

    protected AbstractSpikeEntity(EntityType<? extends AbstractSpikeEntity> type, LivingEntity shooter, World worldIn) {
        this(type, shooter.getX(), shooter.getEyeY() - (double)0.1F, shooter.getZ(), worldIn);
        this.setOwner(shooter);
    }

    public void setHitSound(SoundEvent soundIn) {
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
        this.entityData.define(CRITICAL, (byte)0);
        this.entityData.define(PIERCE_LEVEL, (byte)0);
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
        Vector3d vector3d = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
            this.yRot = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
            this.xRot = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
            this.yRotO = this.yRot;
            this.xRotO = this.xRot;
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level.getBlockState(blockpos);
        if (!blockstate.isAir(this.level, blockpos) && !flag) {
            VoxelShape voxelshape = blockstate.getBlockSupportShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                Vector3d vector3d1 = this.position();

                for(AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
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

        if (this.isInWaterOrRain()) {
            this.clearFire();
        }

        if (this.inGround && !flag) {
            if (this.inBlockState != blockstate && this.shouldFall()) {
                this.startFalling();
            } else if (!this.level.isClientSide) {
                this.tickDespawn();
            }

            ++this.timeInGround;
        } else {
            this.timeInGround = 0;
            Vector3d vector3d2 = this.position();
            Vector3d vector3d3 = vector3d2.add(vector3d);
            RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vector3d3 = raytraceresult.getLocation();
            }

            while(!this.removed) {
                EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vector3d2, vector3d3);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity entity1 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canHarmPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }

                if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
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
                for(int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.CRIT, this.getX() + d3 * (double)i / 4.0D, this.getY() + d4 * (double)i / 4.0D, this.getZ() + d0 * (double)i / 4.0D, -d3, -d4 + 0.2D, -d0);
                }
            }

            double d5 = this.getX() + d3;
            double d1 = this.getY() + d4;
            double d2 = this.getZ() + d0;
            float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
            if (flag) {
                this.yRot = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
            } else {
                this.yRot = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
            }

            this.xRot = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI));
            this.xRot = lerpRotation(this.xRotO, this.xRot);
            this.yRot = lerpRotation(this.yRotO, this.yRot);
            float f2 = 0.99F;
            float f3 = 0.05F;
            if (this.isInWater()) {
                for(int j = 0; j < 4; ++j) {
                    float f4 = 0.25F;
                    this.level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
                }

                f2 = this.getWaterDrag();
            }

            this.setDeltaMovement(vector3d.scale((double)f2));
            if (!this.isNoGravity() && !flag) {
                Vector3d vector3d4 = this.getDeltaMovement();
                this.setDeltaMovement(vector3d4.x, vector3d4.y - (double)0.05F, vector3d4.z);
            }

            this.setPos(d5, d1, d2);
            this.checkInsideBlocks();
        }
    }
    
    public static DamageSource causeSpikeDamage(AbstractSpikeEntity spike, @Nullable Entity indirectEntityIn) {
        return (new IndirectEntityDamageSource("spike", spike, indirectEntityIn)).setProjectile();
    }

    private boolean shouldFall() {
        return this.inGround && this.level.noCollision((new AxisAlignedBB(this.position(), this.position())).inflate(0.06D));
    }

    private void startFalling() {
        this.inGround = false;
        Vector3d vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.multiply((double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F), (double)(this.random.nextFloat() * 0.2F)));
        this.ticksInGround = 0;
    }

    public void move(MoverType typeIn, Vector3d pos) {
        super.move(typeIn, pos);
        if (typeIn != MoverType.SELF && this.shouldFall()) {
            this.startFalling();
        }

    }

    protected void tickDespawn() {
        ++this.ticksInGround;
        if (this.ticksInGround >= 1200) {
            this.remove();
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

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        Entity entity = p_213868_1_.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.damage, 0.0D, 2.147483647E9D));
        if (this.getPierceLevel() > 0) {
            if (this.piercedEntities == null) {
                this.piercedEntities = new IntOpenHashSet(5);
            }

            if (this.hitEntities == null) {
                this.hitEntities = Lists.newArrayListWithCapacity(5);
            }

            if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
                this.remove();
                return;
            }

            this.piercedEntities.add(entity.getId());
        }

        if (this.getIsCritical()) {
            long j = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = causeSpikeDamage(this, this);
        } else {
            damagesource = causeSpikeDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        if (entity.hurt(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                }

                if (this.knockbackStrength > 0) {
                    Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSqr() > 0.0D) {
                        livingentity.push(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.arrowHit(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingentity);
                }

                if (!this.level.isClientSide && entity1 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity1;
                    if (this.hitEntities != null && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, this.hitEntities);
                    } else if (!entity.isAlive() && this.getShotFromCrossbow()) {
                        CriteriaTriggers.KILLED_BY_CROSSBOW.trigger(serverplayerentity, Arrays.asList(entity));
                    }
                }
            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.yRot += 180.0F;
            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                this.remove();
            }
        }

    }

    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        this.inBlockState = this.level.getBlockState(p_230299_1_.getBlockPos());
        super.onHitBlock(p_230299_1_);
        Vector3d vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.playSound(this.getHitGroundSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.spikeShake = 7;
        this.setIsCritical(false);
        this.setPierceLevel((byte)0);
        this.setHitSound(SoundEvents.ARROW_HIT);
        this.setShotFromCrossbow(false);
        this.resetPiercedEntities();
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    protected SoundEvent getHitEntitySound() {
        return SoundEvents.ARROW_HIT;
    }

    protected final SoundEvent getHitGroundSound() {
        return this.hitSound;
    }

    protected void arrowHit(LivingEntity living) {
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
        return ProjectileHelper.getEntityHitResult(this.level, this, startVec, endVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    protected boolean canHitEntity(Entity p_230298_1_) {
        return super.canHitEntity(p_230298_1_) && (this.piercedEntities == null || !this.piercedEntities.contains(p_230298_1_.getId()));
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putShort("life", (short)this.ticksInGround);
        if (this.inBlockState != null) {
            compound.put("inBlockState", NBTUtil.writeBlockState(this.inBlockState));
        }

        compound.putByte("shake", (byte)this.spikeShake);
        compound.putBoolean("inGround", this.inGround);
        compound.putDouble("damage", this.damage);
        compound.putBoolean("crit", this.getIsCritical());
        compound.putByte("PierceLevel", this.getPierceLevel());
        compound.putString("SoundEvent", Registry.SOUND_EVENT.getKey(this.hitSound).toString());
        compound.putBoolean("ShotFromCrossbow", this.getShotFromCrossbow());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.ticksInGround = compound.getShort("life");
        if (compound.contains("inBlockState", 10)) {
            this.inBlockState = NBTUtil.readBlockState(compound.getCompound("inBlockState"));
        }

        this.spikeShake = compound.getByte("shake") & 255;
        this.inGround = compound.getBoolean("inGround");
        if (compound.contains("damage", 99)) {
            this.damage = compound.getDouble("damage");
        }

        this.setIsCritical(compound.getBoolean("crit"));
        this.setPierceLevel(compound.getByte("PierceLevel"));
        if (compound.contains("SoundEvent", 8)) {
            this.hitSound = Registry.SOUND_EVENT.getOptional(new ResourceLocation(compound.getString("SoundEvent"))).orElse(this.getHitEntitySound());
        }

        this.setShotFromCrossbow(compound.getBoolean("ShotFromCrossbow"));
    }

    public void setOwner(@Nullable Entity entityIn) {
        super.setOwner(entityIn);
    }

    protected boolean isMovementNoisy() {
        return false;
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

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
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
            this.entityData.set(CRITICAL, (byte)(b0 | p_203049_1_));
        } else {
            this.entityData.set(CRITICAL, (byte)(b0 & ~p_203049_1_));
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
        this.setDamage((double)(p_190547_2_ * 2.0F) + this.random.nextGaussian() * 0.25D + (double)((float)this.level.getDifficulty().getId() * 0.11F));
        if (i > 0) {
            this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
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
        if (!this.level.isClientSide) {
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

    public IPacket<?> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new SSpawnObjectPacket(this, entity == null ? 0 : entity.getId());
    }
}