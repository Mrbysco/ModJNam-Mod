package com.mrbysco.cactusmod.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityActualSpike extends EntitySpike{
	public EntityActualSpike(World worldIn) {
		super(worldIn);
	}
	
	public EntityActualSpike(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }
	
	public EntityActualSpike(World worldIn, EntityLivingBase shooter)
    {
        this(worldIn, shooter.posX, shooter.posY + (double)shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
        this.shootingEntity = shooter;
    }
}
