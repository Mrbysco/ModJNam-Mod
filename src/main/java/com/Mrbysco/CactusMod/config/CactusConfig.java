package com.Mrbysco.CactusMod.config;

import com.Mrbysco.CactusMod.Reference;

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
	
	public static class General {
		@Config.Comment("Decides if the Cactus Creeper spawns naturally")
		public boolean creeperSpawn = true;
		
		@Config.Comment("Decides if the Cactus Cow spawns naturally")
		public boolean cowSpawn = true;
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
