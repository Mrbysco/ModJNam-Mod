package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.config.CactusConfig;
import com.mrbysco.cactusmod.entities.CactoniEntity;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class CactusModCompatHandlers {
	@SubscribeEvent
	public void SombreroToCactoniEvent(PlayerInteractEvent.EntityInteract event) {
		if(ModList.get().isLoaded("statues") && CactusConfig.COMMON.statuesCompat.get()) {
			PlayerEntity player = event.getPlayer();
			World world = player.world;
			Entity target = event.getTarget();
			ItemStack stack = event.getItemStack();

			if(!world.isRemote) {
				Item sombrero = ForgeRegistries.ITEMS.getValue(new ResourceLocation("statues", "sombrero"));
				if(sombrero != null && stack.getItem() == sombrero && target instanceof CactusSnowGolemEntity) {
					CactoniEntity cactoni = CactusRegistry.CACTONI.get().create(world);
					cactoni.setPositionAndRotation(target.getPosX(), target.getPosY(), target.getPosZ(), target.rotationYaw, target.rotationPitch);
					world.addEntity(cactoni);
					cactoni.onInitialSpawn((ServerWorld)world, world.getDifficultyForLocation(target.getPosition()), SpawnReason.CONVERSION, null, null);
					target.remove();

					if(!player.abilities.isCreativeMode)
						stack.shrink(1);
				}
			}
		}
	}
}
