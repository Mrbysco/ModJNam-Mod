package com.Mrbysco.CactusMod.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.AI.EntityEatSand;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCactusSheep extends EntityAnimal implements net.minecraftforge.common.IShearable, ICactusMob
{
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.<Boolean>createKey(EntityCactusSheep.class, DataSerializers.BOOLEAN);

	private boolean sheepSheared;

    /**
     * Used to control movement as well as wool regrowth. Set to 40 on handleHealthUpdate and counts down with each
     * tick.
     */
    private int sheepTimer;
    private EntityEatSand entityAIEatSand;

    public EntityCactusSheep(World worldIn)
    {
        super(worldIn);
        this.setSize(0.9F, 1.3F);
    }
    
    @Override
    protected void initEntityAI()
    {
        this.entityAIEatSand = new EntityEatSand(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.WHEAT, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, this.entityAIEatSand);
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
    
    @Override
    protected void updateAITasks()
    {
        this.sheepTimer = this.entityAIEatSand.getEatingSandTimer();
        super.updateAITasks();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate()
    {
        if (this.world.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(SHEARED, Boolean.valueOf(false));
        super.entityInit();
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 10)
        {
            this.sheepTimer = 40;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }

    public static void registerFixesSheep(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityCactusSheep.class);
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationPointY(float rotY)
    {
        if (this.sheepTimer <= 0)
        {
            return 0.0F;
        }
        else if (this.sheepTimer >= 4 && this.sheepTimer <= 36)
        {
            return 1.0F;
        }
        else
        {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - rotY) / 4.0F : -((float)(this.sheepTimer - 40) - rotY) / 4.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float rotX)
    {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36)
        {
            float f = ((float)(this.sheepTimer - 4) - rotX) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Sheared", this.getSheared());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setSheared(compound.getBoolean("Sheared"));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }
    
    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared()
    {
        return (Boolean)this.dataManager.get(SHEARED).booleanValue();
    }

    public void setSheared(boolean sheared)
    {
    	if (sheared)
            this.dataManager.set(SHEARED, Boolean.valueOf(sheared));
        else
            this.dataManager.set(SHEARED, Boolean.valueOf(sheared));
    }

    @Override
    public EntityCactusSheep createChild(EntityAgeable ageable)
    {
        EntityCactusSheep EntityCactusSheep = (EntityCactusSheep)ageable;
        EntityCactusSheep EntityCactusSheep1 = new EntityCactusSheep(this.world);
        return EntityCactusSheep1;
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    @Override
    public void eatGrassBonus()
    {
        this.setSheared(false);

        if (this.isChild())
        {
            this.addGrowth(60);
        }
    }

    @Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
    { 
    	return !this.getSheared() && !this.isChild(); 
    }
    
    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        this.setSheared(true);
        int i = 1 + this.rand.nextInt(3);

        List<ItemStack> ret = new ArrayList<ItemStack>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(Item.getItemFromBlock(Blocks.CACTUS), 1));

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }

    @Override
    public float getEyeHeight()
    {
        return 0.95F * this.height;
    }
    
    @Override
	@Nullable
    protected ResourceLocation getLootTable()
    {
    	if(this.getSheared())
    		return new ResourceLocation(Reference.PREFIX + "entities/cactus_sheep");
    	else
    		return new ResourceLocation(Reference.PREFIX + "entities/cactus_sheep1");
    }
    
    @Override
	protected void collideWithEntity(Entity entityIn) {
    	if(!this.getSheared() && !(entityIn instanceof ICactusMob))
    		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
		
		super.collideWithEntity(entityIn);
	}
}