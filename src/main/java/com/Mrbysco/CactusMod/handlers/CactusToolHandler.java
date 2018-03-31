package com.Mrbysco.CactusMod.handlers;

import com.Mrbysco.CactusMod.init.CactusItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CactusToolHandler {
	
	@SubscribeEvent
	public void CactusSwordEvent(LivingAttackEvent event)
	{
		if(event.getSource().getDamageType() == "player")
		{
			if(event.getSource().getTrueSource() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)event.getEntityLiving();
				ItemStack stack = player.getHeldItemMainhand();
				World world = player.world;
				
				if(stack.getItem() == CactusItems.cactus_sword && !world.isRemote)
				{
					player.attackEntityFrom(DamageSource.CACTUS, 0.5F);
				}
			}
		}
	}
}
