package com.mrbysco.cactusmod.feature;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class CactusFeatureConfig {
	public static void initialize() {}
	public static final ConfiguredFeature<?, ?> CACTUS_PLANT = FeatureUtils.register("cactus_plant", CactusRegistry.CACTUS_PLANT_FEATURE.get().configured(FeatureConfiguration.NONE));
}
