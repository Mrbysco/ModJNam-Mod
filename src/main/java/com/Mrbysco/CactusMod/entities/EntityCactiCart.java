package com.Mrbysco.CactusMod.entities;

import java.util.List;

import com.Mrbysco.CactusMod.init.CactusItems;

import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityCactiCart extends EntityMinecartEmpty implements ICactusMob{

	private static final DataParameter<Integer> ROLLING_AMPLITUDE = EntityDataManager.<Integer>createKey(EntityCactiCart.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ROLLING_DIRECTION = EntityDataManager.<Integer>createKey(EntityCactiCart.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.<Float>createKey(EntityCactiCart.class, DataSerializers.FLOAT);

    private int timeInCart;

	public EntityCactiCart(World worldIn) {
		super(worldIn);
	}
	
	public EntityCactiCart(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
	
	@Override
	protected void entityInit()
    {
        this.dataManager.register(ROLLING_AMPLITUDE, Integer.valueOf(0));
        this.dataManager.register(ROLLING_DIRECTION, Integer.valueOf(1));
        this.dataManager.register(DAMAGE, Float.valueOf(0.0F));
    }
	
	@Override
	public String getName() {
		return "CactusMinecart";
	}
	
	@Override
	public boolean hasDisplayTile() {
		return false;
	}
	@Override
	public boolean canBeRidden() {
		return true;
	}
	
	@Override
	public boolean isPoweredCart() {
		return false;
	}
	
	@Override
	public Type getType() {
		return Type.RIDEABLE;
	}
	
	@Override
	public float getMaxCartSpeedOnRail()
    {
        return 1.75f;
    }
	
	@Override
	public float getDamage()
    {
        return ((Float)this.dataManager.get(DAMAGE)).floatValue();
    }

	@Override
    public void setRollingAmplitude(int rollingAmplitude)
    {
        this.dataManager.set(ROLLING_AMPLITUDE, Integer.valueOf(rollingAmplitude));
    }

	@Override
    public int getRollingAmplitude()
    {
        return ((Integer)this.dataManager.get(ROLLING_AMPLITUDE)).intValue();
    }

	@Override
    public void setRollingDirection(int rollingDirection)
    {
        this.dataManager.set(ROLLING_DIRECTION, Integer.valueOf(rollingDirection));
    }

	@Override
    public int getRollingDirection()
    {
        return ((Integer)this.dataManager.get(ROLLING_DIRECTION)).intValue();
    }

	@Override
	public void setDamage(float damage) {
		this.dataManager.set(DAMAGE, Float.valueOf(damage));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		AxisAlignedBB box;
        if (getCollisionHandler() != null) 
        	box = getCollisionHandler().getMinecartCollisionBox(this);
        else                               
        	box = this.getEntityBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D);
		
		if (canBeRidden() && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D)
        {
            List<Entity> list = this.world.getEntitiesInAABBexcluding(this, box, EntitySelectors.getTeamCollisionPredicate(this));

            if (!list.isEmpty())
            {
                for (int j1 = 0; j1 < list.size(); ++j1)
                {
                    Entity entity1 = list.get(j1);

                    if (!(entity1 instanceof EntityPlayer) && !(entity1 instanceof EntityIronGolem) && !(entity1 instanceof EntityCactiCart) && !this.isBeingRidden() && !entity1.isRiding())
                    {
                        entity1.startRiding(this);
                    }
                    else
                    {
                        entity1.applyEntityCollision(this);
                    }
                }
            }
        }
        else
        {
            for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, box))
            {
                if (!this.isPassenger(entity) && entity.canBePushed() && entity instanceof EntityCactiCart)
                {
                    entity.applyEntityCollision(this);
                }
            }
        }

        if(!this.world.isRemote)
		{
			if(this.isBeingRidden())
			{
				Entity entity = this.getPassengers().get(0);
				if(entity instanceof EntityLivingBase)
				{
					++this.timeInCart;
					if(this.timeInCart >= 40)
					{
						entity.attackEntityFrom(DamageSource.CACTUS, 1.0F);
						this.timeInCart = 0;
					}
				}
			}
			else
			{
				if(this.timeInCart > 0)
					this.timeInCart = 0;
			}
		}
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (super.processInitialInteract(player, hand)) return true;

        if (player.isSneaking())
        {
            return false;
        }
        else if (this.isBeingRidden())
        {
            return true;
        }
        else
        {
            if (!this.world.isRemote)
            {
                player.startRiding(this);
            }

            return true;
        }
	}
	
	private BlockPos getCurrentRailPosition()
    {
        int x = MathHelper.floor(this.posX);
        int y = MathHelper.floor(this.posY);
        int z = MathHelper.floor(this.posZ);

        if (BlockRailBase.isRailBlock(this.world, new BlockPos(x, y - 1, z))) y--;
        return new BlockPos(x, y, z);
    }
	
	@Override
	public void killMinecart(DamageSource source) {
		this.setDead();

        if (this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            ItemStack itemstack = new ItemStack(CactusItems.cactus_cart, 1);

            if (this.hasCustomName())
            {
                itemstack.setStackDisplayName(this.getCustomNameTag());
            }

            this.entityDropItem(itemstack, 0.0F);
        }
	}
}