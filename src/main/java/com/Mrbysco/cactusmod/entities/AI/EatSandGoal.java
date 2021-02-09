package com.mrbysco.cactusmod.entities.AI;

import com.mrbysco.cactusmod.entities.CactusSheepEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.EnumSet;

public class EatSandGoal extends Goal {
    /** The entity owner of this AITask */
    private final CactusSheepEntity sandEaterEntity;
    /** The world the sand eater entity is eating from */
    private final World entityWorld;
    /** Number of ticks since the entity started to eat sand */
    private int eatingSandTimer;

    public EatSandGoal(CactusSheepEntity sandEaterEntityIn) {
        this.sandEaterEntity = sandEaterEntityIn;
        this.entityWorld = sandEaterEntityIn.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    public boolean shouldExecute() {
        if (this.sandEaterEntity.getRNG().nextInt(this.sandEaterEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.sandEaterEntity.getPosition();
            return this.entityWorld.getBlockState(blockpos.down()).isIn(Tags.Blocks.SAND);
        }
    }

    public void startExecuting() {
        this.eatingSandTimer = 40;
        this.entityWorld.setEntityState(this.sandEaterEntity, (byte)10);
        this.sandEaterEntity.getNavigator().clearPath();
    }
    
    public void resetTask()
    {
        this.eatingSandTimer = 0;
    }

    public boolean shouldContinueExecuting()
    {
        return this.eatingSandTimer > 0;
    }
    
    public int getEatingSandTimer()
    {
        return this.eatingSandTimer;
    }

    @Override
    public void tick() {
        this.eatingSandTimer = Math.max(0, this.eatingSandTimer - 1);
        if (this.eatingSandTimer == 4) {
            BlockPos blockpos = this.sandEaterEntity.getPosition();
            if (this.entityWorld.getBlockState(blockpos).isIn(Tags.Blocks.SAND)) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.sandEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.sandEaterEntity.eatSandBonus();
            } else {
                BlockPos blockpos1 = blockpos.down();
                BlockState state = this.entityWorld.getBlockState(blockpos1);
                if (state.isIn(Tags.Blocks.SAND)) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.sandEaterEntity)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(state));
                        this.entityWorld.setBlockState(blockpos1, state, 2);
                    }

                    this.sandEaterEntity.eatSandBonus();
                }
            }
        }
    }
}