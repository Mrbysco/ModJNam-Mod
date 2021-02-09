package com.mrbysco.cactusmod.feature;

import com.mojang.serialization.Codec;
import com.mrbysco.cactusmod.blocks.plant.CactusFlowerBlock;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class CactusPlantFeature extends Feature<NoFeatureConfig> {
	public CactusPlantFeature(Codec<NoFeatureConfig> p_i231936_1_) {
		super(p_i231936_1_);
	}

	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		if (reader.isAirBlock(pos) && reader.getBlockState(pos.down()).isIn(TagCollectionManager.getManager().getBlockTags().get(new ResourceLocation("sand")))) {
			CactusFlowerBlock.generatePlant(reader, pos, rand, 8);
			return true;
		} else {
			return false;
		}
	}
}