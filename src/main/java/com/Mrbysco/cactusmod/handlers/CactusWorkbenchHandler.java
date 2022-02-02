package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.blocks.container.CactusWorkbenchContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class CactusWorkbenchHandler {
    @SubscribeEvent
    public void CraftedEvent(ItemCraftedEvent event) {
        World world = event.getPlayer().level;
        if(!world.isClientSide) {
            ServerPlayerEntity playerMP = (ServerPlayerEntity)event.getPlayer();
            if(playerMP.containerMenu instanceof CactusWorkbenchContainer) {
                playerMP.hurt(DamageSource.CACTUS, 1.0F);
            }
        }
    }
}
