package com.mrbysco.cactusmod.blocks.plant;

import com.mojang.serialization.MapCodec;
import com.mrbysco.cactusmod.blocks.CarvedCactusBlock;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.neoforged.neoforge.common.Tags;

import java.util.Random;

public class CactusPlantBlock extends PipeBlock {
	public static final MapCodec<CactusPlantBlock> CODEC = simpleCodec(CactusPlantBlock::new);

	@Override
	protected MapCodec<? extends PipeBlock> codec() {
		return CODEC;
	}
	public CactusPlantBlock(BlockBehaviour.Properties builder) {
		super(0.3125F, builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false)));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
	}

	public BlockState getStateForPlacement(BlockGetter getter, BlockPos pos) {
		BlockState belowState = getter.getBlockState(pos.below());
		BlockState aboveState = getter.getBlockState(pos.above());
		BlockState northState = getter.getBlockState(pos.north());
		BlockState eastState = getter.getBlockState(pos.east());
		BlockState southState = getter.getBlockState(pos.south());
		BlockState westState = getter.getBlockState(pos.west());
		return this.defaultBlockState()
				.setValue(DOWN, Boolean.valueOf(belowState.is(this) || belowState.is(CactusRegistry.CACTUS_PLANT.get()) || belowState.is(Tags.Blocks.SAND)))
				.setValue(UP, Boolean.valueOf(aboveState.is(this) || aboveState.is(CactusRegistry.CACTUS_PLANT.get())))
				.setValue(NORTH, Boolean.valueOf(northState.is(this) || northState.is(CactusRegistry.CACTUS_PLANT.get())))
				.setValue(EAST, Boolean.valueOf(eastState.is(this) || eastState.is(CactusRegistry.CACTUS_PLANT.get())))
				.setValue(SOUTH, Boolean.valueOf(southState.is(this) || southState.is(CactusRegistry.CACTUS_PLANT.get())))
				.setValue(WEST, Boolean.valueOf(westState.is(this) || westState.is(CactusRegistry.CACTUS_PLANT.get())));
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		entity.hurt(entity.damageSources().cactus(), 1.0F);
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
	 * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
	 * returns its solidified counterpart.
	 * Note that this method should ideally consider only the specific face passed in.
	 */
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		if (!stateIn.canSurvive(level, currentPos)) {
			level.scheduleTick(currentPos, this, 1);
			return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
		} else {
			boolean flag = facingState.is(this) || facingState.is(CactusRegistry.CACTUS_FLOWER.get()) || facing == Direction.DOWN && facingState.is(Tags.Blocks.SAND);
			return stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), Boolean.valueOf(flag));
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState blockstate = level.getBlockState(pos.below());
		boolean flag = !level.getBlockState(pos.above()).isAir() && !blockstate.isAir();

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos blockpos = pos.relative(direction);
			BlockState blockstate1 = level.getBlockState(blockpos);
			if (blockstate1.is(this)) {
				if (flag) {
					return false;
				}

				BlockState blockstate2 = level.getBlockState(blockpos.below());
				if (blockstate2.is(this) || blockstate2.is(Tags.Blocks.SAND)) {
					return true;
				}
			}
		}

		Block block2 = blockstate.getBlock();
		return block2 == this || blockstate.is(Tags.Blocks.SAND);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}
}
