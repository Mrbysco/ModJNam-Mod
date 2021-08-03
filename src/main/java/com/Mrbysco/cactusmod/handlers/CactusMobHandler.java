package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.entities.ICactusMob;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class CactusMobHandler {
	@SubscribeEvent
	public void CactusHurtEvent(LivingHurtEvent event) {
		if(event.getEntityLiving() instanceof ICactusMob && event.getSource() == DamageSource.CACTUS) {
			event.setCanceled(true);
		}
		
		if(event.getEntityLiving() instanceof Player) {
			Player player = (Player) event.getEntityLiving();
			Level world = player.getCommandSenderWorld();
			
			if(event.getSource() == DamageSource.CACTUS) {
				boolean slot1 = player.getItemBySlot(EquipmentSlot.HEAD).getItem() == CactusRegistry.CACTUS_HELMET.get();
				boolean slot2 = player.getItemBySlot(EquipmentSlot.CHEST).getItem() == CactusRegistry.CACTUS_CHESTPLATE.get();
				boolean slot3 = player.getItemBySlot(EquipmentSlot.LEGS).getItem() == CactusRegistry.CACTUS_LEGGINGS.get();
				boolean slot4 = player.getItemBySlot(EquipmentSlot.FEET).getItem() == CactusRegistry.CACTUS_BOOTS.get();
				
				if(slot1 && slot2 && slot3 && slot4) {
					ArrayList<ItemStack> armorList = new ArrayList<>();
					armorList.add(player.getItemBySlot(EquipmentSlot.HEAD));
					armorList.add(player.getItemBySlot(EquipmentSlot.CHEST));
					armorList.add(player.getItemBySlot(EquipmentSlot.LEGS));
					armorList.add(player.getItemBySlot(EquipmentSlot.FEET));

					int i = world.random.nextInt(4);
					ItemStack stack = armorList.get(i);
					stack.hurtAndBreak(world.random.nextInt(2), player, (p_214023_1_) -> {
						player.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, i));
					});
					event.setCanceled(true);
				}
			}
		}
		
		if(event.getSource().getEntity() instanceof Player) {
			Player player = (Player) event.getSource().getEntity();
			Level world = player.getCommandSenderWorld();
			
			if(event.getEntityLiving() instanceof ICactusMob) {
				if(world.random.nextInt(10) < 4)
					player.hurt(DamageSource.CACTUS, 1F);
			}
		}
	}
}
