package com.Mrbysco.CactusMod.entities.hostile;

import java.util.Collection;
import java.util.List;

import com.Mrbysco.CactusMod.entities.EntityActualSpike;
import com.Mrbysco.CactusMod.entities.EntitySpike;
import com.Mrbysco.CactusMod.entities.ICactusMob;
import com.google.common.collect.Maps;

import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityCactusCreeper extends EntityCreeper implements ICactusMob{
	
    private int explosionRadius = 2;
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    
	public EntityCactusCreeper(World worldIn) {
		super(worldIn);
	}

	@Override
	public void onUpdate() {
		if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.hasIgnited())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;
                this.setDead();
                
                this.explode();
            }
        }
		
		super.onUpdate();
	}
	
	private void explode()
    {
        if (!this.world.isRemote)
        {
        	this.arrowExplosionB();
            this.arrowExplosion(this.posX, this.posY + (double)(this.height / 16.0F), this.posZ);
        }
    }
	
	public void arrowExplosion(double x, double y, double z)
    {
    	this.world.playSound((EntityPlayer)null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, x, y, z, 1.0D, 0.0D, 0.0D);
        
        for(int i = 0; i <= 20; i++)
        {
        	EntitySpike spike = new EntityActualSpike(world);
        	spike.setDamage(4);
        	
        	spike.motionX = (rand.nextDouble() * 6D - 3D) * 0.3D;
        	spike.motionZ = (rand.nextDouble() * 6D - 3D) * 0.3D;
        	
        	spike.setPosition(x, y + 0.85D, z);
        	
        	world.spawnEntity(spike);
        }
    }

    public void arrowExplosionB()
    {
    	float f3 = 4.0F * 2.0F;
        int k1 = MathHelper.floor(this.posX - (double)f3 - 1.0D);
        int l1 = MathHelper.floor(this.posX + (double)f3 + 1.0D);
        int i2 = MathHelper.floor(this.posY - (double)f3 - 1.0D);
        int i1 = MathHelper.floor(this.posY + (double)f3 + 1.0D);
        int j2 = MathHelper.floor(this.posZ - (double)f3 - 1.0D);
        int j1 = MathHelper.floor(this.posZ + (double)f3 + 1.0D);
    	List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);

        for (int k2 = 0; k2 < list.size(); ++k2)
        {
            Entity entity = list.get(k2);

            if (!entity.isImmuneToExplosions())
            {
                double d12 = entity.getDistance(this.posX, this.posY, this.posZ) / (double)f3;

                if (d12 <= 1.0D)
                {
                    double d5 = entity.posX - this.posX;
                    double d7 = entity.posY + (double)entity.getEyeHeight() - this.posY;
                    double d9 = entity.posZ - this.posZ;
                    double d13 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

                    if (d13 != 0.0D)
                    {
                        d5 = d5 / d13;
                        d7 = d7 / d13;
                        d9 = d9 / d13;
                        double d14 = (double)this.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
                        double d10 = (1.0D - d12) * d14;
                        entity.attackEntityFrom(DamageSource.causeThornsDamage(this), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D)));
                        double d11 = d10;

                        if (entity instanceof EntityLivingBase)
                            d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, d10);

                        entity.motionX += d5 * d11;
                        entity.motionY += d7 * d11;
                        entity.motionZ += d9 * d11;

                        if (entity instanceof EntityPlayer)
                        {
                            EntityPlayer entityplayer = (EntityPlayer)entity;

                            if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying))
                            	Maps.<EntityPlayer, Vec3d>newHashMap().put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
                        }
                    }
                }
            }
        }
    }
	
	private void spawnLingeringCloud()
    {
        Collection<PotionEffect> collection = this.getActivePotionEffects();

        if (!collection.isEmpty())
        {
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            entityareaeffectcloud.setRadius(2.5F);
            entityareaeffectcloud.setRadiusOnUse(-0.5F);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

            for (PotionEffect potioneffect : collection)
            {
                entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
            }

            this.world.spawnEntity(entityareaeffectcloud);
        }
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        
		super.writeEntityToNBT(compound);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		
		if (compound.hasKey("Fuse", 99))
        {
            this.fuseTime = compound.getShort("Fuse");
        }
		
		if (compound.hasKey("ExplosionRadius", 99))
        {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }
	}
	
	@Override
	public float getCreeperFlashIntensity(float p_70831_1_) {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (float)(this.fuseTime - 2);
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {
		super.fall(distance, damageMultiplier);
        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + distance * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
	}
}
