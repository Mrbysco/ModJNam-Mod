package com.mrbysco.cactusmod.gui.compat;

import com.mrbysco.cactusmod.init.CactusBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shadows.fastbench.gui.ContainerFastBench;

public class ContainerFastCactusWorkbench extends ContainerFastBench {
    final World world;
    final BlockPos pos;

    public ContainerFastCactusWorkbench(EntityPlayer player, World worldIn, BlockPos posIn)
    {
        super(player, worldIn, posIn);
        this.world = worldIn;
        this.pos = posIn;
    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        if (this.world.getBlockState(this.pos).getBlock() != CactusBlocks.cactus_crafting_table)
        {
            return false;
        }
        else
        {
            return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }
}
