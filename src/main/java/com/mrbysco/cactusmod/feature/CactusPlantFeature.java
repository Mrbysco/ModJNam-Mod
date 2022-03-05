package com.mrbysco.cactusmod.feature;

import com.mojang.serialization.Codec;
import com.mrbysco.cactusmod.blocks.plant.CactusFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class CactusPlantFeature extends Feature<NoneFeatureConfiguration> {
	public CactusPlantFeature(Codec<NoneFeatureConfiguration> configurationCodec) {
		super(configurationCodec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel level = featurePlaceContext.level();
		BlockPos pos = featurePlaceContext.origin();
		Random random = featurePlaceContext.random();
		if (level.isEmptyBlock(pos) && level.getBlockState(pos.below()).is(Blocks.SAND)) {
			CactusFlowerBlock.generatePlant(level, pos, random, 8);
			return true;
		} else {
			return false;
		}
	}
}