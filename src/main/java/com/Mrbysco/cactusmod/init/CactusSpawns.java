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
import com.mrbysco.cactusmod.feature.CactusFeatureConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CactusSpawns {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        RegistryKey<Biome> biomeKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        if(BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.SANDY) && !(BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.NETHER))) {
            if(CactusConfig.COMMON.cowSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Cow spawn");
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTUS_COW.get(), 8, 4, 4));
            }

            if(CactusConfig.COMMON.creeperSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Creeper spawn");
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTUS_CREEPER.get(),  100, 4, 4));
            }

            if(CactusConfig.COMMON.slimeSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Slime spawn");
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTUS_SLIME.get(), 100, 2, 2));
            }

            if(CactusConfig.COMMON.sheepSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Sheep spawn");
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTUS_SHEEP.get(), 12, 4, 4));
            }

            if(CactusConfig.COMMON.pigSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Pig spawn");
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTUS_PIG.get(), 10, 4, 4));
            }

            if(CactusConfig.COMMON.spiderSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Spider spawn");
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTUS_SPIDER.get(), 100, 4, 4));
            }

            if(CactusConfig.COMMON.skeletonSpawn.get()) {
                CactusMod.logger.debug("Registering Cactus Skeleton spawn");
                event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTUS_SKELETON.get(), 100, 4, 4));
            }

            if(CactusConfig.COMMON.cactoniSpawn.get()) {
                CactusMod.logger.debug("Registering Cactoni spawn");
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(CactusRegistry.CACTONI.get(), 1, 2, 2));
            }

            if(CactusConfig.COMMON.generateCactusPlant.get()) {
                CactusMod.logger.debug("Registering Cactus Plant generation");
                BiomeGenerationSettingsBuilder builder = event.getGeneration();
                if(!builder.getFeatures(Decoration.VEGETAL_DECORATION).contains(CactusFeatureConfig.CACTUS_PLANT)) {
                    builder.withFeature(Decoration.VEGETAL_DECORATION, CactusFeatureConfig.CACTUS_PLANT.withPlacement(Placement.CHANCE.configure(new ChanceConfig(CactusConfig.COMMON.cactusPlantRarity.get()))));
                }
            }
        }
    }

    public static void entityAttributes() {
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_GOLEM.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_COW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CactusCowEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_CREEPER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_SNOW_GOLEM.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_SLIME.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_SHEEP.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CactusSheepEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_PIG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CactusPigEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_SPIDER.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTUS_SKELETON.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight);
        EntitySpawnPlacementRegistry.register(CactusRegistry.CACTONI.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canSpawnOn);

        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_GOLEM.get(), CactusGolem.createAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_COW.get(), CactusCowEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_CREEPER.get(), CactusCreeperEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_SNOW_GOLEM.get(), CactusSnowGolemEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_SLIME.get(), MonsterEntity.func_234295_eP_().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_SHEEP.get(), CactusSheepEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_PIG.get(), CactusPigEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_SPIDER.get(), CactusSpiderEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTUS_SKELETON.get(), AbstractSkeletonEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(CactusRegistry.CACTONI.get(), CactoniEntity.createAttributes().create());
    }
}
