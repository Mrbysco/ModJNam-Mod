package com.mrbysco.cactusmod.blocks;

import com.mrbysco.cactusmod.blocks.decorative.BlockRotatable;
import com.mrbysco.cactusmod.entities.CactusGolem;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CarvedCactusBlock extends BlockRotatable {
    protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    @Nullable
    private static BlockPattern cactusGolemPattern;
    @Nullable
    private static BlockPattern cactusSnowmanPattern;

    private static final java.util.function.Predicate<BlockState> IS_CARVED_CACTUS = (state) -> {
        return state != null && (state.getBlock() == CactusRegistry.CARVED_CACTUS.get() || state.getBlock() == CactusRegistry.JACKO_CACTUS.get());
    };

    
	public CarvedCactusBlock(AbstractBlock.Properties builder) {
		super(builder);
	}

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.hurt(DamageSource.CACTUS, 1.0F);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public void onPlace(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) {
            this.trySpawnGolem(worldIn, pos);
        }
    }

    private void trySpawnGolem(World world, BlockPos pos) {
        BlockPattern.PatternHelper blockpattern$patternhelper = this.getCactusSnowmanPattern().find(world, pos);
        if (blockpattern$patternhelper != null) {
            for(int i = 0; i < this.getCactusSnowmanPattern().getHeight(); ++i) {
                CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.getBlock(0, i, 0);
                world.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
                world.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
            }

            CactusSnowGolemEntity cactusSnowman = null;//EntityType.SNOW_GOLEM.create(world);
            BlockPos blockpos1 = blockpattern$patternhelper.getBlock(0, 2, 0).getPos();
            cactusSnowman.moveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            world.addFreshEntity(cactusSnowman);

            for(ServerPlayerEntity serverplayerentity : world.getEntitiesOfClass(ServerPlayerEntity.class, cactusSnowman.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, cactusSnowman);
            }

            for(int l = 0; l < this.getCactusSnowmanPattern().getHeight(); ++l) {
                CachedBlockInfo cachedblockinfo3 = blockpattern$patternhelper.getBlock(0, l, 0);
                world.blockUpdated(cachedblockinfo3.getPos(), Blocks.AIR);
            }
        } else {
            blockpattern$patternhelper = this.getCactusGolemPattern().find(world, pos);
            if (blockpattern$patternhelper != null) {
                for(int j = 0; j < this.getCactusGolemPattern().getWidth(); ++j) {
                    for(int k = 0; k < this.getCactusGolemPattern().getHeight(); ++k) {
                        CachedBlockInfo cachedblockinfo2 = blockpattern$patternhelper.getBlock(j, k, 0);
                        world.setBlock(cachedblockinfo2.getPos(), Blocks.AIR.defaultBlockState(), 2);
                        world.levelEvent(2001, cachedblockinfo2.getPos(), Block.getId(cachedblockinfo2.getState()));
                    }
                }

                BlockPos blockpos = blockpattern$patternhelper.getBlock(1, 2, 0).getPos();
                CactusGolem cactusGolem = (CactusGolem)EntityType.IRON_GOLEM.create(world);
                cactusGolem.setPlayerCreated(true);
                cactusGolem.moveTo((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.05D, (double)blockpos.getZ() + 0.5D, 0.0F, 0.0F);
                world.addFreshEntity(cactusGolem);

                for(ServerPlayerEntity serverplayerentity1 : world.getEntitiesOfClass(ServerPlayerEntity.class, cactusGolem.getBoundingBox().inflate(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity1, cactusGolem);
                }

                for(int i1 = 0; i1 < this.getCactusGolemPattern().getWidth(); ++i1) {
                    for(int j1 = 0; j1 < this.getCactusGolemPattern().getHeight(); ++j1) {
                        CachedBlockInfo cachedblockinfo1 = blockpattern$patternhelper.getBlock(i1, j1, 0);
                        world.blockUpdated(cachedblockinfo1.getPos(), Blocks.AIR);
                    }
                }
            }
        }
    }
    
    protected BlockPattern getCactusSnowmanPattern() {
        if (this.cactusSnowmanPattern == null) {
        	cactusSnowmanPattern = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockInfo.hasState(IS_CARVED_CACTUS)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.CACTUS))).build();
        }

        return this.cactusSnowmanPattern;
    }
    
    protected BlockPattern getCactusGolemPattern() {
        if (this.cactusGolemPattern == null) {
    		cactusGolemPattern = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockInfo.hasState(IS_CARVED_CACTUS)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(CactusRegistry.PRICKLY_IRON.get()))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return this.cactusGolemPattern;
    }
}
