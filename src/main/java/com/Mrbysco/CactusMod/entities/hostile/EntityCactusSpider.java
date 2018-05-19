package com.Mrbysco.CactusMod.entities.hostile;

import javax.annotation.Nullable;

import com.Mrbysco.CactusMod.entities.ICactusMob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCactusSpider extends EntitySpider implements ICactusMob{
    private static final DataParameter<Integer> SPIDER_SIZE = EntityDataManager.<Integer>createKey(EntityCactusSpider.class, DataSerializers.VARINT);

	public EntityCactusSpider(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();  
		this.dataManager.register(SPIDER_SIZE, Integer.valueOf(1));
	}
	
	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (SPIDER_SIZE.equals(key))
        {
            int i = this.getSpiderSize();
            if(i < 4)
            {
                this.setSize(0.65F, 0.65F);
            }
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;

            if (this.isInWater() && this.rand.nextInt(20) == 0)
            {
                this.doWaterSplashEffect();
            }
        }

        super.notifyDataManagerChange(key);
	}
	
	public void setSpiderSize(int size, boolean resetHealth) {
		this.dataManager.set(SPIDER_SIZE, Integer.valueOf(size));

        if(size == 4)
        {
            this.setSize(1.4F, 0.9F);

        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        }
        else
        {
            this.setSize(0.65F, 0.35F);

        	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(4));
        	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.375D);
        }
        
        this.setPosition(this.posX, this.posY, this.posZ); 

        if (resetHealth)
        {
            this.setHealth(this.getMaxHealth());
        }

        this.experienceValue = size;
	}
	
	public int getSpiderSize()
    {
        return ((Integer)this.dataManager.get(SPIDER_SIZE)).intValue();
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
        compound.setInteger("Size", this.getSpiderSize() - 1);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		int i = compound.getInteger("Size");

        if (i < 0)
        {
            i = 0;
        }

        this.setSpiderSize(i + 1, false);
		super.readEntityFromNBT(compound);
	}
	
	public boolean isSmallSpider() {
        return this.getSpiderSize() <= 4;
	}
	
	@Override
	public void onUpdate() {
		if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.getSpiderSize() > 0)
        {
            this.isDead = true;
        }		
		
		super.onUpdate();
	}
	
	protected EntityCactusSpider createInstance()
    {
        return new EntityCactusSpider(this.world);
    }
	
	@Override
	public void setDead() {
		int i = this.getSpiderSize();

        if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0F)
        {
            int j = 2 + this.rand.nextInt(3);

            for (int k = 0; k < j; ++k)
            {
                float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
                float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
                EntityCactusSpider entityspider = this.createInstance();

                if (this.hasCustomName())
                {
                	entityspider.setCustomNameTag(this.getCustomNameTag());
                }

                if (this.isNoDespawnRequired())
                {
                	entityspider.enablePersistence();
                }

                entityspider.setSize(0.35F, 0.25F);
                entityspider.setSpiderSize(1, true);
                entityspider.setLocationAndAngles(this.posX + (double)f, this.posY + 1D, this.posZ + (double)f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(entityspider);
            }
        }

        super.setDead();
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        this.setSpiderSize(4, true);
        return super.onInitialSpawn(difficulty, livingdata);
	}
	
	@Override
	@Nullable
    protected ResourceLocation getLootTable()
    {
		return new ResourceLocation("cactusmod:entities/cactus_spider");
    }
	
	@Override
	protected void collideWithEntity(Entity entityIn) {
		super.collideWithEntity(entityIn);
		if(!(entityIn instanceof ICactusMob))
			entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
}
