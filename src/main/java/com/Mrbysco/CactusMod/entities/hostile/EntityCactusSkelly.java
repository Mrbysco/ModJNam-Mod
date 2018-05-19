package com.Mrbysco.CactusMod.entities.hostile;

import javax.annotation.Nullable;

import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.EntityActualSpike;
import com.Mrbysco.CactusMod.entities.EntitySpike;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityCactusSkelly extends AbstractCactusSkelly{

	public EntityCactusSkelly(World worldIn)
    {
        super(worldIn);
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
		return new ResourceLocation(Reference.PREFIX + "entities/cactus_skeleton");
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (cause.getTrueSource() instanceof EntityCreeper)
        {
            EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();

            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop())
            {
                entitycreeper.incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F);
            }
        }
    }
    
    @Override
    protected EntitySpike getSpike(float p_190726_1_)
    {
    	EntitySpike spike = new EntityActualSpike(this.world, this);
        return spike;
    }
}
