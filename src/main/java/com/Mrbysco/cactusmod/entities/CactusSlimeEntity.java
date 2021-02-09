package com.mrbysco.cactusmod.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.world.World;

public class CactusSlimeEntity extends SlimeEntity implements ICactusMob{
	
	public CactusSlimeEntity(EntityType<? extends SlimeEntity> type, World worldIn) {
        super(type, worldIn);
	}

    public static AttributeModifierMap.MutableAttribute func_234226_m_() {
        return MonsterEntity.func_234295_eP_();
    }
}