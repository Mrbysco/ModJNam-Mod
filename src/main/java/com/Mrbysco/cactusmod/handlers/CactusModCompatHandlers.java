package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.config.CactusConfig;
import com.mrbysco.cactusmod.entities.EntityCactoni;
import com.mrbysco.cactusmod.entities.EntityCactusSnowman;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CactusModCompatHandlers {
	@Optional.Method(modid = "statues")
	@SubscribeEvent
	public void SombreroToCactoniEvent(PlayerInteractEvent.EntityInteract event)
	{		
		if(CactusConfig.modcompat.StatuesSombreroTocCactoni)
		{
			EntityPlayer player = event.getEntityPlayer();
			World world = player.world;
			Entity target = event.getTarget();
			ItemStack stack = event.getItemStack();

			if(!world.isRemote)
			{
				if(stack.getItem() == Item.getByNameOrId("statues:blocksombrero") && target instanceof EntityCactusSnowman)
				{
					EntityCactoni cactoni = new EntityCactoni(world);
					cactoni.setPositionAndRotation(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
					world.spawnEntity(cactoni);
					cactoni.onInitialSpawn(world.getDifficultyForLocation(target.getPosition()), null);
					target.setDead();

					if(!player.isCreative())
						stack.shrink(1);
				}
			}
		}
	}
}
