package com.mrbysco.cactusmod.feature;

import com.mrbysco.cactusmod.config.CactusConfig;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class CactusPlacements {
	public static final PlacedFeature CACTUS_PLANT = PlacementUtils.register("chorus_plant", CactusFeatureConfig.CACTUS_PLANT.placed(RarityFilter.onAverageOnceEvery(CactusConfig.COMMON.cactusPlantRarity.get()), CountPlacement.of(UniformInt.of(0, 4)), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
}
