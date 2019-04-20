package com.mrbysco.cactusmod.handlers;

import com.mrbysco.cactusmod.gui.ContainerCactusWorkbench;
import com.mrbysco.cactusmod.gui.compat.ContainerFastCactusWorkbench;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CactusBlockHandler {
    @SubscribeEvent
    public void CraftedEvent(ItemCraftedEvent event)
    {
        World world = event.player.world;
        if(!world.isRemote) {
            EntityPlayerMP playerMP = (EntityPlayerMP)event.player;
            if(Loader.isModLoaded("fastbench")) {
                if(playerMP.openContainer instanceof ContainerFastCactusWorkbench) {
                    playerMP.attackEntityFrom(DamageSource.CACTUS, 1.0F);
                }
            } else {
                if(playerMP.openContainer instanceof ContainerCactusWorkbench) {
                    playerMP.attackEntityFrom(DamageSource.CACTUS, 1.0F);
                }
            }
        }
    }
}
