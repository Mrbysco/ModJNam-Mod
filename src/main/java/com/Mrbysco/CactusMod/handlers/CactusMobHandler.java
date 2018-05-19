package com.Mrbysco.CactusMod.handlers;

import com.Mrbysco.CactusMod.entities.ICactusMob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
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
		
		if(event.getEntityLiving() instanceof ICactusMob)
		{
			if(event.getSource().getTrueSource() instanceof EntityPlayer)
			{
				Entity entity = event.getSource().getTrueSource();
				World world = entity.getEntityWorld();
				if(world.rand.nextInt(10) < 4)
					entity.attackEntityFrom(DamageSource.CACTUS, 1F);
			}
		}
	}
}
