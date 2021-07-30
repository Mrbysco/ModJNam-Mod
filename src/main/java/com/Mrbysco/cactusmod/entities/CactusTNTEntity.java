package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.init.CactusRegistry;
import com.mrbysco.cactusmod.util.ExplosionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class CactusTNTEntity extends Entity {
    private static final DataParameter<Integer> FUSE = EntityDataManager.defineId(CactusTNTEntity.class, DataSerializers.INT);
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 80;

    public CactusTNTEntity(EntityType<? extends CactusTNTEntity> type, World worldIn) {
        super(type, worldIn);
        this.blocksBuilding = true;
    }

    public CactusTNTEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(CactusRegistry.CACTUS_TNT_ENTITY.get(), worldIn);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public CactusTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(CactusRegistry.CACTUS_TNT_ENTITY.get(), worldIn);
        this.setPos(x, y, z);
        double d0 = worldIn.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.tntPlacedBy = igniter;
    }
    
    @Override
    protected void defineSynchedData()
    {
        this.entityData.define(FUSE, Integer.valueOf(80));
    }
    
    @Override
    protected boolean isMovementNoisy()
    {
        return false;
    }
    
    @Override
    public boolean isPickable() {
        return !this.removed;
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
            this.remove();
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
        ExplosionHelper.arrowExplosion(this, this.getX(), this.getY() + (double)(this.getBbHeight() / 16.0F), this.getZ(), f, true);
    }

    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    protected void readAdditionalSaveData(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }

    public void setFuse(int fuseIn) {
        this.entityData.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void onSyncedDataUpdated(DataParameter<?> key) {
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