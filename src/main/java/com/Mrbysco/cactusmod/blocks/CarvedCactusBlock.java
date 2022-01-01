package com.mrbysco.cactusmod.blocks;

import com.mrbysco.cactusmod.blocks.decorative.BlockRotatable;
import com.mrbysco.cactusmod.entities.CactusGolem;
import com.mrbysco.cactusmod.entities.CactusSnowGolemEntity;
import com.mrbysco.cactusmod.init.CactusRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
        return state != null && (state.is(CactusRegistry.CARVED_CACTUS.get()) || state.is(CactusRegistry.JACKO_CACTUS.get()));
    };
    
	public CarvedCactusBlock(BlockBehaviour.Properties builder) {
		super(builder);
	}

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        entityIn.hurt(DamageSource.CACTUS, 1.0F);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    @Override
    public void onPlace(BlockState state, Level worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!oldState.is(state.getBlock())) {
            this.trySpawnGolem(worldIn, pos);
        }
    }

    public boolean canSpawnGolem(LevelReader reader, BlockPos pos) {
        return this.getOrCreateCactusGolemBase().find(reader, pos) != null || this.getOrCreateCactusIronGolemBase().find(reader, pos) != null;
    }

    private void trySpawnGolem(Level world, BlockPos pos) {
        BlockPattern.BlockPatternMatch patternMatch = this.getOrCreateCactusGolemFull().find(world, pos);
        if (patternMatch != null) {
            System.out.println(patternMatch);
            for(int i = 0; i < this.getOrCreateCactusGolemFull().getHeight(); ++i) {
                BlockInWorld blockInWorld = patternMatch.getBlock(0, i, 0);
                world.setBlock(blockInWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                world.levelEvent(2001, blockInWorld.getPos(), Block.getId(blockInWorld.getState()));
            }

            CactusSnowGolemEntity cactusSnowGolem = CactusRegistry.CACTUS_SNOW_GOLEM.get().create(world);
            BlockPos blockpos1 = patternMatch.getBlock(0, 2, 0).getPos();
            cactusSnowGolem.moveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            world.addFreshEntity(cactusSnowGolem);

            for(ServerPlayer serverPlayer : world.getEntitiesOfClass(ServerPlayer.class, cactusSnowGolem.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, cactusSnowGolem);
            }

            for(int l = 0; l < this.getOrCreateCactusGolemFull().getHeight(); ++l) {
                BlockInWorld matchBlock = patternMatch.getBlock(0, l, 0);
                world.blockUpdated(matchBlock.getPos(), Blocks.AIR);
            }
        } else {
            patternMatch = this.getOrCreateCactusIronGolemFull().find(world, pos);
            if (patternMatch != null) {
                for(int j = 0; j < this.getOrCreateCactusIronGolemFull().getWidth(); ++j) {
                    for(int k = 0; k < this.getOrCreateCactusIronGolemFull().getHeight(); ++k) {
                        BlockInWorld cachedblockinfo2 = patternMatch.getBlock(j, k, 0);
                        world.setBlock(cachedblockinfo2.getPos(), Blocks.AIR.defaultBlockState(), 2);
                        world.levelEvent(2001, cachedblockinfo2.getPos(), Block.getId(cachedblockinfo2.getState()));
                    }
                }

                BlockPos blockpos = patternMatch.getBlock(1, 2, 0).getPos();
                CactusGolem cactusGolem = CactusRegistry.CACTUS_GOLEM.get().create(world);
                cactusGolem.setPlayerCreated(true);
                cactusGolem.moveTo((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.05D, (double)blockpos.getZ() + 0.5D, 0.0F, 0.0F);
                world.addFreshEntity(cactusGolem);

                for(ServerPlayer serverPlayer : world.getEntitiesOfClass(ServerPlayer.class, cactusGolem.getBoundingBox().inflate(5.0D))) {
                    CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, cactusGolem);
                }

                for(int i1 = 0; i1 < this.getOrCreateCactusIronGolemFull().getWidth(); ++i1) {
                    for(int j1 = 0; j1 < this.getOrCreateCactusIronGolemFull().getHeight(); ++j1) {
                        BlockInWorld matchBlock = patternMatch.getBlock(i1, j1, 0);
                        world.blockUpdated(matchBlock.getPos(), Blocks.AIR);
                    }
                }
            }
        }
    }

    private BlockPattern getOrCreateCactusGolemBase() {
        if (this.cactusGolemBase == null) {
            this.cactusGolemBase = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
        }

        return this.cactusGolemBase;
    }

    private BlockPattern getOrCreateCactusGolemFull() {
        if (this.cactusGolemFull == null) {
            this.cactusGolemFull = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', BlockInWorld.hasState(CARVED_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
        }

        return this.cactusGolemFull;
    }

    private BlockPattern getOrCreateCactusIronGolemBase() {
        if (this.cactusIronGolemBase == null) {
            this.cactusIronGolemBase = BlockPatternBuilder.start().aisle("~ ~", "###", "~#~").where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(CactusRegistry.PRICKLY_IRON.get()))).where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR))).build();
        }

        return this.cactusIronGolemBase;
    }

    private BlockPattern getOrCreateCactusIronGolemFull() {
        if (this.cactusIronGolemFull == null) {
            this.cactusIronGolemFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', BlockInWorld.hasState(CARVED_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(CactusRegistry.PRICKLY_IRON.get()))).where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR))).build();
        }

        return this.cactusIronGolemFull;
    }
}
