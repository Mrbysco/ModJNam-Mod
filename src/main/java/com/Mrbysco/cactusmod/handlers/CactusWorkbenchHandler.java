package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.blocks.container.CactusWorkbenchContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CactusWorkbenchHandler {
	@SubscribeEvent
	public void CraftedEvent(ItemCraftedEvent event) {
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
