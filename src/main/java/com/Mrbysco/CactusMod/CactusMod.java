package com.Mrbysco.CactusMod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Mrbysco.CactusMod.config.CactusConfig;
import com.Mrbysco.CactusMod.entities.EntityCactusCow;
import com.Mrbysco.CactusMod.entities.EntityCactusSheep;
import com.Mrbysco.CactusMod.entities.EntityCactusSlime;
import com.Mrbysco.CactusMod.entities.hostile.EntityCactusCreeper;
import com.Mrbysco.CactusMod.entities.hostile.EntityCactusSkelly;
import com.Mrbysco.CactusMod.entities.hostile.EntityCactusSpider;
import com.Mrbysco.CactusMod.handlers.CactusBlockHandler;
import com.Mrbysco.CactusMod.handlers.CactusMobHandler;
import com.Mrbysco.CactusMod.handlers.CactusModCompatHandlers;
import com.Mrbysco.CactusMod.handlers.CactusToolHandler;
import com.Mrbysco.CactusMod.init.CactusEntities;
import com.Mrbysco.CactusMod.init.CactusItems;
import com.Mrbysco.CactusMod.init.CactusRecipes;
import com.Mrbysco.CactusMod.init.CactusSounds;
import com.Mrbysco.CactusMod.init.CactusTab;
import com.Mrbysco.CactusMod.proxy.CommonProxy;
import com.Mrbysco.CactusMod.world.OverworldGen;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, 
	name = Reference.NAME, 
	version = Reference.VERSION, 
	acceptedMinecraftVersions = Reference.MC_VERSIONS,
	dependencies = Reference.DEPENDENCIES)

public class CactusMod {
	@Instance(Reference.MOD_ID)
	public static CactusMod instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	
	public static CactusTab cactustab = new CactusTab();
	
	public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);
	
	//Stuff
	public static final ToolMaterial cactusTool = EnumHelper.addToolMaterial("cactus", 0, 67, 3.0F, 0.2F, 20).setRepairItem(new ItemStack(Blocks.CACTUS));
	public static final ArmorMaterial cactusArmor = EnumHelper.addArmorMaterial("cactus", "cactusmod:cactus", 20 , new int[]{0, 0, 0, 0}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0F).setRepairItem(new ItemStack(Blocks.CACTUS));
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{			
		logger.debug("Registering Config");
		MinecraftForge.EVENT_BUS.register(new CactusConfig());
		
		CactusSounds.registerSounds();
		
		logger.debug("Register Entities");
		CactusEntities.register();
		
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		for (Biome biome : Biome.REGISTRY) {
			if(biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.MUTATED_DESERT || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY))
			{
				if(CactusConfig.general.cowSpawn)
				{
					logger.debug("Registering Cactus Cow spawn");
					biome.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(EntityCactusCow.class, 8, 4, 4));
				}
				
				if(CactusConfig.general.creeperSpawn)
				{
					logger.debug("Registering Cactus Creeper spawn");
					biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EntityCactusCreeper.class, 100, 4, 4));
				}
				
				if(CactusConfig.general.slimeSpawn)
				{
					logger.debug("Registering Cactus Slime spawn");
					biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EntityCactusSlime.class, 100, 2, 2));
				}
				
				if(CactusConfig.general.sheepSpawn)
				{
					logger.debug("Registering Cactus Sheep spawn");
					biome.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(EntityCactusSheep.class, 12, 4, 4));
				}
				
				if(CactusConfig.general.spiderSpawn)
				{
					logger.debug("Registering Cactus Spider spawn");
					biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EntityCactusSpider.class, 100, 4, 4));
				}
				
				if(CactusConfig.general.skeletonSpawn)
				{
					logger.debug("Registering Cactus Skeleton spawn");
					biome.getSpawnableList(EnumCreatureType.MONSTER).add(new SpawnListEntry(EntityCactusSkelly.class, 100, 4, 4));
				}
				
				if(CactusConfig.general.cactoniSpawn)
				{
					logger.debug("Registering Cactus Skeleton spawn");
					biome.getSpawnableList(EnumCreatureType.CREATURE).add(new SpawnListEntry(EntityCactusSkelly.class, 1, 2, 2));
				}
			}
		}
		
		logger.debug("Registering Handlers");
		MinecraftForge.EVENT_BUS.register(new CactusBlockHandler());
		MinecraftForge.EVENT_BUS.register(new CactusToolHandler());
		MinecraftForge.EVENT_BUS.register(new CactusMobHandler());
		MinecraftForge.EVENT_BUS.register(new CactusModCompatHandlers());
		
		logger.debug("Register WorldGen");
		GameRegistry.registerWorldGenerator(OverworldGen.INSTANCE, 0);
		
		CactusItems.initOredict();
		CactusRecipes.init();
		
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
