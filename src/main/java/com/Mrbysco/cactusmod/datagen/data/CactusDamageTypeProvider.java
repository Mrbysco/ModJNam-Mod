package com.mrbysco.cactusmod.datagen.data;

import com.mrbysco.cactusmod.init.CactusDamageTypes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;

public class CactusDamageTypeProvider {
	public static void bootstrap(BootstapContext<DamageType> context) {
		context.register(CactusDamageTypes.SPIKE, new DamageType("cactusmod.spike", 0.1F));
	}
}
