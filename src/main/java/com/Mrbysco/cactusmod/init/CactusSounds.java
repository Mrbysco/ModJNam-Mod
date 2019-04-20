package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CactusSounds {
	public static SoundEvent hat_music;
	
	public static void registerSounds() 
	{
		hat_music = registerSound("hat.music");
	}
	
	private static SoundEvent registerSound(String soundName)
	{
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, soundName);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(location);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}