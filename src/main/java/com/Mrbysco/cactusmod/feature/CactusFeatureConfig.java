package com.mrbysco.cactusmod.feature;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CactusFeatureConfig {
	public static void initialize() {
	}

	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CACTUS_PLANT = FeatureUtils.register("cactus_plant", CactusRegistry.CACTUS_PLANT_FEATURE.get(), FeatureConfiguration.NONE);
}
