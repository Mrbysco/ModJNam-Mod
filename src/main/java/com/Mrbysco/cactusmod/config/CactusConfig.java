package com.mrbysco.cactusmod.config;

import com.mrbysco.cactusmod.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("cactusmod.config.title")
public class CactusConfig {
	
	@Config.Comment({"General settings."})
	public static final General general = new General();
	
	@Config.Comment({"World Gen settings."})
	public static final WorldGen worldgen = new WorldGen();
	
	@Config.Comment({"World Gen settings."})
	public static final ModCompat modcompat = new ModCompat();
	
	public static class General {
		@Config.Comment("Decides if the Cactus Creeper spawns naturally")
		public boolean creeperSpawn = true;
		
		@Config.Comment("Decides if the Cactus Cow spawns naturally")
		public boolean cowSpawn = true;
		
		@Config.Comment("Decides if the Cactus Slime spawns naturally")
		public boolean slimeSpawn = true;
		
		@Config.Comment("Decides if the Cactus Sheep spawns naturally")
		public boolean sheepSpawn = true;

		@Config.Comment("Decides if the Cactus Pig spawns naturally")
		public boolean pigSpawn = true;

		@Config.Comment("Decides if the Cactus Spider spawns naturally")
		public boolean spiderSpawn = true;
		
		@Config.Comment("Decides if the Cactus Skeleton spawns naturally")
		public boolean skeletonSpawn = true;
		
		@Config.Comment("Decides if the Cactoni spawns naturally")
		public boolean cactoniSpawn = false;
	}
	
	public static class WorldGen {
		@Config.Comment("Decides how low the cactus plants can generate")
		public int seaLevel = 64;
		
		@Config.Comment("Decides if the Cactus Plant will generate")
		public boolean generateCactusPlant = true;
		
		@Config.Comment("Decides the rarity in which the cactus plant spawns")
		public int CactusPlantSpawnRarity = 20;
	}
	
	public static class ModCompat {
		@Config.Comment("While Statues is installed: If true right-clicking a cactus man with a Statues sombrero will spawn a Cactoni")
		public boolean StatuesSombreroTocCactoni = true;
	}
	
	@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Reference.MOD_ID)) {
				ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}
