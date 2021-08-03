package com.mrbysco.cactusmod.feature;

import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class CactusFeatureConfig {
	public static final ConfiguredFeature<?, ?> CACTUS_PLANT = register("cactus_plant",
			CactusRegistry.CACTUS_PLANT_FEATURE.get().configured(FeatureConfiguration.NONE).decorated(Features.Decorators.HEIGHTMAP_SQUARE).countRandom(4));

	private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature) {
		return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Reference.MOD_ID, key), feature);
	}
}
