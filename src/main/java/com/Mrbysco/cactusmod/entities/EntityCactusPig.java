package com.mrbysco.cactusmod.entities;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EntityCactusPig extends EntityPig implements ICactusMob{

	public EntityCactusPig(World worldIn) {
		super(worldIn);
	}
	
	public EntityCactusPig createChild(EntityAgeable ageable)
    {
        return new EntityCactusPig(this.world);
    }

	@Override
	@Nullable
    protected ResourceLocation getLootTable()
    {
		return new ResourceLocation(Reference.PREFIX + "entities/cactus_pig");
    }
}
