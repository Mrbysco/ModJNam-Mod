package com.mrbysco.cactusmod.blocks.redstone;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class CactusDoorBlock extends DoorBlock {
	public CactusDoorBlock(AbstractBlock.Properties builder) {
		super(builder);
	}

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        player.attackEntityFrom(DamageSource.CACTUS, 1.0F);
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
