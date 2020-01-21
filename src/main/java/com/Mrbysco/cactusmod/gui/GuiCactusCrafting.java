package com.mrbysco.cactusmod.gui;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiCactusCrafting extends GuiCrafting {

    public GuiCactusCrafting(InventoryPlayer inv, World world, BlockPos pos) {
        super(inv, world, pos);
        this.inventorySlots = new ContainerCactusWorkbench(inv, world, pos);
    }
}
