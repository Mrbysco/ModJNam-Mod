package com.mrbysco.cactusmod.world;

import com.mrbysco.cactusmod.config.CactusConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class OverworldGen implements IWorldGenerator{
	public static OverworldGen INSTANCE = new OverworldGen();

    CactusPlantGenerator cactusGen;

    public OverworldGen()
    {
        this.cactusGen = new CactusPlantGenerator();
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world, false);
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world, boolean retroGen)
    {
        int xSpawn, ySpawn, zSpawn;

        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        BlockPos position;

        Biome biome = world.getChunk(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (biome == null)
        {
            return;
        }

        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
        {
            if (CactusConfig.worldgen.generateCactusPlant && random.nextInt(CactusConfig.worldgen.CactusPlantSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = CactusConfig.worldgen.seaLevel + random.nextInt(16);
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);
                this.cactusGen.generateCactus(random, world, position);
            }
        }
    }

}