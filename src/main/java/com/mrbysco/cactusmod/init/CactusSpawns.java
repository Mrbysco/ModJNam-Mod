package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.CactusMod;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.config.CactusConfig;
import com.mrbysco.cactusmod.entities.CactoniEntity;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import com.mrbysco.cactusmod.entities.CactusGolem;
import com.mrbysco.cactusmod.entities.CactusPigEntity;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusCreeperEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import com.mrbysco.cactusmod.feature.CactusPlacements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CactusSpawns {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
        if(BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.SANDY) && !(BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.NETHER))) {
            if(CactusConfig.COMMON.cowSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Cow spawn");
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_COW.get(), 8, 4, 4));
            }

            if(CactusConfig.COMMON.creeperSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Creeper spawn");
                event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_CREEPER.get(),  100, 4, 4));
            }

            if(CactusConfig.COMMON.slimeSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Slime spawn");
                event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SLIME.get(), 100, 2, 2));
            }

            if(CactusConfig.COMMON.sheepSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Sheep spawn");
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SHEEP.get(), 12, 4, 4));
            }

            if(CactusConfig.COMMON.pigSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Pig spawn");
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_PIG.get(), 10, 4, 4));
            }

            if(CactusConfig.COMMON.spiderSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Spider spawn");
                event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SPIDER.get(), 100, 4, 4));
            }

            if(CactusConfig.COMMON.skeletonSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Skeleton spawn");
                event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTUS_SKELETON.get(), 100, 4, 4));
            }

            if(CactusConfig.COMMON.cactoniSpawn.get()) {
                CactusMod.logger.debug("Registering Cactoni spawn");
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(CactusRegistry.CACTONI.get(), 1, 2, 2));
            }

            if(CactusConfig.COMMON.generateCactusPlant.get()) {
                CactusMod.logger.debug("Registering Cactus Plant generation");
                BiomeGenerationSettingsBuilder builder = event.getGeneration();
                if(!builder.getFeatures(Decoration.VEGETAL_DECORATION).contains(CactusPlacements.CACTUS_PLANT)) {
                    builder.addFeature(Decoration.VEGETAL_DECORATION, CactusPlacements.CACTUS_PLANT);
                }
            }
        }
    }

    public static void entityAttributes() {
        SpawnPlacements.register(CactusRegistry.CACTUS_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(CactusRegistry.CACTUS_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactusCowEntity::canAnimalSpawn);
        SpawnPlacements.register(CactusRegistry.CACTUS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(CactusRegistry.CACTUS_SNOW_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(CactusRegistry.CACTUS_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(CactusRegistry.CACTUS_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactusSheepEntity::canAnimalSpawn);
        SpawnPlacements.register(CactusRegistry.CACTUS_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactusPigEntity::canAnimalSpawn);
        SpawnPlacements.register(CactusRegistry.CACTUS_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(CactusRegistry.CACTUS_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(CactusRegistry.CACTONI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }


    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(CactusRegistry.CACTUS_GOLEM.get(), CactusGolem.createAttributes().build());
        event.put(CactusRegistry.CACTUS_COW.get(), CactusCowEntity.createAttributes().build());
        event.put(CactusRegistry.CACTUS_CREEPER.get(), CactusCreeperEntity.createAttributes().build());
        event.put(CactusRegistry.CACTUS_SNOW_GOLEM.get(), CactusSnowGolemEntity.createAttributes().build());
        event.put(CactusRegistry.CACTUS_SLIME.get(), Monster.createMonsterAttributes().build());
        event.put(CactusRegistry.CACTUS_SHEEP.get(), CactusSheepEntity.createAttributes().build());
        event.put(CactusRegistry.CACTUS_PIG.get(), CactusPigEntity.createAttributes().build());
        event.put(CactusRegistry.CACTUS_SPIDER.get(), CactusSpiderEntity.createAttributes().build());
        event.put(CactusRegistry.CACTUS_SKELETON.get(), AbstractSkeleton.createAttributes().build());
        event.put(CactusRegistry.CACTONI.get(), CactoniEntity.createAttributes().build());
    }
}
