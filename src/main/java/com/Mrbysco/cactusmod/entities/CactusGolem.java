package com.mrbysco.cactusmod.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class CactusGolem extends IronGolemEntity implements ICactusMob{

	public CactusGolem(EntityType<? extends CactusGolem> type, World worldIn) {
        super(type, worldIn);
	}

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).add(Attributes.ATTACK_DAMAGE, 15.0D);
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
