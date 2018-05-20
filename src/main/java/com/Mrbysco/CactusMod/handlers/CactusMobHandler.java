package com.Mrbysco.CactusMod.handlers;

import java.util.ArrayList;

import com.Mrbysco.CactusMod.entities.ICactusMob;
import com.Mrbysco.CactusMod.init.CactusItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
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
		
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			World world = player.getEntityWorld();
			
			if(event.getSource() == DamageSource.CACTUS)
			{
				boolean slot1 = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == CactusItems.cactus_helmet;
				boolean slot2 = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == CactusItems.cactus_chestplate;
				boolean slot3 = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == CactusItems.cactus_leggings;
				boolean slot4 = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == CactusItems.cactus_boots;
				
				if(slot1 && slot2 && slot3 && slot4)
				{
					ArrayList<ItemStack> armorList = new ArrayList<>();
					armorList.add(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
					armorList.add(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
					armorList.add(player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
					armorList.add(player.getItemStackFromSlot(EntityEquipmentSlot.FEET));
					
					ItemStack stack = armorList.get(world.rand.nextInt(3));
					stack.damageItem(world.rand.nextInt(2), player);
					event.setCanceled(true);
				}
			}
		}
		
		if(event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
			World world = player.getEntityWorld();
			
			if(event.getEntityLiving() instanceof ICactusMob)
			{
				if(world.rand.nextInt(10) < 4)
					player.attackEntityFrom(DamageSource.CACTUS, 1F);
			}
		}
	}
}
