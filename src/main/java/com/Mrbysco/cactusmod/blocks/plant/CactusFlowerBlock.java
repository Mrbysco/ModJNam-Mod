package com.mrbysco.cactusmod.blocks.plant;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class CactusFlowerBlock extends Block{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    private final Supplier<CactusPlantBlock> plantBlock;

	public CactusFlowerBlock(Supplier<CactusPlantBlock> plantBlock, AbstractBlock.Properties builder) {
        super(builder);
        this.plantBlock = plantBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
	}

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.canSurvive(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 5;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockPos blockpos = pos.above();
        if (worldIn.isEmptyBlock(blockpos) && blockpos.getY() < 256) {
            int i = state.getValue(AGE);
            if (i < 5 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true)) {
                boolean flag = false;
                boolean flag1 = false;
                BlockState blockstate = worldIn.getBlockState(pos.below());
                Block block = blockstate.getBlock();
                if (block.is(Tags.Blocks.SAND)) {
                    flag = true;
                } else if (block == this.plantBlock) {
                    int j = 1;

                    for(int k = 0; k < 4; ++k) {
                        Block block1 = worldIn.getBlockState(pos.below(j + 1)).getBlock();
                        if (block1 != this.plantBlock) {
                            if (block1.is(Tags.Blocks.SAND)) {
                                flag1 = true;
                            }
                            break;
                        }

                        ++j;
                    }

                    if (j < 2 || j <= random.nextInt(flag1 ? 5 : 4)) {
                        flag = true;
                    }
                } else if (blockstate.isAir(worldIn, pos.below())) {
                    flag = true;
                }

                if (flag && areAllNeighborsEmpty(worldIn, blockpos, (Direction)null) && worldIn.isEmptyBlock(pos.above(2))) {
                    worldIn.setBlock(pos, this.plantBlock.get().makeConnections(worldIn, pos), 2);
                    this.placeGrownFlower(worldIn, blockpos, i);
                } else if (i < 4) {
                    int l = random.nextInt(4);
                    if (flag1) {
                        ++l;
                    }

                    boolean flag2 = false;

                    for(int i1 = 0; i1 < l; ++i1) {
                        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                        BlockPos blockpos1 = pos.relative(direction);
                        if (worldIn.isEmptyBlock(blockpos1) && worldIn.isEmptyBlock(blockpos1.below()) && areAllNeighborsEmpty(worldIn, blockpos1, direction.getOpposite())) {
                            this.placeGrownFlower(worldIn, blockpos1, i + 1);
                            flag2 = true;
                        }
                    }

                    if (flag2) {
                        worldIn.setBlock(pos, this.plantBlock.get().makeConnections(worldIn, pos), 2);
                    } else {
                        this.placeDeadFlower(worldIn, pos);
                    }
                } else {
                    this.placeDeadFlower(worldIn, pos);
                }
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    private void placeGrownFlower(World worldIn, BlockPos pos, int age) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, Integer.valueOf(age)), 2);
        worldIn.levelEvent(1033, pos, 0);
    }

    private void placeDeadFlower(World worldIn, BlockPos pos) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, Integer.valueOf(5)), 2);
        worldIn.levelEvent(1034, pos, 0);
    }

    private static boolean areAllNeighborsEmpty(IWorldReader worldIn, BlockPos pos, @Nullable Direction excludingSide) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (direction != excludingSide && !worldIn.isEmptyBlock(pos.relative(direction))) {
                return false;
            }
        }
        return true;
    }

    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing != Direction.UP && !stateIn.canSurvive(worldIn, currentPos)) {
            worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.below());
        if (blockstate.getBlock() != this.plantBlock && !blockstate.is(Tags.Blocks.SAND)) {
            if (!blockstate.isAir(worldIn, pos.below())) {
                return false;
            } else {
                boolean flag = false;

                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockState blockstate1 = worldIn.getBlockState(pos.relative(direction));
                    if (blockstate1.is(this.plantBlock.get())) {
                        if (flag) {
                            return false;
                        }

                        flag = true;
                    } else if (!blockstate1.isAir(worldIn, pos.relative(direction))) {
                        return false;
                    }
                }

                return flag;
            }
        } else {
            return true;
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    public static void generatePlant(IWorld worldIn, BlockPos pos, Random rand, int maxHorizontalDistance) {
        worldIn.setBlock(pos, ((CactusPlantBlock)CactusRegistry.CACTUS_PLANT.get()).makeConnections(worldIn, pos), 2);
        growTreeRecursive(worldIn, pos, rand, pos, maxHorizontalDistance, 0);
    }

    private static void growTreeRecursive(IWorld worldIn, BlockPos branchPos, Random rand, BlockPos originalBranchPos, int maxHorizontalDistance, int iterations) {
        CactusPlantBlock CactusPlantBlock = (CactusPlantBlock)CactusRegistry.CACTUS_PLANT.get();
        int i = rand.nextInt(4) + 1;
        if (iterations == 0) {
            ++i;
        }

        for(int j = 0; j < i; ++j) {
            BlockPos blockpos = branchPos.above(j + 1);
            if (!areAllNeighborsEmpty(worldIn, blockpos, (Direction)null)) {
                return;
            }

            worldIn.setBlock(blockpos, CactusPlantBlock.makeConnections(worldIn, blockpos), 2);
            worldIn.setBlock(blockpos.below(), CactusPlantBlock.makeConnections(worldIn, blockpos.below()), 2);
        }

        boolean flag = false;
        if (iterations < 4) {
            int l = rand.nextInt(4);
            if (iterations == 0) {
                ++l;
            }

            for(int k = 0; k < l; ++k) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
                BlockPos blockpos1 = branchPos.above(i).relative(direction);
                if (Math.abs(blockpos1.getX() - originalBranchPos.getX()) < maxHorizontalDistance && Math.abs(blockpos1.getZ() - originalBranchPos.getZ()) < maxHorizontalDistance && worldIn.isEmptyBlock(blockpos1) && worldIn.isEmptyBlock(blockpos1.below()) && areAllNeighborsEmpty(worldIn, blockpos1, direction.getOpposite())) {
                    flag = true;
                    worldIn.setBlock(blockpos1, CactusPlantBlock.makeConnections(worldIn, blockpos1), 2);
                    worldIn.setBlock(blockpos1.relative(direction.getOpposite()), CactusPlantBlock.makeConnections(worldIn, blockpos1.relative(direction.getOpposite())), 2);
                    growTreeRecursive(worldIn, blockpos1, rand, originalBranchPos, maxHorizontalDistance, iterations + 1);
                }
            }
        }

        if (!flag) {
            worldIn.setBlock(branchPos.above(i), CactusRegistry.CACTUS_FLOWER.get().defaultBlockState().setValue(AGE, Integer.valueOf(5)), 2);
        }
    }

    public void onProjectileHit(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (projectile.getType().is(EntityTypeTags.IMPACT_PROJECTILES)) {
            BlockPos blockpos = hit.getBlockPos();
            worldIn.destroyBlock(blockpos, true, projectile);
        }
    }
}
