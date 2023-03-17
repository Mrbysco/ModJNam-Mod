package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.blocks.container.CactusWorkbenchContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CactusWorkbenchHandler {
	@SubscribeEvent
	public void CraftedEvent(ItemCraftedEvent event) {
		Level world = event.getEntity().level;
		if (!world.isClientSide) {
			ServerPlayer playerMP = (ServerPlayer) event.getEntity();
//            if(ModList.get().isLoaded("fastbench")) {
//                com.mrbysco.cactusmod.compat.fastbench.FastBenchHelper.onContainerMatch(playerMP);
//            } else {
			if (playerMP.containerMenu instanceof CactusWorkbenchContainer) {
				playerMP.hurt(DamageSource.CACTUS, 1.0F);
			}
//            }
		}
	}
}
