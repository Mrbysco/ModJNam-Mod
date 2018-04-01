package com.Mrbysco.CactusMod.init;

import com.Mrbysco.CactusMod.CactusMod;
import com.Mrbysco.CactusMod.Reference;
import com.Mrbysco.CactusMod.entities.EntityCactusCow;
import com.Mrbysco.CactusMod.entities.EntityCactusGolem;
import com.Mrbysco.CactusMod.tileentities.TileEntityCactusChest;
import com.Mrbysco.CactusMod.tileentities.TileEntityCactusHopper;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CactusEntities {

	static int ID = 0;
	
	public static void register()
	{
		registerEntity("cactus_golem", EntityCactusGolem.class, "cactusgolem", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_cow", EntityCactusCow.class, "cactuscow", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		
		registerTileEntity(TileEntityCactusChest.class, "_cactus_chest");
		registerTileEntity(TileEntityCactusHopper.class, "_cactus_hopper");
	}

	public static void registerEntity(String registryName, Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary) 
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, registryName), entityClass, Reference.PREFIX + entityName, ID, CactusMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
		ID++;
	}
	
	public static void registerEntity(String registryName, Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) 
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, registryName), entityClass, Reference.PREFIX + entityName, ID, CactusMod.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		ID++;
	}
	
	public static void registerTileEntity(Class<? extends TileEntity> tileentityClass, String tilename) {
		GameRegistry.registerTileEntity(tileentityClass, Reference.MOD_ID + tilename);
	}
}
