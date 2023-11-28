package com.mrbysco.cactusmod.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class PunjiCactusBlock extends WoolCarpetBlock {
	protected static final VoxelShape SHAPE = Stream.of(
			Block.box(1, 0, 1, 15, 1, 15),
			Block.box(4, 1, 5, 5, 2, 6),
			Block.box(4, 1, 8, 5, 2, 9),
			Block.box(9, 1, 6, 10, 2, 7),
			Block.box(7, 1, 3, 8, 2, 4),
			Block.box(11, 1, 3, 12, 2, 4),
			Block.box(12, 1, 8, 13, 2, 9),
			Block.box(11, 1, 11, 12, 2, 12),
			Block.box(9, 1, 11, 10, 2, 12),
			Block.box(7, 1, 9, 8, 2, 10),
			Block.box(5, 1, 12, 6, 2, 13),
			Block.box(14, 1, 8, 15, 2, 9),
			Block.box(14, 1, 5, 15, 2, 6),
			Block.box(14, 1, 11, 15, 2, 12),
			Block.box(12, 1, 14, 13, 2, 15),
			Block.box(9, 1, 14, 10, 2, 15),
			Block.box(6, 1, 14, 7, 2, 15),
			Block.box(2, 1, 14, 3, 2, 15),
			Block.box(1, 1, 10, 2, 2, 11),
			Block.box(1, 1, 7, 2, 2, 8),
			Block.box(1, 1, 3, 2, 2, 4),
			Block.box(3, 1, 1, 4, 2, 2),
			Block.box(7, 1, 1, 8, 2, 2),
			Block.box(11, 1, 1, 12, 2, 2)
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

	public PunjiCactusBlock(BlockBehaviour.Properties builder) {
		super(DyeColor.GREEN, builder);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		super.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		super.entityInside(state, level, pos, entity);

		if (entity instanceof Mob) {
			entity.hurt(entity.damageSources().cactus(), 1.0F);
		} else if (entity instanceof ItemEntity item) {
			if (item.tickCount >= 2400) {
				entity.hurt(entity.damageSources().cactus(), 1.0F);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, level, tooltip, flagIn);
		tooltip.add(Component.translatable("cactus.carpet.info").withStyle(ChatFormatting.GREEN));
		tooltip.add(Component.translatable("cactus.carpet.info2").withStyle(ChatFormatting.GREEN));
	}
}
