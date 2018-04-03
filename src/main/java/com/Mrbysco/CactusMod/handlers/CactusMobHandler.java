package com.Mrbysco.CactusMod.handlers;

import com.Mrbysco.CactusMod.entities.ICactusMob;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CactusMobHandler {
	@SubscribeEvent
	public void CactusHurtEvent(LivingHurtEvent event)
	{
		if(event.getEntityLiving() instanceof ICactusMob && event.getSource() == DamageSource.CACTUS)
		{
			event.setCanceled(true);
		}
	}
}
