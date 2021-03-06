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
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(CactusTNTEntity.class, DataSerializers.VARINT);
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 80;

    public CactusTNTEntity(EntityType<? extends CactusTNTEntity> type, World worldIn) {
        super(type, worldIn);
        this.preventEntitySpawning = true;
    }

    public CactusTNTEntity(FMLPlayMessages.SpawnEntity spawnEntity, World worldIn) {
        this(CactusRegistry.CACTUS_TNT_ENTITY.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public CactusTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(CactusRegistry.CACTUS_TNT_ENTITY.get(), worldIn);
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.tntPlacedBy = igniter;
    }
    
    @Override
    protected void registerData()
    {
        this.dataManager.register(FUSE, Integer.valueOf(80));
    }
    
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.explode();
            }
        } else {
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private void explode() {
        float f = 4.0F;
        ExplosionHelper.arrowExplosion(this, this.getPosX(), this.getPosY() + (double)(this.getHeight() / 16.0F), this.getPosZ(), f, true);
    }

    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    protected void readAdditional(CompoundNBT compound) {
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
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }
    }

    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }
}