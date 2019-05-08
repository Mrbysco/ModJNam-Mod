package com.mrbysco.cactusmod.entities.AI;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityEatSand extends EntityAIBase
{
    /** The entity owner of this AITask */
    private final EntityLiving sandEaterEntity;
    /** The world the sand eater entity is eating from */
    private final World entityWorld;
    /** Number of ticks since the entity started to eat sand */
    int eatingSandTimer;

    public EntityEatSand(EntityLiving sandEaterEntityIn)
    {
        this.sandEaterEntity = sandEaterEntityIn;
        this.entityWorld = sandEaterEntityIn.world;
        this.setMutexBits(7);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.sandEaterEntity.getRNG().nextInt(this.sandEaterEntity.isChild() ? 50 : 1000) != 0)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = new BlockPos(this.sandEaterEntity.posX, this.sandEaterEntity.posY, this.sandEaterEntity.posZ);
            return this.entityWorld.getBlockState(blockpos).getBlock() instanceof BlockSand;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.eatingSandTimer = 40;
        this.entityWorld.setEntityState(this.sandEaterEntity, (byte)10);
        this.sandEaterEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.eatingSandTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.eatingSandTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat sand
     */
    public int getEatingSandTimer()
    {
        return this.eatingSandTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        this.eatingSandTimer = Math.max(0, this.eatingSandTimer - 1);

        if (this.eatingSandTimer == 4)
        {
            BlockPos blockpos = new BlockPos(this.sandEaterEntity.posX, this.sandEaterEntity.posY, this.sandEaterEntity.posZ);

            BlockPos blockpos1 = blockpos;

            if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.AIR)
            {
            	Block oldBlock = this.entityWorld.getBlockState(blockpos1).getBlock();
            	
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.sandEaterEntity))
                {
                    this.entityWorld.playEvent(2001, blockpos1, Block.getIdFromBlock(oldBlock));
                    this.entityWorld.setBlockState(blockpos1, oldBlock.getDefaultState(), 2);
                }

                this.sandEaterEntity.eatGrassBonus();
            }
        }
    }
}