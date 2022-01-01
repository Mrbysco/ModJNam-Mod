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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class CarvedCactusBlock extends BlockRotatable {
    protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    @Nullable
    private BlockPattern cactusGolemBase;
    @Nullable
    private BlockPattern cactusGolemFull;
    @Nullable
    private BlockPattern cactusIronGolemBase;
    @Nullable
    private BlockPattern cactusIronGolemFull;

    private static final Predicate<BlockState> CARVED_PREDICATE = (state) -> {
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
        BlockPattern.PatternHelper patternMatch = this.getOrCreateCactusGolemFull().find(world, pos);
        if (patternMatch != null) {
            for(int i = 0; i < this.getOrCreateCactusGolemFull().getHeight(); ++i) {
                CachedBlockInfo blockInWorld = patternMatch.getBlock(0, i, 0);
                world.setBlock(blockInWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                world.levelEvent(2001, blockInWorld.getPos(), Block.getId(blockInWorld.getState()));
            }

            CactusSnowGolemEntity cactusSnowGolem = CactusRegistry.CACTUS_SNOW_GOLEM.get().create(world);
            BlockPos blockpos1 = patternMatch.getBlock(0, 2, 0).getPos();
            cactusSnowGolem.moveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            world.addFreshEntity(cactusSnowGolem);

            for(ServerPlayerEntity serverPlayer : world.getEntitiesOfClass(ServerPlayerEntity.class, cactusSnowGolem.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, cactusSnowGolem);
            }

            for(int l = 0; l < this.getOrCreateCactusGolemFull().getHeight(); ++l) {
                CachedBlockInfo matchBlock = patternMatch.getBlock(0, l, 0);
                world.blockUpdated(matchBlock.getPos(), Blocks.AIR);
            }
        } else {
            patternMatch = this.getOrCreateCactusIronGolemFull().find(world, pos);
            if (patternMatch != null) {
                for(int j = 0; j < this.getOrCreateCactusIronGolemFull().getWidth(); ++j) {
                    for(int k = 0; k < this.getOrCreateCactusIronGolemFull().getHeight(); ++k) {
                        CachedBlockInfo cachedblockinfo2 = patternMatch.getBlock(j, k, 0);
                        world.setBlock(cachedblockinfo2.getPos(), Blocks.AIR.defaultBlockState(), 2);
                        world.levelEvent(2001, cachedblockinfo2.getPos(), Block.getId(cachedblockinfo2.getState()));
                    }
                }

                BlockPos blockpos = patternMatch.getBlock(1, 2, 0).getPos();
                CactusGolem cactusGolem = CactusRegistry.CACTUS_GOLEM.get().create(world);
                cactusGolem.setPlayerCreated(true);
                cactusGolem.moveTo((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.05D, (double)blockpos.getZ() + 0.5D, 0.0F, 0.0F);
                world.addFreshEntity(cactusGolem);

                for(ServerPlayerEntity serverPlayer : world.getEntitiesOfClass(ServerPlayerEntity.class, cactusGolem.getBoundingBox().inflate(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, cactusGolem);
                }

                for(int i1 = 0; i1 < this.getOrCreateCactusIronGolemFull().getWidth(); ++i1) {
                    for(int j1 = 0; j1 < this.getOrCreateCactusIronGolemFull().getHeight(); ++j1) {
                        CachedBlockInfo matchBlock = patternMatch.getBlock(i1, j1, 0);
                        world.blockUpdated(matchBlock.getPos(), Blocks.AIR);
                    }
                }
            }
        }
    }

    private BlockPattern getOrCreateCactusGolemBase() {
        if (this.cactusGolemBase == null) {
            this.cactusGolemBase = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
        }

        return this.cactusGolemBase;
    }

    private BlockPattern getOrCreateCactusGolemFull() {
        if (this.cactusGolemFull == null) {
            this.cactusGolemFull = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockInfo.hasState(CARVED_PREDICATE)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SNOW_BLOCK))).build();
        }

        return this.cactusGolemFull;
    }

    private BlockPattern getOrCreateCactusIronGolemBase() {
        if (this.cactusIronGolemBase == null) {
            this.cactusIronGolemBase = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(CactusRegistry.PRICKLY_IRON.get()))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return this.cactusIronGolemBase;
    }

    private BlockPattern getOrCreateCactusIronGolemFull() {
        if (this.cactusIronGolemFull == null) {
            this.cactusIronGolemFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', CachedBlockInfo.hasState(CARVED_PREDICATE)).where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(CactusRegistry.PRICKLY_IRON.get()))).where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR))).build();
        }

        return this.cactusIronGolemFull;
    }
}
