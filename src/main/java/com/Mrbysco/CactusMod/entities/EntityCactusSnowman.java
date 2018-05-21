package com.Mrbysco.CactusMod.entities;

import javax.annotation.Nullable;

import com.Mrbysco.CactusMod.Reference;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class EntityCactusSnowman extends EntityGolem implements IRangedAttackMob, IShearable, ICactusMob{
    private static final DataParameter<Byte> CACTUS_EQUIPPED = EntityDataManager.<Byte>createKey(EntitySnowman.class, DataSerializers.BYTE);

	public EntityCactusSnowman(World worldIn) {
		super(worldIn);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntitySpike spike = new EntityActualSpike(this.world, this);
		spike.setDamage(1);
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - spike.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        spike.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(spike);
	}
	
	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return new ResourceLocation(Reference.PREFIX + "entities/cactus_snowman");
	}

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.25D, 20, 10.0F));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1.0D, 1.0000001E-5F));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, true, false, IMob.MOB_SELECTOR));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CACTUS_EQUIPPED, Byte.valueOf((byte)16));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Cactus", this.isCactusEquipped());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        if (compound.hasKey("Cactus"))
        {
            this.setCactusEquipped(compound.getBoolean("Cactus"));
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.world.isRemote)
        {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);

            if (this.isWet())
            {
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            }
        }
    }

    @Override
    public float getEyeHeight()
    {
        return 1.7F;
    }

    public boolean isCactusEquipped()
    {
        return (((Byte)this.dataManager.get(CACTUS_EQUIPPED)).byteValue() & 16) != 0;
    }

    public void setCactusEquipped(boolean CactusEquipped)
    {
        byte b0 = ((Byte)this.dataManager.get(CACTUS_EQUIPPED)).byteValue();

        if (CactusEquipped)
        {
            this.dataManager.set(CACTUS_EQUIPPED, Byte.valueOf((byte)(b0 | 16)));
        }
        else
        {
            this.dataManager.set(CACTUS_EQUIPPED, Byte.valueOf((byte)(b0 & -17)));
        }
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SNOWMAN_AMBIENT;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SNOWMAN_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SNOWMAN_DEATH;
    }

    @Override public boolean isShearable(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos) { return this.isCactusEquipped(); }
    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        this.setCactusEquipped(false);
        return com.google.common.collect.Lists.newArrayList();
    }

	@Override
	public void setSwingingArms(boolean swingingArms) {}
}
