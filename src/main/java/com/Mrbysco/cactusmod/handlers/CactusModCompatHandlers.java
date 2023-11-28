package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.config.CactusConfig;
import com.mrbysco.cactusmod.entities.CactoniEntity;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class CactusModCompatHandlers {
	@SubscribeEvent
	public void SombreroToCactoniEvent(PlayerInteractEvent.EntityInteract event) {
		if (ModList.get().isLoaded("statues") && CactusConfig.COMMON.statuesCompat.get()) {
			Player player = event.getEntity();
			Level level = player.level();
			Entity target = event.getTarget();
			ItemStack stack = event.getItemStack();

			if (!level.isClientSide) {
				Item sombrero = BuiltInRegistries.ITEM.get(new ResourceLocation("statues", "sombrero"));
				if (sombrero != null && stack.getItem() == sombrero && target instanceof CactusSnowGolemEntity) {
					CactoniEntity cactoni = CactusRegistry.CACTONI.get().create(level);
					if (cactoni != null) {
						cactoni.absMoveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
						level.addFreshEntity(cactoni);
						cactoni.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(target.blockPosition()), MobSpawnType.CONVERSION, null, null);
						target.discard();

						if (!player.getAbilities().instabuild)
							stack.shrink(1);
					}
				}
			}
		}
	}
}
