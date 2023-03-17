package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.entities.CactoniEntity;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import com.mrbysco.cactusmod.entities.CactusGolem;
import com.mrbysco.cactusmod.entities.CactusPigEntity;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusCreeperEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;

public class CactusSpawns {

	public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
		event.register(CactusRegistry.CACTUS_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactusCowEntity::canAnimalSpawn, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_SNOW_GOLEM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactusSheepEntity::canAnimalSpawn, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CactusPigEntity::canAnimalSpawn, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_SPIDER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTUS_SKELETON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(CactusRegistry.CACTONI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
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
