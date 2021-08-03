package com.mrbysco.cactusmod.entities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;

public class CactusGolem extends IronGolem implements ICactusMob{

	public CactusGolem(EntityType<? extends CactusGolem> type, Level worldIn) {
        super(type, worldIn);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_DAMAGE, 15.0D);
	}

	@Override
	protected void doPush(Entity entityIn) {
		super.doPush(entityIn);
		if(!(entityIn instanceof ICactusMob))
			entityIn.hurt(DamageSource.CACTUS, 1.0F);
	}

    @Override
    public void offerFlower(boolean holdingRose) {
    }
}
