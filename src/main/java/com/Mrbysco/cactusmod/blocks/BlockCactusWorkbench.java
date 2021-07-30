package com.mrbysco.cactusmod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockCactusWorkbench extends CraftingTableBlock {
	public BlockCactusWorkbench(AbstractBlock.Properties builder) {
		super(builder);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return super.getShape(state, worldIn, pos, context);
	}

	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.hurt(DamageSource.CACTUS, 1.0F);
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			player.openMenu(state.getMenuProvider(worldIn, pos));
			player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
			return ActionResultType.CONSUME;
		}
	}

	@Override
	public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
		return new com.mrbysco.cactusmod.util.WorkBenchHelper().getContainer(state, worldIn, pos);
	}
}
