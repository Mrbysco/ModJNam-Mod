package com.Mrbysco.CactusMod.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCactusGolem extends EntityIronGolem{

    private int attackTimer;

	public EntityCactusGolem(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onLivingUpdate() {
		if (this.attackTimer > 0)
        {
            --this.attackTimer;
        }
		
		super.onLivingUpdate();
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		super.collideWithEntity(entityIn);
		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!source.isExplosion() && !source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)source.getImmediateSource();
            entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
        }
        return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		attackTimer = 10;
        this.world.setEntityState(this, (byte)4);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(7)));

        if (flag)
        {
            entityIn.motionY += 0.4000000059604645D;
            this.applyEnchantments(this, entityIn);
        }

        this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
	}
	
	@Override
	public void setHoldingRose(boolean p_70851_1_) {}

	@SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 4)
        {
            this.attackTimer = 10;
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
	
	@Override
	protected ResourceLocation getLootTable() {
		return new ResourceLocation("cactusmod:loot_tables/entities/cactus_golem");
	}
}
