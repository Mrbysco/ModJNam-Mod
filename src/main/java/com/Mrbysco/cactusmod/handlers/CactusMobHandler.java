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
			World world = player.getEntityWorld();
			
			if(event.getSource() == DamageSource.CACTUS) {
				boolean slot1 = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == CactusRegistry.CACTUS_HELMET.get();
				boolean slot2 = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == CactusRegistry.CACTUS_CHESTPLATE.get();
				boolean slot3 = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == CactusRegistry.CACTUS_LEGGINGS.get();
				boolean slot4 = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == CactusRegistry.CACTUS_BOOTS.get();
				
				if(slot1 && slot2 && slot3 && slot4) {
					ArrayList<ItemStack> armorList = new ArrayList<>();
					armorList.add(player.getItemStackFromSlot(EquipmentSlotType.HEAD));
					armorList.add(player.getItemStackFromSlot(EquipmentSlotType.CHEST));
					armorList.add(player.getItemStackFromSlot(EquipmentSlotType.LEGS));
					armorList.add(player.getItemStackFromSlot(EquipmentSlotType.FEET));

					int i = world.rand.nextInt(4);
					ItemStack stack = armorList.get(i);
					stack.damageItem(world.rand.nextInt(2), player, (p_214023_1_) -> {
						player.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, i));
					});
					event.setCanceled(true);
				}
			}
		}
		
		if(event.getSource().getTrueSource() instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
			World world = player.getEntityWorld();
			
			if(event.getEntityLiving() instanceof ICactusMob) {
				if(world.rand.nextInt(10) < 4)
					player.attackEntityFrom(DamageSource.CACTUS, 1F);
			}
		}
	}
}
