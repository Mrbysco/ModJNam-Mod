package com.Mrbysco.CactusMod.world;

import java.util.Random;

import com.Mrbysco.CactusMod.blocks.plant.BlockCactusFlower;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class CactusPlantGenerator implements IWorldGenerator
{
    private Random rand;

    private World world;

    public CactusPlantGenerator()
    {
    	
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {

    }

	public void generateCactus(Random random, World worldIn, BlockPos position) {
		this.world = worldIn;
        this.rand = random;

		BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

        if (worldIn.isAirBlock(blockpos))
        {
            int j = 1 + rand.nextInt(rand.nextInt(3) + 1);

            for (int k = 0; k < j; ++k)
            {
            	if (this.world.isAirBlock(position) && this.world.getBlockState(position.down()).getBlock() == Blocks.SAND)
                {
                    BlockCactusFlower.generatePlant(this.world, position, this.rand, 5);
                }
            }
        }
		
	}
}