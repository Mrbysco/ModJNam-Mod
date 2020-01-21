package com.mrbysco.cactusmod.init;

import com.mrbysco.cactusmod.gui.ContainerCactusWorkbench;
import com.mrbysco.cactusmod.gui.GuiCactusCrafting;
import com.mrbysco.cactusmod.gui.compat.ContainerFastCactusWorkbench;
import com.mrbysco.cactusmod.gui.compat.GuiFastCactusCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class CactusGuiHandler implements IGuiHandler {
    public static final int GUI_WORKBENCH = 0;
    public static final int GUI_FAST_WORKBENCH = 1;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUI_WORKBENCH)
        {
            return new ContainerCactusWorkbench(player.inventory, world, new BlockPos(x, y, z));
        }
        else if(ID == GUI_FAST_WORKBENCH)
        {
            return new ContainerFastCactusWorkbench(player, world, new BlockPos(x, y, z));
        }
        else {
            return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUI_WORKBENCH)
        {
            return new GuiCactusCrafting(player.inventory, world, new BlockPos(x, y, z));
        }
        else if(ID == GUI_FAST_WORKBENCH)
        {
            return new GuiFastCactusCrafting(player.inventory, world, new BlockPos(x, y, z));
        }
        else {
            return null;
        }
    }
}
