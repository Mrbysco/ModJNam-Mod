package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.CactusMod;
import com.mrbysco.cactusmod.Reference;
import com.mrbysco.cactusmod.entities.EntityActualSpike;
import com.mrbysco.cactusmod.entities.EntityCactiCart;
import com.mrbysco.cactusmod.entities.EntityCactoni;
import com.mrbysco.cactusmod.entities.EntityCactusBoat;
import com.mrbysco.cactusmod.entities.EntityCactusCow;
import com.mrbysco.cactusmod.entities.EntityCactusGolem;
import com.mrbysco.cactusmod.entities.EntityCactusPig;
import com.mrbysco.cactusmod.entities.EntityCactusSheep;
import com.mrbysco.cactusmod.entities.EntityCactusSlime;
import com.mrbysco.cactusmod.entities.EntityCactusSnowman;
import com.mrbysco.cactusmod.entities.EntityCactusTnt;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusCreeper;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusSkelly;
import com.mrbysco.cactusmod.entities.hostile.EntityCactusSpider;
import com.mrbysco.cactusmod.tileentities.TileEntityCactusChest;
import com.mrbysco.cactusmod.tileentities.TileEntityCactusHopper;
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
		registerEntity("cactus_cart", EntityCactiCart.class, "cactuscart", 80, 3, true);
		registerEntity("cactus_tnt", EntityCactusTnt.class, "cactustnt", 80, 3, true);
		registerEntity("cactus_spike", EntityActualSpike.class, "cactusspike", 80, 3, true);
		registerEntity("cactus_creeper", EntityCactusCreeper.class, "cactuscreeper", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_snowman", EntityCactusSnowman.class, "cactussnowman", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_slime", EntityCactusSlime.class, "cactusslime", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_sheep", EntityCactusSheep.class, "cactussheep", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_pig", EntityCactusPig.class, "cactuspig", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_spider", EntityCactusSpider.class, "cactusspider", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_skeleton", EntityCactusSkelly.class, "cactusskeleton", 80, 3, true, 0xFF0f751b, 0xFF89132f);
		registerEntity("cactus_boat", EntityCactusBoat.class, "cactusboat", 80, 3, true);
		registerEntity("cactoni", EntityCactoni.class, "cactoni", 80, 3, true, 0xFF0f751b, 0xFF89132f);

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
