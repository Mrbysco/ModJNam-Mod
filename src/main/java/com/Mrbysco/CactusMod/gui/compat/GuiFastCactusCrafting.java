package com.mrbysco.cactusmod.gui.compat;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shadows.fastbench.gui.GuiFastBench;

public class GuiFastCactusCrafting extends GuiFastBench {
    public GuiFastCactusCrafting(InventoryPlayer inv, World world) {
        this(inv, world, BlockPos.ORIGIN);
    }

    public GuiFastCactusCrafting(InventoryPlayer inv, World world, BlockPos pos) {
        super(inv, world, pos);
        this.inventorySlots = new ContainerFastCactusWorkbench(inv.player, world, pos);
    }
}
