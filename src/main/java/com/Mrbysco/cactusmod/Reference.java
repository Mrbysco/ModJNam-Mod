package com.mrbysco.cactusmod;

import com.mrbysco.cactusmod.init.CactusDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class Reference {
	public static final String MOD_ID = "cactusmod";

	public static DamageSource spikeDamage(Entity entity, Entity attacking) {
		return entity.damageSources().source(CactusDamageTypes.SPIKE, entity, attacking);
	}
}
