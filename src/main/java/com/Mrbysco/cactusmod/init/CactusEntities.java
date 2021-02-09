package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.entities.CactiCartEntity;
import com.mrbysco.cactusmod.entities.CactoniEntity;
import com.mrbysco.cactusmod.entities.CactusBoatEntity;
import com.mrbysco.cactusmod.entities.CactusCowEntity;
import com.mrbysco.cactusmod.entities.CactusGolem;
import com.mrbysco.cactusmod.entities.CactusPigEntity;
import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import com.mrbysco.cactusmod.entities.CactusSlimeEntity;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.entities.CactusTNTEntity;
import com.mrbysco.cactusmod.entities.SpikeEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusCreeperEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusSkeletonEntity;
import com.mrbysco.cactusmod.entities.hostile.CactusSpiderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class CactusEntities {

	static int ID = 0;
	
	public static void register()
	{
		registerEntity("cactus_golem", CactusGolem.class, "cactusgolem", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_cow", CactusCowEntity.class, "cactuscow", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_cart", CactiCartEntity.class, "cactuscart", 80, 3, true);
		registerEntity("cactus_tnt", CactusTNTEntity.class, "cactustnt", 80, 3, true);
		registerEntity("cactus_spike", SpikeEntity.class, "cactusspike", 80, 3, true);
		registerEntity("cactus_creeper", CactusCreeperEntity.class, "cactuscreeper", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_snowman", CactusSnowGolemEntity.class, "cactussnowman", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_slime", CactusSlimeEntity.class, "cactusslime", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_sheep", CactusSheepEntity.class, "cactussheep", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_pig", CactusPigEntity.class, "cactuspig", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_spider", CactusSpiderEntity.class, "cactusspider", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_skeleton", CactusSkeletonEntity.class, "cactusskeleton", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_boat", CactusBoatEntity.class, "cactusboat", 80, 3, true);
		registerEntity("cactoni", CactoniEntity.class, "cactoni", 80, 3, true, 0xFF0f751b, 0xFF89132f);
	}

	public static void registerEntity(String registryName, Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) 
	{
//		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, registryName), entityClass, Reference.PREFIX + entityName, ID, CactusMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
		ID++;
	}
	
	public static void registerEntity(String registryName, Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) 
	{
//		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, registryName), entityClass, Reference.PREFIX + entityName, ID, CactusMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		ID++;
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileentityClass, String tilename) {
//		GameRegistry.registerTileEntity(tileentityClass, Reference.MOD_ID + tilename);
	}
}
