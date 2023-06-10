package com.mrbysco.cactusmod.feature;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class CactusConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> CACTUS_PLANT = FeatureUtils.createKey("cactusmod:cactus_plant");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		FeatureUtils.register(context, CACTUS_PLANT, CactusRegistry.CACTUS_PLANT_FEATURE.get(), FeatureConfiguration.NONE);
	}
}
