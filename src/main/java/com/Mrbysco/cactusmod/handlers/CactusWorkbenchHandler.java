package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.blocks.container.CactusWorkbenchContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CactusWorkbenchHandler {
	@SubscribeEvent
	public void CraftedEvent(PlayerEvent.ItemCraftedEvent event) {
		Level level = event.getEntity().level();
		if (!level.isClientSide) {
			ServerPlayer serverPlayer = (ServerPlayer) event.getEntity();
//            if(ModList.get().isLoaded("fastbench")) {
//                com.mrbysco.cactusmod.compat.fastbench.FastBenchHelper.onContainerMatch(playerMP);
//            } else {
			if (serverPlayer.containerMenu instanceof CactusWorkbenchContainer) {
				serverPlayer.hurt(serverPlayer.damageSources().cactus(), 1.0F);
			}
//            }
		}
	}
}
