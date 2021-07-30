package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.entities.ICactusMob;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class CactusMobHandler {
	@SubscribeEvent
	public void CactusHurtEvent(LivingHurtEvent event) {
		if(event.getEntityLiving() instanceof ICactusMob && event.getSource() == DamageSource.CACTUS) {
			event.setCanceled(true);
		}
		
		if(event.getEntityLiving() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getEntityLiving();
			World world = player.getCommandSenderWorld();
			
			if(event.getSource() == DamageSource.CACTUS) {
				boolean slot1 = player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == CactusRegistry.CACTUS_HELMET.get();
				boolean slot2 = player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == CactusRegistry.CACTUS_CHESTPLATE.get();
				boolean slot3 = player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == CactusRegistry.CACTUS_LEGGINGS.get();
				boolean slot4 = player.getItemBySlot(EquipmentSlotType.FEET).getItem() == CactusRegistry.CACTUS_BOOTS.get();
				
				if(slot1 && slot2 && slot3 && slot4) {
					ArrayList<ItemStack> armorList = new ArrayList<>();
					armorList.add(player.getItemBySlot(EquipmentSlotType.HEAD));
					armorList.add(player.getItemBySlot(EquipmentSlotType.CHEST));
					armorList.add(player.getItemBySlot(EquipmentSlotType.LEGS));
					armorList.add(player.getItemBySlot(EquipmentSlotType.FEET));

					int i = world.random.nextInt(4);
					ItemStack stack = armorList.get(i);
					stack.hurtAndBreak(world.random.nextInt(2), player, (p_214023_1_) -> {
						player.broadcastBreakEvent(EquipmentSlotType.byTypeAndIndex(EquipmentSlotType.Group.ARMOR, i));
					});
					event.setCanceled(true);
				}
			}
		}
		
		if(event.getSource().getEntity() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
			World world = player.getCommandSenderWorld();
			
			if(event.getEntityLiving() instanceof ICactusMob) {
				if(world.random.nextInt(10) < 4)
					player.hurt(DamageSource.CACTUS, 1F);
			}
		}
	}
}
