package com.mrbysco.cactusmod.blocks.plant;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CactusFlowerBlock extends Block {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
	private final Supplier<CactusPlantBlock> plantBlock;

	public CactusFlowerBlock(Supplier<CactusPlantBlock> plantBlock, BlockBehaviour.Properties builder) {
		super(builder);
		this.plantBlock = plantBlock;
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		}
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < 5;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		BlockPos blockpos = pos.above();
		if (level.isEmptyBlock(blockpos) && blockpos.getY() < 256) {
			int i = state.getValue(AGE);
			if (i < 5 && CommonHooks.onCropsGrowPre(level, blockpos, state, true)) {
				boolean flag = false;
				boolean flag1 = false;
				BlockState blockstate = level.getBlockState(pos.below());
				if (blockstate.is(Tags.Blocks.SAND)) {
					flag = true;
				} else if (blockstate.is(this.plantBlock.get())) {
					int j = 1;

					for (int k = 0; k < 4; ++k) {
						BlockState blockstate1 = level.getBlockState(pos.below(j + 1));
						if (!blockstate1.is(this.plantBlock.get())) {
							if (blockstate1.is(Tags.Blocks.SAND)) {
								flag1 = true;
							}
							break;
						}

						++j;
					}

					if (j < 2 || j <= random.nextInt(flag1 ? 5 : 4)) {
						flag = true;
					}
				} else if (blockstate.isAir()) {
					flag = true;
				}

				if (flag && areAllNeighborsEmpty(level, blockpos, (Direction) null) && level.isEmptyBlock(pos.above(2))) {
					level.setBlock(pos, this.plantBlock.get().getStateForPlacement(level, pos), 2);
					this.placeGrownFlower(level, blockpos, i);
				} else if (i < 4) {
					int l = random.nextInt(4);
					if (flag1) {
						++l;
					}

					boolean flag2 = false;

					for (int i1 = 0; i1 < l; ++i1) {
						Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
						BlockPos blockpos1 = pos.relative(direction);
						if (level.isEmptyBlock(blockpos1) && level.isEmptyBlock(blockpos1.below()) && areAllNeighborsEmpty(level, blockpos1, direction.getOpposite())) {
							this.placeGrownFlower(level, blockpos1, i + 1);
							flag2 = true;
						}
					}

					if (flag2) {
						level.setBlock(pos, this.plantBlock.get().getStateForPlacement(level, pos), 2);
					} else {
						this.placeDeadFlower(level, pos);
					}
				} else {
					this.placeDeadFlower(level, pos);
				}
				CommonHooks.onCropsGrowPost(level, pos, state);
			}
		}
	}

	private void placeGrownFlower(Level level, BlockPos pos, int age) {
		level.setBlock(pos, this.defaultBlockState().setValue(AGE, Integer.valueOf(age)), 2);
		level.levelEvent(1033, pos, 0);
	}

	private void placeDeadFlower(Level level, BlockPos pos) {
		level.setBlock(pos, this.defaultBlockState().setValue(AGE, Integer.valueOf(5)), 2);
		level.levelEvent(1034, pos, 0);
	}

	private static boolean areAllNeighborsEmpty(LevelReader level, BlockPos pos, @Nullable Direction excludingSide) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (direction != excludingSide && !level.isEmptyBlock(pos.relative(direction))) {
				return false;
			}
		}
		return true;
	}

	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
		if (facing != Direction.UP && !stateIn.canSurvive(level, currentPos)) {
			level.scheduleTick(currentPos, this, 1);
		}

		return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
	}

	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockState blockstate = level.getBlockState(pos.below());
		if (!blockstate.is(this.plantBlock.get()) && !blockstate.is(Tags.Blocks.SAND)) {
			if (!blockstate.isAir()) {
				return false;
			} else {
				boolean flag = false;

				for (Direction direction : Direction.Plane.HORIZONTAL) {
					BlockState blockstate1 = level.getBlockState(pos.relative(direction));
					if (blockstate1.is(this.plantBlock.get())) {
						if (flag) {
							return false;
						}

						flag = true;
					} else if (!blockstate1.isAir()) {
						return false;
					}
				}

				return flag;
			}
		} else {
			return true;
		}
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	public static void generatePlant(LevelAccessor level, BlockPos pos, RandomSource rand, int maxHorizontalDistance) {
		level.setBlock(pos, ((CactusPlantBlock) CactusRegistry.CACTUS_PLANT.get()).getStateForPlacement(level, pos), 2);
		growTreeRecursive(level, pos, rand, pos, maxHorizontalDistance, 0);
	}

	private static void growTreeRecursive(LevelAccessor level, BlockPos branchPos, RandomSource rand, BlockPos originalBranchPos, int maxHorizontalDistance, int iterations) {
		CactusPlantBlock CactusPlantBlock = (CactusPlantBlock) CactusRegistry.CACTUS_PLANT.get();
		int i = rand.nextInt(4) + 1;
		if (iterations == 0) {
			++i;
		}

		for (int j = 0; j < i; ++j) {
			BlockPos blockpos = branchPos.above(j + 1);
			if (!areAllNeighborsEmpty(level, blockpos, (Direction) null)) {
				return;
			}

			level.setBlock(blockpos, CactusPlantBlock.getStateForPlacement(level, blockpos), 2);
			level.setBlock(blockpos.below(), CactusPlantBlock.getStateForPlacement(level, blockpos.below()), 2);
		}

		boolean flag = false;
		if (iterations < 4) {
			int l = rand.nextInt(4);
			if (iterations == 0) {
				++l;
			}

			for (int k = 0; k < l; ++k) {
				Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
				BlockPos blockpos1 = branchPos.above(i).relative(direction);
				if (Math.abs(blockpos1.getX() - originalBranchPos.getX()) < maxHorizontalDistance && Math.abs(blockpos1.getZ() - originalBranchPos.getZ()) < maxHorizontalDistance && level.isEmptyBlock(blockpos1) && level.isEmptyBlock(blockpos1.below()) && areAllNeighborsEmpty(level, blockpos1, direction.getOpposite())) {
					flag = true;
					level.setBlock(blockpos1, CactusPlantBlock.getStateForPlacement(level, blockpos1), 2);
					level.setBlock(blockpos1.relative(direction.getOpposite()), CactusPlantBlock.getStateForPlacement(level, blockpos1.relative(direction.getOpposite())), 2);
					growTreeRecursive(level, blockpos1, rand, originalBranchPos, maxHorizontalDistance, iterations + 1);
				}
			}
		}

		if (!flag) {
			level.setBlock(branchPos.above(i), CactusRegistry.CACTUS_FLOWER.get().defaultBlockState().setValue(AGE, Integer.valueOf(5)), 2);
		}
	}

	public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
		BlockPos blockpos = hit.getBlockPos();
		if (!level.isClientSide && projectile.mayInteract(level, blockpos) && projectile.getType().is(EntityTypeTags.IMPACT_PROJECTILES)) {
			level.destroyBlock(blockpos, true, projectile);
		}
	}
}
