package com.mrbysco.cactusmod.blocks.plant;

import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class CactusPlantBlock extends SixWayBlock {
    
	public CactusPlantBlock(AbstractBlock.Properties builder) {
        super(0.3125F, builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false)));
	}

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.makeConnections(context.getLevel(), context.getClickedPos());
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.hurt(DamageSource.CACTUS, 1.0F);
    }

    public BlockState makeConnections(IBlockReader blockReader, BlockPos pos) {
        Block block = blockReader.getBlockState(pos.below()).getBlock();
        Block block1 = blockReader.getBlockState(pos.above()).getBlock();
        Block block2 = blockReader.getBlockState(pos.north()).getBlock();
        Block block3 = blockReader.getBlockState(pos.east()).getBlock();
        Block block4 = blockReader.getBlockState(pos.south()).getBlock();
        Block block5 = blockReader.getBlockState(pos.west()).getBlock();
        return this.defaultBlockState().setValue(DOWN, Boolean.valueOf(block == this || block == CactusRegistry.CACTUS_FLOWER.get() || block.is(Tags.Blocks.SAND))).setValue(UP, Boolean.valueOf(block1 == this || block1 == CactusRegistry.CACTUS_FLOWER.get())).setValue(NORTH, Boolean.valueOf(block2 == this || block2 == CactusRegistry.CACTUS_FLOWER.get())).setValue(EAST, Boolean.valueOf(block3 == this || block3 == CactusRegistry.CACTUS_FLOWER.get())).setValue(SOUTH, Boolean.valueOf(block4 == this || block4 == CactusRegistry.CACTUS_FLOWER.get())).setValue(WEST, Boolean.valueOf(block5 == this || block5 == CactusRegistry.CACTUS_FLOWER.get()));
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (!stateIn.canSurvive(worldIn, currentPos)) {
            worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
            return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            boolean flag = facingState.getBlock() == this || facingState.is(CactusRegistry.CACTUS_FLOWER.get()) || facing == Direction.DOWN && facingState.is(Tags.Blocks.SAND);
            return stateIn.setValue(PROPERTY_BY_DIRECTION.get(facing), Boolean.valueOf(flag));
        }
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.canSurvive(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }

    }

    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.below());
        boolean flag = !worldIn.getBlockState(pos.above()).isAir() && !blockstate.isAir();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockpos = pos.relative(direction);
            Block block = worldIn.getBlockState(blockpos).getBlock();
            if (block == this) {
                if (flag) {
                    return false;
                }

                Block block1 = worldIn.getBlockState(blockpos.below()).getBlock();
                if (block1 == this || block1.is(Tags.Blocks.SAND)) {
                    return true;
                }
            }
        }

        Block block2 = blockstate.getBlock();
        return block2 == this || block2.is(Tags.Blocks.SAND);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
