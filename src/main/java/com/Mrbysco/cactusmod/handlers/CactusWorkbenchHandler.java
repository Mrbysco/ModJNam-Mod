package com.mrbysco.cactusmod.handlers;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class CactusWorkbenchHandler {
    @SubscribeEvent
    public void CraftedEvent(ItemCraftedEvent event) {
        World world = event.getPlayer().world;
        if(!world.isRemote) {
            ServerPlayerEntity playerMP = (ServerPlayerEntity)event.getPlayer();
            if(ModList.get().isLoaded("fastbench")) {
                if(playerMP.openContainer instanceof shadows.fastbench.gui.ContainerFastBench) {
                    playerMP.attackEntityFrom(DamageSource.CACTUS, 1.0F);
                }
            } else {
                if(playerMP.openContainer instanceof WorkbenchContainer) {
                    playerMP.attackEntityFrom(DamageSource.CACTUS, 1.0F);
                }
            }
        }
    }
}
